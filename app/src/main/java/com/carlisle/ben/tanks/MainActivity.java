package com.carlisle.ben.tanks;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements JoystickView.JoystickListener {

	private Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void promptImage(View view) {
		Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePicture, 0);
	}

	@Override
	public void onJoystickMoved(float xPercent, float yPercent, int id) {
		switch (id) {
			case R.id.joystickLeft:
				game.movePlayer1(xPercent, yPercent);
				break;
			case R.id.joystickRight:
				game.movePlayer2(xPercent, yPercent);
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap imageBitmap = null;
		if (requestCode == 0) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				imageBitmap = (Bitmap) extras.get("data");
				if (imageBitmap != null) {
					Map map = new Map(imageBitmap, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels);
					setContentView(R.layout.game_layout);
					DrawView drawView = findViewById(R.id.draw_view);
					drawView.setBackground(imageBitmap);
					game = new Game(map, drawView);
					game.start();
				}
			}
		}

	}

    public void firePlayer1(View v) {
	    game.firePlayer1();
    }

    public void firePlayer2(View v) {
	    game.firePlayer2();
    }
}
