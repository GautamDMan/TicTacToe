package com.tej6;

public class TicTackToe {
	private IController controller = null;
	public static void main(String[] args) {
		TicTackToe application = new TicTackToe();
		application.start();
	}
	
	public void start() {
		controller = new GameController();
		controller.launchGame();
	}
}
