package aryasoft.company.arachoob.Models;

public class ActivationAccount {

    private String MobileNumber;
    private String ActiveCode;

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getActiveCode() {
        return ActiveCode;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public void setActiveCode(String activeCode) {
        ActiveCode = activeCode;
    }
}
