package com.carlisle.ben.tanks;

import android.graphics.Bitmap;
import android.util.Log;

public class Map {
	private Entity[][] entities;

	public Map(Bitmap image) {
		entities = new Entity[image.getHeight()][image.getWidth()];
		for (int r = 0; r < image.getHeight(); r++) {
			for (int c = 0; c < image.getWidth(); c++) {
				if (isWall(image.getPixel(c, r))) {
					entities[r][c] = new Wall(r, c);
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

	public Entity getEntity(int r, int c) {
		return entities[r][c];
	}

	public void setEntity(int r, int c, Entity entity) {
		entities[r][c] = entity;
	}

	public void moveEntity(int r1, int c1, int r2, int c2) {
		entities[r2][c2] = entities[r1][c2];
	}

	private boolean isWall(int color) {
		int a = (color >> 24) & 0xff;
		int r = (color >> 16) & 0xff;
		int g = (color >>  8) & 0xff;
		int b = (color) & 0xff;
		return a > 120 && r > 100 && r < 150 && g > 100 && g < 150 && b > 100 && b < 150;
	}
}
