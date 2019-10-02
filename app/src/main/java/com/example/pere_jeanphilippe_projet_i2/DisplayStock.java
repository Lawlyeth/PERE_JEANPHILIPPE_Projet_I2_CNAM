package com.example.pere_jeanphilippe_projet_i2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import static java.lang.System.err;

public class DisplayStock extends AppCompatActivity {

    EditText textmsg;
    EditText qtymsg;
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_stock);



        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        //TextView textView = findViewById(R.id.textView2);


        textmsg=(EditText)findViewById(R.id.editText);
        qtymsg=(EditText)findViewById(R.id.editText3);


        textmsg.setText(message);
        //textView.setText(message);



        try {

            String Article;
            String filePath;

            Article = message;
            filePath = message + ".txt";

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
            qtymsg.setText(s);

        } catch (Exception e) {
            e.printStackTrace();
            err.println("error5");
        }


    }


}
