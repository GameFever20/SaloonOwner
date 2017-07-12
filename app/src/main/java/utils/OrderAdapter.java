package utils;

import android.content.Context;
import android.graphics.Movie;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.owner.saloon.craftystudio.saloonowner.R;

/**
 * Created by bunny on 14/06/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private ArrayList<Order> orderArrayList;
    Context context;


    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView saloonNameTextView, orderDateTextView, orderStatusTextView, orderPriceTextView, serviceNameTextView;

        public CardView cardView;

        public OrderViewHolder(View view) {
            super(view);
            saloonNameTextView = (TextView) view.findViewById(R.id.orderAdapterRow_saloonName_textview);
            orderDateTextView = (TextView) view.findViewById(R.id.orderAdapterRow_orderDate_textview);
            orderStatusTextView = (TextView) view.findViewById(R.id.orderAdapterRow_orderStatus_textview);
            orderPriceTextView = (TextView) view.findViewById(R.id.orderAdapterRow_orderPrice_textview);
            serviceNameTextView = (TextView) view.findViewById(R.id.orderAdapterRow_serviceName_textview);
            cardView = (CardView) view.findViewById(R.id.orderAdapter_colorCard_cardView);

        }


    }


    public OrderAdapter(ArrayList<Order> orderArrayList, Context context) {
        this.orderArrayList = orderArrayList;
        OrderAdapter.this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_adapter_row, parent, false);

        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order order = orderArrayList.get(position);
        if (order.getOrderStatus() >1) {
            holder.saloonNameTextView.setText(order.getUserPhoneNumber());
        }
        holder.orderDateTextView.setText(order.resolveOrderDate());
        holder.orderPriceTextView.setText(order.getOrderPrice() + "");
        holder.orderStatusTextView.setText(order.resolveOrderStatus());
        holder.serviceNameTextView.setText(order.getUserName());

        if (order.getOrderStatus() == 2) {

            holder.orderStatusTextView.setTextColor(ContextCompat.getColorStateList(context, R.color.blue));
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.blue));

            //red rejected green comleted blue accepted
        }else if(order.getOrderStatus() == 3){
            holder.orderStatusTextView.setTextColor(ContextCompat.getColorStateList(context, R.color.green));
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green));

        }else if(order.getOrderStatus() == -1){
            holder.orderStatusTextView.setTextColor(ContextCompat.getColorStateList(context, R.color.redish));
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.redish));

        }


    }


    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }
}
