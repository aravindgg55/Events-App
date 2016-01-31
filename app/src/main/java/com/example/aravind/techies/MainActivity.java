package com.example.aravind.techies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> data;
    String name;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        listView=(ListView)findViewById(R.id.listView);
        bundle=new Bundle();
        String[] domains=getResources().getStringArray(R.array.domains);
        final String [] management=getResources().getStringArray(R.array.management);
        final String [] general=getResources().getStringArray(R.array.general);
        final String [] engineering=getResources().getStringArray(R.array.engineering);
        final String [] online=getResources().getStringArray(R.array.online);
        final String [] coding=getResources().getStringArray(R.array.coding);
        final String [] quizzes=getResources().getStringArray(R.array.quizzes);
        final String[] robotics=getResources().getStringArray(R.array.robotics);
        data=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,domains);
        listView.setAdapter(data);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                name=((TextView)view).getText().toString();
                Intent intent=new Intent(getApplicationContext(),Selected_item.class);
                if("management".equalsIgnoreCase(name)){
                    bundle.putStringArray("key", management);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if("quizzes".equalsIgnoreCase(name)){
                    bundle.putStringArray("key", quizzes);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if("robotics".equalsIgnoreCase(name)){
                    bundle.putStringArray("key", robotics);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if("coding".equalsIgnoreCase(name)){
                    bundle.putStringArray("key",coding);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if ("online".equalsIgnoreCase(name)){
                    bundle.putStringArray("key", online);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if("general".equalsIgnoreCase(name)) {
                    bundle.putStringArray("key", general);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if ("engineering".equalsIgnoreCase(name)) {
                    bundle.putStringArray("key", engineering);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        });


    }
}