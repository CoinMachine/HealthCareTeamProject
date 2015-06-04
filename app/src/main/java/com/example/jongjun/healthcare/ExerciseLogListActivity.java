package com.example.jongjun.healthcare;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
/*2015-06-05
* ExerciseLogListActivity - activity_exercise_log_list 메인 엑티비티에서 어레이 리스트를
* 인텐트로 받아서 리스트로 띄워준다. 메뉴에 추가버튼을 만들어서 리스트에 새로운 리스트 추가해주도록
* 액티비티가 종료될 때 메인액티비티로 어레이 리스트를 인텐트에 넣어서 보내준다.
*
* LogListAdapter - log_list 리스트뷰를 보여줄 어탭터와 그 레이아웃
* 리스트를 클릭하면 해당 리스트를 수정할 인텐트를 쏴준다. 그 엑티비티에서 인텐트를 돌려받으면 그 값으로
* 리스트의 정보를 수정
*
* //리스트에서 새로운 액티비티를 호출하는 부분을 작성해야한다.
*
* ExerciseLog 운동일지의 정보를 가지는 클래스
* Parcelable을 구현해서, 인텐트로 액티비티간 객체를 전달해 줄 수 있다.
*
*
*
* */

public class ExerciseLogListActivity extends ActionBarActivity {
    TextView firstWeight;
    TextView currentWeight; //현재 체중은, 현재 액티비티에서 일지 추가 할때, 인텐트 결과값을 돌려받으면서 수정해주자.
    ListView logListView;
    ArrayList<ExerciseLog> logArrayList; //메인 엑티비티에서 인텐트로 리스트를 받자.
    LogListAdapter logListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_log_list);
        firstWeight =(TextView)findViewById(R.id.first_weight_text);
        currentWeight=(TextView)findViewById(R.id.current_weight_text);
        logListView=(ListView)findViewById(R.id.log_list);

        Intent i = getIntent();
        logArrayList=i.getParcelableArrayListExtra("LogList");

        logListAdapter=new LogListAdapter(this,logArrayList);
        logListView.setAdapter(logListAdapter);
        logListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //리스트 아이템 클릭시 수정할 액티비티 창을 띄워 준다.
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Arraylist 값을 메인 엑티비티로 돌려준다.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise_log_list, menu);
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
