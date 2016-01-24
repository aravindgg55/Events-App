package com.example.aravind.techies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by aravind on 21/01/16.
 */
public class Selected_item extends Activity {
    TextView textView;
    String name;
    ListView listView;
    ArrayAdapter<String> data;
    Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_item);
        listView=(ListView)findViewById(R.id.listView2);
        //textView=(TextView)findViewById(R.id.item_name);
        Bundle bundle=this.getIntent().getExtras();
        String [] val=bundle.getStringArray("key");
        data=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,val);
        listView.setAdapter(data);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                name=((TextView)view).getText().toString();
               // Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                intent=new Intent(getApplicationContext(),Event.class);
                intent.putExtra("key-2",name);
                startActivity(intent);
            }
        });
        //textView.setText(val[0]);
        //  Intent intent=getIntent();
        //name=intent.getStringExtra("key");
        //textView.setText(name);
    }
}
