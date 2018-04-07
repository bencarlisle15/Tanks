package com.carlisle.ben.tanks;

import android.graphics.Bitmap;
import android.util.Log;

public class Map {
	private Entity[][] entities;

	public Map(Bitmap image, int gameWidth, int gameHeight) {
		entities = new Entity[gameWidth][gameHeight];
		Bitmap newImage = Bitmap.createScaledBitmap(image, gameWidth, gameHeight, false);
		for (int r = 0; r < gameWidth; r++) {
			for (int c = 0; c < gameHeight; c++) {
				if (getEntity(r, c) == null && isWall(newImage.getPixel(r, c)) || c == 0 || r == 0 || r == newImage.getWidth() - 1 || c == newImage.getHeight() - 1) {
					entities[r][c] = new Wall(r, c);
				} else {
					entities[r][c] = null;
				}
			}
		}
	}

	public int getWidth() {
		return entities.length;
	}

	public int getHeight() {
		return entities[0].length;
	}

	public Entity[][] getEntities() {
		return entities;
	}

	public Entity getEntity(int x, int y) {
		if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
			return null;
		}
		return entities[x][y];
	}

	public void setEntity(int x, int y, Entity entity) {
		entities[x][y] = entity;
	}

	public void moveEntity(int x1, int y1, int x2, int y2) {
		entities[x2][y2] = entities[x1][y1];
		entities[x1][y1] = null;
	}

	private boolean isWall(int color) {
		int a = (color >> 24) & 0xff;
		int r = (color >> 16) & 0xff;
		int g = (color >>  8) & 0xff;
		int b = (color) & 0xff;
		return a > 120 && r > 100 && r < 150 && g > 100 && g < 150 && b > 100 && b < 150;
	}
}
