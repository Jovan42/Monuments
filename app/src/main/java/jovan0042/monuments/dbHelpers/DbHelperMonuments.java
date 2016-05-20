package jovan0042.monuments.dbHelpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import jovan0042.monuments.Monument;


public class DbHelperMonuments extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Database";

    public DbHelperMonuments(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //Returning all monuments from table Monuments
    public ArrayList<Monument> getAllMonuments() {
        ArrayList<Monument> monuments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Monuments", null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            monuments.add(new Monument(cursor.getString(0), cursor.getString(1),cursor.getString(2), cursor.getString(3)));
        }
        cursor.close();
        return monuments;
    }

    //Returning all monuments from table Monuments added by specific user
    public ArrayList<Monument> getMyMonuments(String user) {

        ArrayList<Monument> monuments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Monuments WHERE user = \'" + user + "\'", null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            monuments.add(new Monument(cursor.getString(0), cursor.getString(1),cursor.getString(2), cursor.getString(3)));
        }
        cursor.close();
        return monuments;
    }

    //Adding monument in table Monument
    public void add(Monument m) {
        SQLiteDatabase db = this.getReadableDatabase();
        if(!doesExist(m.getName())) {
            String query = "INSERT INTO Monuments (name, user, type, description) VALUES ( \'" + m.getName() +"\', \'" + m.getUser()+
                    "\', \'" + m.getType() + "\', \'" + m.getDescription() + "\');";
            db.execSQL(query);
        }
    }

    //Check if monuments exist in table Monuments
    public boolean doesExist(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Monuments WHERE name = \'" + name + "\'", null);
        boolean tmp = cursor.getCount() == 0;
        cursor.close();
        return !tmp;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String exec = "CREATE TABLE IF NOT EXISTS Users (eMail TEXT, password TEXT, loggedIn INT DEFAULT 0)";
        db.execSQL(exec);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Monuments");
        onCreate(db);
    }
}
