package utils;

/**
 * Created by bunny on 12/06/17.
 */

public class Order {

    String saloonID ;
    String userID;
    String serviceID;
    String orderStatus;
    String saloonName ;
    long orderTime = 0 ;

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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
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
}
