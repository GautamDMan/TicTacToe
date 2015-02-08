package com.tej6;

import java.io.IOException;

import com.tej6.IGamePlayData.Sign;

public interface IController {
	public enum Player {
		Me,
		You
	}
	public void launchGame();
	public IConnectionInfoData selectConnectionMode(IPlayModeData playmodedata); 
	public void play();
	public Player whoPlaysfirst();
	public Sign whichSymbol();
	public void symbolIs(Sign sign);
	public void firstPlayerIs(Player player);
	public void establishConnection() throws IOException;
	public IConnectionInfoData getInfoData();
	public IConnector getConnector();
	public void setRefresh();
}
