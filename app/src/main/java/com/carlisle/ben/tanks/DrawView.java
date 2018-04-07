package com.carlisle.ben.tanks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class DrawView extends View {

	private final Paint g = new Paint();
	private Rect rectangle;
	public Map map;
	private final Path path = new Path();
	private Bitmap image;
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
		canvas.drawBitmap(image, null, rectangle, g);

		for(Entity[] e : map.getEntities())
		{
			for(Entity entity : e)
			{
				if(entity != null)
				{
					if(entity instanceof Tank)
					{
						int[][] vertices = ((Tank) entity).getVertices(entity.getXpos(), entity.getYpos(), ((Tank) entity).getJoyAngle());
						g.setColor(Color.GREEN);

						g.setStrokeWidth(4);
						g.setStyle(Paint.Style.FILL_AND_STROKE);
						g.setAntiAlias(true);

						path.setFillType(Path.FillType.EVEN_ODD);
						path.moveTo(vertices[0][0], vertices[0][1]);
						path.lineTo(vertices[1][0], vertices[1][1]);
						path.lineTo(vertices[2][0], vertices[2][1]);
						path.lineTo(vertices[0][0], vertices[0][1]);
						path.close();
						canvas.drawPath(path, g);

					}
					else if(entity instanceof Bullet)
					{
						int x = entity.getXpos();
						int y = entity.getYpos();
						g.setColor(Color.RED);
						g.setStyle(Paint.Style.FILL);
						canvas.drawPoint(x, y, g);

					}
					/*else if(entity instanceof Wall)
					{
						int x = entity.getXpos();
						int y = entity.getYpos();
						g.setColor(Color.BLACK);
						g.setStyle(Paint.Style.FILL);
						canvas.drawPoint(x, y, g);
					}*/
				}
			}
		}
	}

	public void setBackground(Bitmap image) {
		this.image = image;
		rectangle = new Rect(0, 0, map.getWidth(), map.getHeight());

	}

}
