package jovan0042.monuments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import jovan0042.monuments.dbHelpers.DbHelperUsers;

public class SingUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
    }

    public void SingUpHandler(View view) {
        DbHelperUsers db = new DbHelperUsers(this);

        EditText eteMail = (EditText) findViewById(R.id.eteMail);
        EditText etPass = (EditText) findViewById(R.id.etPassword);
        EditText etPassConf = (EditText) findViewById(R.id.etPasswordConf);

        if(etPass.getText().toString().equals(etPassConf.getText().toString()) && !db.doesExist(eteMail.getText().toString())) {

            db.addUser(eteMail.getText().toString(), etPass.getText().toString());
            Toast.makeText(this, "Successfully sing up.",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            if (db.doesExist(eteMail.getText().toString())) {
                ShowToast("User already singed up");
            } else {
                ShowToast("Passwords does not match");
            }
        }


    }

    public void ShowToast(String message) {
        Toast.makeText(this, message,
                Toast.LENGTH_LONG).show();
    }
}
