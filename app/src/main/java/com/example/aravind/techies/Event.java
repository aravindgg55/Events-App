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

/**
 * Created by aravind on 22/01/16.
 */
public class Event extends Activity {


    public static final String TEAM_COUNT = "TEAM_COUNT";


    TextView textView;
    TextView textView_counts;
    Button button;
    Button upload;
    Intent intent;
    Intent intent2;
   String event_name;
    int teamCount;

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

        //pref_event=event_name;
        //Toast.makeText(getApplicationContext(),event_name,Toast.LENGTH_SHORT).show();
        textView.setText(event_name);
        editor.putString(pref_event,event_name);
      // intent2=getIntent();
      //  count=intent.getStringExtra("Key-3");
       // textView_counts.setText(count);
        button=(Button)findViewById(R.id.button_scan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText.getText().toString().matches("")){
                    return;
                }
                teamCount = Integer.parseInt(editText.getText().toString());
                intent=new Intent(getApplicationContext(),Scanner.class);
                intent.putExtra(TEAM_COUNT,teamCount);
                startActivityForResult(intent,45);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode){


            case 45:

//                Toast.makeText(this,"a "+data.getStringExtra(Scanner.RESULT_CODES),Toast.LENGTH_SHORT).show();

                ArrayList<CharSequence> collected_data = data.getCharSequenceArrayListExtra(Scanner.RESULT_CODES);

                String finalStr = "";
                for(int i=0;i<teamCount;i++)
                    finalStr = finalStr + collected_data.get(i) + "\n";

                Toast.makeText(getApplicationContext(), finalStr, Toast.LENGTH_SHORT).show();

                break;
        }
    }

}
