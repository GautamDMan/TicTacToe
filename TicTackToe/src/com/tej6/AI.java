package com.tej6;

import java.io.IOException;
import java.net.SocketTimeoutException;

import com.tej6.IController.Player;
import com.tej6.IGamePlayData.Sign;
import com.tej6.ISituation.Situation;

public class AI extends GameController implements Runnable {
	private Thread gameThread = null;
	private boolean keeprunning = true;
	
	public AI(IConnectionInfoData infodata) {
		this.infodata = infodata;
	}
	
	public void start() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void play() {
		try {
			establishConnection();
			IProtocol protocol = new ProtocolHandler(this);
			keepRunning = true;
			while(keepRunning) {
				sessiondata = new GamePlaySessionData();
				protocol.startPlay();
				hasAnybodyWon = false;
				while(!hasAnybodyWon) {
					if(firstPlayer == Player.Me) 
						createPosition();
					if(isRefresh) 
						protocol.refresh();
					else
						protocol.sendPosition(sessiondata,firstPlayer);
					if(isRefresh)
						break;
					isRefresh = false;
					Situation what = situation.getMode(sessiondata, mySign);
					situation.incrementScore(what);
					if(what != Situation.gameon) {
						hasAnybodyWon = true;
					}
					else {
						if(firstPlayer == Player.Me)
							firstPlayer = Player.You;
						else
							firstPlayer = Player.Me;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		play();
	}

	public void createPosition() {
		int count = 0;
		for( ; count < 9; count++) {
			if(sessiondata.getSign(count) != Sign.Empty)
				break;
		}
		if(count == 9) {
			sessiondata.setSign(4, mySign);
			return;
		}
		Sign yourSign = null;
		if(mySign == Sign.Circle)
			yourSign = Sign.Cross;
		else
			yourSign = Sign.Circle;
		if((sessiondata.getSign(0) == mySign)&&(sessiondata.getSign(1) == mySign)&&(sessiondata.getSign(2) == Sign.Empty))
			sessiondata.setSign(2, mySign);
		else if((sessiondata.getSign(0) == mySign)&&(sessiondata.getSign(2) == mySign)&&(sessiondata.getSign(1) == Sign.Empty))
			sessiondata.setSign(1, mySign);
		else if((sessiondata.getSign(1) == mySign)&&(sessiondata.getSign(2) == mySign)&&(sessiondata.getSign(0) == Sign.Empty))
			sessiondata.setSign(0, mySign);
		else if((sessiondata.getSign(3) == mySign)&&(sessiondata.getSign(4) == mySign)&&(sessiondata.getSign(5) == Sign.Empty))
			sessiondata.setSign(5, mySign);
		else if((sessiondata.getSign(3) == mySign)&&(sessiondata.getSign(5) == mySign)&&(sessiondata.getSign(4) == Sign.Empty))
			sessiondata.setSign(4, mySign);
		else if((sessiondata.getSign(4) == mySign)&&(sessiondata.getSign(5) == mySign)&&(sessiondata.getSign(3) == Sign.Empty))
			sessiondata.setSign(3, mySign);
		else if((sessiondata.getSign(6) == mySign)&&(sessiondata.getSign(7) == mySign)&&(sessiondata.getSign(8) == Sign.Empty))
			sessiondata.setSign(8, mySign);
		else if((sessiondata.getSign(6) == mySign)&&(sessiondata.getSign(8) == mySign)&&(sessiondata.getSign(7) == Sign.Empty))
			sessiondata.setSign(7, mySign);
		else if((sessiondata.getSign(7) == mySign)&&(sessiondata.getSign(8) == mySign)&&(sessiondata.getSign(6) == Sign.Empty))
			sessiondata.setSign(6, mySign);
		else if((sessiondata.getSign(0) == mySign)&&(sessiondata.getSign(3) == mySign)&&(sessiondata.getSign(6) == Sign.Empty))
			sessiondata.setSign(6, mySign);
		else if((sessiondata.getSign(6) == mySign)&&(sessiondata.getSign(3) == mySign)&&(sessiondata.getSign(0) == Sign.Empty))
			sessiondata.setSign(0, mySign);
		else if((sessiondata.getSign(0) == mySign)&&(sessiondata.getSign(6) == mySign)&&(sessiondata.getSign(3) == Sign.Empty))
			sessiondata.setSign(3, mySign);
		else if((sessiondata.getSign(1) == mySign)&&(sessiondata.getSign(4) == mySign)&&(sessiondata.getSign(7) == Sign.Empty))
			sessiondata.setSign(7, mySign);
		else if((sessiondata.getSign(1) == mySign)&&(sessiondata.getSign(7) == mySign)&&(sessiondata.getSign(4) == Sign.Empty))
			sessiondata.setSign(4, mySign);
		else if((sessiondata.getSign(4) == mySign)&&(sessiondata.getSign(7) == mySign)&&(sessiondata.getSign(1) == Sign.Empty))
			sessiondata.setSign(1, mySign);
		else if((sessiondata.getSign(2) == mySign)&&(sessiondata.getSign(5) == mySign)&&(sessiondata.getSign(8) == Sign.Empty))
			sessiondata.setSign(8, mySign);
		else if((sessiondata.getSign(2) == mySign)&&(sessiondata.getSign(8) == mySign)&&(sessiondata.getSign(5) == Sign.Empty))
			sessiondata.setSign(5, mySign);
		else if((sessiondata.getSign(5) == mySign)&&(sessiondata.getSign(8) == mySign)&&(sessiondata.getSign(2) == Sign.Empty))
			sessiondata.setSign(2, mySign);
		else if((sessiondata.getSign(0) == mySign)&&(sessiondata.getSign(4) == mySign)&&(sessiondata.getSign(8) == Sign.Empty))
			sessiondata.setSign(8, mySign);
		else if((sessiondata.getSign(0) == mySign)&&(sessiondata.getSign(8) == mySign)&&(sessiondata.getSign(4) == Sign.Empty))
			sessiondata.setSign(4, mySign);
		else if((sessiondata.getSign(4) == mySign)&&(sessiondata.getSign(8) == mySign)&&(sessiondata.getSign(0) == Sign.Empty))
			sessiondata.setSign(0, mySign);
		else if((sessiondata.getSign(2) == mySign)&&(sessiondata.getSign(4) == mySign)&&(sessiondata.getSign(6) == Sign.Empty))
			sessiondata.setSign(6, mySign);
		else if((sessiondata.getSign(2) == mySign)&&(sessiondata.getSign(6) == mySign)&&(sessiondata.getSign(4) == Sign.Empty))
			sessiondata.setSign(4, mySign);
		else if((sessiondata.getSign(4) == mySign)&&(sessiondata.getSign(6) == mySign)&&(sessiondata.getSign(2) == Sign.Empty))
			sessiondata.setSign(2, mySign);
		else if((sessiondata.getSign(0) == yourSign)&&(sessiondata.getSign(1) == yourSign)&&(sessiondata.getSign(2) == Sign.Empty))
			sessiondata.setSign(2, mySign);
		else if((sessiondata.getSign(0) == yourSign)&&(sessiondata.getSign(2) == yourSign)&&(sessiondata.getSign(1) == Sign.Empty))
			sessiondata.setSign(1, mySign);
		else if((sessiondata.getSign(1) == yourSign)&&(sessiondata.getSign(2) == yourSign)&&(sessiondata.getSign(0) == Sign.Empty))
			sessiondata.setSign(0, mySign);
		else if((sessiondata.getSign(3) == yourSign)&&(sessiondata.getSign(4) == yourSign)&&(sessiondata.getSign(5) == Sign.Empty))
			sessiondata.setSign(5, mySign);
		else if((sessiondata.getSign(3) == yourSign)&&(sessiondata.getSign(5) == yourSign)&&(sessiondata.getSign(4) == Sign.Empty))
			sessiondata.setSign(4, mySign);
		else if((sessiondata.getSign(4) == yourSign)&&(sessiondata.getSign(5) == yourSign)&&(sessiondata.getSign(3) == Sign.Empty))
			sessiondata.setSign(3, mySign);
		else if((sessiondata.getSign(6) == yourSign)&&(sessiondata.getSign(7) == yourSign)&&(sessiondata.getSign(8) == Sign.Empty))
			sessiondata.setSign(8, mySign);
		else if((sessiondata.getSign(6) == yourSign)&&(sessiondata.getSign(8) == yourSign)&&(sessiondata.getSign(7) == Sign.Empty))
			sessiondata.setSign(7, mySign);
		else if((sessiondata.getSign(7) == yourSign)&&(sessiondata.getSign(8) == yourSign)&&(sessiondata.getSign(6) == Sign.Empty))
			sessiondata.setSign(6, mySign);
		else if((sessiondata.getSign(0) == yourSign)&&(sessiondata.getSign(3) == yourSign)&&(sessiondata.getSign(6) == Sign.Empty))
			sessiondata.setSign(6, mySign);
		else if((sessiondata.getSign(6) == yourSign)&&(sessiondata.getSign(3) == yourSign)&&(sessiondata.getSign(0) == Sign.Empty))
			sessiondata.setSign(0, mySign);
		else if((sessiondata.getSign(0) == yourSign)&&(sessiondata.getSign(6) == yourSign)&&(sessiondata.getSign(3) == Sign.Empty))
			sessiondata.setSign(3, mySign);
		else if((sessiondata.getSign(1) == yourSign)&&(sessiondata.getSign(4) == yourSign)&&(sessiondata.getSign(7) == Sign.Empty))
			sessiondata.setSign(7, mySign);
		else if((sessiondata.getSign(1) == yourSign)&&(sessiondata.getSign(7) == yourSign)&&(sessiondata.getSign(4) == Sign.Empty))
			sessiondata.setSign(4, mySign);
		else if((sessiondata.getSign(4) == yourSign)&&(sessiondata.getSign(7) == yourSign)&&(sessiondata.getSign(1) == Sign.Empty))
			sessiondata.setSign(1, mySign);
		else if((sessiondata.getSign(2) == yourSign)&&(sessiondata.getSign(5) == yourSign)&&(sessiondata.getSign(8) == Sign.Empty))
			sessiondata.setSign(8, mySign);
		else if((sessiondata.getSign(2) == yourSign)&&(sessiondata.getSign(8) == yourSign)&&(sessiondata.getSign(5) == Sign.Empty))
			sessiondata.setSign(5, mySign);
		else if((sessiondata.getSign(5) == yourSign)&&(sessiondata.getSign(8) == yourSign)&&(sessiondata.getSign(2) == Sign.Empty))
			sessiondata.setSign(2, mySign);
		else if((sessiondata.getSign(0) == yourSign)&&(sessiondata.getSign(4) == yourSign)&&(sessiondata.getSign(8) == Sign.Empty))
			sessiondata.setSign(8, mySign);
		else if((sessiondata.getSign(0) == yourSign)&&(sessiondata.getSign(8) == yourSign)&&(sessiondata.getSign(4) == Sign.Empty))
			sessiondata.setSign(4, mySign);
		else if((sessiondata.getSign(4) == yourSign)&&(sessiondata.getSign(8) == yourSign)&&(sessiondata.getSign(0) == Sign.Empty))
			sessiondata.setSign(0, mySign);
		else if((sessiondata.getSign(2) == yourSign)&&(sessiondata.getSign(4) == yourSign)&&(sessiondata.getSign(6) == Sign.Empty))
			sessiondata.setSign(6, mySign);
		else if((sessiondata.getSign(2) == yourSign)&&(sessiondata.getSign(6) == yourSign)&&(sessiondata.getSign(4) == Sign.Empty))
			sessiondata.setSign(4, mySign);
		else if((sessiondata.getSign(4) == yourSign)&&(sessiondata.getSign(6) == yourSign)&&(sessiondata.getSign(2) == Sign.Empty))
			sessiondata.setSign(2, mySign);
		else {
			count = 0;
			for( ; count < 9; count++) 
				if(sessiondata.getSign(count) == Sign.Empty) {
					sessiondata.setSign(count, mySign);
					break;
				}
		}
	}
}
