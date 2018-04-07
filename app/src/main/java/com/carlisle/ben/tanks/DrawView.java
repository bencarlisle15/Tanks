package com.carlisle.ben.tanks;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View implements View.OnTouchListener {

	public DrawView(Context context) {
		super(context);
		create();
	}

	private void create() {
		//things to create
	}

	public DrawView(Context context, AttributeSet aSet) {
		super(context, aSet);
		create();
	}

	public DrawView(Context context, AttributeSet aSet, int dStyle) {
		super(context, aSet, dStyle);
		create();
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		//what to do on touch
		return true;
	}
}
