package com.practice.greendzineproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText passwordEntry = findViewById(R.id.password);
        EditText userEmail = findViewById(R.id.email);

        Button loginBtn = findViewById(R.id.login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are getting data from our edit text.
                String userName = userEmail.getText().toString();
                String password = passwordEntry.getText().toString();

                // checking if the entered text is empty or not.
                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Please enter email id and password", Toast.LENGTH_SHORT).show();
                }

                // calling a method to login our user.
                    loginUser(userName, password);

            }
        });
    }
    private void loginUser(String userName, String password)
    {
        Resources res = getResources();

        InputStream is = res.openRawResource(R.raw.employee_data);

        Scanner sc = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while(sc.hasNextLine())
        {
            builder.append(sc.nextLine());
        }

        String jsonString = builder.toString();

        boolean flag = false;

        try
        {
            JSONObject obj = new JSONObject(jsonString);

            JSONArray employees = obj.getJSONArray("employee");

            for(int i = 0; i < employees.length(); i++)
            {
                JSONObject employee = employees.getJSONObject(i);

                String emailCheck = employee.getString("email");
                String passwordCheck = employee.getString("password");

                if(emailCheck.equals(userName) && passwordCheck.equals(password))
                {
                    flag = true;
                    String idNum = employee.getString("EMP ID");
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    intent.putExtra("EMP ID", idNum);
                    startActivity(intent);


                }


            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(!flag)
        {
            Toast.makeText(MainActivity.this, "Invalid email address and/or password", Toast.LENGTH_SHORT).show();
        }
    }

}