package app.owner.saloon.craftystudio.saloonowner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import utils.ClickListener;
import utils.FireBaseHandler;
import utils.RecyclerTouchListener;
import utils.Service;
import utils.ServiceAdapter;

public class ServiceListActivity extends AppCompatActivity {

    ArrayList<Service> serviceArrayList = new ArrayList<>();
    ServiceAdapter serviceAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
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


        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.downloadServiceList(LoginActivity.saloonUID, 30, new FireBaseHandler.OnServiceListener() {
            @Override
            public void onSeviceUpload(boolean isSuccesful) {

            }

            @Override
            public void onServiceList(ArrayList<Service> serviceArrayList, boolean isSuccesful) {
                if (isSuccesful) {
                    ServiceListActivity.this.serviceArrayList = serviceArrayList;
                    serviceAdapter = new ServiceAdapter(ServiceListActivity.this.serviceArrayList);
                    initializeRecycler();
                } else {

                    Toast.makeText(ServiceListActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initializeRecycler() {
        RecyclerView recyclerView= (RecyclerView)findViewById(R.id.serviceList_service_recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(serviceAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    onScrolledToBottom();
                    Toast.makeText(ServiceListActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void onScrolledToBottom() {

    }


}
