package com.carlisle.ben.tanks;

public class Entity {

	private int xpos;
	private int ypos;

	Entity(int xpos, int ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
	}

	public int getXpos() {
		return xpos;
	}

	public int getYpos() {
		return ypos;
	}

	void setPosition(int xpos, int ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
	}
}
