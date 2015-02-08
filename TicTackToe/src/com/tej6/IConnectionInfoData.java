package com.tej6;

public interface IConnectionInfoData {
	public String getIpAddress();
	public int getPort();
	public void setPort(int port);
	public void setIpAddress(String ipaddress);
	public IConnectionModeData getConnectionMode();
	public void setConnectionMode(IConnectionModeData modedata);
}
