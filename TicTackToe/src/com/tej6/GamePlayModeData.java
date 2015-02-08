package com.tej6;

public class GamePlayModeData implements IPlayModeData {

	PlayMode _playmode;
	
	public PlayMode getPlayMode() {
		return _playmode;
	}

	@Override
	public void setPlayMode(PlayMode playmode) {
		_playmode = playmode;

	}

}
