package com.example.jongjun.healthcare;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/*2015-06-05
* ExerciseLogListActivity - activity_exercise_log_list 메인 엑티비티에서 어레이 리스트를
* 인텐트로 받아서 리스트로 띄워준다. 메뉴에 추가버튼을 만들어서 리스트에 새로운 리스트 추가해주도록
* 액티비티가 종료될 때 메인액티비티로 어레이 리스트를 인텐트에 넣어서 보내준다.
*
* menu_exercise_loglist - 메뉴에서 추가버튼을 누르면 LogAddActivity를 호출한다.
*
* LogListAdapter - log_list 리스트뷰를 보여줄 어탭터와 그 레이아웃
* 리스트를 클릭하면 해당 값을 수정할 인텐트를 쏴준다. 그 엑티비티에서 인텐트를 돌려받으면 그 값으로
* 리스트의 정보를 수정
*
* //리스트를 누르면 새로운 액티비티를 호출하는 부분을 작성해야한다.
*
* ExerciseLog 운동일지의 정보를 가지는 클래스
* Serializable을 구현해서, 인텐트로 액티비티간 객체를 전달해 줄 수 있다.
*
*
*
* */

public class ExerciseLogListActivity extends ActionBarActivity {
    static final int LOG_ADD=1;
    static final int LOG_UPDATE=2;
    static final int DELETE=666; // 데이터를 지울때 받는 값

    final String FILENAME="exercise.dat";
    

    TextView firstWeight;
    TextView currentWeight; //현재 체중은, 현재 액티비티에서 일지 추가 할때, 인텐트 결과값을 돌려받으면서 수정해주자.
    ListView logListView;
    ArrayList<ExerciseLog> logArrayList;
    LogListAdapter logListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_log_list);
        firstWeight =(TextView)findViewById(R.id.first_weight_text);
        currentWeight=(TextView)findViewById(R.id.current_weight_text);
        logListView=(ListView)findViewById(R.id.log_list);

        try {
            FileInputStream fis = openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            logArrayList = (ArrayList<ExerciseLog>)ois.readObject();
            ois.close();
            fis.close();
        }catch (FileNotFoundException e){
            logArrayList=new ArrayList<>();
            //디버깅용 코드
            Toast.makeText(getApplicationContext(),"File not found exception1",Toast.LENGTH_SHORT).show();
        }catch (IOException e1){
            //디버깅용 코드
            Toast.makeText(getApplicationContext(),"IO exception1",Toast.LENGTH_SHORT).show();
        }catch (ClassNotFoundException e2){
            //디버깅용 코드
            Toast.makeText(getApplicationContext(),"class not found exception1",Toast.LENGTH_SHORT).show();
        }



        logListAdapter=new LogListAdapter(this,logArrayList);
        logListView.setAdapter(logListAdapter);
        logListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //리스트 아이템 클릭시 수정할 액티비티 창을 띄워 준다.
                Intent intent = new Intent(ExerciseLogListActivity.this, LogAddActivity.class);
                intent.putExtra("log",logArrayList.get(position));
                intent.putExtra("position",position);
                intent.putExtra("update",true);
                startActivityForResult(intent,LOG_UPDATE);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        try{
            FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(logArrayList);
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
        switch (id){
            case R.id.add:
                Intent intent=new Intent(ExerciseLogListActivity.this,LogAddActivity.class);
                startActivityForResult(intent,LOG_ADD);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(LOG_ADD==requestCode){
            if(RESULT_OK==resultCode){
                logArrayList.add((ExerciseLog)data.getSerializableExtra("log"));
                currentWeight.setText(((ExerciseLog) data.getSerializableExtra("log")).weight);
                logListAdapter.notifyDataSetChanged();
            }
        }
        if(LOG_UPDATE==requestCode){
            if(RESULT_OK==resultCode){
                logArrayList.set(data.getIntExtra("position",0),
                        (ExerciseLog)data.getSerializableExtra("log"));
                logListAdapter.notifyDataSetChanged();
            }
            if(DELETE==resultCode){
                logArrayList.remove(data.getIntExtra("position",0));
                logListAdapter.notifyDataSetChanged();
            }
        }
    }
}
