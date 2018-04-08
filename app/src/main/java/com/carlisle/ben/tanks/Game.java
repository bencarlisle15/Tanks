package com.carlisle.ben.tanks;

import android.util.Log;

import java.util.ArrayList;

public class Game extends Thread {

	private Tank player1;
	private Tank player2;
	private Map map;
	private DrawView drawView;
	private boolean firePlayer1 = false, firePlayer2 = false, movePlayer1 = false, movePlayer2 = false;
	private float player1XPercentage, player1YPercentage, player2XPercentage, player2YPercentage;
	private ArrayList<Bullet> bullets;

	public Game(Map map, DrawView drawView) {
		this.map = map;
		this.drawView = drawView;
	}

	public void run() {
		player2 = new Tank(map.getWidth()/2, map.getWidth()/8, map.getWidth()/15, false);
		player1 = new Tank(map.getWidth()/2 , map.getHeight() - 3*map.getWidth()/8, map.getWidth()/15, true);
		map.setEntity(player1.getXpos(), player1.getYpos(), player1);
		map.setEntity(player2.getXpos(), player2.getYpos(), player2);
		bullets = new ArrayList<>();
		Bullet bullet;
		try {
			while (true) {
				if (firePlayer1) {
					bullet = player1.fire(map);
					map.setEntity(bullet.getXpos(), bullet.getYpos(), bullet);
					bullets.add(bullet);
					firePlayer1 = false;
				}
				if (firePlayer2) {
					bullet = player2.fire(map);
					map.setEntity(bullet.getXpos(), bullet.getYpos(), bullet);
					bullets.add(bullet);
					firePlayer2 = false;
				}
				if (movePlayer1) {
					player1.move(player1XPercentage, player1YPercentage, map);
					movePlayer1 = false;
				} else {
					player1.move(0,0, map);
				}
				if (movePlayer2) {
					player2.move(player2XPercentage, player2YPercentage, map);
					movePlayer2 = false;
				} else {
					player2.move(0, 0, map);
				}
				if (player1.isDead()) {

				}

				for (int i = 0; i < bullets.size(); i++) {
					bullet = bullets.get(i);
					if (bullet.getNumBounces() < 0) {
						bullets.remove(bullet);
						map.setEntity(bullet.getXpos(), bullet.getYpos(), null);
					} else {
						bullet.updatePosition(map);
					}
				}
				drawView.updateMap(map);
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
