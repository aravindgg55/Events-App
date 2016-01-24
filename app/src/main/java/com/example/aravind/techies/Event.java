package com.example.aravind.techies;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.transition.ChangeTransform;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Event extends Activity {

    public static final String TEAM_COUNT = "TEAM_COUNT";
    public static final int MAX_UPLOAD_COUNT = 5;
    int teamCount =0;

    ArrayList<Team> teamList;
    Team team;

    TextView textView;
    TextView textView_counts;
    Button button;
    Button upload;
    Intent intent;
    String event_name;
    int memberCount;

    EditText editText;
    public static String pref_event="key";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
        sharedPreferences=getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();


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
        editText  = (EditText) findViewById(R.id.editText);
        textView.setText(event_name);
        editor.putString(pref_event,event_name);
        button=(Button)findViewById(R.id.button_scan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText.getText().toString().matches("")){
                    return;
                }
                if( teamCount == MAX_UPLOAD_COUNT){
                    Toast.makeText(Event.this,"Maximum lIMIT reached",Toast.LENGTH_SHORT).show();
                    return;
                }
                memberCount = Integer.parseInt(editText.getText().toString());
                intent=new Intent(getApplicationContext(),Scanner.class);
                intent.putExtra(TEAM_COUNT,memberCount);
                startActivityForResult(intent,45);
            }
        });
        teamList = new ArrayList<>();
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
        //Call services and reinitialise teamlist and teamcount
        teamList.clear();
        teamCount=0;

    }

}
