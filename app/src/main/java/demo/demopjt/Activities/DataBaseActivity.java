package demo.demopjt.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import demo.demopjt.Adapter.DataBaseAdapter;
import demo.demopjt.Adapter.ProductAdapter;
import demo.demopjt.Database.Contact;
import demo.demopjt.Database.DatabaseHandler;
import demo.demopjt.ModelClass.CategoryList;
import demo.demopjt.ModelClass.CategoryMain;
import demo.demopjt.ModelClass.Productlist;
import demo.demopjt.R;
import demo.demopjt.retrofit.APIClient;
import demo.demopjt.retrofit.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataBaseActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private DataBaseAdapter mAdapter;
    DatabaseHandler db;
    Button btnDelete, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        db = new DatabaseHandler(this);


        init();
        setOnclick();

    }

    private void setOnclick() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                List<Contact> contacts = db.getAllContacts();

                for (Contact cn : contacts) {
                    String log = cn.getName();
                    // Writing Contacts to log
                    Log.e("Name: ", log);


                    if (cn.getName().equalsIgnoreCase("30gm (20pouch)")) {


                        db.deleteContact(cn);
                        Log.e("record ", "yes");
                        Log.e("size ", "" + db.getAllContacts().size());

                    } else {

                        Log.e("record ", "not");
                    }


                }


            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                List<Contact> contacts = db.getAllContacts();

                for (Contact cn : contacts) {
                    String log = cn.getName();
                    // Writing Contacts to log
                    Log.e("Name: ", log);


                    if (cn.getName().equalsIgnoreCase("30gm (20pouch)")) {


                        cn.setName("asasas");
                        cn.setPrice("sasasas");
                        cn.setCatid("wewe");

                        db.updateContact(cn);


                        Log.e("record ", "yes");


                    } else {

                        Log.e("record ", "not");
                    }


                }


            }
        });


    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        List<Contact> contacts = db.getAllContacts();
        mAdapter = new DataBaseAdapter(DataBaseActivity.this, contacts);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();


    }


}
