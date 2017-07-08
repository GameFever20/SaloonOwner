package app.owner.saloon.craftystudio.saloonowner;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import utils.FireBaseHandler;
import utils.Order;
import utils.OrderAdapter;

public class SearchCustomerPhoneNo extends AppCompatActivity {

    ArrayList<Order> orderArrayList = new ArrayList<>();
    OrderAdapter orderAdapter;

    FireBaseHandler fireBaseHandler;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;

    EditText mSearchPhoneNoEditText;

    String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_customer_phone_no);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        fireBaseHandler = new FireBaseHandler();
        progressDialog = new ProgressDialog(SearchCustomerPhoneNo.this);
        recyclerView = (RecyclerView) findViewById(R.id.searchby_orderList_recyclerView);

        mSearchPhoneNoEditText = (EditText) findViewById(R.id.search_phone_no_edittext);


    }

    public void searchOrdersByPhoneNo(View view) {

        if (mSearchPhoneNoEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Number First", Toast.LENGTH_SHORT).show();
        } else {
            mPhoneNumber = mSearchPhoneNoEditText.getText().toString();
            showProgressDialog("Fetching Detail...");
            downloadOrdersByPhoneSearch();

        }


    }

    private void downloadOrdersByPhoneSearch() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        fireBaseHandler.downloadOrderList(LoginActivity.saloonUID, mPhoneNumber, 20, new FireBaseHandler.OnOrderListener() {
            @Override
            public void onOrderList(ArrayList<Order> orderArrayList) {
                orderAdapter = new OrderAdapter(orderArrayList, SearchCustomerPhoneNo.this);
                recyclerView.setAdapter(orderAdapter);

                closeProgressDialog();
            }
        });

    }


    public void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        // progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void closeProgressDialog() {
        progressDialog.cancel();
    }


}
