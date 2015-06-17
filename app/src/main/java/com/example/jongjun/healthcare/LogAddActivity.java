package com.example.jongjun.healthcare;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

//운동일지를 생성해서 저장 해 줄 클래스
public class LogAddActivity extends Activity {

    ImageView photo;
    EditText memo,weight;
    TextView day;
    ArrayList<ExerciseLog> list;
    Button store,cancel;
    Button delete;
    int position=0; // 수정 시 리스트의 위치를 나타내는 변수
    SerialBitmap userImage;//이미지 비트맵 저장 변수

    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID=0;
    static final int DELETE=666; // 데이터를 지울 때 주는 값
    static final int CAMERA_CAPTURE=1;//카메라 인텐트


//-----------------------------달력함수-----------------------------------------------------//
    private DatePickerDialog.OnDateSetListener listener
        = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear=year;
            mMonth=monthOfYear;
            mDay=dayOfMonth;
            updateDisplay();
        }
    };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, listener,mYear,mMonth,mDay);
        }
        return null;
    }

    private void updateDisplay(){
        day.setText(new StringBuilder().append(" ")
                .append(mYear).append("년 ").append(mMonth+1).append("월 ").append(mDay).append("일"));
    }
//--------------------------------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_add);

        weight=(EditText)findViewById(R.id.weightEdit1);
        day=(TextView)findViewById(R.id.dayText1);
        memo=(EditText)findViewById(R.id.editMemo1);
        store=(Button)findViewById(R.id.storeButton1);
        cancel=(Button)findViewById(R.id.cancelButton1);
        delete=(Button)findViewById(R.id.deleteButton1);
        photo=(ImageView)findViewById(R.id.imageView);




        final Calendar c = Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();

        //LOG_UPDATE인 경우
        if(getIntent().getBooleanExtra("update",false)) {
            Intent intent = getIntent();
            day.setText(((ExerciseLog) intent.getSerializableExtra("log")).day);
            weight.setText(((ExerciseLog) intent.getSerializableExtra("log")).weight);
            memo.setText(((ExerciseLog) intent.getSerializableExtra("log")).memo);
            delete.setVisibility(View.VISIBLE);
            userImage=((ExerciseLog) intent.getSerializableExtra("log")).image;
            if(userImage!=null) {
                photo.setImageBitmap(userImage.getBitmap());
                photo.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            position = intent.getIntExtra("position", 0);
        }

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("log",new ExerciseLog(
                        weight.getText().toString(),
                        day.getText().toString(),
                        memo.getText().toString(),
                        userImage));
                intent.putExtra("position",position);
                setResult(RESULT_OK,intent);
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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("position",position);
                setResult(DELETE,intent);
                finish();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
                i.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
                startActivityForResult(i, CAMERA_CAPTURE);
            }
        });
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

                userImage = new SerialBitmap(sizingBmp);
                photo.setImageBitmap(userImage.getBitmap());
                photo.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
