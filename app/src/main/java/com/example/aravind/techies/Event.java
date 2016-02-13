package com.example.aravind.techies;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Event extends Activity {

    public static final String FILE_CHECK = "FILE_CHECK";
    public static final String TEAM_COUNT = "TEAM_COUNT";
    public static final String LEVEL = "LEVEL";

    public static final int MAX_UPLOAD_COUNT = 10;
    public static int teamCount =0;
    public static ArrayList<Team> teamList;
    Team team;
    Spinner spinner,level_spinner;
    ArrayAdapter<String> spinner_adapter,level_spinner_adapter;
    String [] spinner_values,level_spinner_values;
    TextView textView;
    TextView textView_counts;
    Button button;
    Button upload;

    String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    Intent intent;
    String event_name;
    ListView listView;
    ArrayAdapter<Team> adapter;
    Button fileButton;
    String restored_event_name;
    String event;
    int memberCount;
    public static String pref_event="key";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;
    public  static String filename;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
        sharedPreferences=getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.commit();
        event = getIntent().getStringExtra("key-2");
        spinner_values=getResources().getStringArray(R.array.spinner_val);
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,spinner_values);

        spinner_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(spinner_adapter);

        level_spinner_values=getResources().getStringArray(R.array.level_spinner_val);
        level_spinner=(Spinner)findViewById(R.id.level_spinner);
        spinner_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,level_spinner_values);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        level_spinner.setAdapter(spinner_adapter);

        teamList = new ArrayList<>();
        upload = (Button) findViewById(R.id.button_upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teamList.size() ==0){
                    filename = event_name+"-"+level_spinner.getSelectedItem().toString()+".txt";
                    if(sharedPreferences.getBoolean(filename,false)) {
                        PostScanned p = new PostScanned();
                        String json = getJSONFromFile();
                        if (json != null) {
                            p.execute(json);
                        } else {
                            Toast.makeText(Event.this, "File not found, Please write to file", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(Event.this, "No file to upload", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(Event.this,"Please write to file before upload",Toast.LENGTH_LONG).show();
                }
            }
        });

        textView=(TextView)findViewById(R.id.textView);
        textView_counts=(TextView)findViewById(R.id.codes_count);
        intent=getIntent();
        event_name=intent.getStringExtra("key-2");


        textView.setText(event_name);
        editor.putString(pref_event, event_name);
        button=(Button)findViewById(R.id.button_scan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( teamCount == MAX_UPLOAD_COUNT){
                    Toast.makeText(Event.this,"Maximum LIMIT reached",Toast.LENGTH_SHORT).show();
                    teamCount=0;
                    return;
                }

                memberCount = Integer.parseInt(spinner.getSelectedItem().toString());
                intent=new Intent(getApplicationContext(),Scanner.class);
                intent.putExtra(TEAM_COUNT,memberCount);
                intent.putExtra(LEVEL,spinner.getSelectedItem().toString());
                startActivityForResult(intent,45);
            }
        });


        fileButton =  (Button) findViewById(R.id.button_scan);

        listView = (ListView) findViewById(R.id.teamList);
        adapter = new ArrayAdapter<Team>(this,
               android.R.layout.simple_list_item_1,teamList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alertDialgBuilder=new AlertDialog.Builder(Event.this);
                alertDialgBuilder.setTitle("Do you want to remove this team?");
                alertDialgBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        teamList.remove(position);
                        teamCount--;
                        adapter.notifyDataSetChanged();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                AlertDialog alertDialog=alertDialgBuilder.create();
                alertDialog.show();

            }
        });
    }

    private String getJSONFromFile() {
        File inFile = new File(filePath, filename);
        InputStream in;
            String line;
        try {
            in = new FileInputStream(inFile);
            StringBuffer sb = new StringBuffer("");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while((line = reader.readLine())!=null){
                    sb.append(line);
                }
            in.close();
            return sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode){

            case 45:
                if(data == null)
                    return;
                ArrayList<CharSequence> collected_data = data.getCharSequenceArrayListExtra(Scanner.RESULT_CODES);
                if(collected_data==null) {
                    Toast.makeText(this,"Aborted",Toast.LENGTH_SHORT).show();
                    return;
                }
                team = new Team();
                team.participants = (ArrayList<String>) collected_data.clone();
                String finalStr = "";
                for(int i=0;i<memberCount;i++) {
                    finalStr = finalStr + team.participants.get(i) + "\n";
                }
                teamCount++;
                Toast.makeText(getApplicationContext(),""+"Team "+teamCount+"\n"+finalStr, Toast.LENGTH_SHORT).show();
                teamList.add(team);
                listView.setAdapter(adapter);

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

//        Toast.makeText(this,filePath,Toast.LENGTH_SHORT).show();


        String json="",oldJson="",level=level_spinner.getSelectedItem().toString();
        filename = event_name+"-"+level+".txt";
        if(!teamList.isEmpty())
            json = Utility.generateJSON(teamList,level,event_name) ;
        else{
            Toast.makeText(this,"Team List is empty!",Toast.LENGTH_SHORT).show();
            return;
        }


        sharedPreferences=getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        if(sharedPreferences.getBoolean(filename,false)) {

            InputStream in;
            File inFile = new File(filePath, filename);
            try{

                String line;
                in = new FileInputStream(inFile);
                StringBuffer sb = new StringBuffer("");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while((line = reader.readLine())!=null){
                    sb.append(line);
                }
                oldJson = sb.toString();
                in.close();
               // Toast.makeText(this,oldJson,Toast.LENGTH_SHORT).show();


                JSONObject jsonObject = new JSONObject(oldJson);
                JSONArray jsonArray = jsonObject.getJSONArray("teams");
                //Toast.makeText(this,jsonArray.toString(),Toast.LENGTH_SHORT).show();
                jsonObject.remove("teams");
                //Toast.makeText(this,jsonObject.toString(),Toast.LENGTH_SHORT).show();
                int i=0;
                for(i=0;i<teamList.size();i++){
                    JSONObject team  = new JSONObject(Utility.generateTeamJson(teamList.get(i),"0"));
                    jsonArray.put(team);
                }
                jsonObject.put("teams", jsonArray);
                json = jsonObject.toString();

            } catch (FileNotFoundException e) {
                final SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean(filename,false);
                editor.commit();
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        // Handle empty list

        Toast.makeText(this,json,Toast.LENGTH_SHORT).show();
        FileOutputStream out;
        File outFile = new File(filePath , filename);
        try {
            out = new FileOutputStream(outFile);
            out.write(json.getBytes());
            out.close();
        }
        catch (Exception e){

        }

        final SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(filename,true);
        editor.commit();

        teamList.clear();
        adapter.notifyDataSetChanged();
        teamCount=0;

    }

    class PostScanned extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String uss=params[0];
            String link="http://techteam.kurukshetra.org.in/app_scripts/upload_result.php";
            String data;
            try {
                data = URLEncoder.encode("json", "UTF-8") + "=" + URLEncoder.encode(uss, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                Log.d("Yeah:", sb.toString());
                return "success";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }


        protected void onPostExecute(String r){
            if(r.equals("success")) {
                Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Unable to upload, Check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
