package app.owner.saloon.craftystudio.saloonowner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import utils.ClickListener;
import utils.FireBaseHandler;
import utils.Order;
import utils.OrderAdapter;
import utils.RecyclerTouchListener;
import utils.Saloon;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean isRegistered = false;
    public static Saloon SALOON = null;


    ArrayList<Order> orderArrayList = new ArrayList<>();
    OrderAdapter orderAdapter = new OrderAdapter(orderArrayList);
    private boolean isLoadingMoreOrder =false;

    ProgressDialog progressDialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        progressDialog =new ProgressDialog(this);

        showProgressDialog("Fetching Details" , "wait..");

        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.downloadSaloon("abc", new FireBaseHandler.OnSaloonDownload() {
            @Override
            public void onSaloon(Saloon saloon) {

                closeProgressDialog();
                MainActivity.SALOON = saloon;
                if (saloon != null) {
                    saloonCheck(saloon);
                    //saloon.setSaloonUID(LoginActivity.saloonUID);
                    Toast.makeText(MainActivity.this, "Saloon fetched ", Toast.LENGTH_SHORT).show();

                } else {
                    showExitDialogue();
                }
            }

            @Override
            public void onSaloonValueUploaded(boolean isSucessful) {

            }
        });





    }

    private void saloonCheck(Saloon saloon) {
        //check values of saloon obj and redirect to desiered screen

        if (saloon.getSaloonName() != null) {
            if (!saloon.getSaloonName().isEmpty()) {

                if (saloon.getSaloonPoint() > 1) {

                    saloonOrderFetch();

                } else if (saloon.getSaloonPoint() == -10) {
                    openSaloonProfileActivity();
                } else if (saloon.getSaloonPoint() == -1) {
                    openSaloonImageActivity();
                } else if (saloon.getSaloonPoint() == -100) {
                    saloonOrderFetch();
                    showSuspendedDialog();
                } else if (saloon.getSaloonPoint() == 0) {
                    showExitDialogue();
                } else {
                    showSomethingWrongDialogue();
                }

            } else {
                showExitDialogue();
            }
        } else {
            showExitDialogue();
        }


    }

    private void saloonOrderFetch() {

        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.downloadOrderList("abc", 20, new FireBaseHandler.OnOrderListener() {
            @Override
            public void onOrderList(ArrayList<Order> orderArrayList) {

                //Reverse arraylist
                MainActivity.this.orderArrayList = orderArrayList;
                orderAdapter = new OrderAdapter(MainActivity.this.orderArrayList);
                initializeRecyclerView();

                Toast.makeText(MainActivity.this, "orderList Fetched", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void saloonMoreOrderFetch() {
        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.downloadOrderList(LoginActivity.saloonUID, 20
                , orderArrayList.get(orderArrayList.size() - 1).getOrderID()
                , new FireBaseHandler.OnOrderListener() {
                    @Override
                    public void onOrderList(ArrayList<Order> orderArrayList) {

                        if(orderArrayList.size()>0) {
                            for (int i = orderArrayList.size() - 1; i >= 0; i--) {
                                MainActivity.this.orderArrayList.add(orderArrayList.get(i));

                            }
                            orderAdapter.notifyDataSetChanged();
                            isLoadingMoreOrder=false;
                        }



                    }
                });
    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mainActivity_orderList_recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(orderAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Order order = orderArrayList.get(position);

                //passing data to detail activity
                Bundle bundle = new Bundle();
                bundle.putString("SaloonID", order.getSaloonID());
                bundle.putString("OrderID", order.getOrderID());
                bundle.putString("UserID", order.getUserID());
                bundle.putString("ServiceID", order.getServiceID());

                Intent intent = new Intent(MainActivity.this, FullDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

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
                    Toast.makeText(MainActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onScrolledToBottom() {
        if (isLoadingMoreOrder) {

        } else {

            isLoadingMoreOrder= true;
            saloonMoreOrderFetch();
        }
    }

    private void openSaloonProfileActivity() {

        Intent intent = new Intent(MainActivity.this, SaloonProfile.class);
        startActivity(intent);

    }

    private void openSaloonImageActivity() {

        Intent intent = new Intent(MainActivity.this, SaloonImageActivity.class);
        startActivity(intent);
    }

    private void showSuspendedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());
        builder.setTitle("Suspended")
                .setMessage("Your Acoount has been suspended \n Contact Admin")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*account suspended
                        * contact via email or call option
                        * or exit()*/
                    }
                }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //exit
                finish();
            }
        });

        builder.create();
        builder.show();
    }

    private void showSomethingWrongDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());
        builder.setTitle("Something Went Wrong")
                .setMessage("Please Try again later")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        // Create the AlertDialog object and return it
        builder.create();
        builder.show();


    }

    private void showExitDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle("Exit")
                .setMessage("You are not a registered saloon \n Contact Admin")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        // Create the AlertDialog object and return it
        builder.create();
        builder.show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this, SaloonProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this, SaloonImageActivity.class);
            startActivity(intent);

        }  else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this, AddSaloonServiceActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(MainActivity.this , ServiceListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void resolveDateDummy() {
        String dateFormat = "dd/MM/yyyy hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        Date date = new Date();

        String str = simpleDateFormat.format(date);
        Toast.makeText(this, "Date is " + str, Toast.LENGTH_SHORT).show();
    }


    public void showProgressDialog(String title,String message){
        progressDialog.setMessage(message);
        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void closeProgressDialog(){
        progressDialog.dismiss();
    }


}
