package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bunny on 12/06/17.
 */

public class Order {

    private String saloonID ;
    private String userID;
    private String serviceID;
    private String orderID;
    private int orderStatus;
    private String saloonName ;
    private long orderTime = 0 ;
    private int orderPrice =0 ;
    private String orderServiceName ="" ;



    public Order() {
    }


    public String getSaloonID() {
        return saloonID;
    }

    public void setSaloonID(String saloonID) {
        this.saloonID = saloonID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSaloonName() {
        return saloonName;
    }

    public void setSaloonName(String saloonName) {
        this.saloonName = saloonName;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderServiceName() {
        return orderServiceName;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setOrderServiceName(String orderServiceName) {
        this.orderServiceName = orderServiceName;
    }

    public String resolveOrderStatus() {
        if (orderStatus == 1){
            return "Placed";
        } else if (orderStatus ==2) {
            return "Accepted";
        }else if(orderStatus == 3){
            return "completed";
        }else{
            return "";
        }
    }

    public String resolveOrderDate() {
         String dateFormat = "dd/MM/yyyy  hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        Date date = new Date();
        date.setTime(orderTime);
        return simpleDateFormat.format(date);

    }
}
