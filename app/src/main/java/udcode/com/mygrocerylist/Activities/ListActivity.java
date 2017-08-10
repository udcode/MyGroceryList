package udcode.com.mygrocerylist.Activities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import udcode.com.mygrocerylist.Activities.Data.DataBaseHandler;
import udcode.com.mygrocerylist.Activities.Ui.RecyclerViewAdapter;
import udcode.com.mygrocerylist.Activities.Util.UtilFunctions;
import udcode.com.mygrocerylist.R;

public class ListActivity extends AppCompatActivity {
        RecyclerView.Adapter adapter;
        RecyclerView recyclerView;
        DataBaseHandler db;
        UtilFunctions util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = new DataBaseHandler(this);
        adapter = new RecyclerViewAdapter(this,db.getAll());
        recyclerView.setAdapter(adapter);
        util = new UtilFunctions(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(0xc0fabf));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                util.createPopUp();

            }
        });
    }

}
