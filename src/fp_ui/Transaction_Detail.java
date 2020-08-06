package fp_ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaction_Detail {
    private final IntegerProperty transaction_details_id;
    private final IntegerProperty transaction_id;
    private final StringProperty product_name;
    private final IntegerProperty quantity;

    public Transaction_Detail(Integer transaction_details_id, Integer transaction_id, String product_name, Integer quantity) {
        this.transaction_details_id = new SimpleIntegerProperty(transaction_details_id);
        this.transaction_id = new SimpleIntegerProperty(transaction_id);
        this.product_name = new SimpleStringProperty(product_name);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public int getTransaction_details_id() {
        return transaction_details_id.get();
    }

    public void setTransaction_details_id(Integer value) {
        transaction_details_id.set(value);
    }

    public int getTransaction_id() {
        return transaction_id.get();
    }

    public void setTransaction_id(Integer value) {
        transaction_id.set(value);
    }

    public String getProduct_name() {
        return product_name.get();
    }

    public void setProduct_name(String value) {
        product_name.set(value);

    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(Integer value) {
        quantity.set(value);
    }
}
