package com.acadgild.s8A4WriteExternalFile;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;

    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text_view);
        textView.setMovementMethod(new ScrollingMovementMethod());

        editText = (EditText) findViewById(R.id.edit);

    }

    public void writeExternalStorage(View view){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/S8A4WriteExternalFile");
            if (!dir.exists())
                dir.mkdir();

            String msg = textView.getText().toString();
            msg += editText.getText().toString();

            file = new File(dir, "text.txt");
            try {

                if (!file.exists())
                    file.createNewFile();

                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(msg.getBytes());
                outputStream.close();

                editText.requestFocus();
                editText.setText("");
                Toast.makeText(getApplicationContext(), "Message Saved", Toast.LENGTH_LONG).show();


                FileInputStream inputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();

                while ((msg = bufferedReader.readLine()) != null){
                    stringBuffer.append(msg + "\n");
                }

                textView.setText(stringBuffer.toString());



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(getApplicationContext(), "External storage not found", Toast.LENGTH_LONG).show();
        }

    }

    public void deleteAll(View view){
        if (file.exists())
            file.delete();

        textView.setText("");
    }


}