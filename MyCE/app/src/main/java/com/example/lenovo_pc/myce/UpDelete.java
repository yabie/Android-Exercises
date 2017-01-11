package com.example.lenovo_pc.myce;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lenovo-PC on 08/11/2016.
 */

public class UpDelete extends AppCompatActivity {
    DBConnect db;
    EditText fname = null;
    EditText lname = null;
    EditText position = null;
    EditText email = null;
    ArrayList<ContentValues> employee_list = null;
    int itempos = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updelete);
        db = new DBConnect(this);

//        Initialize Modal
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * .8),(int)(height * .72));

        fname = (EditText) findViewById(R.id.udFName);
        lname = (EditText) findViewById(R.id.udLName);
        position = (EditText) findViewById(R.id.udPosition);
        email = (EditText) findViewById(R.id.udEmail);

        final Button update = (Button) findViewById(R.id.udUpdate);
        final Button delete = (Button) findViewById(R.id.udDelete);
        final TextView close = (TextView) findViewById(R.id.udClose);

        itempos = db.account.getItempos();
        employee_list = db.account.getEmployees();
        employeeData();

//        Update Employee
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                Verify Email
                boolean result = db.verifyEmail("update_employee", employee_list.get(itempos)
                        .get(db.ID).toString(), email.getText().toString());

//                Required Field
                if( fname.getText().toString().equals("")) {
                    Toast.makeText(UpDelete.this,"First Name Required !!!",Toast.LENGTH_LONG).show();
                }
                else if( lname.getText().toString().equals("")) {
                    Toast.makeText(UpDelete.this,"Last Name Required !!!",Toast.LENGTH_LONG).show();
                }
                else if( position.getText().toString().equals("")) {
                    Toast.makeText(UpDelete.this,"Position Required !!!",Toast.LENGTH_LONG).show();
                }
                else if( email.getText().toString().equals("")) {
                    Toast.makeText(UpDelete.this,"Email Required !!!",Toast.LENGTH_LONG).show();
                }
                else if(result == true) {
                    Toast.makeText(UpDelete.this,"Email Exist !!!",Toast.LENGTH_LONG).show();
                }
                else {
                    updateEmployeeData();
                }
            }
        });

//        Delete Employee
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(UpDelete.this);
                builder.setTitle("Delete");
                builder.setMessage("Are You Sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        boolean result = db.removeEmployee(employee_list.get(itempos).get(db.ID).toString());
                        if(result == true) {
                            Toast.makeText(UpDelete.this,"Employee Removed !!!",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(UpDelete.this, Menu.class);
                            UpDelete.this.startActivity(i);
                            UpDelete.this.finish();
                        }
                        else {
                            Toast.makeText(UpDelete.this,"Failed to Remove !!!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

//        Close Modal
        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(UpDelete.this, Menu.class);
                UpDelete.this.startActivity(i);
                UpDelete.this.finish();
            }
        });
    }

    public void updateEmployeeData() {
        boolean result = db.updateEmployee(employee_list.get(itempos).get(db.ID).toString(),
                fname.getText().toString(), lname.getText().toString(),
                position.getText().toString(),email.getText().toString());
        if(result) {
            Toast.makeText(UpDelete.this,"Updated !!!",Toast.LENGTH_LONG).show();
            Intent i = new Intent(UpDelete.this, Menu.class);
            UpDelete.this.startActivity(i);
            UpDelete.this.finish();
        }
        else {
            Toast.makeText(UpDelete.this,"Not Updated !!!",Toast.LENGTH_LONG).show();
        }
    }

    public void employeeData() {
        fname.setText(employee_list.get(itempos).get(db.FNAME).toString());
        lname.setText(employee_list.get(itempos).get(db.LNAME).toString());
        position.setText(employee_list.get(itempos).get(db.POSITION).toString());
        email.setText(employee_list.get(itempos).get(db.EMAIL).toString());
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(UpDelete.this, Menu.class);
        UpDelete.this.startActivity(i);
        UpDelete.this.finish();
    }
}
