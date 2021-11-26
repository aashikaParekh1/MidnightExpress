package com.example.midnightexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FinalActivity extends AppCompatActivity {

    JSONObject data;

    String uniE;
    String cityE;
    String stateE;

    String [] address = {uniE, cityE, stateE};

    EditText uni;
    EditText city;
    EditText state;
    Button calculate;
    TextView showDis;
    TextView totalBill;
    Button goBack;
    TextView name;
    TextView number;
    Button submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        uni = findViewById(R.id.id_uni);
        city = findViewById(R.id.id_city);
        state = findViewById(R.id.id_state);
        calculate = findViewById(R.id.id_getTime);
        showDis = findViewById(R.id.id_dis);
        totalBill = findViewById(R.id.id_tot);
        goBack = findViewById(R.id.id_backButton);
        submit = findViewById(R.id.id_finalPlace);


        Intent mIntent = getIntent();
        final int intValue = mIntent.getIntExtra("key_int", 0);
        Log.d("SOMETHING", String.valueOf(intValue));

            calculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ENTRY", uni.getText().toString());
                    if (uni.getText().toString().equals("")) {
                        Toast.makeText(FinalActivity.this, "Please enter a school", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        uniE = uni.getText().toString().replace(" ", "-");
                        cityE = city.getText().toString().replace(" ", "-");
                        stateE = state.getText().toString().replace(" ", "-");

                        address = new String[]{uniE, cityE, stateE};


                        new JsonTask().execute(address);
                        totalBill.setText(" Total: $" + intValue);
                        submit.setEnabled(true);
                    }

                }


            });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FinalActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Cancelation");
                builder.setMessage("Once you hit cancel, the order won't be saved anymore. It will take you back to the menu options.");

                builder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(FinalActivity.this, CartActivity.class));
                    }
                });
                builder.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FinalActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Finalize Order!");
                builder.setMessage("By clicking confirm, you are finalizing your order and letting us know to start your delivery.");

                builder.setNegativeButton("Not yet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(FinalActivity.this, MessageActivity.class));
                    }
                });
                builder.show();


            }
        });




    }


    public class JsonTask extends AsyncTask<String, Void, Void>
    {

        String time;
        String duration;


        @Override
        protected Void doInBackground(String... params) {

            Log.d("URL", "https://maps.googleapis.com/maps/api/distancematrix/json?origins=750+Ridge+Road,%20Monmouth-Junction,NJ&destinations=" + address[0] + "," + address[1] + "," + address[2] +"&mode=driving&key=AIzaSyCMboHAAnLLGxS-ZbrKEhHpcHSjYKN5QSo");



            try {
                URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins=750+Ridge+Road,%20Monmouth-Junction,NJ&destinations=" + address[0] + "," + address[1] + "," + address[2] +"&mode=driving&key=AIzaSyCMboHAAnLLGxS-ZbrKEhHpcHSjYKN5QSo");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String stat = readJSON(reader);
                data = new JSONObject(stat);

                Log.d("DATA", String.valueOf(data));

                JSONObject root=new JSONObject(stat);
                JSONArray array_rows=root.getJSONArray("rows");
                Log.d("JSON","array_rows:"+array_rows);
                JSONObject object_rows=array_rows.getJSONObject(0);
                Log.d("JSON","object_rows:"+object_rows);
                JSONArray array_elements=object_rows.getJSONArray("elements");
                Log.d("JSON","array_elements:"+array_elements);
                JSONObject  object_elements=array_elements.getJSONObject(0);
                Log.d("JSON","object_elements:"+object_elements);
                JSONObject object_duration=object_elements.getJSONObject("duration");
                time = object_duration.getString("text");
                Log.d("ONPOST", time);
                //Log.d("JSON","object_duration:"+object_duration);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;



        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            showDis.setText(" Delivery Time: " + time);
            //Log.d("ONPOST", time);
        }
    }

    public String readJSON(BufferedReader buffIn) throws IOException {

        StringBuffer buffer = new StringBuffer();
        String line = "";

        while ((line = buffIn.readLine()) != null) {
            buffer.append(line + "\n");
            Log.d("JSON: ", "> " + line);   //here u ll get whole response...... :-)
        }
        return buffer.toString();
    }

}