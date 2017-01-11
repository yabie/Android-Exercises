package com.example.lenovo_pc.myce;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity {
    DBConnect db;
    private LoginButton facebookLoginButton;
    private CallbackManager callbackManager;
    Button fb;
    String facebookId;
    String facebookFirstName;
    String facebookLastName;
    String facebookEmail;
    String facebookGender;
    String facebookBirthday;
    String TAG = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.login);
        db = new DBConnect(this);

        final EditText email = (EditText)findViewById(R.id.login_email);
        final EditText password = (EditText)findViewById(R.id.login_password);
        final Button login = (Button) findViewById(R.id.login_button);
        final TextView register = (TextView) findViewById(R.id.login_register);
//        facebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);

//        List<String> permissionNeeds = Arrays.asList("user_photos", "email",
//                "user_birthday", "public_profile");
//        facebookLoginButton.setReadPermissions(permissionNeeds);
//
//        facebookLoginButton.registerCallback(callbackManager,
//                new FacebookCallback < LoginResult > () {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//
//                        GraphRequest request = GraphRequest.newMeRequest(
//                             loginResult.getAccessToken(),
//                             new GraphRequest.GraphJSONObjectCallback() {
//                                 @Override
//                                 public void onCompleted(JSONObject object,
//                                                         GraphResponse response) {
//                                     try {
//                                         facebookId = object.getString("id");
////                                         try {
////                                             URL profile_pic = new URL(
////                                                     "http://graph.facebook.com/" + facebookId + "/picture?type=large");
////                                             Log.d("profile_pic",
////                                                     profile_pic + "");
////
////                                         } catch (MalformedURLException e) {
////                                             e.printStackTrace();
////                                         }
//                                         facebookEmail = object.getString("email");
//                                         facebookGender = object.getString("gender");
//                                         facebookBirthday = object.getString("birthday");
//                                         facebookFirstName = object.getString("first_name");
//                                         facebookLastName = object.getString("last_name");
//
//                                         Log.d("ID", "onCompleted: " + facebookId);
//                                         Log.d("FIRST_NAME", "onCompleted: " + facebookFirstName);
//                                         Log.d("LAST_NAME", "onCompleted: " + facebookLastName);
//                                         Log.d("EMAIL", "onCompleted: " + facebookEmail);
//                                         Log.d("GENDER", "onCompleted: " + facebookGender);
//                                         Log.d("BIRTHDAY", "onCompleted: " + facebookBirthday);
//
//                                     } catch (JSONException e) {
//                                         Log.e(TAG, "onERRRRRRRRR: ");
////                                         e.printStackTrace();
//                                     }
//                                 }
//                             });
//
//                        Bundle parameters = new Bundle();
//                        parameters.putString("fields", "id,email,gender, birthday, first_name, last_name");
//                        request.setParameters(parameters);
//                        request.executeAsync();
//
//                        boolean status = db.verifyFacebookAccount(facebookEmail);
//                        if(status){
//                            Intent i = new Intent(Login.this,Menu.class);
//                            Login.this.startActivity(i);
//                            Login.this.finish();
//                        }
//                        else {
//                            Log.d(TAG, "onRegister: " + facebookEmail);
//                            db.insertAccount( facebookFirstName, facebookLastName, "", facebookEmail, "");
//                            Intent i = new Intent(Login.this,Menu.class);
//                            Login.this.startActivity(i);
//                            Login.this.finish();
//                        }
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//
//                    }
//                });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean result = false;
                try {
                    result = db.verifyAccount(email.getText().toString(),
                            password.getText().toString());
                } catch (Exception e) {
                   e.printStackTrace();
                } finally {
                    if(result == true) {
                        Toast.makeText(Login.this,"Account Verified !!!",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Login.this,Menu.class);
                        Login.this.startActivity(i);
                        Login.this.finish();
                    }
                    else {
                        Toast.makeText(Login.this,"Failed !!!",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Login.this,Login.class);
                        Login.this.startActivity(i);
                        Login.this.finish();
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Login.this,Register.class);
                Login.this.startActivity(i);
                Login.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are You Sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
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

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
        Log.d(TAG, "onActivityResult: " );
    }


//    public void fbLogin(View view)
//    {
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos", "email", "public_profile", "user_posts" , "AccessToken"));
//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>()
//                {
//                    @Override
//                    public void onSuccess(LoginResult loginResult)
//                    {
//                        Log.d("Success", "Success: ");
//                    }
//
//                    @Override
//                    public void onCancel()
//                    {
//                        Log.d("Cancel", "onCancel: ");
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception)
//                    {
//                        Log.d("Error", "Error: ");
//                    }
//                });
//    }
}
