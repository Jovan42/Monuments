package jovan0042.monuments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

import jovan0042.monuments.dbHelpers.DbHelperMonuments;
import jovan0042.monuments.dbHelpers.DbHelperUsers;


public class MonumentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monuments);
        SQLiteDatabase db = openOrCreateDatabase("Database", MODE_PRIVATE, null);

        //Create table on first run
        String exec = "CREATE TABLE IF NOT EXISTS Monuments (name TEXT, user TEXT, type TEXT, description TEXT)";
        db.execSQL(exec);

        DbHelperUsers thu = new DbHelperUsers(this);
        TextView tvUser = (TextView) findViewById(R.id.Type);
        tvUser.setText(thu.getLoggedIn());

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab AllMonuments
        TabHost.TabSpec spec = host.newTabSpec("All Monuments");
        spec.setContent(R.id.tabAllMonuments);
        spec.setIndicator("All Monuments");
        host.addTab(spec);

        DbHelperMonuments dbHelperMonuments = new DbHelperMonuments(this);
        ListView allTab = (ListView) findViewById(R.id.lvAllMon);
        ArrayList<Monument> monuments1 = dbHelperMonuments.getAllMonuments();

        MonumentArrayAdapter adapter1 =  new MonumentArrayAdapter(this, R.layout.monument_item, monuments1);
        allTab.setAdapter(adapter1);

        //Tab MyMonuments
        spec = host.newTabSpec("My Monuments");
        spec.setContent(R.id.tabMyMonuments);
        spec.setIndicator("My Monuments");
        host.addTab(spec);

        DbHelperUsers dbu = new DbHelperUsers(this);

        ListView myTab = (ListView) findViewById(R.id.lvMyMon);
        ArrayList<Monument> monuments2 = dbHelperMonuments.getMyMonuments(dbu.getLoggedIn());

        MonumentArrayAdapter adapter = new MonumentArrayAdapter(this, R.layout.monument_item, monuments2);
        myTab.setAdapter(adapter);
    }


    public void TypesClickHandler(View view) {
        startActivity(new Intent(this, TypesActivity.class));
    }

    public void AddClickHandler(View view) {
        startActivity(new Intent(this, AddMonumentActivity.class));
    }

    //Override to forbid getting on Login/SingUp Activity without logging out
    @Override
    public void onBackPressed() {

    }

    public void LogOutHandler(View view) {
        DbHelperUsers db = new DbHelperUsers(this);
        db.logOut();
        startActivity(new Intent(this,LoginActivity.class));
    }
}
