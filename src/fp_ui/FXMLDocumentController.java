/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp_ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author m
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField quantity;
    @FXML
    private ComboBox<String> type;
    @FXML
    private TextField price;
    @FXML
    private TextField expiry_date;
    @FXML
    private TableView<Product_Details> tableProduct;
    @FXML
    private TableColumn<Product_Details, Integer> product_id;
    @FXML
    private TableColumn<Product_Details, String> product_name;
    @FXML
    private TableColumn<Product_Details, Integer> product_quantity;
    @FXML
    private TableColumn<Product_Details, String> product_type;
    @FXML
    private TableColumn<Product_Details, Integer> product_price;
    @FXML
    private TableColumn<Product_Details, String> product_expiry_date;
    @FXML
    private Button btn_generate;
    @FXML
    private Button btnSignOut;
    @FXML
    private Button btnBack;

    private ObservableList<Product_Details> data;
    private PreparedStatement preparedStatement;
    private Connector db;

    public String command;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new Connector();
        type.getItems().addAll("Generic Medicine", "Antibiotic", "Vitamin", "Paracetamol", "Capsules", "Inhalers", "Injections", "Tablets", "Others");
        type.getSelectionModel().select("Generic Medicine");
    }

    @FXML
    public void insertData() throws SQLException {
        String product_name = name.getText();
        String product_quantity = quantity.getText();
        String product_type = type.getValue();
        String product_price = price.getText();
        String product_expiry_date = expiry_date.getText();

        int product_quantity_int = Integer.parseInt(product_quantity);
        int product_price_int = Integer.parseInt(product_price);

        String query = "INSERT INTO product (product_name, product_quantity, type, price, expiry_date) VALUES (?, ?, ?, ?, ?)";

        Connection con = db.Connect();

        preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, product_name);
            preparedStatement.setInt(2, product_quantity_int);
            preparedStatement.setString(3, product_type);
            preparedStatement.setInt(4, product_price_int);
            preparedStatement.setString(5, product_expiry_date);
        } catch (SQLException e) {
            alertMessage("" + e);
            System.out.println(e);
        } finally {
            name.clear();
            quantity.clear();
            //type.clear();
            price.clear();
            expiry_date.clear();
            preparedStatement.execute();
//            preparedStatement.close();
            refresh();
        }
    }

    @FXML
    private void deleteData() throws SQLException {
        String retrieved_id = id.getText();
        Connection con = db.Connect();

        String query = "DELETE FROM product WHERE product_id=" + retrieved_id;

        try {
            preparedStatement = con.prepareStatement(query);
        } catch (SQLException e) {
            alertMessage("" + e);
            System.out.println(e);
        } finally {
            id.clear();
            preparedStatement.execute();
//            preparedStatement.close();1234
            refresh();
        }
    }

    public void refresh() {
        try {
            Connection con = db.Connect();
            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM product");
            while (rs.next()) {
                data.add(new Product_Details(rs.getInt("product_id"), rs.getString("product_name"), rs.getInt("product_quantity"), rs.getString("type"), rs.getInt("price"),
                        rs.getString("expiry_date")));
            }

            product_id.setCellValueFactory(new PropertyValueFactory<>("product_id"));
            product_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
            product_quantity.setCellValueFactory(new PropertyValueFactory<>("product_quantity"));
            product_type.setCellValueFactory(new PropertyValueFactory<>("type"));
            product_price.setCellValueFactory(new PropertyValueFactory<>("price"));
            product_expiry_date.setCellValueFactory(new PropertyValueFactory<>("expiry_date"));

//        tableProduct.setItems(null);
            tableProduct.setItems(data);


        } catch (SQLException ex) {
            alertMessage("" + ex);
            System.err.println("Error" + ex);
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        refresh();
    }

    @FXML
    private void updateData() throws SQLException {
        try {
            String product_id_ = id.getText();
            String product_name_ = name.getText();
            String product_quantity_ = quantity.getText();
            String product_type_ = type.getValue();
            String product_price_ = price.getText();
            String product_expiry_date_ = expiry_date.getText();

            int product_id_int;
            String product_name_string;
            int product_quantity_int;
            String product_type_string;
            int product_price_int;
            String product_expiry_date_string;

            if (!product_id_.equals("")) {
                Connection con = db.Connect();
                if (product_name_.equals("")) {
                    String query = "SELECT product_name FROM product WHERE product_id=" + product_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    product_name_string = rs.getString("product_name");
                    rs.close();
                } else {
                    product_name_string = product_name_;
                }

                if (product_quantity_.equals("")) {
                    String query = "SELECT product_quantity FROM product WHERE product_id=" + product_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    product_quantity_int = rs.getInt("product_quantity");
                    rs.close();
                } else {
                    product_quantity_int = Integer.parseInt(product_quantity_);
                }

                if (product_type_.equals("")) {
                    String query = "SELECT type FROM Product WHERE product_id=" + product_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    product_type_string = rs.getString("type");
                    rs.close();
                } else {
                    product_type_string = product_type_;
                }

                if (product_price_.equals("")) {
                    String query = "SELECT price FROM Product WHERE product_id=" + product_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    product_price_int = rs.getInt("price");
                    rs.close();
                } else {
                    product_price_int = Integer.parseInt(product_price_);
                }

                if (product_expiry_date_.equals("")) {
                    String query = "SELECT expiry_date FROM Product WHERE product_id=" + product_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    product_expiry_date_string = rs.getString("expiry_date");
                    rs.close();
                } else {
                    product_expiry_date_string = product_expiry_date_;
                }

                String query = "UPDATE product SET product_name=?, product_quantity=?, type=?, price=?, " +
                        "expiry_date=? " + "WHERE product_id=?";

                product_id_int = Integer.parseInt(product_id_);

                preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, product_name_string);
                preparedStatement.setInt(2, product_quantity_int);
                preparedStatement.setString(3, product_type_string);
                preparedStatement.setInt(4, product_price_int);
                preparedStatement.setString(5, product_expiry_date_string);
                preparedStatement.setInt(6, product_id_int);

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alertMessage("Something is missing!");
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            id.clear();
            name.clear();
            quantity.clear();
            //type.clear();
            price.clear();
            expiry_date.clear();
            preparedStatement.execute();
            preparedStatement.close();
            refresh();
        }
    }

    @FXML
    public void signOutButtonAction(MouseEvent event) {
        if (event.getSource() == btnSignOut) {
            try {
                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Login.fxml")));
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }

        }
    }

    @FXML
    public void backButtonAction(MouseEvent event) throws SQLException {
        if (event.getSource() == btnBack) {
            try {
                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml")));
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                System.out.println("WTFFFF");

            }
        }
    }

    private void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Hi there, " + message);
        alert.showAndWait();
    }

}
