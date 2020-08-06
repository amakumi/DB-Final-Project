package fp_ui;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;


public class Customer_Details {
    @FXML
    private final IntegerProperty customer_id;
    private final StringProperty customer_name;
    private final StringProperty customer_gender;
    private final StringProperty customer_address;
    private final StringProperty customer_telp;
    private final StringProperty customer_dob;

    public Customer_Details(Integer customer_id, String customerName, String customer_gender, String customerAddress, String customerTelp, String dob) {
        this.customer_id = new SimpleIntegerProperty(customer_id);
        this.customer_name = new SimpleStringProperty(customerName);
        this.customer_gender = new SimpleStringProperty(customer_gender);
        this.customer_address = new SimpleStringProperty(customerAddress);
        this.customer_telp = new SimpleStringProperty(customerTelp);
        this.customer_dob = new SimpleStringProperty(dob);
    }

    public int getCustomer_id() {
        return customer_id.get();
    }

    public void setCustomer_id(Integer value) {
        customer_id.set(value);
    }

    public String getCustomer_name() {
        return customer_name.get();
    }

    public void setCustomer_name(String value) {
        customer_name.set(value);
    }

    public String getCustomer_gender() {
        return customer_gender.get();
    }

    public void setCustomer_gender(String value) {
        customer_gender.set(value);
    }

    public String getCustomer_address() {
        return customer_address.get();
    }

    public void setCustomer_address(String value) {
        customer_address.set(value);
    }

    public String getCustomer_telp() {
        return customer_telp.get();
    }

    public void setCustomer_telp(String value) {
        customer_telp.set(value);
    }

    public String getCustomer_dob() {
        return customer_dob.get();
    }

    public void setCustomer_dob(String value) {
        customer_dob.set(value);
    }
}
