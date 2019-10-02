package com.example.pere_jeanphilippe_projet_i2;

import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static java.lang.System.err;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button scanBtn;
    private TextView formatTxt, contentTxt;

    EditText textmsg;
    EditText qtymsg;
    Switch simpleSwitch1;

    static final int READ_BLOCK_SIZE = 100;
    public static final String EXTRA_MESSAGE = "MESSAGE";

    public MainActivity() throws FileNotFoundException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);

        scanBtn.setOnClickListener(this);

        textmsg=(EditText)findViewById(R.id.editText1);
        qtymsg=(EditText)findViewById(R.id.editText2);
        simpleSwitch1= (Switch) findViewById(R.id.switch1);
    }


    public void onClick(View v){

        if(v.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);
            textmsg.setText(scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }




    /*
    public void WriteBtn(View v) {
        try {
            FileOutputStream fileout=openFileOutput("Stock.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(textmsg.getText().toString());
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "Sauvegarde",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    public void WriteBtn(View v) {


            String Article;
            String Quantity;
            int QuantityStock=0;
            String Reception; //(1-> reception or 2->Send/Substraction)
            String filePath;

            Article = textmsg.getText().toString();
            Quantity = qtymsg.getText().toString();
            if (simpleSwitch1.isChecked()) { Reception="1";}
            else {Reception="2";}

            QuantityStock = Integer.parseInt(Quantity);

            filePath = Article + ".txt";


        try {
            FileInputStream fileIn=openFileInput(filePath);
            InputStreamReader InputRead= new InputStreamReader(fileIn);
            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;



            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            //Toast.makeText(getBaseContext(), s,Toast.LENGTH_SHORT).show();

            if (Integer.parseInt(Reception) == 1){
                QuantityStock = QuantityStock + Integer.parseInt(s);
                err.println("Reception");
            }
            else {
                QuantityStock = Integer.parseInt(s) - QuantityStock ;
                err.println("Envoi");
            }






        } catch (Exception e) {
            e.printStackTrace();
            err.println("error2");
        }

        try {

            FileOutputStream fileout=openFileOutput(filePath, MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
           // outputWriter.write(textmsg.getText().toString());
            outputWriter.write(String.valueOf(QuantityStock));
            outputWriter.close();

            //display file saved message
            //Toast.makeText(getBaseContext(), "Sauvegarde", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            err.println("error3");
        }





    }



    public void ReadBtn(View v) {

        try {



            String Article;
            String filePath;

            Article = textmsg.getText().toString();

            filePath = Article + ".txt";

            FileInputStream fileIn=openFileInput(filePath);
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            //Toast.makeText(getBaseContext(), s,Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            err.println("error4");
        }
    }






    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayStock.class);
        EditText editText = (EditText) findViewById(R.id.editText1);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
