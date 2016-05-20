package jovan0042.monuments.dbHelpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelperTypes extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Database";

    //Adding type in database in table Types
    public void add(String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        if(!doesExist(type)) {
            String query = "INSERT INTO Types (name) VALUES ( \'" + type + "\');";
            db.execSQL(query);
        }
    }

    //Checking if type already exist in table
    public boolean doesExist(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Types WHERE name = \'" + name + "\'", null);
        boolean tmp = cursor.getCount() == 0;
        cursor.close();
        return !tmp;
    }

    //Returning all types from table Types
    public ArrayList<String> getTypes() {
        ArrayList<String> types = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Types", null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            types.add(cursor.getString(0));
        }
        cursor.close();
        return types;
    }

    public DbHelperTypes(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String  exec =  "CREATE TABLE IF NOT EXISTS Types (name TEXT)";
        db.execSQL(exec);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Monuments");
        onCreate(db);
    }
}
