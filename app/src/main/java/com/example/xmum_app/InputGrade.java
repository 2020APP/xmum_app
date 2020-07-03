package com.example.xmum_app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InputGrade extends AppCompatActivity{

    private EditText etStudentID, etCourseName, etGrade;
    private Button btInputGrade;
    private String studentid, coursename, grade;
    private String grade_url = "http://10.0.2.2:80/xmum_app_server/grade.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_grade);
        etStudentID = (EditText)findViewById(R.id.Studentid_edit);
        etCourseName = (EditText)findViewById(R.id.CourseName_edit);
        etGrade = (EditText)findViewById(R.id.Grade_edit);
        btInputGrade = (Button) findViewById(R.id.btnInputGrade);


        btInputGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input();
            }
        });
    }
        private void input(){
            studentid = etStudentID.getText().toString().toLowerCase().trim();
            coursename = etCourseName.getText().toString().trim();
            grade = etGrade.getText().toString().trim();
            inputGrade();
        }

    private void inputGrade(){
        String urlsuffix="?studentid="+studentid+"&coursename="+coursename+"&grade"+grade;
        class inputResult extends AsyncTask<String,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading=ProgressDialog.show(InputGrade.this,"Please Wait",null,true,true);
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(),"Internet not found",Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader=null;
                try{
                    URL url=new URL(grade_url+s);
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    bufferedReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String result;
                    result=bufferedReader.readLine();
                    return result;
                }catch (Exception e){
                    return null;
                }

            }
        }
        inputResult ur= new inputResult();
        ur.execute(urlsuffix);

    }






}
