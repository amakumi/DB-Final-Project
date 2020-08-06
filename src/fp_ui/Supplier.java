package fp_ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Supplier {
    private final IntegerProperty supplier_id;
    private final StringProperty supplier_name;

    public Supplier(Integer supplier_id, String supplier_name) {
        this.supplier_id = new SimpleIntegerProperty(supplier_id);
        this.supplier_name = new SimpleStringProperty(supplier_name);
    }

    public int getSupplier_id() {
        return supplier_id.get();
    }

    public void setSupplier_id(Integer value) {
        supplier_id.set(value);
    }

    public String getSupplier_name() {
        return supplier_name.get();
    }

    public void setSupplier_name(String value) {
        supplier_name.set(value);
    }
}
