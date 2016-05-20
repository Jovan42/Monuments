package jovan0042.monuments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import jovan0042.monuments.dbHelpers.DbHelperMonuments;
import jovan0042.monuments.dbHelpers.DbHelperTypes;
import jovan0042.monuments.dbHelpers.DbHelperUsers;

public class AddMonumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monument);
    }
    //Checking if all fields are not empty, then if type exist in table
    //If there is no type in table Types user have chance to add that type throw alertDialog
    public void AddHandler(View view) {
        EditText etName = (EditText) findViewById(R.id.etName);
        EditText etType = (EditText) findViewById(R.id.etType);
        EditText etDescription = (EditText) findViewById(R.id.etDescription);
        DbHelperMonuments db = new DbHelperMonuments(this);
        DbHelperUsers dbh = new DbHelperUsers(this);
        if(etName.getText().toString().isEmpty() || etType.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_LONG).show();
        } else {
            DbHelperTypes dbt = new DbHelperTypes(this);
            if (dbt.doesExist(etType.getText().toString())) {
                Monument m = new Monument(etName.getText().toString(), dbh.getLoggedIn(), etType.getText().toString(), etDescription.getText().toString());
                db.add(m);
                startActivity(new Intent(this, MonumentsActivity.class));
            } else {
                alertDialogAddType();
            }
        }
    }


    public void alertDialogAddType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Type does not exist");
        builder.setMessage("Do you want to add type?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                DbHelperTypes dbt = new DbHelperTypes(AddMonumentActivity.this);
                EditText etTypes = (EditText) findViewById(R.id.etType);
                dbt.add(etTypes.getText().toString());
                Toast.makeText(AddMonumentActivity.this, "Type added", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You must add type before adding monument", Toast.LENGTH_LONG).show();
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
