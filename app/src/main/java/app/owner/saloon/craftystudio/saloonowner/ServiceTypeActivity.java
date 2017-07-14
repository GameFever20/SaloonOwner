package app.owner.saloon.craftystudio.saloonowner;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import utils.FireBaseHandler;
import utils.Order;
import utils.PendingSaloonRequest;
import utils.Saloon;
import utils.Service;
import utils.ServiceTypeExpandableAdapter;

public class ServiceTypeActivity extends AppCompatActivity {

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
    TabLayout tabLayout;


    //static HashMap<String, ArrayList<Service>> mServiceHashMap = new HashMap<>();
    //static ArrayList<String> mServiceSubType = new ArrayList<>();

    ArrayList<Service> serviceArrayList = new ArrayList<>();


    private ProgressDialog progressDialog;

    AppBarLayout appBarLayout;

    Saloon saloon = MainActivity.SALOON;

    private static LinearLayout nextLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //getting window component
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);


        try {
            saloon = (Saloon) getIntent().getSerializableExtra("Saloon");
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressDialog = new ProgressDialog(this);
        showProgressDialog("Fetching Service");


        tabLayout = (TabLayout) findViewById(R.id.serviceType_tabLayout);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        //serviceArrayList = createServiceList();


        new FireBaseHandler().downloadServiceList(MainActivity.SALOON.getSaloonUID(), 50, new FireBaseHandler.OnServiceListener() {
            @Override
            public void onSeviceUpload(boolean isSuccesful) {

            }

            @Override
            public void onServiceList(ArrayList<Service> serviceArrayList, boolean isSuccesful) {

                //animate app bar in service activity

                ServiceTypeActivity.this.serviceArrayList = serviceArrayList;
                initializeActivity();
                closeProgressDialog();

            }
        });


        //createServiceHashMap();
        nextLinearLayout = (LinearLayout) findViewById(R.id.serviceType_nextActivity_Linearlayout);


    }


    public void initializeActivity() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), serviceArrayList);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        tabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            switch (i) {
                case 0:
                    tabLayout.getTabAt(i).setIcon(R.drawable.ic_action_haircare);
                    break;
                case 1:
                    tabLayout.getTabAt(i).setIcon(R.drawable.ic_action_skincare);
                    break;
                case 2:
                    tabLayout.getTabAt(i).setIcon(R.drawable.ic_action_manicure);
                    break;
                case 3:
                    tabLayout.getTabAt(i).setIcon(R.drawable.ic_action_bodyscrub);
                    break;
                case 4:
                    tabLayout.getTabAt(i).setIcon(R.drawable.ic_action_massage);
                    break;
                case 5:
                    tabLayout.getTabAt(i).setIcon(R.drawable.ic_action_makeup);
                    break;


            }
        }


    }


    private void createServiceHashMap() {

       /* mServiceHashMap.put("Hair Cut", createServiceList());
        mServiceHashMap.put("Hair color", createServiceColorList());
        mServiceHashMap.put("Hair stra", createServiceList());


        mServiceSubType.add("Hair Cut");
        mServiceSubType.add("Hair color");
        mServiceSubType.add("Hair stra");
*/

    }

    private ArrayList<Service> createServiceColorList() {
        ArrayList<Service> serviceArrayList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Service service = new Service();
            service.setServiceName("Brown color");
            serviceArrayList.add(service);
        }

        return serviceArrayList;
    }

    private ArrayList<Service> createServiceList() {
        ArrayList<Service> serviceArrayList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Service service = new Service();
            service.setServiceName("cutting");
            service.setServiceType(1);
            service.setServiceSubType(0);
            service.setServiceUID("service_" + (100 + i));
            service.setServicePrice(i * 50 + 125);
            serviceArrayList.add(service);
        }


        for (int i = 0; i < 5; i++) {
            Service service = new Service();
            service.setServiceName("Bold cut");
            service.setServiceType(1);
            service.setServiceSubType(2);
            service.setServiceUID("service_" + (120 + i));
            service.setServicePrice(i * 50 + 25);
            serviceArrayList.add(service);

        }


        for (int i = 0; i < 5; i++) {
            Service service = new Service();
            service.setServiceName("awesome cut");
            service.setServiceType(2);
            service.setServiceSubType(1);
            service.setServiceUID("service_" + (i + 210));
            service.setServicePrice(i * 50 + 75);
            serviceArrayList.add(service);
        }


        return serviceArrayList;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_service_type, menu);
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


    public void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void closeProgressDialog() {
        progressDialog.dismiss();
    }


    public static boolean updateSaloonServiceUpdated(final boolean updated) {

        if (MainActivity.SALOON == null) {
            return false;
        }

        final long saloonpoint = checkSaloonPoint(MainActivity.SALOON);

        new FireBaseHandler().uploadSaloonInfo(MainActivity.SALOON.getSaloonUID(), "saloonServiceUpdated", updated, saloonpoint, new FireBaseHandler.OnSaloonDownload() {
            @Override
            public void onSaloon(Saloon saloon) {

            }

            @Override
            public void onSaloonValueUploaded(boolean isSucessful) {

                //Toast.makeText(AddSaloonServiceActivity.this, "Saloon ready to be verified ", Toast.LENGTH_SHORT).show();

                MainActivity.SALOON.setSaloonServiceUpdated(updated);

                MainActivity.SALOON.setSaloonPoint(saloonpoint);

                nextLinearLayout.setVisibility(View.VISIBLE);

                if (saloonpoint == -100) {
                    PendingSaloonRequest pendingSaloonRequest = new PendingSaloonRequest(MainActivity.SALOON.getSaloonName(), MainActivity.SALOON.getSaloonUID(), MainActivity.SALOON.getSaloonAddress(), true);
                    new FireBaseHandler().uploadPendingSaloonRequest(pendingSaloonRequest, new FireBaseHandler.OnPendingSaloonRequest() {
                        @Override
                        public void onSaloonRequest(boolean isSuccessful) {
                            if (isSuccessful) {

                            }
                        }
                    });
                }

            }
        });

        return true;

    }

    public void onNextClick(View view) {
        Intent intent = new Intent(ServiceTypeActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private static long checkSaloonPoint(Saloon saloon) {
        if (saloon.getSaloonPoint() < -1 && saloon.getSaloonPoint() > -100) {

            int i = -100;

            if (!saloon.checkSaloonImageUpdated()) {
                i = i + 10;


            }
            if (!saloon.isSaloonServiceUpdated()) {
                //i = i + 10;

            }
            if (!saloon.isSaloonUpdated()) {
                i = i + 10;

            }


            return i;

        } else {
            return saloon.getSaloonPoint();
        }


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

        private static ArrayList<Service> mServiceArrayList;

        ArrayList<String> mServiceSubTypeList;
        HashMap<String, ArrayList<Service>> mServiceHashMap;

        public PlaceholderFragment() {
        }


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, ArrayList<Service> serviceArrayList) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            mServiceArrayList = serviceArrayList;
            fragment.setArguments(args);

            return fragment;

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final int serviceTypeIndex = getArguments().getInt(ARG_SECTION_NUMBER);

            View rootView = inflater.inflate(R.layout.fragment_service_type, container, false);
            createServiceHashMap(serviceTypeIndex);

            ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.serviceTypeFragment_ExpandableListView);

            ServiceTypeExpandableAdapter serviceTypeExpandableAdapter = new ServiceTypeExpandableAdapter(mServiceHashMap
                    , mServiceSubTypeList, getContext()
            );


            expandableListView.setAdapter(serviceTypeExpandableAdapter);


            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    Toast.makeText(getContext(), "position is " + serviceTypeIndex + " , " + groupPosition + " , " + childPosition, Toast.LENGTH_SHORT).show();

                    ArrayList<Service> serviceArrayList = mServiceHashMap.get(mServiceSubTypeList.get(groupPosition));

                    Service serviceSelected = mServiceHashMap.get(mServiceSubTypeList.get(groupPosition)).get(childPosition);

                    if ((serviceArrayList.size() - 1) == childPosition) {
                        openAddServiceDialog(serviceSelected, 1);


                    } else {
                        openAddServiceDialog(serviceSelected, 2);
                    }


                    Toast.makeText(getContext(), "Selected service is " + serviceSelected.getServiceName(), Toast.LENGTH_SHORT).show();


                    return false;

                }
            });

            return rootView;
        }


        public void openAddServiceDialog(final Service service, final int typeOfAction) {

            final Dialog dialog = new Dialog(getContext());
            // Include dialog.xml file
            dialog.setContentView(R.layout.dialog_add_service_layout);
            // Set dialog title
            dialog.setTitle("Add Service");

            // set values for custom dialog components - text, image and button
            final EditText mServiceNameEditText, mServicePriceEditText, mServiceOfferPriceEditText;

            mServiceNameEditText = (EditText) dialog.findViewById(R.id.dialog_addService_serviceName_editText);
            mServiceOfferPriceEditText = (EditText) dialog.findViewById(R.id.dialog_addService_serviceOfferPrice_editText);
            mServicePriceEditText = (EditText) dialog.findViewById(R.id.dialog_addService_servicePrice_editText);

            mServiceNameEditText.setText(service.getServiceName());
            if (service.getServicePrice() >0) {
                mServicePriceEditText.setText(service.getServicePrice() + "");
            }
            if (service.getServiceOfferPrice() >0) {
                mServiceOfferPriceEditText.setText(service.getServiceOfferPrice() + "");
            }

            dialog.show();

            Button declineButton = (Button) dialog.findViewById(R.id.dialog_addService_uploadService_button);
            // if decline button is clicked, close the custom dialog
            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog

                    String stringName = mServiceNameEditText.getText().toString();
                    if (stringName.isEmpty()) {
                        mServiceNameEditText.setError("Required");
                        return;
                    }

                    String stringOfferPrice = mServiceOfferPriceEditText.getText().toString();
                    if (stringOfferPrice.isEmpty()) {
                        mServiceOfferPriceEditText.setError("Required");
                        return;
                    } else if (Integer.valueOf(stringOfferPrice) == 0) {
                        mServiceOfferPriceEditText.setError("cannot be 0");
                        return;
                    }

                    String stringPrice = mServicePriceEditText.getText().toString();
                    if (stringPrice.isEmpty()) {
                        mServicePriceEditText.setError("Required");
                        return;
                    } else if (Integer.valueOf(stringPrice) == 0) {
                        mServiceOfferPriceEditText.setError("cannot be 0");
                        return;
                    }


                    service.setServiceName(stringName);
                    service.setServicePrice(Integer.valueOf(stringPrice));
                    service.setServiceOfferPrice(Integer.valueOf(stringOfferPrice));

                    if (typeOfAction == 1) {
                        addService(service);
                    } else if (typeOfAction == 2) {
                        updateService(service);
                    }


                    dialog.dismiss();
                }
            });


        }


        private void updateService(Service serviceSelected) {

            new FireBaseHandler().uploadService(serviceSelected, new FireBaseHandler.OnServiceListener() {
                @Override
                public void onSeviceUpload(boolean isSuccesful) {
                    if (isSuccesful) {


                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(PlaceholderFragment.this).attach(PlaceholderFragment.this).commit();


                    }
                }

                @Override
                public void onServiceList(ArrayList<Service> serviceArrayList, boolean isSuccesful) {

                }
            });


        }

        private void addService(final Service serviceSelected) {

            new FireBaseHandler().uploadService(serviceSelected, new FireBaseHandler.OnServiceListener() {
                @Override
                public void onSeviceUpload(boolean isSuccesful) {
                    if (isSuccesful) {

                        mServiceArrayList.add(serviceSelected);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(PlaceholderFragment.this).attach(PlaceholderFragment.this).commit();

                        if (mServiceArrayList.size() > 5 && !MainActivity.SALOON.isSaloonServiceUpdated()) {

                            if (!updateSaloonServiceUpdated(true)) {
                                Toast.makeText(getContext(), "Something went wrong ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "saloon updated ", Toast.LENGTH_SHORT).show();

                            }
                        }


                    }
                }

                @Override
                public void onServiceList(ArrayList<Service> serviceArrayList, boolean isSuccesful) {

                }
            });

        }


        public void createServiceHashMap(int serviceTypeIndex) {


            String[] serviceTypeName;

            switch (serviceTypeIndex) {
                case 1:
                    serviceTypeName = getResources().getStringArray(R.array.haircare_service_sub_type);
                    break;
                case 2:
                    serviceTypeName = getResources().getStringArray(R.array.skincare_service_sub_type);
                    break;
                case 3:
                    serviceTypeName = getResources().getStringArray(R.array.manicure_service_sub_type);
                    break;
                case 4:
                    serviceTypeName = getResources().getStringArray(R.array.bodywrap_service_sub_type);
                    break;
                case 5:
                    serviceTypeName = getResources().getStringArray(R.array.massage_service_sub_type);
                    break;
                case 6:
                    serviceTypeName = getResources().getStringArray(R.array.makeup_service_sub_type);
                    break;
                default:
                    serviceTypeName = getResources().getStringArray(R.array.haircare_service_sub_type);
                    break;


            }


            ArrayList<String> serviceSubTypeList = new ArrayList<String>();
            HashMap<String, ArrayList<Service>> serviceHashMap = new HashMap<>();

            for (String serviceSubTypeName : serviceTypeName) {

                serviceSubTypeList.add(serviceSubTypeName);

                serviceHashMap.put(serviceSubTypeName, new ArrayList<Service>());

            }


            for (Service service : mServiceArrayList) {

                if (service.getServiceType() == serviceTypeIndex) {

                    serviceHashMap.get(serviceSubTypeList.get(service.getServiceSubType())).add(service);

                }

            }

            for (int i = 0; i < serviceSubTypeList.size(); i++) {
                Service service = new Service();
                service.setServiceType(serviceTypeIndex);

                service.setServiceSubType(i);
                service.setServiceSubTypeName(serviceSubTypeList.get(i));
                service.setSaloonUID(MainActivity.SALOON.getSaloonUID());
                service.setSaloonName(MainActivity.SALOON.getSaloonName());

                serviceHashMap.get(serviceSubTypeList.get(i)).add(service);
            }

            mServiceHashMap = serviceHashMap;
            mServiceSubTypeList = serviceSubTypeList;

        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Service> mServiceArrayList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Service> serviceArrayList) {
            super(fm);
            mServiceArrayList = serviceArrayList;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).


            return PlaceholderFragment.newInstance(position + 1, mServiceArrayList);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Hair Care";
                case 1:
                    return "Skin Care";
                case 2:
                    return "MANICURE";
                case 3:
                    return "Body Scrub";
                case 4:
                    return "MASSAGES";
                case 5:
                    return "Make up";
            }
            return null;
        }
    }
}
