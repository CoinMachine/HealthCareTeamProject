package com.example.jongjun.healthcare;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class UserSetActivity extends Activity {
    EditText name, weight;
    Button store , cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_set);
        name = (EditText)findViewById(R.id.nameEdit2);
        weight=(EditText)findViewById(R.id.weightEdit2);
        store=(Button)findViewById(R.id.store);
        cancel=(Button)findViewById(R.id.cancel);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("weight",weight.getText().toString());
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

    }
}
