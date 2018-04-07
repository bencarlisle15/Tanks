package com.carlisle.ben.tanks;

public class Game extends Thread {

	private Tank player1;
	private Tank player2;
	private Map map;

	public Game(Map map) {
		this.map = map;
		Tank player1 = new Tank(0, map.getHeight()/2, map.getWidth()/15);
		Tank player2 = new Tank(map.getWidth(), map.getWidth()/15);
		this.start();
	}

	public void run() {

		try {
			while (true) {
				Thread.sleep(5);

			}
		} catch (InterruptedException e) {
			return;
		}
	}

	public void firePlayer1() {

	}

	public void firePlayer2() {

	}

	public void movePlayer1(int xPercentage, int yPercentage) {

	}

	public void movePlayer2(int xPercentage, int yPercentage) {

	}
}
