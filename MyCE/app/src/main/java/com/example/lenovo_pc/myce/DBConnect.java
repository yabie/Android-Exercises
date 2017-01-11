package com.example.lenovo_pc.myce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

/**
 * Created by Lenovo-PC on 03/11/2016.
 */

public class DBConnect extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    public static Account account = new Account();
    public static final String DATABASE_NAME = "myceapp2.db";
    public static final String TABLE_NAME = "employee";
    public static final String TABLE_MEMBER = "member";
    public static final String ID = "ID";
    public static final String FNAME = "FNAME";
    public static final String LNAME = "LNAME";
    public static final String POSITION = "POSITION";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String LEAD_ID = "LEAD_ID";

    public DBConnect(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, FNAME TEXT, LNAME TEXT, POSITION TEXT, EMAIL TEXT, PASSWORD TEXT);");
        db.execSQL("CREATE TABLE " + TABLE_MEMBER + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, FNAME TEXT, LNAME TEXT, POSITION TEXT, EMAIL TEXT, LEAD_ID TEXT);");
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER + ";");
        onCreate(db);
    }

//    Open Database to Write
    private DBConnect open_write() {
        try {
            db = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return this;
        }
    }

//    Open Database to Read
    private DBConnect open_read() {
        try {
            db = getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return this;
        }
    }

//    Close Database
    public void close() {
        db.close();
    }

    public boolean verifyAccount(String email, String password) {
        Cursor account_data = null;
        boolean result = false;
        try {
            open_read();
            account_data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL='" + email +
                "' AND PASSWORD='" + password + "'", null);
            if (account_data.getCount() == 1) {
                account_data.moveToFirst();
                account.setId(account_data.getString(account_data.getColumnIndex(ID)));
                account.setFirstName(account_data.getString(account_data.getColumnIndex(FNAME)));
                account.setLastName(account_data.getString(account_data.getColumnIndex(LNAME)));
                account.setPosition(account_data.getString(account_data.getColumnIndex(POSITION)));
                account.setEmail(email);
                account.setPassword(password);
                result = true;
            } else {
//                Do Nothing
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
            assert account_data != null;
            account_data.close();
            return result;
        }
    }

    public boolean verifyEmail(String type, String id, String email) {
        boolean result = false;
        Cursor status = null;
        try {
            open_read();
//            Check Type
            if(type.equals("account")) {
                status = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL='" + email +
                        "'", null);
            }
            else if(type.equals("add_employee")){
                status = db.rawQuery("SELECT * FROM " + TABLE_MEMBER + " WHERE EMAIL='" + email +
                        "' AND LEAD_ID='" + account.getId() + "'", null);
            }
            else {
                status = db.rawQuery("SELECT * FROM " + TABLE_MEMBER + " WHERE EMAIL='" + email +
                        "' AND LEAD_ID='" + account.getId() + "' AND ID!='" + id + "'", null);
            }

//            Check Status
            if (status.getCount() == 1) {
                result = true;
            } else {
//                Do Nothing
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            assert status != null;
            status.close();
            return result;
        }
    }

    public boolean verifyFacebookAccount(String email) {
        Cursor account_data = null;
        boolean result = false;
        try {
            open_read();
            account_data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL='" + email +
                    "'", null);
            if (account_data.getCount() == 1) {
                account_data.moveToFirst();
                account.setId(account_data.getString(account_data.getColumnIndex(ID)));
                account.setFirstName(account_data.getString(account_data.getColumnIndex(FNAME)));
                account.setLastName(account_data.getString(account_data.getColumnIndex(LNAME)));
                account.setPosition(account_data.getString(account_data.getColumnIndex(POSITION)));
                account.setEmail(email);
                result = true;
            } else {
//                Do Nothing
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
            assert account_data != null;
            account_data.close();
            return result;
        }
    }

    public boolean loadEmployees() {
        Cursor account_data = null;
        boolean result = false;
        try {
            open_read();
            account_data = db.rawQuery("SELECT * FROM " + TABLE_MEMBER + " WHERE LEAD_ID='" + account.getId() + "'", null);
            ArrayList<ContentValues> employees = new ArrayList<ContentValues>();
            while (account_data.moveToNext()) {
                ContentValues content = new ContentValues();
                content.put(ID,account_data.getString(account_data.getColumnIndex(ID)));
                content.put(FNAME,account_data.getString(account_data.getColumnIndex(FNAME)));
                content.put(LNAME,account_data.getString(account_data.getColumnIndex(LNAME)));
                content.put(POSITION,account_data.getString(account_data.getColumnIndex(POSITION)));
                content.put(EMAIL,account_data.getString(account_data.getColumnIndex(EMAIL)));
                content.put(LEAD_ID,account_data.getString(account_data.getColumnIndex(LEAD_ID)));
                employees.add(content);
            }
            account.setEmployees(employees);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
            assert account_data != null;
            account_data.close();
            return result;
        }
    }

    public boolean insertAccount(String fname, String lname, String position, String email,
                                 String password) {
        boolean result = false;
        try {
            open_write();
            ContentValues contentValues = new ContentValues();
            contentValues.put(FNAME, fname);
            contentValues.put(LNAME, lname);
            contentValues.put(POSITION, position);
            contentValues.put(EMAIL, email);
            contentValues.put(PASSWORD, password);
            long status = db.insert(TABLE_NAME, null, contentValues);
            if (status == -1) {
//                Do Nothing
            } else {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
            return result;
        }
    }

    public boolean insertEmployee(String fname, String lname, String position, String email) {
        boolean result = false;
        try {
            open_write();
            ContentValues contentValues = new ContentValues();
            contentValues.put(FNAME, fname);
            contentValues.put(LNAME, lname);
            contentValues.put(POSITION, position);
            contentValues.put(EMAIL, email);
            contentValues.put(LEAD_ID, account.getId());
            long status = db.insert(TABLE_MEMBER, null, contentValues);
            if (status == -1) {
//                Do Nothing
            } else {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
            return result;
        }
    }

    public boolean updateEmployee(String id, String fname, String lname, String position, String email) {
        boolean result = false;
        try {
            open_write();
            ContentValues contentValues = new ContentValues();
            contentValues.put(FNAME, fname);
            contentValues.put(LNAME, lname);
            contentValues.put(POSITION, position);
            contentValues.put(EMAIL, email);
            String condition = "ID='" + id + "' AND LEAD_ID='" + account.getId() + "'";
            int status = db.update(TABLE_MEMBER, contentValues, condition , null);
            if (status == 1) {
                result = true;
            }
            else {
//                Do Nothing
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
            return result;
        }
    }

    public boolean removeEmployee(String id) {
        boolean result = false;
        try {
            open_write();
            int status = db.delete(TABLE_MEMBER, "ID='" + id +
                    "' AND LEAD_ID='" + account.getId() + "'", null);
            if (status == 1) {
                result = true;
            } else {
//                Do Nothing
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            return result;
        }
    }

    public boolean removeAccount(String email) {
        boolean result = false;
        try {
            open_write();
            int status = db.delete(TABLE_NAME, "EMAIL='" + email + "'", null);
            if (status == 1) {
                result = true;
            } else {
//                Do Nothing
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            return result;
        }
    }
}
