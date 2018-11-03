package aryasoft.company.arachoob.Models;

public class UserRegistration {

    private String MobileNumber;
    private String Email;
    private String Password;
    private String RePassword;

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getRePassword() {
        return RePassword;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setRePassword(String rePassword) {
        RePassword = rePassword;
    }
}
