package fp_ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Supplier_Product {
    private final IntegerProperty supp_prod_id;
    private final IntegerProperty product_id;
    private final IntegerProperty supplier_id;
    private final IntegerProperty buying_price;

    public Supplier_Product(Integer supp_prod_id, Integer product_id, Integer supplier_id, Integer buying_price) {
        this.supp_prod_id = new SimpleIntegerProperty(supp_prod_id);
        this.product_id = new SimpleIntegerProperty(product_id);
        this.supplier_id = new SimpleIntegerProperty(supplier_id);
        this.buying_price = new SimpleIntegerProperty(buying_price);
    }

    public int getSupp_prod_id() {
        return supp_prod_id.get();
    }

    public void setSupp_prod_id(Integer value) {
        supp_prod_id.set(value);
    }

    public int getProduct_id() {
        return product_id.get();
    }

    public void setProduct_id(Integer value) {
        product_id.set(value);
    }

    public int getSupplier_id() {
        return supplier_id.get();
    }

    public void setSupplier_id(Integer value) {
        supplier_id.set(value);
    }

    public int getBuying_price() {
        return buying_price.get();
    }

    public void setBuying_price(Integer value) {
        buying_price.set(value);
    }

}
