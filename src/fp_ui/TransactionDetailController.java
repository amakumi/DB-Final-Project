package fp_ui;

import fp_ui.Staff_details;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class TransactionDetailController implements Initializable {
    @FXML
    private TextField id_1;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField qty;
    @FXML
    private Button btnBack;
    @FXML
    private TableView<Transaction_Detail> tableTransactionDetails;
    @FXML
    private TableColumn<Transaction_Detail, Integer> transaction_details_id;
    @FXML
    private TableColumn<Transaction_Detail, Integer> transaction_id;
    @FXML
    private TableColumn<Transaction_Detail, String> product_trans_name;
    @FXML
    private TableColumn<Transaction_Detail, Integer> quantity;
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


    private ObservableList<Transaction_Detail> data;
    private ObservableList<Product_Details> data_product;
    private PreparedStatement preparedStatement;
    private Connector db;
    private ResultSet rs;
    private FXMLDocumentController product_info;

    public String command;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new Connector();
        refresh();
        refresh_product();
    }

    public int getMaxId() {
        String query_3 = "SELECT MAX(transaction_details_id) FROM transaction_details";

        Connection con = db.Connect();

        preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement(query_3);
            rs = preparedStatement.executeQuery();
            rs.next();
            int transaction_id_int = rs.getInt(1);
            rs.close();
            System.out.println(transaction_id_int);
            return transaction_id_int;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @FXML
    public void printBill() {
        String transaction_id = id.getText();
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String Date = "Date: " + date;
        String Transaction_Id = "Transaction id: " + transaction_id;

        preparedStatement = null;
        Connection con = db.Connect();

        if (!transaction_id.equals("")) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("d:/receipt.pdf"));
                document.open();
                document.add(new Paragraph("                  THEFOODHALL                                      "));
                document.add(new Paragraph("               -  FX Sudirman  -                "));
                document.add(new Paragraph("                                                       "));
                document.add(new Paragraph("==============================================                                  "));
                document.add(new Paragraph(Date));
                document.add(new Paragraph("Bill ID:"+ Transaction_Id));
                document.add(new Paragraph("----------------------------------------------"));

                String query_1 = "SELECT product.product_name, transaction_details.quantity, product.price FROM transaction_details INNER JOIN product ON transaction_details.product_id = product.product_id WHERE transaction_details.`transaction_id` = " + transaction_id;
                preparedStatement = con.prepareStatement(query_1);
                ResultSet rs = preparedStatement.executeQuery();

                document.add(new Paragraph("Product Name     Quantity     Price"));

                while (rs.next()) {
                    String product_name = rs.getString("product_name");
                    String quantity = rs.getString("quantity");
                    String price = rs.getString("price");

                    String Info = product_name + "               " + quantity + "                " + price;


                    document.add(new Paragraph(Info));
                }

                String query_2 = "SELECT SUM(product.price * transaction_details.quantity) FROM transaction_details INNER JOIN product ON transaction_details.product_id = product.product_id WHERE transaction_details.`transaction_id` = " + transaction_id;
                preparedStatement = con.prepareStatement(query_2);
                rs = preparedStatement.executeQuery();
                rs.next();

                String total_price = rs.getString(1);
                String Total_Price = "Total Price: " + total_price;

                document.add(new Paragraph("-----------------------------------------------"));
                document.add(new Paragraph(Total_Price));
                document.add(new Paragraph("_______________________________________________                                    "));
                document.add(new Paragraph("   THANK YOU FOR BEING OUR LOYAL CUSTOMER"));
                document.add(new Paragraph("       Sahid Sudirman Center Lt. 38"));
                document.add(new Paragraph("        Jl. Jend. Sudirman Kav. 86"));
                document.add(new Paragraph("                Jakarta"));
                document.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Receipt successfully printed!");

                alert.showAndWait();
            } catch (Exception e) {
                System.out.println(e);
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something is missing!");
            alert.setContentText("The ID is empty");

            alert.showAndWait();
        }

    }

    @FXML
    public void display() {
        Product_Details pd = tableProduct.getSelectionModel().getSelectedItem();
        name.setText(pd.getProduct_name());
    }

    @FXML
    public void insertData() throws SQLException {
        String transaction_id = id.getText();
        String product_name_string = name.getText();
        String quantity = qty.getText();

        int transaction_id_int = Integer.parseInt(transaction_id);
        int quantity_int = Integer.parseInt(quantity);


        String query = "INSERT INTO transaction_details (transaction_id, product_id, quantity) VALUES (?, ?, ?)";

        Connection con = db.Connect();

        preparedStatement = null;

        String query_2 = "SELECT product_id, product_quantity FROM product WHERE product_name=?";
        preparedStatement = con.prepareStatement(query_2);
        preparedStatement.setString(1, product_name_string);
        rs = preparedStatement.executeQuery();
        rs.next();
        int product_id_int = rs.getInt("product_id");
        int product_quantity_in_table_product = rs.getInt("product_quantity");
        rs.close();

        if ((quantity_int - product_quantity_in_table_product) > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something is missing!");
            alert.setContentText("Sorry we don't have this number of items. Kindly insert a lesser number than stated before");

            alert.showAndWait();
        } else if (product_quantity_in_table_product == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something is missing!");
            alert.setContentText("Oops, the item is finished");

            alert.showAndWait();
        } else {
            try {
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, transaction_id_int);
                preparedStatement.setInt(2, product_id_int);
                preparedStatement.setInt(3, quantity_int);
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                id_1.clear();
                id.clear();
                name.clear();
                qty.clear();
                preparedStatement.execute();
                preparedStatement.close();
                refresh();
                refresh_product();

                try {
                    int transaction_details_id_int = getMaxId();

                    String query_4 = "UPDATE product INNER JOIN transaction_details ON transaction_details.product_id = product.product_id SET " +
                            "product_quantity=product_quantity-? WHERE transaction_details_id=?";
                    preparedStatement = con.prepareStatement(query_4);
                    preparedStatement.setInt(1, quantity_int);
                    preparedStatement.setInt(2, transaction_details_id_int);
                } catch (SQLException e) {
                    System.out.println(e);
                } finally {
                    preparedStatement.execute();
                    preparedStatement.close();
                }
            }
        }
    }


    @FXML
    private void deleteData() throws SQLException {
        String retrieved_id = id_1.getText();

        Connection con = db.Connect();

        String query = "DELETE FROM transaction_details WHERE transaction_details_id=" + retrieved_id;

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
            ResultSet rs = con.createStatement().executeQuery("SELECT transaction_details_id, transaction_id, product.product_name, quantity FROM transaction_details INNER JOIN product ON transaction_details.`product_id`= product.product_id");
            while (rs.next()) {
                data.add(new Transaction_Detail(rs.getInt("transaction_details_id"), rs.getInt("transaction_id")
                        , rs.getString("product_name"), rs.getInt("quantity")));
            }

            transaction_details_id.setCellValueFactory(new PropertyValueFactory<>("transaction_details_id"));
            transaction_id.setCellValueFactory(new PropertyValueFactory<>("transaction_id"));
            product_trans_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
            quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

//        tableProduct.setItems(null);
            tableTransactionDetails.setItems(data);
            refresh_product();

        } catch (SQLException ex) {
            System.out.println("I am not here G-Ladies");
            System.err.println("Error" + ex);
        }
    }

    public void refresh_product() {
        try {
            Connection con = db.Connect();
            data_product = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM product");
            while (rs.next()) {
                data_product.add(new Product_Details(rs.getInt("product_id"), rs.getString("product_name"), rs.getInt("product_quantity"), rs.getString("type"), rs.getInt("price"),
                        rs.getString("expiry_date")));
            }

            product_id.setCellValueFactory(new PropertyValueFactory<>("product_id"));
            product_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
            product_quantity.setCellValueFactory(new PropertyValueFactory<>("product_quantity"));
            product_type.setCellValueFactory(new PropertyValueFactory<>("type"));
            product_price.setCellValueFactory(new PropertyValueFactory<>("price"));
            product_expiry_date.setCellValueFactory(new PropertyValueFactory<>("expiry_date"));

//        tableProduct.setItems(null);
            tableProduct.setItems(data_product);


        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
    }


    @FXML
    private void handleButtonAction(ActionEvent event) {
        refresh();
        refresh_product();
    }

    @FXML
    public void displayNo() {
        Transaction_Detail td = tableTransactionDetails.getSelectionModel().getSelectedItem();
        String transaction_details_string = String.valueOf(td.getTransaction_details_id());

        id_1.setText(transaction_details_string);
    }

    @FXML
    private void updateData() throws SQLException {
        try {
            String transaction_details_id_ = id_1.getText();
            String transaction_id_ = id.getText();
            String product_name_ = name.getText();
            String quantity_ = qty.getText();

            int transaction_id_int;
            int product_id_int;
            int quantity_int;


            if (!transaction_details_id_.equals("")) {
                Connection con = db.Connect();
                if (transaction_id_.equals("")) {
                    String query = "SELECT transaction_id FROM transaction_details WHERE transaction_details_id=" + transaction_details_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    transaction_id_int = rs.getInt("transaction_id");
                    rs.close();
                } else {
                    transaction_id_int = Integer.parseInt(transaction_id_);
                }
                if (product_name_.equals("")) {
                    String query = "SELECT product_id FROM transaction_details WHERE transaction_details_id=" + transaction_details_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    product_id_int = rs.getInt("product_id");
                    rs.close();
                } else {
                    String query = "SELECT product_id FROM product WHERE product_name=?";
                    preparedStatement = con.prepareStatement(query);
                    preparedStatement.setString(1, product_name_);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    product_id_int = rs.getInt("product_id");
                    rs.close();

                }
                if (quantity_.equals("")) {
                    String query = "SELECT quantity FROM transaction_details WHERE transaction_details_id=" + transaction_details_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    quantity_int = rs.getInt("quantity");
                    rs.close();
                } else {
                    quantity_int = Integer.parseInt(quantity_);
                }
                String query = "UPDATE transaction_details SET transaction_id=?, product_id=?, quantity=? WHERE transaction_details_id=?";
                int transaction_details_id_int = Integer.parseInt(transaction_details_id_);

                preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, transaction_id_int);
                preparedStatement.setInt(2, product_id_int);
                preparedStatement.setInt(3, quantity_int);
                preparedStatement.setInt(4, transaction_details_id_int);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Something is missing!");
                alert.setContentText("Please enter your ID!!!");

                alert.showAndWait();
            }


        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            id_1.clear();
            id.clear();
            qty.clear();
            preparedStatement.execute();
            preparedStatement.close();
            refresh();
            refresh_product();
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
