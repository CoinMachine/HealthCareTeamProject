package com.example.jongjun.healthcare;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/*
* 2015-06-05
* 메인 액티비티에서 운동일지 버튼을 누르면 ExerciseLogListActivity를 호출하고
* 인탠트에 list클래스를 전달한다.
*
* 앱이 실행될 때 저장되어 있는 list값을 불러와야한다. 없으면 생성.
* 종료될 때 list를 저장한다.
*
* 사용자 생성 버튼에서 입력받은 이름과 첫 체중에서 첫 체중도 ExerciseLogListActivity를 호출 할 때
* 같이 넣어야한다.
*
* */

public class MainActivity extends ActionBarActivity {
    static final int EXERCISE_VIEW=1;
    Button button;
    ArrayList<ExerciseLog> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.exerciseButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ExerciseLogListActivity.class);
                intent.putParcelableArrayListExtra("LogList",list);
                startActivityForResult(intent,EXERCISE_VIEW);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

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
