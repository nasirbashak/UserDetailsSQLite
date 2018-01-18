package com.nasirbashak007.usersqlite;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewDataActivity extends AppCompatActivity {

    ListView listView;
    DetailsListAdapter myAdapter;


    ArrayList<PersonDetails> list;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        Toast.makeText(getApplicationContext(), "Second Activity", Toast.LENGTH_LONG).show();

        listView = (ListView) findViewById(R.id.listview);

        list = new ArrayList<>();

        myAdapter = new DetailsListAdapter(this, R.layout.data_items, list);
        listView.setAdapter(myAdapter);

        // Get All The Data From The SQLite

        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM USER");
        list.clear();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new PersonDetails(id, name, email, image));


        }
        myAdapter.notifyDataSetChanged();
    }
}
