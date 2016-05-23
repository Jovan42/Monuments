package jovan0042.monuments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import jovan0042.monuments.dbHelpers.DbHelperUsers;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Crating Database and Users table on first run
        SQLiteDatabase db = openOrCreateDatabase("Database", MODE_PRIVATE, null);


        //---------Reset app----------------//
        //db.execSQL("DROP TABLE Users");
        //db.execSQL("DROP TABLE Monuments");
        //db.execSQL("DROP TABLE Types");


        String query = "CREATE TABLE IF NOT EXISTS Users (eMail TEXT, password TEXT, loggedIn INT DEFAULT 0)";
        db.execSQL(query);

        //If there is user with loggedIn = 1 SingInActivity will be skipped
        query = "SELECT * FROM Users WHERE loggedIn = 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() == 1) {
            cursor.close();
            startActivity(new Intent(this, MonumentsActivity.class));
        } else if(cursor.getCount() > 1) {
            cursor.close();
            db.execSQL("UPDATE Users SET loggedIn = 0");
        }
    }

    //Checking username and password
    public void SingInHandler(View view) {
        DbHelperUsers db = new DbHelperUsers(this);
        EditText eteMail = (EditText) findViewById(R.id.eteMail);
        EditText etPass = (EditText) findViewById(R.id.etPassword);

        if(db.doesExist(eteMail.getText().toString()) && db.checkPass(eteMail.getText().toString(),
                etPass.getText().toString())) {
            db.setLoggedIn(eteMail.getText().toString());
            startActivity(new Intent(this, MonumentsActivity.class));
        } else {
            Toast.makeText(this, "Wrong username and/or password",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void SingUpHandler(View view) {
        startActivity(new Intent(this, SingUpActivity.class));
    }

}
