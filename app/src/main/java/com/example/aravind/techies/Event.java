package com.example.aravind.techies;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.transition.ChangeTransform;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Event extends Activity {

    public static final String TEAM_COUNT = "TEAM_COUNT";
    public static final int MAX_UPLOAD_COUNT = 2;
    int teamCount =0;
    ArrayList<Team> teamList;
    Team team;
    Spinner spinner;
    ArrayAdapter<String> spinner_adapter;
    String [] spinner_values;
    TextView textView;
    TextView textView_counts;
    Button button;
    Button upload;
    Intent intent;
    String event_name;

    Button fileButton;
    String restored_event_name;
    int memberCount;
    public static String pref_event="key";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
        sharedPreferences=getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.commit();

        spinner_values=getResources().getStringArray(R.array.spinner_val);
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,spinner_values);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(spinner_adapter);

        teamList = new ArrayList<>();
        upload = (Button) findViewById(R.id.button_upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        textView=(TextView)findViewById(R.id.textView);
        textView_counts=(TextView)findViewById(R.id.codes_count);
        intent=getIntent();
        event_name=intent.getStringExtra("key-2");

        /*editor.putString("event_name",event_name);
        editor.apply();
        restored_event_name=sharedPreferences.getString("event_name",null);
        if(restored_event_name!=null){
            event_name=sharedPreferences.getString("event_name","no name");
            textView.setText(event_name);
        }*/
        textView.setText(event_name);
        editor.putString(pref_event, event_name);
        button=(Button)findViewById(R.id.button_scan);
       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( teamCount == MAX_UPLOAD_COUNT){
                    Toast.makeText(Event.this,"Maximum LIMIT reached",Toast.LENGTH_SHORT).show();
                    // reinitialise teamcount and teamlist
                    teamList.clear();
                    teamCount=0;
                    return;
                }
                memberCount = Integer.parseInt(spinner.getSelectedItem().toString());
                intent=new Intent(getApplicationContext(),Scanner.class);
                intent.putExtra(TEAM_COUNT,memberCount);
                startActivityForResult(intent,45);
            }
        });


        fileButton =  (Button) findViewById(R.id.button_scan);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode){

            case 45:
                ArrayList<CharSequence> collected_data = data.getCharSequenceArrayListExtra(Scanner.RESULT_CODES);
                team = new Team();
                team.participants = (ArrayList<String>) collected_data.clone();
                String finalStr = "";
                for(int i=0;i<memberCount;i++) {
                    finalStr = finalStr + collected_data.get(i) + "\n";
                }
                teamCount++;
                Toast.makeText(getApplicationContext(),""+"Team "+teamCount+"\n"+finalStr, Toast.LENGTH_SHORT).show();
                teamList.add(team);
                if(teamCount == MAX_UPLOAD_COUNT){

                    upload();
                }
                break;
        }
    }

    public void upload(){

        Toast.makeText(Event.this, "Maximum Reached", Toast.LENGTH_SHORT).show();

    }


    public void storeToFile(View view){

        Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show();

    }

}
