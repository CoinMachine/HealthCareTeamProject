package com.example.jongjun.healthcare;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

//운동일지를 생성해서 저장 해 줄 클래스
public class LogAddActivity extends ActionBarActivity {

    EditText memo,weight,day;
    ArrayList<ExerciseLog> list;
    Button store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_add);

        weight=(EditText)findViewById(R.id.weightEdit1);
        day=(EditText)findViewById(R.id.dayEdit1);
        memo=(EditText)findViewById(R.id.editMemo1);
        store=(Button)findViewById(R.id.storeButton1);


        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("log",new ExerciseLog(
                        weight.getText().toString(),
                        day.getText().toString(),
                        memo.getText().toString()));
                setResult(RESULT_OK,intent);
                finish();
            }
        });

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
