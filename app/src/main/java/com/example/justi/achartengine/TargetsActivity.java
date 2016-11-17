package com.example.justi.achartengine;

import android.support.v7.app.AppCompatActivity;



/**
 * Created by behi on 2016-11-17.
 */

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TargetsActivity extends AppCompatActivity {
    ListView lv;
    EditText targetEdit;
    EditText dueDateEdit;
    EditText howMuchEdit;

    Button bt;
    ArrayList<String> targetList = new ArrayList<>();
    ArrayList<String> dueDateList = new ArrayList<>();
    ArrayList<String> howMuchList = new ArrayList<>();
    TargetAdapter targetAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        lv = (ListView) findViewById(R.id.listView);
        targetEdit = (EditText) findViewById(R.id.target);
        dueDateEdit = (EditText) findViewById(R.id.dueDate);
        howMuchEdit = (EditText) findViewById(R.id.howMuch);

        bt = (Button) findViewById(R.id.submitButton);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String target = targetEdit.getText().toString();
                String dueDate = dueDateEdit.getText().toString();
                String howMuch = howMuchEdit.getText().toString();

                targetList.add(target);
                dueDateList.add(dueDate);
                howMuchList.add(howMuch);
                //boolean dbAddResult = chatDataHelp.insertData(result,sqlDb);
                //Log.i(ACTIVITY_NAME, "DB Add Result" + dbAddResult);
                targetAdapter.notifyDataSetChanged();
                targetEdit.setText("");
                dueDateEdit.setText("");
                howMuchEdit.setText("");
            }

        });

        targetAdapter = new TargetAdapter(this);

        lv.setAdapter(targetAdapter);
    }

    public class TargetAdapter extends ArrayAdapter<String> {
        public TargetAdapter(Context ctx) {
            super(ctx,0);
        }

        public int getCount() {

            return targetList.size();
        }

        @Override
        public String getItem(int position) {
            return targetList.get(position);
        }

        public String getDueDate(int position) {
            return dueDateList.get(position);
        }


        public String getHowMuch(int position) {
            return howMuchList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = TargetsActivity.this.getLayoutInflater();

            View result = null;
            result = inflater.inflate(R.layout.target_list, null);

            TextView target = (TextView)result.findViewById(R.id.target);
            TextView dueDate = (TextView)result.findViewById(R.id.dueDate);
            TextView howMuch = (TextView)result.findViewById(R.id.howMuch);
            target.setText( getItem(position)   );
            dueDate.setText( getDueDate(position)   );
            howMuch.setText( getHowMuch(position)   );
            return result;

        }

    }
}