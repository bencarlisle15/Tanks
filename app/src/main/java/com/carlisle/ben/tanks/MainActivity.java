package com.carlisle.ben.tanks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements GameView.GameListener {

	private Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void promptImage(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select Method")
				.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
						photoPickerIntent.setType("image/*");
						startActivityForResult(photoPickerIntent, 1);
					}
				})
				.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(takePicture, 2);
					}
				})
				.setIcon(android.R.drawable.ic_menu_camera)
				.show();
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
		if (requestCode == 1) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = {MediaStore.Images.Media.DATA};

			Cursor cursor = null;
			if (selectedImage != null) {
				cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			}

			if (cursor != null) {
				cursor.moveToFirst();
				String imagePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
				cursor.close();
				imageBitmap = BitmapFactory.decodeFile(imagePath);
			}
		} else if (requestCode == 2) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				imageBitmap = (Bitmap) extras.get("data");
			}
		}
		if (imageBitmap != null) {
			Map map = new Map(imageBitmap);
			setContentView(R.layout.game_layout);
			getWindow().getDecorView().setBackground(new BitmapDrawable(getResources(), imageBitmap));
			game = new Game(map);
		}
	}

    public void firePlayer1(View v) {
	    game.firePlayer1();
    }

    public void firePlayer2(View v) {
	    game.firePlayer2();
    }
}
