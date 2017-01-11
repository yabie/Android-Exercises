package com.example.lenovo_pc.myce;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {
    DBConnect db;
    GridView gridview = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        db = new DBConnect(this);

//        LoginManager.getInstance().logOut();

        final TextView add = (TextView) findViewById(R.id.menu_add_button);
        gridview = (GridView) findViewById(R.id.menu_employee);

//         Display Employee
        displayEmployees();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                db.account.setItempos(position);
                Intent i = new Intent(Menu.this,UpDelete.class);
                Menu.this.startActivity(i);
            }
        });


        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Menu.this,Add.class);
                Menu.this.startActivity(i);
            }
        });
    }

    public void displayEmployees() {
        Boolean result = db.loadEmployees();
        if (result == true) {
            ArrayList<ContentValues> employee_list = db.account.getEmployees();
            ArrayList<String> employee_names = new ArrayList<String>();
            int ctr;
            for (ctr = 0; ctr < employee_list.size(); ctr++) {
                String names = employee_list.get(ctr).get(db.FNAME).toString() + " " +
                        employee_list.get(ctr).get(db.LNAME).toString();
                employee_names.add(names);
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                    employee_names);
            gridview.setAdapter(arrayAdapter);
        } else {
//             Do Nothing
        }
    }

//    Logout
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are You Sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent i = new Intent(Menu.this,Login.class);
                Menu.this.startActivity(i);
                Menu.this.finish();
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
//
//    @Override
//    public void onDestroy() {
//        LoginManager.getInstance().logOut();
//    }
}
