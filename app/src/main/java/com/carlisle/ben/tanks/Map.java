package com.carlisle.ben.tanks;

import android.graphics.Bitmap;

class Map {
	private final Entity[][] entities;

	public Map(Bitmap image) {
		entities = new Entity[image.getWidth()][image.getHeight()];
		for (int r = 0; r < image.getWidth(); r++) {
			for (int c = 0; c < image.getWidth(); c++) {
				if (getEntity(r, c) == null && isWall(image.getPixel(r, c)) || c == 0 || r == 0 || r == image.getWidth() - 1 || c == image.getHeight() - 1) {
					entities[r][c] = new Wall(r, c);
				} else {
					entities[r][c] = null;
				}

			}
		}
	}

	public void clear() {
		for (int r = 0; r < entities.length; r++) {
			for (int c = 0; c < entities[r].length; c++) {
				if (!(entities[r][c] instanceof Wall)) {
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
		return entities[x][y];
	}

	public void setEntity(int x, int y, Entity entity) {
		entities[x][y] = entity;
	}

	public void moveEntity(int x1, int y1, int x2, int y2) {
		entities[x2][y2] = entities[x1][y1];
		if (x1 != x2 || y1 != y2) {
			entities[x1][y1] = null;
		}
	}

	private boolean isWall(int color) {
		int r = (color >> 16) & 0xff;
		int g = (color >> 8) & 0xff;
		int b = (color) & 0xff;
		return (r + g + b) / 3 < 100;
	}
}
