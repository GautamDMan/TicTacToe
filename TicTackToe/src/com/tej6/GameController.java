package com.tej6;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Random;

import com.tej6.IConnectionModeData.ConnectionMode;
import com.tej6.IGamePlayData.Sign;
import com.tej6.IPlayModeData.PlayMode;
import com.tej6.ISituation.Situation;

public class GameController implements IController, IManager {

	protected IConnectionInfoData infodata = null;
	protected IPlayModeView playmodeview = null;
	protected IConnectionView view = null;
	protected GamePlaySessionView playview = null;
	protected ISituation situation = new GameSituation();
	protected IGamePlayData sessiondata = null;
	static final int AIPORT = 4999;
    Random randomGenerator = new Random();
    protected IConnector connector = null;
    protected boolean keepRunning = true;
    protected Player firstPlayer = null;
    protected Sign mySign = null;
    protected boolean hasAnybodyWon = false;
    protected Object state = new Object();
    protected boolean isRefresh = false; 
    protected AI aiApplication = null;
    
	public void launchGame() {
		playmodeview = new GamePlayModeView(); 
		do {	
			System.out.println("I am here..");
			IPlayModeData playmodedata = playmodeview.show(true);
			infodata = selectConnectionMode(playmodedata); 
			System.out.println("I am here now..");
		} while(infodata == null);
		System.out.println("ip ="+infodata.getIpAddress()+" port ="+infodata.getPort());
		play();
	}

	@Override
	public IConnectionInfoData selectConnectionMode(IPlayModeData playmodedata) {
		switch(playmodedata.getPlayMode()) {
		case noGame:
			System.out.println("I am here at exit.");
			System.exit(0);
		case singlePlayer:
			System.out.println("I am here single");
			playmodeview.show(false);
			infodata = new GameConnectionInfoData();
			infodata.setIpAddress(GamePlayConnectionView.getIp());
			infodata.setPort(AIPORT);
			infodata.setConnectionMode(new GameConnectionModeData(IConnectionModeData.ConnectionMode.AI));
			return infodata;
		case multiPlayer:
			if(view == null)
				view = new GamePlayConnectionView();
			System.out.println("I am here multi.1");
			playmodeview.show(false);
			return view.show();
		default:
			break;		
		}
		return null;
	}

	@Override
	public void play() {
		playview = new GamePlaySessionView(this);
		playview.show();
		try {
			establishConnection();
			IProtocol protocol = new ProtocolHandler(this);
			keepRunning = true;
			while(keepRunning) {
				sessiondata = new GamePlaySessionData();
				protocol.startPlay();
				playview.buttonEnabler(sessiondata, firstPlayer,true);
				playview.setCurrentSign(mySign);
				hasAnybodyWon = false;
				while(!hasAnybodyWon) {
					synchronized(state) {
						try {
							if(firstPlayer == Player.Me) 
								state.wait();
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
								playview.setScore(situation.getScore());
								playview.buttonEnabler(sessiondata, firstPlayer,false);
								hasAnybodyWon = true;
							}
							else {
								if(firstPlayer == Player.Me)
									firstPlayer = Player.You;
								else
									firstPlayer = Player.Me;
								playview.buttonEnabler(sessiondata, firstPlayer,true);
							}
								
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Player whoPlaysfirst() {
		int who = randomGenerator.nextInt(2);
		if(who == 0)
			return Player.Me;
		return Player.You;
	}

	@Override
	public Sign whichSymbol() {
		int which = randomGenerator.nextInt(2); 
		if(which == 0)
			return Sign.Circle;
		return Sign.Cross;
	}

	@Override
	public void onEveryClick() {
		synchronized(state) {	
			state.notify();
		}
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRefresh() {
		synchronized(state) {	
			state.notify();
		}
		isRefresh = true;
	}

	@Override
	public void establishConnection() throws IOException {
		if(infodata.getConnectionMode().getMode() == ConnectionMode.AI) {
			IConnectionInfoData aiInfoData = new GameConnectionInfoData(infodata);
			aiInfoData.setConnectionMode(new GameConnectionModeData(ConnectionMode.create));
			aiApplication = new AI(aiInfoData);
			aiApplication.start();
			infodata.setConnectionMode(new GameConnectionModeData(ConnectionMode.join));
		}
		connector = new TransportManager(infodata);
		while( keepRunning) {
			try {
				connector.connect();
				return;
			} catch(SocketTimeoutException e) {
				
			} catch(IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}

	@Override
	public void symbolIs(Sign sign) {
		mySign = sign;
	}

	@Override
	public void firstPlayerIs(Player player) {
		firstPlayer = player;
	}

	@Override
	public IConnectionInfoData getInfoData() {
		return infodata;
	}

	@Override
	public IConnector getConnector() {
		return connector;
	}

	@Override
	public IGamePlayData getSessionData() {
		return sessiondata;
	}

	@Override
	public void setRefresh() {
		isRefresh = true;		
	}
}
