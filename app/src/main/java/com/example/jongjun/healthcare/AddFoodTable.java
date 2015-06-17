package com.example.jongjun.healthcare;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

class dbHelper2 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contact2.db";
    private static final int DATABASE_VERSION = 2;

    public dbHelper2(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE contact ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+"name TEXT, tel TEXT);");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'쌀밥'"+", '300');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'김치찌개'"+", '400');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'된장찌개'"+", '500');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'달걀찜'"+", '100');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'닭찜'"+", '290');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'감자조림'"+", '80');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'깻잎조림'"+", '70');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'어묵볶음'"+", '100');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'우엉조림'"+", '60');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'고등어조림'"+", '90');");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS contact");
        onCreate(db);
    }
}

public class AddFoodTable extends ActionBarActivity {

    dbHelper2 helper;
    SQLiteDatabase db;
    EditText editAdd_search;
    TextView resAddCal, totalCal, dateview;
    ListView list;
    String cal;
    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    int totalkcal=0;
    Button select;
    Button selectDate;
    Boolean check = false;

    static final int DATE_ID = 0;
    public int Year, Month, Day;
    private int mYear, mMonth, mDay;

    public AddFoodTable(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_table);

        dateview = (TextView)findViewById(R.id.dateview);

        selectDate = (Button)findViewById(R.id.selectdate);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });
        select = (Button)findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check){
                    Intent in = new Intent();
                    String tmp = String.valueOf(totalkcal).toString()+"/1800 kcal";
                    String total = String.valueOf(totalkcal).toString();
                    in.putExtra("date",  dateview.getText().toString());
                    in.putExtra("kcal", tmp);
                    in.putExtra("totalkcal",total);
                    setResult(RESULT_OK, in);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "날짜를 설정하세요.",Toast.LENGTH_SHORT).show();
                }

            }
        });

        totalCal = (TextView)findViewById(R.id.totalcal);
        editAdd_search = (EditText)findViewById(R.id.editAdd_search);
        resAddCal = (TextView)findViewById(R.id.resAddCal);
        items = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);


        helper = new dbHelper2(this);
        db = helper.getReadableDatabase();
        list = (ListView)findViewById(R.id.add_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = items.get(position).toString();
                Cursor cursor;
                cursor = db.rawQuery("SELECT name, tel FROM contact WHERE name='"+name+"';", null);
                while(cursor.moveToNext()){
                    String cal = cursor.getString(1);
                    Toast.makeText(getApplicationContext(), cal+"kcal 입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });


        findViewById(R.id.btnAdd_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editAdd_search.getText().toString();
                Cursor cursor;
                cursor = db.rawQuery("SELECT name, tel FROM contact WHERE name='"+name+"';", null);
                while(cursor.moveToNext()){
                    String cal = cursor.getString(1);
                    resAddCal.setText("    " +name + "    " + cal+"kcal");
                }
            }
        });

        findViewById(R.id.btnAdd_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editAdd_search.getText().toString();
                Cursor cursor;
                cursor = db.rawQuery("SELECT name, tel FROM contact WHERE name='"+name+"';", null);

                while(cursor.moveToNext()){
                    cal = cursor.getString(1);
                    if(editAdd_search.getText().equals("")){
                        Toast.makeText(getApplicationContext(), "이미 입력하셨습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        String resName;
                        resName = cursor.getString(0);
                        items.add(resName);
                        totalkcal += Integer.valueOf(cal);
                        totalCal.setText(String.valueOf(totalkcal).toString()+"kcal/1800kcal");
                    }

                }

                editAdd_search.setText("");
                adapter.notifyDataSetChanged();

            }
        });
    }

    private DatePickerDialog.OnDateSetListener mDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Year = year;
            Month = monthOfYear+1;
            Day = dayOfMonth;
            check = true;
            dateview.setText(Year+"/"+Month+"/"+Day);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDate, mYear, mMonth, mDay);
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_food_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.ActivtiyDel:
                if(check){
                    Intent in = new Intent();
                    in.putExtra("date01",  dateview.getText().toString());
                    setResult(RESULT_OK, in);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "날짜를 설정하세요.",Toast.LENGTH_SHORT).show();
                }


                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
