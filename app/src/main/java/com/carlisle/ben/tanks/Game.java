package com.carlisle.ben.tanks;

import android.util.Log;

import java.util.ArrayList;

class Game extends Thread {

	private final Map map;
	private final DrawView drawView;
	private boolean firePlayer1 = false, firePlayer2 = false, movePlayer1 = false, movePlayer2 = false;
	private float player1XPercentage, player1YPercentage, player2XPercentage, player2YPercentage;
	private final MainActivity main;

	public Game(Map map, DrawView drawView, MainActivity main) {
		this.map = map;
		this.drawView = drawView;
		this.main = main;
		drawView.updateMap(map);
	}

	public void run() {
		Log.e("game", "init");
		Tank player2 = new Tank(map.getWidth() / 2, map.getWidth() / 8, map.getWidth() / 15, false);
		Tank player1 = new Tank(map.getWidth() / 2, map.getHeight() - map.getWidth() / 8, map.getWidth() / 15, true);
		map.setEntity(player1.getXpos(), player1.getYpos(), player1);
		map.setEntity(player2.getXpos(), player2.getYpos(), player2);
		ArrayList<Bullet> bullets = new ArrayList<>();
		Bullet bullet;
		while (true) {
			if (System.currentTimeMillis() % 20 > 0) {
				continue;
			}
			if (firePlayer1) {
				bullet = player1.fire(map);
				if (bullet != null) {
					map.setEntity(bullet.getXpos(), bullet.getYpos(), bullet);
					bullets.add(bullet);
				}
				firePlayer1 = false;
			}
			if (firePlayer2) {
				bullet = player2.fire(map);
				if (bullet != null) {
					map.setEntity(bullet.getXpos(), bullet.getYpos(), bullet);
					bullets.add(bullet);
				}
				firePlayer2 = false;
			}
			if (movePlayer1) {
				player1.move(player1XPercentage, player1YPercentage, map);
				movePlayer1 = player1XPercentage != 0 || player1YPercentage != 0;
			} else {
				player1.checkCollision(player1.getXpos(), player1.getYpos(), map, false);
			}
			if (movePlayer2) {
				player2.move(player2XPercentage, player2YPercentage, map);
				movePlayer2 = player2XPercentage != 0 || player2YPercentage != 0;
			} else {
				player2.checkCollision(player2.getXpos(), player2.getYpos(), map, false);
			}
			if (player1.isDead()) {
				main.player1Wins(false);
				main.runOnUiThread(main);
				break;
			} else if (player2.isDead()) {
				main.player1Wins(true);
				main.runOnUiThread(main);
				break;
			}

			for (int i = 0; i < bullets.size(); i++) {
				bullet = bullets.get(i);
				if (!bullet.isAlive()) {
					bullets.remove(bullet);
					map.setEntity(bullet.getXpos(), bullet.getYpos(), null);
				} else {
					bullet.updatePosition(map);
				}
			}
			drawView.updateMap(map);
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
