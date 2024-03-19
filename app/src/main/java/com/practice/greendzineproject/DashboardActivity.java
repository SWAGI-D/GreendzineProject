package com.practice.greendzineproject;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;


public class DashboardActivity extends AppCompatActivity
{

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = this.getIntent();
        String empID = intent.getStringExtra("EMP ID");

        showProgress(empID);

        ImageButton homeBtn = findViewById(R.id.homeBtn);
        ImageButton userBtn = findViewById(R.id.userBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
                intent.putExtra("EMP ID", empID);
                startActivity(intent);
            }
        });

        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
                intent.putExtra("EMP ID", empID);
                startActivity(intent);
            }
        });


    }

    private void showProgress(String empID)
    {
        Resources res = getResources();

        InputStream is = res.openRawResource(R.raw.employee_progress);

        Scanner sc = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while(sc.hasNextLine())
        {
            builder.append(sc.nextLine());
        }

        String jsonString = builder.toString();

        try
        {
            JSONObject obj = new JSONObject(jsonString);

            JSONArray empProgress = obj.getJSONArray("progress");

            for(int i = 0; i < empProgress.length(); i++)
            {
                JSONObject progress = empProgress.getJSONObject(i);

                String idCheck = progress.getString("EMP ID");

                if(idCheck.equals(empID))
                {
                    TextView percentage = findViewById(R.id.monPer);
                    ProgressBar progressBar = findViewById(R.id.monProg);

                    int currProgress = progress.getInt("monday");

                    percentage.setText("" + currProgress + "%");
                    progressBar.setProgress(currProgress);

                    percentage = findViewById(R.id.tuePer);
                    progressBar = findViewById(R.id.tueProg);

                    currProgress = progress.getInt("tuesday");

                    percentage.setText("" + currProgress + "%");
                    progressBar.setProgress(currProgress);

                    percentage = findViewById(R.id.wedPer);
                    progressBar = findViewById(R.id.wedProg);

                    currProgress = progress.getInt("wednesday");

                    percentage.setText("" + currProgress + "%");
                    progressBar.setProgress(currProgress);

                    percentage = findViewById(R.id.thursPer);
                    progressBar = findViewById(R.id.thursProg);

                    currProgress = progress.getInt("thursday");

                    percentage.setText("" + currProgress + "%");
                    progressBar.setProgress(currProgress);

                    percentage = findViewById(R.id.friPer);
                    progressBar = findViewById(R.id.friProg);

                    currProgress = progress.getInt("friday");

                    percentage.setText("" + currProgress + "%");
                    progressBar.setProgress(currProgress);

                    percentage = findViewById(R.id.satPer);
                    progressBar = findViewById(R.id.satProg);

                    currProgress = progress.getInt("saturday");

                    percentage.setText("" + currProgress + "%");
                    progressBar.setProgress(currProgress);


                }


            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
