package com.example.celiachen.lectureexample0305;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText textmsg;
    TextView textContent;
    Button externalSave, externalRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textmsg = findViewById(R.id.editText);
        textContent = findViewById(R.id.textView);
        externalSave = findViewById(R.id.external_write);
        externalRead = findViewById(R.id.external_read);

        // save externally
        externalSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // same thing as the file output stream
                try{
                    FileOutputStream fos = new FileOutputStream(
                            new File(getExternalFilesDir(null), "external_file.txt"));
                    // first parameter -> file path
                    // second parameter -> file name
                    fos.write(textmsg.getText().toString().getBytes());
                    fos.close();

                    textContent.setText("Saved externally!");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    // everytime when you want to access external storage
    // you need to make sure that the external storage is available

    private static boolean isExternalStorageAvailable(){
        // we check the status of external storage
        // if it is true, return true
        String externalStorageStatus = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(externalStorageStatus)){
            return true;
        }
        else{
            return false;
        }
    }

    // file permission
    // read, write, execute
    // chmod 777
    // what if the file is set to read only?
    public static boolean isExternalStorageReadOnly(){

        String externalStorageStatus = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(externalStorageStatus)){
            return true;
        }
        else{
            return false;
        }
    }

    public void WriteInternalBtn(View view){
        //add the text into a file
        // add the text into a txt file and store it internally
        try {
            FileOutputStream fileout = openFileOutput("internal_text.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(textmsg.getText().toString());
            outputWriter.close();
            // toast pop up saying file is successfully saved
            Toast.makeText(getBaseContext(), "File is saved!", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void ReadInternalBtn(View view){

        // file input stream to open the file
        // for each character in the file
        // add it to a String
        // display
        try {
            FileInputStream fileIn = openFileInput("internal_text.txt");
            InputStreamReader inputRead = new InputStreamReader(fileIn);

            // input buffer -> character array
            // string -> result that you want to display

            char[] inputBuffer = new char[100];
            String content = "";
            int charRead;

            while ((charRead=inputRead.read(inputBuffer)) > 0){
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                content += readString;
            }
            inputRead.close();
            textContent.setText(content);


        } catch (Exception e){
            e.printStackTrace();
        }












    }
}
