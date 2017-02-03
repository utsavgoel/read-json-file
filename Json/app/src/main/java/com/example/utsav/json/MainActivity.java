package com.example.utsav.json;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView json;
    HttpURLConnection  connection=null;
    URL url;
    String stringurl="http://ieeensit.org/ieeemembers.json";
    StringBuffer buffer=new StringBuffer();
    String line=" ";
    String jsondata=new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        json=(TextView) findViewById(R.id.jsonstring);
        new fetchdata().execute();
        json.setMovementMethod(new ScrollingMovementMethod());
    }

    private class fetchdata extends AsyncTask<Void, Void, Void>{

     /*   @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar pb=new ProgressBar();
            pb=(ProgressBar) findViewById(R.id.progressBar);
            pb.setEnabled(true);
        }
       */ @Override
        protected Void doInBackground(Void... params) {
         ProgressBar pb;
         pb=(ProgressBar) findViewById(R.id.progressBar);
         pb.setVisibility(View.INVISIBLE);
            try {
                url=new URL("http://ieeensit.org/ieeemembers.json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                connection=(HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                connection.connect();
                connection.setConnectTimeout(10000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream stream = null;
            try {
                stream = connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader reader=new BufferedReader(new InputStreamReader(stream));
            try {
                while((line=reader.readLine())!=null)
                {
                    buffer.append(line+ '\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            jsondata=buffer.toString();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            json.setText(jsondata);
        }
    }
}
