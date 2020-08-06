package fp_ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Transaction {
    private final IntegerProperty transaction_id;
    private final IntegerProperty customer_id;
    private final IntegerProperty staff_id;
//    private final StringProperty date;

    public Transaction(Integer transaction_id, Integer customer_id, Integer staff_id) {
        this.transaction_id = new SimpleIntegerProperty(transaction_id);
        this.customer_id = new SimpleIntegerProperty(customer_id);
        this.staff_id = new SimpleIntegerProperty(staff_id);
//        this.date = new SimpleStringProperty(date);
    }

    public int getTransaction_id() {
        return transaction_id.get();
    }

    public void setTransaction_id(Integer value) {
        transaction_id.set(value);
    }

    public int getCustomer_id() {
        return customer_id.get();
    }

    public void setCustomer_id(Integer value) {
        customer_id.set(value);
    }

    public int getStaff_id() {
        return staff_id.get();
    }

    public void setStaff_id(Integer value) {
        staff_id.set(value);
    }

//    public String getDate() {return date.getValue();}
//
//    public void setDate(String value) {date.set(value);}
}
