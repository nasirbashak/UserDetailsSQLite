package com.nasirbashak007.usersqlite;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button browse, upload, show;
    ImageView imageView;
    EditText name, email;

    Animation upToDown, downToUp, leftToRight, rightToLeft;

    LinearLayout layout1, layout2;
    static SQLiteHelper sqLiteHelper;

    final int REQUEST_READ_GALLARY = 999;
    final int CAMERA_REQUEST_PERMISSION = 123;
    int FLAG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        upload = (Button) findViewById(R.id.upload);
        show = (Button) findViewById(R.id.viewall);


        upToDown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        layout1.setAnimation(upToDown);

        downToUp = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        layout2.setAnimation(downToUp);

        leftToRight = AnimationUtils.loadAnimation(this, R.anim.left_to_right);
        upload.setAnimation(leftToRight);

        rightToLeft = AnimationUtils.loadAnimation(this, R.anim.right_to_left);
        show.setAnimation(rightToLeft);


        sqLiteHelper = new SQLiteHelper(this, "UserData.sqlite", null, 1);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS USER(Id INTEGER PRIMARY kEY AUTOINCREMENT, name VARCHAR, email VARCHAR, image BLOG )");
    }

    public void browseImage(View view) {

        ActivityCompat.requestPermissions(
                MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_READ_GALLARY
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_READ_GALLARY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_READ_GALLARY);
            } else {
                Toast.makeText(getApplicationContext(), "You Have Not Provided The Permission", Toast.LENGTH_LONG).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_READ_GALLARY && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();

            try {

                InputStream inputStream = getContentResolver().openInputStream(imageUri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                imageView.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "Image SET", Toast.LENGTH_LONG).show();
                FLAG = 1;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }


        } else if (requestCode == CAMERA_REQUEST_PERMISSION && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            FLAG = 1;

        }

        super.onActivityResult(requestCode, resultCode, data);


    }

    public void uploadData(View view) {



        if (FLAG == 1 ) {


            try {

                String NAME = name.getText().toString().trim();
                String EMAIL = email.getText().toString().trim();

                sqLiteHelper.insertData(NAME, EMAIL, ImageToByteCode(imageView));

                Toast.makeText(getApplicationContext(), "Data Added to DataBase", Toast.LENGTH_LONG).show();

                name.setText("");
                email.setText("");
                imageView.setImageResource(R.drawable.user_image);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Select The Data First", Toast.LENGTH_SHORT).show();
        }


    }

    private byte[] ImageToByteCode(ImageView imageView) {

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }

    public void viewAll(View view) {

        Intent intent = new Intent(MainActivity.this, ViewDataActivity.class);

        startActivity(intent);

    }


    public void launchCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_PERMISSION);

    }


}
