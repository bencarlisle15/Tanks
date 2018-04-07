package com.carlisle.ben.tanks;

public class Game extends Thread {

	private Tank player1;
	private Tank player2;
	private Map map;
	private boolean firePlayer1 = false, firePlayer2 = false, movePlayer1 = false, movePlayer2 = false;
	private float player1XPercentage, player1YPercentage, player2XPercentage, player2YPercentage;

	public Game(Map map) {
		this.map = map;
		Tank player1 = new Tank(0, map.getHeight()/2, map.getWidth()/15);
		Tank player2 = new Tank(map.getWidth(), 0, map.getWidth()/15);
		this.start();
	}

	public void run() {

		try {
			while (true) {
				if (firePlayer1) {
					player1.fire();
					firePlayer1 = false;
				}
				if (firePlayer2) {
					player2.fire();
					firePlayer2 = false;
				}
				if (movePlayer1) {
					player1.move(player1XPercentage, player1YPercentage);
					movePlayer1 = false;
				}
				if (movePlayer2) {
					player2.move(player2XPercentage, player2YPercentage);
					movePlayer2 = false;
				}
				Thread.sleep(5);
			}
		} catch (InterruptedException e) {
			return;
		}
	}

	public void firePlayer1() {
		firePlayer1 = true;
	}

	public void firePlayer2() {
		firePlayer2 = true;
	}

	public void movePlayer1(float xPercentage, float yPercentage) {
		movePlayer1 = true;
		player1XPercentage = xPercentage;
		player1YPercentage = yPercentage;
	}

	public void movePlayer2(float xPercentage, float yPercentage) {
		movePlayer2 = true;
		player2XPercentage = xPercentage;
		player2YPercentage = yPercentage;
	}
}