package com.example.jongjun.healthcare;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

class dbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    public dbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE contact ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+"name TEXT, tel TEXT);");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'쌀밥'"+", '300kcal');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'김치찌개'"+", '400kcal');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'된장찌개'"+", '500kcal');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'달걀찜'"+", '100kcal');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'닭찜'"+", '290kcal');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'감자조림'"+", '80kcal');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'깻잎조림'"+", '70kcal');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'어묵볶음'"+", '100kcal');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'우엉조림'"+", '60kcal');");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'고등어조림'"+", '90kcal');");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS contact");
        onCreate(db);
    }
}

public class CalorieTable extends ActionBarActivity {

    dbHelper helper;
    SQLiteDatabase db;
    EditText edit_search;
    TextView resCal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_table);

        edit_search = (EditText)findViewById(R.id.edit_search);
        resCal = (TextView)findViewById(R.id.resCal);

        helper = new dbHelper(this);
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM contact", null);
        startManagingCursor(cursor);
        String[] from = {"name", "tel"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to);
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);


        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_search.getText().toString();
                Cursor cursor;
                cursor = db.rawQuery("SELECT name, tel FROM contact WHERE name='"+name+"';", null);
                while(cursor.moveToNext()){
                    String cal = cursor.getString(1);
                    resCal.setText("    " +name + "    " + cal);
                    edit_search.setText("");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calorie_table, menu);
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
