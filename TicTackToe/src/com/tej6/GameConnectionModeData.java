package com.tej6;

public class GameConnectionModeData implements IConnectionModeData {

	ConnectionMode _connectionmode;

	GameConnectionModeData(ConnectionMode mode) {
		_connectionmode = mode;
	}
	public ConnectionMode getMode() {
		return _connectionmode;
	}

	public void setMode(ConnectionMode mode) {
		_connectionmode = mode;
	}

}
