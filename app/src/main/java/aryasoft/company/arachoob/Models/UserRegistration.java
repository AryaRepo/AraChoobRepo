package aryasoft.company.arachoob.Models;

public class UserRegistration {

    private String MobileNumber;
    private String UserEmail;
    private String Password;

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public String getPassword() {
        return Password;
    }


    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
