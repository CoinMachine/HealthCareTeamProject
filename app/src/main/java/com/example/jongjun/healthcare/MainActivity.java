package com.example.jongjun.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/*
* 2015-06-05
* 메인 액티비티에서 운동일지 버튼을 누르면 ExerciseLogListActivity를 호출
*
* 사용자 생성 버튼에서 입력받은 이름과 첫 체중에서 첫 체중을 ExerciseLogListActivity를 호출 할 때
* 넣어야한다.
*
* */

class UserData implements Serializable{
    private static final long serialVersionUID = -5234135919664263905L;
    String name,weight;
    public UserData(){
    }
    public UserData(String name, String weight){
        this.name=name;
        this.weight=weight;
    }
}
public class MainActivity extends Activity {
    final String FILENAME1 = "user.dat";
    final String FILENAME_USER_IMAGE = "userImage.dat";

    static final int EXERCISE_VIEW = 1;
    static final int USER_SET_VIEW = 2;
    ImageButton exerciseButton, userButton, infoButton,counterButton,foodButton;
    TextView name, weight;
    UserData userData;
    SerialBitmap userImage;

    boolean bImage=false,bUserData=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (TextView) findViewById(R.id.main_name_text);
        weight = (TextView) findViewById(R.id.main_weight_text);
        exerciseButton = (ImageButton) findViewById(R.id.exerciseButton);
        userButton = (ImageButton) findViewById(R.id.userButton);
        infoButton = (ImageButton) findViewById(R.id.infoButton);
        counterButton=(ImageButton)findViewById(R.id.counterButton);
        foodButton=(ImageButton)findViewById(R.id.foodButton);



        try {
            FileInputStream fis = openFileInput(FILENAME1);
            ObjectInputStream ois = new ObjectInputStream(fis);
            userData = (UserData) ois.readObject();
            name.setText(userData.name);
            weight.setText(userData.weight);
            bUserData=true;
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            userData = new UserData(name.getText().toString(), weight.getText().toString());
            Toast.makeText(getApplicationContext(), "사람 모양 버튼을 누르고 데이터를 입력하세요", Toast.LENGTH_SHORT).show();
        } catch (IOException e1) {
            //디버깅용 코드
            Toast.makeText(getApplicationContext(), "IO exception1", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e2) {
            //디버깅용 코드
            Toast.makeText(getApplicationContext(), "class not found exception1", Toast.LENGTH_SHORT).show();
        }

        try {
            FileInputStream fis = openFileInput(FILENAME_USER_IMAGE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            userImage = (SerialBitmap) ois.readObject();
            bImage=true;
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            userImage = new SerialBitmap(Bitmap.createBitmap(300,200, Bitmap.Config.ARGB_8888));
            bImage=false;
        } catch (IOException e1) {
            //디버깅용 코드
            //Toast.makeText(getApplicationContext(), "IO exception1", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e2) {
            //디버깅용 코드
            //Toast.makeText(getApplicationContext(), "class not found exception1", Toast.LENGTH_SHORT).show();
        }


        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExerciseLogListActivity.class);
                intent.putExtra("weight3", weight.getText().toString());
                startActivityForResult(intent, EXERCISE_VIEW);
            }
        });

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserSetActivity.class);
                intent.putExtra("bImage",bImage);
                if(bImage)
                    intent.putExtra("userImage",userImage);
                intent.putExtra("bUserData",bUserData);
                if(bUserData)
                    intent.putExtra("userData",userData);
                startActivityForResult(intent, USER_SET_VIEW);
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("http://terms.naver.com/list.nhn?cid=51029&categoryId=51029");
                intent.setData(uri);
                startActivity(intent);
            }
        });
        counterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShakeActivity.class);
                startActivity(intent);
            }
        });
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FoodManagement.class);
                intent.putExtra("bImage",bImage);
                intent.putExtra("userImage",userImage);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (EXERCISE_VIEW == requestCode) {
            if (RESULT_OK == resultCode) {

            }
        }
        if (USER_SET_VIEW == requestCode) {
            if (RESULT_OK == resultCode) {
                name.setText(data.getStringExtra("name"));
                weight.setText(data.getStringExtra("weight"));
                userData.name = data.getStringExtra("name");
                userData.weight = data.getStringExtra("weight");
                userImage=(SerialBitmap)data.getSerializableExtra("userImage");
                bImage=true;
                bUserData=true;
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            FileOutputStream fos = openFileOutput(FILENAME1, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userData);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            //디버깅용 코드
            Toast.makeText(getApplicationContext(), "File not found exception2", Toast.LENGTH_SHORT).show();
        } catch (IOException e1) {
            //디버깅용 코드
            Toast.makeText(getApplicationContext(), "IO exception2", Toast.LENGTH_SHORT).show();
        }
        try {
            FileOutputStream fos = openFileOutput(FILENAME_USER_IMAGE, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userImage);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            //디버깅용 코드
            Toast.makeText(getApplicationContext(), "File not found exception2", Toast.LENGTH_SHORT).show();
        } catch (IOException e1) {
            //디버깅용 코드
            Toast.makeText(getApplicationContext(), "IO exception2", Toast.LENGTH_SHORT).show();
        }

    }
}