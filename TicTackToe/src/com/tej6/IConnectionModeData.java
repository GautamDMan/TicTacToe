package com.tej6;

public interface IConnectionModeData {
	public enum ConnectionMode {
		AI,
		create,
		join
	}
	public ConnectionMode getMode();
	public void setMode(ConnectionMode mode);
}
