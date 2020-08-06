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

public class SupplierProductController implements Initializable {
    @FXML
    private TextField id_1;
    @FXML
    private TextField id;
    @FXML
    private TextField id_2;
    @FXML
    private TextField price;
    @FXML
    private Button btnBack;
    @FXML
    private TableView<Supplier_Product> tableSupplierProduct;
    @FXML
    private TableColumn<Supplier_Product, Integer> supp_prod_id;
    @FXML
    private TableColumn<Supplier_Product, Integer> supplier_id;
    @FXML
    private TableColumn<Supplier_Product, Integer> product_id;
    @FXML
    private TableColumn<Supplier_Product, Integer> buying_price;

    private ObservableList<Supplier_Product> data;
    private PreparedStatement preparedStatement;
    private Connector db;

    public String command;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new Connector();
        refresh();
    }

    @FXML
    public void insertData() throws SQLException {
        String supplier_id = id.getText();
        String product_id = id_2.getText();
        String buying_price = price.getText();

        int supplier_id_int = Integer.parseInt(supplier_id);
        int product_id_int = Integer.parseInt(product_id);
        int buying_price_int = Integer.parseInt(buying_price);


        String query = "INSERT INTO supplier_product (prod_id, supp_id, buying_price) VALUES (?, ?, ?)";

        Connection con = db.Connect();

        preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, product_id_int);
            preparedStatement.setInt(2, supplier_id_int);
            preparedStatement.setInt(3, buying_price_int);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            id.clear();
            id_2.clear();
            price.clear();
            preparedStatement.execute();
            preparedStatement.close();
            refresh();
        }
    }

    @FXML
    private void deleteData() throws SQLException {
        String retrieved_id = id_1.getText();

        Connection con = db.Connect();

        String query = "DELETE FROM supplier_product WHERE supp_prod_id=" + retrieved_id;

        try {
            preparedStatement = con.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            id.clear();
            preparedStatement.execute();
            preparedStatement.close();
            refresh();
        }
    }

    public void refresh() {
        try {
            Connection con = db.Connect();
            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM supplier_product");
            while (rs.next()) {
                data.add(new Supplier_Product(rs.getInt("supp_prod_id"), rs.getInt("prod_id")
                        , rs.getInt("supp_id"), rs.getInt("buying_price")));
            }

            supp_prod_id.setCellValueFactory(new PropertyValueFactory<>("supp_prod_id"));
            supplier_id.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
            product_id.setCellValueFactory(new PropertyValueFactory<>("product_id"));
            buying_price.setCellValueFactory(new PropertyValueFactory<>("buying_price"));

//        tableProduct.setItems(null);
            tableSupplierProduct.setItems(data);
        } catch (SQLException ex) {
            System.out.println("I am not here G-Ladies");
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
            String supp_prod_id_ = id_1.getText();
            String supplier_id_ = id.getText();
            String product_id_ = id_2.getText();
            String buying_price_ = price.getText();

            int supplier_id_int;
            int product_id_int;
            int buying_price_int;


            if (!supp_prod_id_.equals("")) {
                Connection con = db.Connect();
                if (supplier_id_.equals("")) {
                    String query = "SELECT supp_id FROM supplier_product WHERE supp_prod_id=" + supp_prod_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    supplier_id_int = rs.getInt("supp_id");
                    rs.close();
                } else {
                    supplier_id_int = Integer.parseInt(supplier_id_);
                }
                if (product_id_.equals("")) {
                    String query = "SELECT prod_id FROM supplier_product WHERE supp_prod_id=" + supp_prod_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    product_id_int = rs.getInt("prod_id");
                    rs.close();
                } else {
                    product_id_int = Integer.parseInt(product_id_);
                }
                if (buying_price_.equals("")) {
                    String query = "SELECT buying_price FROM supplier_product WHERE supp_prod_id=" + supp_prod_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    buying_price_int = rs.getInt("buying_price");
                    rs.close();
                } else {
                    buying_price_int = Integer.parseInt(buying_price_);
                }
                String query = "UPDATE supplier_product SET prod_id=?, supp_id=?, buying_price=? WHERE supp_prod_id=?";
                int supp_prod_id_int = Integer.parseInt(supp_prod_id_);

                System.out.println(buying_price_int);
                System.out.println(product_id_int);

                preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, product_id_int);
                preparedStatement.setInt(2, supplier_id_int);
                preparedStatement.setInt(3, buying_price_int);
                preparedStatement.setInt(4, supp_prod_id_int);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Something is missing!");
                alert.setContentText("I have a great message for you!");

                alert.showAndWait();
            }


        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            id_1.clear();
            id.clear();
            id_2.clear();
            price.clear();
            preparedStatement.execute();
            preparedStatement.close();
            refresh();
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
}
