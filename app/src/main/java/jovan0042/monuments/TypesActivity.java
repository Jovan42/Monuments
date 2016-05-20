package jovan0042.monuments;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import jovan0042.monuments.dbHelpers.DbHelperTypes;

public class TypesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_types);
        SQLiteDatabase db = openOrCreateDatabase("Database", MODE_PRIVATE, null);
        //Create Types table on first run
        String  exec =  "CREATE TABLE IF NOT EXISTS Types (name TEXT)";
        db.execSQL(exec);

        //Setting up listView with all types
        DbHelperTypes dbt = new DbHelperTypes(this);
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> types = dbt.getTypes();
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, types);
        listView.setAdapter(adapter);

    }

    //Add type in Types table and refresh listView
    public void AddHandler(View view) {
        DbHelperTypes db = new DbHelperTypes(this);
        EditText etType = (EditText) findViewById(R.id.etType);

        if(!db.doesExist(etType.getText().toString())) {
            db.add(etType.getText().toString());
            DbHelperTypes dbt = new DbHelperTypes(this);
            ListView listView = (ListView) findViewById(R.id.listView);
            ArrayList<String> types = dbt.getTypes();
            ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, types);
            listView.setAdapter(adapter);
            Toast.makeText(this, "Type added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Type exist", Toast.LENGTH_LONG).show();
        }
    }
}
