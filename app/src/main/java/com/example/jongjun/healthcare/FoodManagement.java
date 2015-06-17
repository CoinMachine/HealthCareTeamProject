package com.example.jongjun.healthcare;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

class dbHelper3 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contact3.db";
    private static final int DATABASE_VERSION = 2;

    public dbHelper3(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE contact ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+"date TEXT, kcal TEXT);");
        db.execSQL("INSERT INTO contact VALUES (null, "+"'날짜'"+", '칼로리');");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS contact");
        onCreate(db);
    }
}


public class FoodManagement extends ActionBarActivity {
    static final int GO_ADD_TABLE = 1;
    dbHelper3 helper;
    SQLiteDatabase db;
    ListView list;
    static final int ADD_FOOD = 1;
    String lkcal, ldate, ltotalkcal;
    int index = 0;
    String a;
    ImageView image;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_management);
        helper = new dbHelper3(this);
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM contact", null);
        startManagingCursor(cursor);
        String[] from = {"date", "kcal"};
        int[] to = {R.id.listdate, R.id.listkcal};
        SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this, R.layout.manage_list, cursor, from, to);
        list = (ListView) findViewById(R.id.manage_list);
        list.setAdapter(adapter2);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(FoodManagement.this, AddFoodTable.class);
                startActivityForResult(in, GO_ADD_TABLE);
            }
        });

        Button btn_cal = (Button) findViewById(R.id.btn_cal);
        btn_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(FoodManagement.this, CalorieTable.class);
                startActivity(in);
            }
        });
        image = (ImageView) findViewById(R.id.imageView);

        if (getIntent().getBooleanExtra("bImage", false)) {
            SerialBitmap serialBitmap = (SerialBitmap) getIntent().getSerializableExtra("userImage");
            image.setImageBitmap(serialBitmap.getBitmap());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_management, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_food:
                Intent in = new Intent(FoodManagement.this, AddFoodTable.class);
                startActivityForResult(in, ADD_FOOD);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_FOOD) {
            if (resultCode == RESULT_OK) {
                ldate = data.getStringExtra("date");
                lkcal = data.getStringExtra("kcal");
                ltotalkcal = data.getStringExtra("totalkcal");
                db.execSQL("INSERT INTO contact VALUES (null, " + "'" + ldate + "'" + ", '" + lkcal + "');");

            }
        }
        if (requestCode == GO_ADD_TABLE) {
            if (resultCode == RESULT_OK) {
                ldate = data.getStringExtra("date01");
                db.execSQL("DELETE FROM contact WHERE date = '" + ldate + "';");
                db.execSQL("DELETE FROM contact WHERE date = '" + null + "';");

            }
        }
    }
}
