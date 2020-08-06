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

public class SupplierController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TableView<Supplier> tableSupplier;
    @FXML
    private Button btnBack;
    @FXML
    private TableColumn<Supplier, Integer> supplier_id;
    @FXML
    private TableColumn<Supplier, String> supplier_name;

    private ObservableList<Supplier> data;
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
        String supplier_name = name.getText();

        String query = "INSERT INTO supplier (supplier_name) VALUES (?)";

        Connection con = db.Connect();

        preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, supplier_name);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            name.clear();
            preparedStatement.execute();
            preparedStatement.close();
            refresh();
        }
    }

    @FXML
    private void deleteData() throws SQLException {
        String retrieved_id = id.getText();

        Connection con = db.Connect();

        String query = "DELETE FROM supplier WHERE supplier_id=" + retrieved_id;

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
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM supplier");
            while (rs.next()) {
                data.add(new Supplier(rs.getInt("supplier_id"), rs.getString("supplier_name")));
            }

            supplier_id.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
            supplier_name.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));


//        tableProduct.setItems(null);
            tableSupplier.setItems(data);
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
            String supplier_id_ = id.getText();
            String supplier_name_ = name.getText();


            if (!supplier_id_.equals("")) {
                Connection con = db.Connect();
                if (!supplier_name_.equals("")) {
                    String query = "UPDATE supplier SET supplier_name=? WHERE supplier_id=?";
                    int supplier_id_int = Integer.parseInt(supplier_id_);

                    preparedStatement = con.prepareStatement(query);
                    preparedStatement.setString(1, supplier_name_);
                    preparedStatement.setInt(2, supplier_id_int);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Something is missing!");
                alert.setContentText("I have a great message for you!");

                alert.showAndWait();
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            id.clear();
            name.clear();
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
