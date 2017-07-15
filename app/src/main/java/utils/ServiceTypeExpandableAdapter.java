package utils;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import app.owner.saloon.craftystudio.saloonowner.R;

import static android.content.ContentValues.TAG;

/**
 * Created by bunny on 30/06/17.
 */

public class ServiceTypeExpandableAdapter extends BaseExpandableListAdapter {

    HashMap<String, ArrayList<Service>> mServiceHashMap;
    ArrayList<String> mServiceSubType;
    Context mContext;

    public ServiceTypeExpandableAdapter(HashMap<String, ArrayList<Service>> serviceHashMap, ArrayList<String> serviceSubType, Context context) {
        mServiceHashMap = serviceHashMap;
        mServiceSubType = serviceSubType;
        mContext = context;


    }

    @Override
    public int getGroupCount() {
        return mServiceSubType.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mServiceHashMap.get(mServiceSubType.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mServiceSubType.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mServiceHashMap.get(mServiceSubType.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.servicetype_adapter_header_row, null);
        }

        TextView headerTextview = (TextView) convertView.findViewById(R.id.serviceType_adapter_header_subType_textview);
        headerTextview.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final Service service = (Service) getChild(groupPosition, childPosition);


           /* if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.servicetype_adapter_child_row, null);
            }*/

        if (isLastChild) {

            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.servicetype_adapter_child_row, null);

            TextView serviceNameTextview = (TextView) convertView
                    .findViewById(R.id.serviceType_adapter_child_subType_textview);
            TextView serviceSubTypePriceTextview = (TextView) convertView.findViewById(R.id.serviceType_adapter_child_subTypeprice_textview);
            TextView serviceSubTypeOfferPriceTextview = (TextView) convertView.findViewById(R.id.serviceType_adapter_child_subTypeOfferprice_textview);



            serviceSubTypePriceTextview.setText(null);
            serviceSubTypeOfferPriceTextview.setText(null);
            serviceNameTextview.setText("Add More Service");
            serviceNameTextview.setTextSize(20f);
            serviceNameTextview.setTextColor(ContextCompat.getColor(mContext, R.color.redish));


        } else {

            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.servicetype_adapter_child_row, null);

            TextView serviceNameTextview = (TextView) convertView
                    .findViewById(R.id.serviceType_adapter_child_subType_textview);
            TextView serviceSubTypePriceTextview = (TextView) convertView.findViewById(R.id.serviceType_adapter_child_subTypeprice_textview);
            TextView serviceSubTypeOfferPriceTextview = (TextView) convertView.findViewById(R.id.serviceType_adapter_child_subTypeOfferprice_textview);


            serviceSubTypePriceTextview.setText(service.getServicePrice() + "");

            serviceNameTextview.setText(service.getServiceName());

            serviceSubTypeOfferPriceTextview.setText(service.getServiceOfferPrice() + "");
            serviceSubTypePriceTextview.setPaintFlags(serviceSubTypePriceTextview.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        }


        return convertView;
    }


}
