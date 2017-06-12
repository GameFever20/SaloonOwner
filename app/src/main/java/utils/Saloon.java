package utils;

/**
 * Created by bunny on 12/06/17.
 */

public class Saloon {

    String saloonUID ;
    String saloonName ; //input
    String saloonPhoneNumber ;//input
    String saloonAddress;//input
    String saloonLocation ;//input in background
    String saloonPoint ;
    int saloonRating ;
    //timing
    //images


    public Saloon() {

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

    public String getSaloonPhoneNumber() {
        return saloonPhoneNumber;
    }

    public void setSaloonPhoneNumber(String saloonPhoneNumber) {
        this.saloonPhoneNumber = saloonPhoneNumber;
    }

    public String getSaloonAddress() {
        return saloonAddress;
    }

    public void setSaloonAddress(String saloonAddress) {
        this.saloonAddress = saloonAddress;
    }

    public String getSaloonLocation() {
        return saloonLocation;
    }

    public void setSaloonLocation(String saloonLocation) {
        this.saloonLocation = saloonLocation;
    }

    public String getSaloonPoint() {
        return saloonPoint;
    }

    public void setSaloonPoint(String saloonPoint) {
        this.saloonPoint = saloonPoint;
    }

    public int getSaloonRating() {
        return saloonRating;
    }

    public void setSaloonRating(int saloonRating) {
        this.saloonRating = saloonRating;
    }
}
