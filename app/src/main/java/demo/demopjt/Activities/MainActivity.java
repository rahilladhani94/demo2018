package demo.demopjt.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import demo.demopjt.Adapter.ProductAdapter;
import demo.demopjt.Database.Contact;
import demo.demopjt.Database.DatabaseHandler;
import demo.demopjt.ModelClass.CategoryList;
import demo.demopjt.ModelClass.CategoryMain;
import demo.demopjt.ModelClass.Productlist;
import demo.demopjt.ModelClass.ProductlistMain;
import demo.demopjt.ModelClass.SimpleMessageStatusResponse;
import demo.demopjt.R;
import demo.demopjt.UIUtil;
import demo.demopjt.retrofit.APIClient;
import demo.demopjt.retrofit.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    APIInterface apiInterface;
    List<CategoryList> cato= new ArrayList<>();
    List<Productlist> productList= new ArrayList<>();

    ProgressDialog progressBar;
    private RecyclerView recyclerView;
    private ProductAdapter mAdapter;
    DatabaseHandler db;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressBar = new ProgressDialog(MainActivity.this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Loading ...");
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();

        init();





//        //////todo map method/////
//
//
//        Map<String, String> map = new HashMap<>();
//        map.put("nCatId", "3");
//
//        Call<ProductlistMain> callpost = apiInterface.getProductList(map);
//        callpost.enqueue(new Callback<ProductlistMain>() {
//            @Override
//            public void onResponse(Call<ProductlistMain> call, Response<ProductlistMain> response) {
//                progressBar.cancel();
//
//
//                Log.e("TAG", response.code() + "");
//
//                if (response.isSuccessful()) {
//
//                    ProductlistMain apiresponse = response.body();
//
//                    if (apiresponse.getStatus() == 1) {
//
//
//                        // cato = apiresponse.getProductlist();
//                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                Log.e("ss", "ss");
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ProductlistMain> call, Throwable t) {
//                call.cancel();
//                progressBar.cancel();
//                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
//
//            }
//        });












        ///////////////////////

//        HashMap<String, Object> map = new HashMap<>();
//
//        map.put("name", edt_labelname.getText().toString().trim());
//        map.put("color", selected_color);
//        ServiceGenerator.ApiInterface apiService =
//                ServiceGenerator.createService(ServiceGenerator.ApiInterface.class);
//        Call<SimpleMessageStatusResponse> call = apiService.updateLabel(sessionManager.getKeyAuthtoken(),map,id);
//        call.enqueue(new Callback<SimpleMessageStatusResponse>() {
//            @Override
//            public void onResponse(Call<NewProjectMainResponse> call, Response<NewProjectMainResponse> response) {
//                UIUtil.dismissDialog();
//                if (response.isSuccessful()) {
//                    NewProjectMainResponse registrationResponse = response.body();
//                    if (registrationResponse.getData() != null)
//                    {
//                        AppUtil.Toast(ActivityCreateNewLabel.this,"Label successfully updated");
//                        ActivityAllLabelsListing.refresh = "yes";
//                        finish();
//                    } else {
//                        AppUtil.Toast(ActivityCreateNewLabel.this,"Something went wrong,Please try again.");
//                    }
//                } else {
//                    if (response.code() == 400) {
//                        InputStream in = response.errorBody().byteStream();
//                        String error = AppUtil.getStringRequestBody(in);
//
//                        JSONObject jresponse = null;
//                        try {
//                            jresponse = new JSONObject(error);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            error = jresponse.getString("error");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        // AppUtil.Toast(ActivityCreateNewLabel.this,""+error);
//
//                        AppUtil.Toast(ActivityCreateNewLabel.this,"Your session has expired please re login again to continue.");
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<NewProjectMainResponse> call, Throwable t) {
//                UIUtil.dismissDialog();
//                AppUtil.Toast(ActivityCreateNewLabel.this,"Something went wrong,Please try again.");
//                Log.e("TAG", t.toString());
//            }
//        });


    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btn = (Button) findViewById(R.id.btn);



        callGetApi();
    }

    private void callGetApi() {




        /**
         GET List Resources
         **/
        Call<CategoryMain> call = apiInterface.getCategorylist();
        call.enqueue(new Callback<CategoryMain>() {
            @Override
            public void onResponse(Call<CategoryMain> call, Response<CategoryMain> response) {
                progressBar.cancel();


                Log.e("TAG", response.code() + "");

                if (response.isSuccessful()) {

                    CategoryMain apiresponse = response.body();


                    if (apiresponse.getStatus() == 1) {


                        cato = apiresponse.getCategorylist();
                        //Toast.makeText(MainActivity.this, "" + cato.size(), Toast.LENGTH_SHORT).show();
                        mAdapter = new ProductAdapter(MainActivity.this,cato);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);


                        mAdapter.notifyDataSetChanged();

                        db.delete();
                        addtoDatabase();


                    }
                }

                Log.e("ss", "ss");


            }

            @Override
            public void onFailure(Call<CategoryMain> call, Throwable t) {
                call.cancel();
                progressBar.cancel();
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addtoDatabase() {

        for (int i = 0; i < cato.size(); i++) {

                db.addContact(new Contact("" + cato.get(i).getSName(), "" + cato.get(i).getNBasePrice(),""+cato.get(i).getNCatId()));

        }

        List<Contact> contacts = db.getAllContacts();
        Log.e("si", "" + contacts.size());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,DataBaseActivity.class);
                startActivity(intent);
            }
        });
    }
}
