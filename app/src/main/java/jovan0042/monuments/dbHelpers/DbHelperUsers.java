package jovan0042.monuments.dbHelpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelperUsers extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Database";

    public DbHelperUsers(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String exec = "CREATE TABLE IF NOT EXISTS Users (eMail TEXT, password TEXT, loggedIn INT DEFAULT 0)";
        db.execSQL(exec);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    //Adding user in table Users
    public void addUser(String eMail, String password) {
        if(!doesExist(eMail)) {
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "INSERT INTO Users (eMail, password, loggedIn) VALUES (\'" + eMail + "\', \'" + password + "\', 0);";
            db.execSQL(query);
        }
    }

    //Check if specific user exist in table Users
    public boolean doesExist(String eMail) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Users WHERE eMail = \'" + eMail + "\'";
        Cursor cursor = db.rawQuery(query, null);
        boolean tmp = cursor.getCount() != 0;
        cursor.close();
        return tmp;
    }

    //Check if password is corect for specific user
    public boolean checkPass(String eMail, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Users WHERE eMail = \'" + eMail + "\'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        boolean tmp = pass.equals(cursor.getString(1));
        cursor.close();
        return tmp;
    }

    //Return logged in user
    public String getLoggedIn() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Users WHERE loggedIn = 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String tmp = cursor.getString(0);
        cursor.close();
        return tmp;
    }

    //Setting up logged in user
    public void setLoggedIn(String eMail) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Users SET loggedIn = 1 WHERE eMail = \'" + eMail + "\'";
        db.execSQL(query);
    }

    //Log out all users
    public void logOut(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Users SET loggedIn = 0");
    }
}
