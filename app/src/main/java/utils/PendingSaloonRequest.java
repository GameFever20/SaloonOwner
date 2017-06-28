package utils;

/**
 * Created by bunny on 28/06/17.
 */

public class PendingSaloonRequest {
    String saloonName ;
    String saloonUID ;
    String saloonAddress;
    boolean pendingStatus;

    public PendingSaloonRequest(String saloonName, String saloonUID, String saloonAddress) {
        this.saloonName = saloonName;
        this.saloonUID = saloonUID;
        this.saloonAddress = saloonAddress;
    }

    public String getSaloonName() {
        return saloonName;
    }

    public void setSaloonName(String saloonName) {
        this.saloonName = saloonName;
    }

    public String getSaloonUID() {
        return saloonUID;
    }

    public void setSaloonUID(String saloonUID) {
        this.saloonUID = saloonUID;
    }

    public String getSaloonAddress() {
        return saloonAddress;
    }

    public void setSaloonAddress(String saloonAddress) {
        this.saloonAddress = saloonAddress;
    }

    public boolean isPendingStatus() {
        return pendingStatus;
    }

    public void setPendingStatus(boolean pendingStatus) {
        this.pendingStatus = pendingStatus;
    }
}
