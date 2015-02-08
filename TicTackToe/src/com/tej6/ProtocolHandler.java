package com.tej6;

import java.io.IOException;

import com.tej6.IConnectionModeData.ConnectionMode;
import com.tej6.IController.Player;
import com.tej6.IGamePlayData.Sign;

public class ProtocolHandler implements IProtocol {

	private IController controller = null;
	public ProtocolHandler(IController controller) {
		this.controller = controller;
	}

	public void startPlay() throws IOException {
		if(controller.getInfoData().getConnectionMode().getMode() == ConnectionMode.create) {
			Player player = controller.whoPlaysfirst();
			Sign sign = controller.whichSymbol();
			controller.symbolIs(sign);
			controller.firstPlayerIs(player);
			if(player == Player.Me)
				player = Player.You;
			else
				player = Player.Me;
			if(sign == Sign.Circle)
				sign = Sign.Cross;
			else
				sign = Sign.Circle;
			String initString = ":L";
			initString = initString.concat(GamePlaySessionData.stringFromPlayer(player));
			initString = initString.concat(GamePlaySessionData.stringFromSign(sign));
			System.out.println("Initialization message from server is :"+initString);
			controller.getConnector().sendMessage(initString);
		}
		else if(controller.getInfoData().getConnectionMode().getMode() == ConnectionMode.join) {
			String initString = controller.getConnector().receiveMessage();
			System.out.println("Initialization message to client is :"+initString);
			Player player = GamePlaySessionData.playerFromString(initString.substring(2,3));
			Sign sign = GamePlaySessionData.signFromString(initString.substring(3,4));
			controller.symbolIs(sign);
			controller.firstPlayerIs(player);
		}
	}

	@Override
	public void sendPosition(IGamePlayData playdata,Player player) throws IOException {
		if(player == Player.Me) {
			String postring = ":P";
			postring = postring.concat(playdata.getSigns());
			System.out.println("data message from server is :"+postring);
			controller.getConnector().sendMessage(postring);
		}
		else {
			String postring = controller.getConnector().receiveMessage();
			System.out.println("data message to client is :"+postring);
			if(!postring.equalsIgnoreCase(":R")) {
				postring = postring.substring(2);
				playdata.setSigns(postring);
			}
			else
				controller.setRefresh();
		}
	}

	@Override
	public void refresh() throws IOException {
		String restring = ":R";
		controller.getConnector().sendMessage(restring);
	}

	@Override
	public void exit() throws IOException {
		// TODO Auto-generated method stub

	}
}
