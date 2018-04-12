package com.carlisle.ben.tanks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class DrawView extends View {

	private final Paint g = new Paint();
	private Map map;
	private Bitmap image;
	private Rect rect;

	public DrawView(Context context) {
		super(context);
	}

	public DrawView(Context context, AttributeSet attributes, int style) {
		super(context, attributes, style);
	}

	public DrawView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public void updateMap(Map map) {
		this.map = map;
		postInvalidate();
	}

	@SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (map != null) {
			g.setAntiAlias(false);
			canvas.drawBitmap(image, null, rect, g);
			for (Entity[] e : map.getEntities()) {
				for (Entity entity : e) {
					if (entity != null) {
						if (entity instanceof Tank) {
							Tank tank = (Tank) entity;
							g.setStrokeWidth(4);
							g.setStyle(Paint.Style.FILL_AND_STROKE);
							switch (tank.getLives()) {
								case 3:
									g.setColor(Color.GREEN);
									break;
								case 2:
									g.setColor(Color.BLUE);
									break;
								case 1:
									g.setColor(Color.MAGENTA);
									break;
							}
							canvas.drawCircle(tank.getXpos(), tank.getYpos(), (float) tank.getRadius(), g);
							double h = Math.sqrt(tank.getXPercentage() * tank.getXPercentage() + tank.getYPercentage() * tank.getYPercentage());
							g.setStyle(Paint.Style.FILL_AND_STROKE);
							g.setColor(Color.BLACK);
							if (h == 0) {
								canvas.drawCircle(tank.getXpos(), tank.getYpos(), 10, g);
							} else {
								canvas.drawCircle((int) (tank.getXpos() + tank.getRadius() * tank.getXPercentage() / (2 * h)), (int) (tank.getYpos() + tank.getRadius() * tank.getYPercentage() / (2 * h)), 10, g);
							}

						} else if (entity instanceof Bullet) {
							int x = entity.getXpos();
							int y = entity.getYpos();
							g.setColor(Color.RED);
							g.setStyle(Paint.Style.FILL);
							canvas.drawCircle(x, y, 10, g);
		//					} else if (entity instanceof Wall) {
		//						canvas.drawPoint(entity.getXpos(), entity.getYpos(), g);
						}
					}
				}
			}
		} else {
			postInvalidate();
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN);
		}
	}

	public void setBackground(Bitmap image) {
		this.image = image;
		rect = new Rect(0, 0, image.getWidth(), image.getHeight());
	}

}
