package com.carlisle.ben.tanks;

import android.graphics.Bitmap;

public class Map {
	private Entity[][] entities;

	public Map(Bitmap image, int gameWidth, int gameHeight) {
		Bitmap newImage = Bitmap.createScaledBitmap(
				image, gameWidth, gameHeight, false);
		entities = new Entity[newImage.getHeight()][newImage.getWidth()];
		for (int r = 0; r < newImage.getHeight(); r++) {
			for (int c = 0; c < newImage.getWidth(); c++) {
				if (isWall(newImage.getPixel(c, r)) || c == 0 || r == 0 || r == newImage.getWidth() - 1 || c == newImage.getHeight() - 1) {
					entities[r][c] = new Wall(c, r);
				} else {
					entities[r][c] = null;
				}
			}
		}
	}

	public int getWidth() {
		return entities[0].length;
	}

	public int getHeight() {
		return entities.length;
	}

	public Entity[][] getEntities() {
		return entities;
	}

	public Entity getEntity(int x, int y) {
		return entities[y][x];
	}

	public void setEntity(int x, int y, Entity entity) {
		entities[y][x] = entity;
	}

	public void moveEntity(int y1, int x1, int y2, int x2) {
		entities[y2][x2] = entities[y1][x2];
	}

	private boolean isWall(int color) {
		int a = (color >> 24) & 0xff;
		int r = (color >> 16) & 0xff;
		int g = (color >>  8) & 0xff;
		int b = (color) & 0xff;
		return a > 120 && r > 100 && r < 150 && g > 100 && g < 150 && b > 100 && b < 150;
	}
}
