package com.tej6;

public interface IPlayModeData {
	public enum PlayMode {
		noGame,
		singlePlayer,
		multiPlayer
	}
	public PlayMode getPlayMode();
	public void setPlayMode(PlayMode playmode);
}
