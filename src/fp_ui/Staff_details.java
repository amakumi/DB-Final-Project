package fp_ui;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;


public class Staff_details {
    @FXML
    private final IntegerProperty staff_id;
    private final StringProperty staff_name;
    private final StringProperty gender;
    private final StringProperty address;
    private final StringProperty password;
    private final StringProperty staff_telp;

    public Staff_details(Integer staff_id, String staffName, String gender, String address, String telp_no, String password) {
        this.staff_id = new SimpleIntegerProperty(staff_id);
        this.staff_name = new SimpleStringProperty(staffName);
        this.gender = new SimpleStringProperty(gender);
        this.address = new SimpleStringProperty(address);
        this.staff_telp = new SimpleStringProperty(telp_no);
        this.password = new SimpleStringProperty(password);
    }

    public int getStaff_id() {
        return staff_id.get();
    }

    public void setStaff_id(Integer value) {
        staff_id.set(value);
    }


    public String getStaff_name() {
        return staff_name.get();
    }

    public void setStaff_name(String value) {
        staff_name.set(value);
    }


    public String getGender() {
        return gender.get();
    }

    public void setGender(String value) {
        gender.set(value);
    }


    public String getAddress() {
        return address.get();
    }

    public void setAddress(String value) {
        address.set(value);
    }


    public String getStaff_telp() {
        return staff_telp.get();
    }

    public void setStaff_telp(String value) {
        staff_telp.set(value);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String value) {
        password.set(value);
    }


}
