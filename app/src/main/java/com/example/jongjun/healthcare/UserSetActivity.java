package com.example.jongjun.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class UserSetActivity extends Activity {
    static final int CAMERA_CAPTURE=1;//카메라 인텐트

    EditText name, weight;
    Button store , cancel;
    ImageView userImage;
    SerialBitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_set);
        name = (EditText)findViewById(R.id.nameEdit2);
        weight=(EditText)findViewById(R.id.weightEdit2);
        store=(Button)findViewById(R.id.store);
        cancel=(Button)findViewById(R.id.cancel);
        userImage=(ImageView)findViewById(R.id.userImage);


        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("weight",weight.getText().toString());
                intent.putExtra("userImage",image);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(i, CAMERA_CAPTURE);
            }
        });

        if(getIntent().getBooleanExtra("bImage",false)){
            image=(SerialBitmap)getIntent().getSerializableExtra("userImage");
            userImage.setImageBitmap(image.getBitmap());
        }
        if(getIntent().getBooleanExtra("bUserData",false)){
            UserData userData= (UserData)getIntent().getSerializableExtra("userData");
            name.setText(userData.name);
            weight.setText(userData.weight);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(CAMERA_CAPTURE==requestCode){
            if(RESULT_OK==resultCode){
                Bitmap captureBmp=null;
                Bitmap sizingBmp=null;
                File file = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
                try{
                    captureBmp= MediaStore.Images.Media.getBitmap(getContentResolver(),Uri.fromFile(file));
                    sizingBmp=Bitmap.createScaledBitmap(captureBmp,captureBmp.getWidth()/6,captureBmp.getHeight()/6,true);
                }catch (FileNotFoundException e){
                }catch (IOException e){}

                image = new SerialBitmap(sizingBmp);
                userImage.setImageBitmap(image.getBitmap());
                userImage.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
    }
}
