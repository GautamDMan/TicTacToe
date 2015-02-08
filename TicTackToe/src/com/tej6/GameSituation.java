package com.tej6;

import com.tej6.IGamePlayData.Sign;

public class GameSituation implements ISituation {

	private Situation situation = Situation.draw; 
	private int wins = 0,losses = 0,draw = 0;
	
	@Override
	public Situation getMode(IGamePlayData data,Sign sign) {
		if((data.getSign(0) == sign) && (data.getSign(1) == sign) && (data.getSign(2) == sign))
			return Situation.win;
		if((data.getSign(3) == sign) && (data.getSign(4) == sign) && (data.getSign(5) == sign))
			return Situation.win;
		if((data.getSign(6) == sign) && (data.getSign(7) == sign) && (data.getSign(8) == sign))
			return Situation.win;
		if((data.getSign(0) == sign) && (data.getSign(3) == sign) && (data.getSign(6) == sign))
			return Situation.win;
		if((data.getSign(1) == sign) && (data.getSign(4) == sign) && (data.getSign(7) == sign))
			return Situation.win;
		if((data.getSign(2) == sign) && (data.getSign(5) == sign) && (data.getSign(8) == sign))
			return Situation.win;
		if((data.getSign(0) == sign) && (data.getSign(4) == sign) && (data.getSign(8) == sign))
			return Situation.win;
		if((data.getSign(2) == sign) && (data.getSign(4) == sign) && (data.getSign(6) == sign))
			return Situation.win;
		Sign sign2 = null;
		if(sign == Sign.Circle)
			sign2 = Sign.Cross;
		else
			sign2 = Sign.Circle;
		if((data.getSign(0) == sign2) && (data.getSign(1) == sign2) && (data.getSign(2) == sign2))
			return Situation.loss;
		if((data.getSign(3) == sign2) && (data.getSign(4) == sign2) && (data.getSign(5) == sign2))
			return Situation.loss;
		if((data.getSign(6) == sign2) && (data.getSign(7) == sign2) && (data.getSign(8) == sign2))
			return Situation.loss;
		if((data.getSign(0) == sign2) && (data.getSign(3) == sign2) && (data.getSign(6) == sign2))
			return Situation.loss;
		if((data.getSign(1) == sign2) && (data.getSign(4) == sign2) && (data.getSign(7) == sign2))
			return Situation.loss;
		if((data.getSign(2) == sign2) && (data.getSign(5) == sign2) && (data.getSign(8) == sign2))
			return Situation.loss;
		if((data.getSign(0) == sign2) && (data.getSign(4) == sign2) && (data.getSign(8) == sign2))
			return Situation.loss;
		if((data.getSign(2) == sign2) && (data.getSign(4) == sign2) && (data.getSign(6) == sign2))
			return Situation.loss;
		for( int i = 0; i < 9; i++) 
			if(data.getSign(i) == Sign.Empty)
				return Situation.gameon;
		return Situation.draw;
	}

	@Override
	public void setMode(Situation situation) {
		this.situation = situation;
	}

	@Override
	public String getScore() {
		return new String("W:"+String.valueOf(wins)+" L:"+String.valueOf(losses));
	}

	@Override
	public void incrementScore(Situation situation) {
		if(situation == Situation.win)
			wins++;
		else if(situation == Situation.loss)
			losses++;
		else if(situation == Situation.draw)
			draw++;
	}

}
