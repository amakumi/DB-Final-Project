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

public class TransactionController implements Initializable {
    @FXML
    private TextField id_1;
    @FXML
    private TextField id;
    @FXML
    private TextField id_2;
    @FXML
    private TableView<Transaction> tableTransaction;
    @FXML
    private TableColumn<Transaction, Integer> transaction_id;
    @FXML
    private TableColumn<Transaction, Integer> customer_id;
    @FXML
    private TableColumn<Transaction, Integer> staff_id;

    @FXML
    private Button btnBack;

    private ObservableList<Transaction> data;
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
        String customer_id = id.getText();
        String staff_id = id_2.getText();

        int customer_id_int = Integer.parseInt(customer_id);
        int staff_id_int = Integer.parseInt(staff_id);

        String query = "INSERT INTO transaction (customer_id, staff_id) VALUES (?, ?)";

        Connection con = db.Connect();

        preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, customer_id_int);
            preparedStatement.setInt(2, staff_id_int);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            id.clear();
            id_2.clear();
            preparedStatement.execute();
            preparedStatement.close();
            refresh();
        }
    }

    @FXML
    private void deleteData() throws SQLException {
        String retrieved_id = id_1.getText();

        Connection con = db.Connect();

        String query = "DELETE FROM transaction WHERE transaction_id=" + retrieved_id;

        try {
            preparedStatement = con.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            id_1.clear();
            preparedStatement.execute();
            preparedStatement.close();
            refresh();
        }
    }

    public void refresh() {
        try {
            Connection con = db.Connect();
            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM transaction");
            while (rs.next()) {
                data.add(new Transaction(rs.getInt("transaction_id"), rs.getInt("customer_id"), rs.getInt("staff_id")));
            }

            transaction_id.setCellValueFactory(new PropertyValueFactory<>("transaction_id"));
            customer_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
            staff_id.setCellValueFactory(new PropertyValueFactory<>("staff_id"));

//        tableProduct.setItems(null);
            tableTransaction.setItems(data);
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        refresh();
    }

    @FXML
    private void updateData() throws SQLException
    {
        try
        {
            String transaction_id_ = id_1.getText();
            String customer_id_ = id.getText();
            String staff_id_ = id_2.getText();

            int transaction_id_int;
            int customer_id_int;
            int staff_id_int;

            if (!transaction_id_.equals(""))
            {
                Connection con = db.Connect();
                if (customer_id_.equals(""))
                {
                    String query = "SELECT customer_id FROM transaction WHERE transaction_id=" + transaction_id_;

                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    customer_id_int = rs.getInt("transaction_id");
                    rs.close();
                }
                else
                    {
                    customer_id_int = Integer.parseInt(customer_id_);
                }
                if (staff_id_.equals(""))
                {
                    String query = "SELECT staff_id FROM transaction WHERE transaction_id=" + transaction_id_;

                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    staff_id_int = rs.getInt("staff_id");
                    rs.close();
                }
                else
                    {
                    staff_id_int = Integer.parseInt(staff_id_);
                }
                String query = "UPDATE transaction SET customer_id=?, staff_id=? WHERE transaction_id=?";
                transaction_id_int = Integer.parseInt(transaction_id_);

                preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, customer_id_int);
                preparedStatement.setInt(2, staff_id_int);
                preparedStatement.setInt(3, transaction_id_int);

            }
            else
                {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Something is missing!");
                alert.setContentText("Unable to update");

                alert.showAndWait();
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        finally
        {
            id_1.clear();
            id.clear();
            id_2.clear();
            preparedStatement.execute();
            preparedStatement.close();
            refresh();
        }
    }

    @FXML
    public void backButtonAction(MouseEvent event) throws SQLException
    {
        if (event.getSource() == btnBack)
        {
            try
            {
                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml")));
                stage.setScene(scene);
                stage.show();

            }
            catch (IOException ex)
            {
                System.err.println(ex.getMessage());
                System.out.println("WTFFFF");

            }
        }
    }

}
