package com.tej6;

public class GameConnectionInfoData implements IConnectionInfoData {

	private IConnectionModeData gamemodedata = null;
	private int portnumber = 0;
	private String ip = null;
	
	public GameConnectionInfoData(IConnectionInfoData infoData) {
		gamemodedata = infoData.getConnectionMode();
		portnumber = infoData.getPort();
		ip = infoData.getIpAddress();
	}
	
	public GameConnectionInfoData() {
		gamemodedata = null;
		portnumber = 0;
		ip = null;
	}

	@Override
	public String getIpAddress() {
		return ip;
	}

	@Override
	public int getPort() {
		return portnumber;
	}

	@Override
	public void setPort(int port) {
		portnumber = port;
	}

	@Override
	public void setIpAddress(String ipaddress) {
		ip = ipaddress; 
	}

	@Override
	public IConnectionModeData getConnectionMode() {
		return gamemodedata;
	}

	@Override
	public void setConnectionMode(IConnectionModeData modedata) {
		gamemodedata = modedata;
	}

}
