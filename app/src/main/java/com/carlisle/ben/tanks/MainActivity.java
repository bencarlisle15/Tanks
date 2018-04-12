package com.carlisle.ben.tanks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements JoystickView.JoystickListener, Runnable {

	private Game game;
	private boolean player1Wins;
	private Map map;
	private Bitmap imageBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
		if (requestCode == 0) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				imageBitmap = (Bitmap) extras.get("data");
				if (imageBitmap != null) {
					setContentView(R.layout.game_layout);
					final DrawView drawView = findViewById(R.id.draw_view);
					imageBitmap = Bitmap.createScaledBitmap(imageBitmap, drawView.getRootView().getWidth(), drawView.getRootView().getHeight(), false);
					drawView.setBackground(imageBitmap);
					map = new Map(imageBitmap, drawView.getRootView().getWidth(), drawView.getRootView().getHeight());
					game = new Game(map, drawView, this);
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

	public void player1Wins(boolean player1Wins) {
		this.player1Wins = player1Wins;
	}

	@Override
	public void run() {

		setContentView(R.layout.end_screen);
		if (player1Wins) {
			TextView winner = findViewById(R.id.winner);
			String text = "GAME OVER! Player 1 Wins!";
			winner.setText(text);
		}
	}

	public void restart(View v) {
		setContentView(R.layout.game_layout);
		DrawView drawView = findViewById(R.id.draw_view);
		drawView.setBackground(imageBitmap);
		map.clear();
		game = new Game(map, drawView, this);
		game.start();
	}
}
