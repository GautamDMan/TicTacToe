package com.tej6;

public interface IGamePlayData {
	public enum Sign {
		Empty,
		Circle,
		Cross
	}
	public Sign getSign(int index);
	public void setSign(int index,Sign sign);
	public String getSigns();
	public void setSigns(String signstring);
}
