package com.tej6;

import com.tej6.IGamePlayData.Sign;

public interface ISituation {
	public enum Situation {
		win,
		loss,
		draw,
		gameon
	}
	public Situation getMode(IGamePlayData data,Sign sign);
	public void setMode(Situation situation);
	public String getScore();
	public void incrementScore(Situation situation);
}
