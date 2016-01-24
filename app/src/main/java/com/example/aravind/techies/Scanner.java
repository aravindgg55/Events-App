package com.example.aravind.techies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by aravind on 22/01/16.
 */
public class Scanner extends Activity implements ZXingScannerView.ResultHandler{
    Intent intent;
    int count=0;
    String num;
    ArrayList<CharSequence> collected_data;
    Button button;
    TextView textView;
    TextView textView_counter;
    LinearLayout linearLayout;
     Integer numbers=0;
    private ZXingScannerView z;

    public static final String RESULT_CODES = "RESULT_CODES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        count  = intent.getIntExtra(Event.TEAM_COUNT,0);
        setContentView(R.layout.scan);
        z=new ZXingScannerView(this);
        button=(Button)findViewById(R.id.b);
        collected_data=new ArrayList<>();
        linearLayout=(LinearLayout)findViewById(R.id.lv);
        textView=(TextView)findViewById(R.id.t);
        textView_counter=(TextView)findViewById(R.id.counter);
        linearLayout.addView(z);
        z.startCamera();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int j;
                if(collected_data.size()<1)
                {
                    Toast.makeText(getApplicationContext(),"No inputs",Toast.LENGTH_SHORT).show();
                }
                for (j = 0; j < collected_data.size(); j++) {

                }

            }
        });
    }
    public void onPause()
    {
        super.onPause();
        z.stopCamera();
    }
    public void onResume()
    {
        super.onResume();
        z.setResultHandler(this);
        z.startCamera();
    }
    @Override
    public void handleResult(Result result) {
        String info=result.getText().toString();

        if(!checkKQrCode(info))
            return;

        if(!collected_data.contains(info)) {
            collected_data.add(info);
            numbers++;
            num=numbers.toString();
            textView_counter.setText(num);
            Toast.makeText(getApplicationContext(),"added",Toast.LENGTH_SHORT).show();
            if(numbers!=count)
                z.startCamera();
            else{
                Intent resultIntent = new Intent();
                resultIntent.putCharSequenceArrayListExtra(RESULT_CODES,collected_data);
                setResult(45, resultIntent);
                finish();

            }
        }
        else {
            z.startCamera();
            Toast.makeText(getApplicationContext(),"Duplicate detected",Toast.LENGTH_SHORT).show();
        }



    }



    boolean checkKQrCode(String code){

        return true;
    }

}