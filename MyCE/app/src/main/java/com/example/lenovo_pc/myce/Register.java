package com.example.lenovo_pc.myce;

/**
 * Created by Lenovo-PC on 05/11/2016.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private DBConnect db;
    EditText fname = null;
    EditText lname = null;
    EditText position = null;
    EditText email = null;
    EditText password = null;
    EditText cpassword = null;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
//        db = new DBConnect(this);
//
        fname = (EditText)findViewById(R.id.rFName);
        lname = (EditText)findViewById(R.id.rLName);
        position = (EditText)findViewById(R.id.rPosition);
        email = (EditText)findViewById(R.id.rEmail);
        password = (EditText)findViewById(R.id.rPassword);
        cpassword = (EditText)findViewById(R.id.rCPassword);
        final Button register = (Button) findViewById(R.id.rRegister);
//
                firebaseAuth = FirebaseAuth.getInstance();
//        Register
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
////                Verify Email
//                boolean result =db.verifyEmail("account", "",email.getText().toString());
//
////                Required Fields
//                if( fname.getText().toString().equals("")) {
//                    Toast.makeText(Register.this,"First Name Required !!!",Toast.LENGTH_LONG).show();
//                }
//                else if( lname.getText().toString().equals("")) {
//                    Toast.makeText(Register.this,"Last Name Required !!!",Toast.LENGTH_LONG).show();
//                }
//                else if( position.getText().toString().equals("")) {
//                    Toast.makeText(Register.this,"Position Required !!!",Toast.LENGTH_LONG).show();
//                }
//                else if( email.getText().toString().equals("")) {
//                    Toast.makeText(Register.this,"Email Required !!!",Toast.LENGTH_LONG).show();
//                }
//                else if(result == true) {
//                    Toast.makeText(Register.this,"Email Exist !!!",Toast.LENGTH_LONG).show();
//                }
//                else if( password.getText().toString().equals("")) {
//                    Toast.makeText(Register.this,"Password Required !!!",Toast.LENGTH_LONG).show();
//                }
//                else if( password.getText().toString().equals(cpassword.getText().toString()) == false) {
//                    Toast.makeText(Register.this,"Password not Match !!!",Toast.LENGTH_LONG).show();
//                }
//                else {
//                    registerAccount();
//                }
                registerUser();
            }
        });
    }

    public void registerAccount() {
        boolean result = db.insertAccount(fname.getText().toString(),
                lname.getText().toString(), position.getText().toString(),
                email.getText().toString(), password.getText().toString());
        if(result == true){
            Toast.makeText(Register.this,"Registered !!!",Toast.LENGTH_LONG).show();
            Intent i = new Intent(Register.this,Login.class);
            Register.this.startActivity(i);
            Register.this.finish();
        }
        else{
            Toast.makeText(Register.this,"Failed !!!",Toast.LENGTH_LONG).show();
            Intent i = new Intent(Register.this,Register.class);
            Register.this.startActivity(i);
            Register.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Register.this,Login.class);
        Register.this.startActivity(i);
        Register.this.finish();
    }

    private void registerUser(){

        //getting email and password from edit texts
//        String email = editTextEmail.getText().toString().trim();
//        String password = editTextPassword.getText().toString().trim();

//        checking if email and passwords are empty
        if(TextUtils.isEmpty(email.getText().toString())){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password.getText().toString())){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
        //checking if success
            if(task.isSuccessful()){
                //display some message here
                Toast.makeText(Register.this,"Successfully registered",Toast.LENGTH_LONG).show();
                Log.d("REGISTERED", "onComplete: ");
            }else{
                //display some message here
                Toast.makeText(Register.this,"Registration Error",Toast.LENGTH_LONG).show();
                Log.d("ERROR", "onComplete: ");
            }
            }
        });
    }

//    @Override
//    public void onClick(View view) {
//        //calling register method on click
//        registerUser();
//    }
}