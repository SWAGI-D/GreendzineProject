package com.practice.greendzineproject;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = this.getIntent();
        String empID = intent.getStringExtra("EMP ID");

        setOriginalText();

        ImageButton searchBtn = findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchText = findViewById(R.id.searchBar);

                String empName = searchText.getText().toString();

                searchEmployee(empName);


            }
        });

        ImageButton homeBtn = findViewById(R.id.homeBtn);
        ImageButton userBtn = findViewById(R.id.userBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, DashboardActivity.class);
                intent.putExtra("EMP ID", empID);
                startActivity(intent);
            }
        });

        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                intent.putExtra("EMP ID", empID);
                startActivity(intent);
            }
        });
    }

    private void setOriginalText() {
        Resources res = getResources();

        InputStream is = res.openRawResource(R.raw.employee_data);

        Scanner sc = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while (sc.hasNextLine()) {
            builder.append(sc.nextLine());
        }

        String jsonString = builder.toString();


        try {
            JSONObject obj = new JSONObject(jsonString);

            JSONArray employees = obj.getJSONArray("employee");

            for (int i = 0; i < employees.length(); i++)
            {
                JSONObject employee = employees.getJSONObject(i);

                TextView textView;

                if(i == 0)
                {
                    textView=findViewById(R.id.employee1);
                }

                else
                {
                    textView=findViewById(R.id.employee2);
                }

                String one = "<font color=" + Color.LTGRAY + ">EMP ID : </font><font color=" + Color.WHITE + ">" + employee.getString("EMP ID") +  "</font> <br><br>";
                String two = "<font color=" + Color.LTGRAY + ">Name : </font><font color=" + Color.WHITE + ">" + employee.getString("Name") +  "</font> <br><br>";
                String three = "<font color=" + Color.LTGRAY + ">DOB : </font><font color=  #FFA500>" + employee.getString("DOB") +  "</font> <br><br>";
                String four = "<font color=" + Color.LTGRAY + ">Role : </font><font color=  #36A546>" + employee.getString("Role") +  "</font> <br><br>";

                String empProfile = one + "\n" + two + "\n" + three + "\n" + four;

                textView.setText(Html.fromHtml(empProfile));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void searchEmployee(String empName)
    {
        TextView textV = findViewById(R.id.employee2);
        textV.setText("");

        TextView textV2 = findViewById(R.id.employee1);
        textV2.setText("");

        Resources res = getResources();

        InputStream is = res.openRawResource(R.raw.employee_data);

        Scanner sc = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while (sc.hasNextLine()) {
            builder.append(sc.nextLine());
        }

        String jsonString = builder.toString();


        boolean flag = false;

        try {
            JSONObject obj = new JSONObject(jsonString);

            JSONArray employees = obj.getJSONArray("employee");

            for (int i = 0; i < employees.length(); i++)
            {
                JSONObject employee = employees.getJSONObject(i);

                TextView textView = findViewById(R.id.employee1);

                if(employee.getString("Name").equals(empName))
                {

                    flag = true;

                    String one = "<font color=" + Color.LTGRAY + ">EMP ID : </font><font color=" + Color.WHITE + ">" + employee.getString("EMP ID") + "</font> <br><br>";
                    String two = "<font color=" + Color.LTGRAY + ">Name : </font><font color=" + Color.WHITE + ">" + employee.getString("Name") + "</font> <br><br>";
                    String three = "<font color=" + Color.LTGRAY + ">DOB : </font><font color=  #FFA500>" + employee.getString("DOB") + "</font> <br><br>";
                    String four = "<font color=" + Color.LTGRAY + ">Role : </font><font color=  #36A546>" + employee.getString("Role") + "</font> <br><br>";

                    String empProfile = one + "\n" + two + "\n" + three + "\n" + four;

                    textView.setText(Html.fromHtml(empProfile));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!flag)
        {
            Toast.makeText(SearchActivity.this, "No user found", Toast.LENGTH_SHORT).show();

        }
    }
}
