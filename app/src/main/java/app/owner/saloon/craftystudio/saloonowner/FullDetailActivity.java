package app.owner.saloon.craftystudio.saloonowner;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import utils.FireBaseHandler;
import utils.Order;
import utils.Service;

public class FullDetailActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    //All id's
    public static  String SaloonID, UserID, OrderID, ServiceID="";


    //Imageview for showcasig saloon's image
    ImageView imageView;

     static Order ORDER ;
     static Service SERVICE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //SaloonID = bundle.getString("SaloonID");
        SaloonID=bundle.getString("SaloonID");
        UserID = bundle.getString("UserID");
        OrderID = bundle.getString("OrderID");
        ServiceID = bundle.getString("ServiceID");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.downloadOrder(SaloonID ,OrderID , new FireBaseHandler.OnOrderDownloadListner() {
            @Override
            public void onOrder(Order order) {
                ORDER = order;
            }
        });
        fireBaseHandler.downloadService(SaloonID, ServiceID, new FireBaseHandler.OnServiceDownLoadListner() {
            @Override
            public void onServiceDownload(Service service) {
                SERVICE =service;

                initializeActivity();
            }
        });





        imageView=(ImageView)findViewById(R.id.full_detail_showcase_imageview);

    }

    private void initializeActivity() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        if (ORDER.getOrderStatus()==1){
            View view =(View)findViewById(R.id.fullDetail_button_linearLayout);
            view.setVisibility(View.VISIBLE);
        }

        if(ORDER.getOrderStatus() == 2){
            View view =(View)findViewById(R.id.fullDetail_completed_button);
            view.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_full_detail, menu);
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

    public void acceptOrderClick(View view) {
        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.updateOrderstatus(SaloonID, OrderID, 2, new FireBaseHandler.OnOrderStatusUpdateListener() {
            @Override
            public void onOrderStatusUpdate(int newStatus, boolean isSuccesful) {
                if (isSuccesful){
                    Toast.makeText(FullDetailActivity.this, "Order accepted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cancelOrderClick(View view) {

        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.updateOrderstatus(SaloonID, OrderID, -1, new FireBaseHandler.OnOrderStatusUpdateListener() {
            @Override
            public void onOrderStatusUpdate(int newStatus, boolean isSuccesful) {
                if (isSuccesful){
                    Toast.makeText(FullDetailActivity.this, "Order Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void completeOrderClick(View view) {
        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.updateOrderstatus(SaloonID, OrderID, 3, new FireBaseHandler.OnOrderStatusUpdateListener() {
            @Override
            public void onOrderStatusUpdate(int newStatus, boolean isSuccesful) {
                if (isSuccesful){
                    Toast.makeText(FullDetailActivity.this, "Order Completed ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_full_detail, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    TabbedService tabbedService =new TabbedService();
                    return tabbedService;
                case 1:
                    TabbedOrder tabbedOrder=new TabbedOrder();
                    return tabbedOrder;
                case 2:
                    TabbedUser tabbedUser =new TabbedUser();
                    return tabbedUser;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Service";
                case 1:
                    return "Order";
                case 2:
                    return "User";

            }
            return null;
        }
    }
}
