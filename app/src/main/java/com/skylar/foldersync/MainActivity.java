package com.skylar.foldersync;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {


    public String computeHash(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.reset();
        byte[] byteData = digest.digest(input.getBytes("UTF-8"));

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.editText1);
                String in = et.getText().toString();
                TextView tv = (TextView) findViewById(R.id.textView2);
                try {
                    tv.setText(computeHash(in));
                } catch (NoSuchAlgorithmException x) {
                    Log.i("Error", "No such Algorithm");
                } catch (UnsupportedEncodingException x) {
                    Log.i("Error", "Unsupported Encoding");
                }
            }
        });


    }
}
