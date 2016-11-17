package com.example.justi.achartengine;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Data extends AppCompatActivity {
    protected SQLiteDatabase database;
    protected ArrayList<datapoint> datapoints = new ArrayList<datapoint>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final EditText column = (EditText) findViewById(R.id.category);
        final EditText expense = (EditText) findViewById(R.id.Expense);
        final Button adddata = (Button) findViewById(R.id.adddata);
        final DatabaseHelper dh = new DatabaseHelper(this);
        database = dh.getWritableDatabase();
        //database.execSQL("UPDATE summary set expenses=3000 where Type = 'Dogfood'");
        String[] allColumns = {dh.ID, dh.type, dh.expenses};
        Cursor cursor = database.rawQuery("SELECT * FROM summary", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast() && cursor!=null){
            int k = cursor.getInt(cursor.getColumnIndex("KEY_ID"));
            String c = cursor.getString(cursor.getColumnIndex("Type"));
            double e = cursor.getDouble(cursor.getColumnIndex("expenses"));
            System.out.println("k: " + k + " c: " + c + " e: " +e );
            datapoints.add(new datapoint(k,c,e));

            cursor.moveToNext();
        }
        adddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c = column.getText().toString();
                double e = Double.parseDouble(expense.getText().toString());
                ContentValues insertValues = new ContentValues();
                insertValues.put(dh.type, c);
                insertValues.put(dh.expenses, e);
                database.insert("summary",null, insertValues);
            }
        });


    }
    class datapoint{
        public int key_id = 0;
        public String column="";
        public double expense = 0;
        public datapoint (int k, String c, double e){
            this.key_id = k; this.column=c; this.expense=e;
        }
    }

}
