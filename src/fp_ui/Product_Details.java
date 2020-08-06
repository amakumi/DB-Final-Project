/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp_ui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * // *
 *
 * @author Naman
 */
public class Product_Details {

    private final IntegerProperty product_id;
    private final StringProperty product_name;
    private final IntegerProperty product_quantity;
    private final StringProperty type;
    private final IntegerProperty price;
    private final StringProperty expiry_date;

    public Product_Details(Integer product_id, String product_name, Integer product_quantity,
                           String type, Integer price, String expiry_date) {
        this.product_id = new SimpleIntegerProperty(product_id);
        this.product_name = new SimpleStringProperty(product_name);
        this.product_quantity = new SimpleIntegerProperty(product_quantity);
        this.type = new SimpleStringProperty(type);
        this.price = new SimpleIntegerProperty(price);
        this.expiry_date = new SimpleStringProperty(expiry_date);
    }


    public int getProduct_id() {
        return product_id.get();
    }

    public void setProduct_id(Integer value) {
        product_id.set(value);
    }

    public String getProduct_name() {
        return product_name.get();
    }

    public void setProduct_name(String value) {
        product_name.set(value);
    }

    public int getProduct_quantity() {
        return product_quantity.get();
    }

    public void setProduct_quantity(Integer value) {
        product_quantity.set(value);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String value) {
        type.set(value);
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(Integer value) {
        price.set(value);
    }

    public String getExpiry_date() {
        return expiry_date.get();
    }

    public void setExpiry_date(String value) {
        expiry_date.set(value);
    }
}
