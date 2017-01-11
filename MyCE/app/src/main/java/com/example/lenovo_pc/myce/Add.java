package com.example.lenovo_pc.myce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Lenovo-PC on 14/11/2016.
 */

public class Add extends AppCompatActivity {
    DBConnect db;
    EditText firstName = null;
    EditText lastName = null;
    EditText position = null;
    EditText email = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        db = new DBConnect(this);

        //        Initialize Modal
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * .8),(int)(height * .72));

        firstName = (EditText)findViewById(R.id.add_first_name);
        lastName = (EditText)findViewById(R.id.add_last_name);
        position = (EditText)findViewById(R.id.add_position);
        email = (EditText)findViewById(R.id.add_email);
        final Button add = (Button) findViewById(R.id.add_button);
        final TextView close = (TextView) findViewById(R.id.add_close);

//         Add Employee
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                Verify Email
                boolean result =db.verifyEmail("add_employee", "",email.getText().toString());

//                Required Field
                if( firstName.getText().toString().equals("")) {
                    Toast.makeText(Add.this,"First Name Required !!!",Toast.LENGTH_LONG).show();
                }
                else if( lastName.getText().toString().equals("")) {
                    Toast.makeText(Add.this,"Last Name Required !!!",Toast.LENGTH_LONG).show();
                }
                else if( email.getText().toString().equals("")) {
                    Toast.makeText(Add.this,"Email Required !!!",Toast.LENGTH_LONG).show();
                }
                else if(result == true) {
                    Toast.makeText(Add.this,"Email Exist !!!",Toast.LENGTH_LONG).show();
                }
                else if( position.getText().toString().equals("")) {
                    Toast.makeText(Add.this,"Position Required !!!",Toast.LENGTH_LONG).show();
                }
                else {
                    addEmployee();
                }
            }
        });

//        Close Modal
        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Add.this, Menu.class);
                Add.this.startActivity(i);
                Add.this.finish();
            }
        });
    }

    public void addEmployee() {
        boolean result = db.insertEmployee(firstName.getText().toString(),
                lastName.getText().toString(), position.getText().toString(),
                email.getText().toString());
        if(result == true){
            Toast.makeText(Add.this,"Employee Added",Toast.LENGTH_LONG).show();
            Intent i = new Intent(Add.this, Menu.class);
            Add.this.startActivity(i);
            Add.this.finish();
        }
        else{
            Toast.makeText(Add.this,"Failed to Add",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Add.this, Menu.class);
        Add.this.startActivity(i);
        Add.this.finish();
    }
}
