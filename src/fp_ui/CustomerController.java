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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private TextField customerId;
    @FXML
    private TextField customerName;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private TextField address;
    @FXML
    private TextField telephone;
    @FXML
    private TextField dob;
    @FXML
    private TableView<Customer_Details> tableCustomer;
    @FXML
    private TableColumn<Customer_Details, Integer> customer_id;
    @FXML
    private TableColumn<Customer_Details, String> customer_name;
    @FXML
    private TableColumn<Customer_Details, String> customer_gender;
    @FXML
    private TableColumn<Customer_Details, String> customer_address;
    @FXML
    private TableColumn<Customer_Details, Integer> customer_telp;
    @FXML
    private TableColumn<Customer_Details, String> customer_dob;
    @FXML
    private Button btnBack;

    private ObservableList<Customer_Details> data;
    private PreparedStatement preparedStatement;
    private Connector db;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new Connector();
        gender.getItems().addAll("M", "F", "Others");
        gender.getSelectionModel().select("M");
        refresh();
    }

    @FXML
    public void insertData() throws SQLException {
        String customer_name = customerName.getText();
        String customer_gender = gender.getValue();
        String customer_address = address.getText();
        String customer_telp = telephone.getText();
        String customer_dob = dob.getText();

        String query = "INSERT INTO customer_details (customer_name, gender, address, telp_no, dob) VALUES (?, ?, ?, ?, ?)";

        Connection con = db.Connect();

        preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, customer_name);
            preparedStatement.setString(2, customer_gender);
            preparedStatement.setString(3, customer_address);
            preparedStatement.setString(4, customer_telp);
            preparedStatement.setString(5, customer_dob);


            if (preparedStatement.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "New Customer Data Added");
            }

        } catch (SQLException e) {
            System.out.println(e);

        } finally {
            customerName.clear();
            //gender.clear();
            address.clear();
            telephone.clear();
            dob.clear();
            preparedStatement.execute();
            preparedStatement.close();
            refresh();
        }
    }

    @FXML
    private void deleteData() throws SQLException {
        String delete_id = customerId.getText();

        Connection con = db.Connect();

        String query = "DELETE FROM customer_details WHERE customer_id=" + delete_id;

        try {
            preparedStatement = con.prepareStatement(query);

            if (preparedStatement.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Customer Data Deleted");
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            customerId.clear();
            preparedStatement.execute();
            preparedStatement.close();
            refresh();
        }
    }

    public void refresh() {
        try {
            Connection con = db.Connect();
            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM customer_details");
            while (rs.next()) {
                data.add(new Customer_Details(rs.getInt("customer_id"), rs.getString("customer_name"), rs.getString("gender"), rs.getString("address"), rs.getString("telp_no"), rs.getString("dob")));
            }

            customer_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
            customer_name.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
            customer_gender.setCellValueFactory(new PropertyValueFactory<>("customer_gender"));
            customer_address.setCellValueFactory(new PropertyValueFactory<>("customer_address"));
            customer_telp.setCellValueFactory(new PropertyValueFactory<>("customer_telp"));
            customer_dob.setCellValueFactory(new PropertyValueFactory<>("customer_dob"));

            tableCustomer.setItems(data);
        } catch (SQLException ex) {
            System.out.println("Invalid");
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
            String customer_id_ = customerId.getText();
            String customer_name_ = customerName.getText();
            String customer_gender_ = gender.getValue();
            String customer_address_ = address.getText();
            String customer_telp_ = telephone.getText();
            String customer_dob_ = dob.getText();


            int customer_id_int;
            String customer_name_string;
            String customer_gender_string;
            String customer_address_string;
            String customer_telp_string;
            String customer_dob_string;


            if (!customer_id_.equals("")) {
                Connection con = db.Connect();
                if (customer_name_.equals("")) {
                    String query = "SELECT customer_name FROM customer_details WHERE customer_id=" + customer_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    customer_name_string = rs.getString("customer_name");
                    rs.close();
                } else {
                    customer_name_string = customer_name_;
                }
                if (customer_gender_.equals("")) {
                    String query = "SELECT gender FROM customer_details WHERE customer_id=" + customer_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    customer_gender_string = rs.getString("gender");
                    rs.close();
                } else {
                    customer_gender_string = customer_gender_;
                }
                if (customer_address_.equals("")) {
                    String query = "SELECT address FROM customer_details WHERE customer_id=" + customer_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    customer_address_string = rs.getString("address");
                    rs.close();
                } else {
                    customer_address_string = customer_address_;
                }
                if (customer_telp_.equals("")) {
                    String query = "SELECT telp_no FROM customer_details WHERE customer_id=" + customer_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    customer_telp_string = rs.getString("telp_no");
                    rs.close();
                } else {
                    customer_telp_string = customer_telp_;
                }
                if (customer_dob_.equals("")) {
                    String query = "SELECT dob FROM customer_details WHERE customer_id=" + customer_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    customer_dob_string = rs.getString("dob");
                    rs.close();
                } else {
                    customer_dob_string = customer_dob_;
                }

                String query = "UPDATE customer_details SET customer_name=?, gender=?, address=?, telp_no=?, " +
                        "dob=? " + "WHERE customer_id=?";
                customer_id_int = Integer.parseInt(customer_id_);

                preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, customer_name_string);
                preparedStatement.setString(2, customer_gender_string);
                preparedStatement.setString(3, customer_address_string);
                preparedStatement.setString(4, customer_telp_string);
                preparedStatement.setString(5, customer_dob_string);
                preparedStatement.setInt(6, customer_id_int);

                if (preparedStatement.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Staff Data Updated");
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Something is missing!");
                alert.setContentText("Check again");

                alert.showAndWait();
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            customerId.clear();
            customerName.clear();
            //gender.clear();
            address.clear();
            telephone.clear();
            dob.clear();
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

