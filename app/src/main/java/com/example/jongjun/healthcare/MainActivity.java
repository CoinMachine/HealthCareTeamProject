package com.example.jongjun.healthcare;

import android.content.Intent;
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
public class MainActivity extends ActionBarActivity {
    final String FILENAME1 = "user.dat";
    static final int EXERCISE_VIEW=1;
    static final int USER_SET_VIEW=2;
    ImageButton exerciseButton,userButton;
    TextView name,weight;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=(TextView)findViewById(R.id.main_name_text);
        weight=(TextView)findViewById(R.id.main_weight_text);
        exerciseButton=(ImageButton)findViewById(R.id.exerciseButton);
        userButton=(ImageButton)findViewById(R.id.userButton);

        try {
            FileInputStream fis = openFileInput(FILENAME1);
            ObjectInputStream ois = new ObjectInputStream(fis);
            userData = (UserData)ois.readObject();
            name.setText(userData.name);
            weight.setText(userData.weight);
            ois.close();
            fis.close();
        }catch (FileNotFoundException e){
            userData=new UserData(name.getText().toString(),weight.getText().toString());
            Toast.makeText(getApplicationContext(),"사람 모양 버튼을 누르고 데이터를 입력하세요",Toast.LENGTH_SHORT).show();
        }catch (IOException e1){
            //디버깅용 코드
            Toast.makeText(getApplicationContext(),"IO exception1",Toast.LENGTH_SHORT).show();
        }catch (ClassNotFoundException e2){
            //디버깅용 코드
            Toast.makeText(getApplicationContext(),"class not found exception1",Toast.LENGTH_SHORT).show();
        }


        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ExerciseLogListActivity.class);
                intent.putExtra("weight3",weight.getText().toString());
                startActivityForResult(intent, EXERCISE_VIEW);
            }
        });

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserSetActivity.class);
                startActivityForResult(intent,USER_SET_VIEW);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(EXERCISE_VIEW==requestCode){
            if(RESULT_OK==resultCode){

            }
        }
        if(USER_SET_VIEW==requestCode){
            if(RESULT_OK==resultCode){
                name.setText(data.getStringExtra("name"));
                weight.setText(data.getStringExtra("weight"));
                userData.name=data.getStringExtra("name");
                userData.weight=data.getStringExtra("weight");
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        try{
            FileOutputStream fos = openFileOutput(FILENAME1, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userData);
            oos.close();
            fos.close();
        }catch (FileNotFoundException e){
            //디버깅용 코드
            Toast.makeText(getApplicationContext(),"File not found exception2",Toast.LENGTH_SHORT).show();
        }catch (IOException e1){
            //디버깅용 코드
            Toast.makeText(getApplicationContext(),"IO exception2",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
