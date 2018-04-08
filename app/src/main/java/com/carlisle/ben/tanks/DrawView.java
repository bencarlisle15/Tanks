package com.carlisle.ben.tanks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class DrawView extends View {

	private final Paint g = new Paint();
	private Map map;
	private Bitmap image;
	private final Path path = new Path();
	public DrawView(Context context)
	{
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

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		g.setAntiAlias(false);

		canvas.drawBitmap(image, null, new Rect(0, 0, getWidth(), getHeight()), g);
		for(Entity[] e : map.getEntities())
		{
			for(Entity entity : e)
			{
				if(entity != null)
				{
					if(entity instanceof Tank)
					{
						Tank tank = (Tank) entity;
//						int[][] vertices = ((Tank) entity).getVertices(entity.getXpos(), entity.getYpos());
//						Log.e(String.valueOf(vertices[0][0]), String.valueOf(vertices[0][1]));
//						Log.e(String.valueOf(vertices[1][0]), String.valueOf(vertices[1][1]));
//						Log.e(String.valueOf(vertices[2][0]), String.valueOf(vertices[2][1]));
						g.setStrokeWidth(4);
						g.setStyle(Paint.Style.FILL_AND_STROKE);
						g.setColor(Color.GREEN);
//						path.reset();
//						path.setFillType(Path.FillType.EVEN_ODD);
//						path.moveTo(vertices[0][0], vertices[0][1]);
//						path.lineTo(vertices[1][0], vertices[1][1]);
//						path.lineTo(vertices[2][0], vertices[2][1]);
//						path.lineTo(vertices[0][0], vertices[0][1]);
//						path.close();
//						canvas.drawPath(path, g);
						canvas.drawCircle(tank.getXpos(), tank.getYpos(),(float) tank.getWidth(), g);
						double h = Math.sqrt(tank.getXPercentage()*tank.getXPercentage() + tank.getXPercentage()*tank.getYPercentage());
						g.setStyle(Paint.Style.FILL_AND_STROKE);
						g.setColor(Color.RED);
						if (h == 0) {
							canvas.drawCircle(tank.getXpos(), tank.getYpos(), 10, g);
						} else {
							canvas.drawCircle((int)(tank.getXpos() + 5*tank.getXPercentage()/h), (int)(tank.getYpos() + 5*tank.getYPercentage()/h), 10, g);
						}

					}
					else if(entity instanceof Bullet)
					{
						int x = entity.getXpos();
						int y = entity.getYpos();
						g.setColor(Color.RED);
						g.setStyle(Paint.Style.FILL);
						canvas.drawCircle(x, y, 10, g);
						Log.e("ISSA"," BULLET");
					}
//					else if(entity instanceof Wall)
//					{
//						int x = entity.getXpos();
//						int y = entity.getYpos();
//						g.setColor(Color.BLACK);
//						g.setStyle(Paint.Style.FILL);
//						canvas.drawPoint(x, y, g);
//					}
				}
			}
		}
	}

	public void setBackground(Bitmap image) {
		this.image = image;
	}

}
