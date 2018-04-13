package com.carlisle.ben.tanks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity implements JoystickView.JoystickListener, Runnable, ViewTreeObserver.OnGlobalLayoutListener {

	private Game game;
	private boolean player1Wins;
	private Map map;
	private Bitmap imageBitmap;
	private static final String TAG = "MainActivity";
	private AdView mAdView;
	private AdRequest adRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		mAdView = findViewById(R.id.adView);
		adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
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
		if (requestCode == 0 && data != null) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				imageBitmap = (Bitmap) extras.get("data");
				if (imageBitmap != null) {
					setContentView(R.layout.game_layout);
					mAdView = findViewById(R.id.adView);
					adRequest = new AdRequest.Builder().build();
					mAdView.loadAd(adRequest);
					findViewById(R.id.draw_view).getViewTreeObserver().addOnGlobalLayoutListener(this);
				}
			}
		}
	}

	@Override
	public void onGlobalLayout() {
		final DrawView drawView = findViewById(R.id.draw_view);
		imageBitmap = Bitmap.createScaledBitmap(imageBitmap, drawView.getWidth(), drawView.getHeight(), false);
		drawView.setBackground(imageBitmap);
		map = new Map(imageBitmap);
		game = new Game(map, drawView, this);
		game.start();
		drawView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

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
		mAdView = findViewById(R.id.adView);
		adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
	}

	public void restart(View v) {
		setContentView(R.layout.game_layout);
		mAdView = findViewById(R.id.adView);
		adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		DrawView drawView = findViewById(R.id.draw_view);
		drawView.setBackground(imageBitmap);
		map.clear();
		game = new Game(map, drawView, this);
		game.start();
	}
}
