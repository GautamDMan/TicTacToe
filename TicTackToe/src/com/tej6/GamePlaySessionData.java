package com.tej6;

import com.tej6.IController.Player;

public class GamePlaySessionData implements IGamePlayData {

	private Sign[] signarray = new Sign[9]; /* This is the actual data underlying buttons */
	
	public GamePlaySessionData() {
		for(int i =0; i< 9; i++)
			signarray[i] = Sign.Empty;
	}
	
	public Sign getSign(int index) {
		return signarray[index];
	}

	@Override
	public void setSign(int index, Sign sign) {
		signarray[index] = sign;
	}

	@Override
	public String getSigns() {
		String data = "";
		for(int i = 0; i < 9; i++) {
			data = data.concat(stringFromSign(signarray[i]));
		}
		return data;
	}

	@Override
	public void setSigns(String signstring) {
		for(int i = 0;i < 9; i++ ) {
			signarray[i] = signFromString(signstring.substring(i, i+1));
		}
	}

	public static Sign signFromString(String data) {
		int loc = Integer.parseInt(data);
		if(loc == 0)
			return Sign.Empty;
		else if(loc == 1)
			return Sign.Circle;
		else
			return Sign.Cross;
	}
	
	public static Player playerFromString(String data) {
		int loc = Integer.parseInt(data);
		if(loc == 0)
			return Player.Me;
		else 
			return Player.You;
	}
	
	public static String stringFromSign(Sign sign) {
		if(sign == Sign.Empty)
			return "0";
		else if(sign == Sign.Circle)
			return "1";
		else
			return "2";
	}
	
	public static String stringFromPlayer(Player player) {
		if(player == Player.Me)
			return "0";
		else 
			return "1";
	}

}
