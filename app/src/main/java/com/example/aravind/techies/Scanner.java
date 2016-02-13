package com.example.aravind.techies;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
    AlertDialog alertDialog;
    ArrayList<CharSequence> collected_data;
    Button button;
    TextView textView;
    TextView textView_counter;
    LinearLayout linearLayout;
    Integer numbers=0;


    public static final int codeLen = 8;

    private ZXingScannerView z;

    public static final String RESULT_CODES = "RESULT_CODES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        count  = intent.getIntExtra(Event.TEAM_COUNT, 0);

        setContentView(R.layout.scan);
        z=new ZXingScannerView(this);
        linearLayout=(LinearLayout)findViewById(R.id.lv);
        linearLayout.addView(z);
        button=(Button)findViewById(R.id.b);
        collected_data=new ArrayList<>();
        textView=(TextView)findViewById(R.id.t);
        textView_counter=(TextView)findViewById(R.id.counter);
    }

    public void onPause() {
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
        Toast.makeText(this,"Scanned: "+info,Toast.LENGTH_SHORT).show();

        if(checkKQrCode(info)) {

            if (!collected_data.contains(info)) {
                collected_data.add(info);
                numbers++;
                num = numbers.toString();
                textView_counter.setText(num);
                Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_SHORT).show();
                if (numbers != count)
                    z.resumeCameraPreview(this);
                else {
                    Intent resultIntent = new Intent();
                    resultIntent.putCharSequenceArrayListExtra(RESULT_CODES, collected_data);
                    setResult(45, resultIntent);
                    finish();
                }
            } else {
                z.resumeCameraPreview(this);
                Toast.makeText(getApplicationContext(), "Duplicate detected", Toast.LENGTH_SHORT).show();
            }

        }


        else{
            Toast.makeText(this,"Invalid QR Code: "+info,Toast.LENGTH_SHORT).show();
            z.resumeCameraPreview(this);
        }
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertDialgBuilder=new AlertDialog.Builder(this);
        alertDialgBuilder.setTitle("Do you want to Discard");
        alertDialgBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent();
                setResult(45,intent);
                finish();
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
      //  Toast.makeText(getApplicationContext(),"back pressed",Toast.LENGTH_SHORT).show();

    }

    boolean checkKQrCode(String code){

        if(code.length() != codeLen)
            return false;


        String prefix = "KQ16";

        if(!code.substring(0,4).equals(prefix))
            return false;

        try{
            Integer.parseInt(code.substring(4));
        }
        catch(Exception e){
            return false;
        }

        return true;
    }

}