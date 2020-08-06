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


public class StaffController implements Initializable {

    @FXML
    private TextField staffId;
    @FXML
    private TextField staffName;
    @FXML
    private TextField gender;
    @FXML
    private TextField address;
    @FXML
    private TextField telephone;
    @FXML
    private Button btnBack;
    @FXML
    private TableView<Staff_details> tableStaff;
    @FXML
    private TableColumn<Staff_details, Integer> staff_id;
    @FXML
    private TableColumn<Staff_details, String> staff_name;
    @FXML
    private TableColumn<Staff_details, String> staff_gender;
    @FXML
    private TableColumn<Staff_details, String> staff_address;
    @FXML
    private TableColumn<Staff_details, Integer> staff_telp;
    @FXML
    private TableColumn<Staff_details, String> password;

    private ObservableList<Staff_details> data;
    private PreparedStatement preparedStatement;
    private Connector db;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new Connector();
        refresh();
    }

    @FXML
    public void insertData() throws SQLException {
        String staff_Name = staffName.getText();
        String staff_gender = gender.getText();
        String staff_address = address.getText();
        String staff_telp = telephone.getText();

        String query = "INSERT INTO staff_details (staff_name, gender, address, telp_no) VALUES (?, ?, ?, ?)";

        Connection con = db.Connect();

        preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, staff_Name);
            preparedStatement.setString(2, staff_gender);
            preparedStatement.setString(3, staff_address);
            preparedStatement.setString(4, staff_telp);

            if (preparedStatement.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "New Staff Added");
            }

        } catch (SQLException e) {
            System.out.println(e);

        } finally {
            staffName.clear();
            gender.clear();
            address.clear();
            telephone.clear();
            preparedStatement.execute();
            preparedStatement.close();
            refresh();
        }
    }

    @FXML
    private void deleteData() throws SQLException {
        String delete_id = staffId.getText();

        Connection con = db.Connect();

        String query = "DELETE FROM staff_details WHERE staff_id=" + delete_id;

        try {
            preparedStatement = con.prepareStatement(query);

            if (preparedStatement.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Staff Data Deleted");
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            staffId.clear();
            preparedStatement.execute();
            preparedStatement.close();
            refresh();
        }
    }

    public void refresh() {
        try {
            Connection con = db.Connect();
            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM staff_details");
            while (rs.next()) {
                data.add(new fp_ui.Staff_details(rs.getInt("staff_id"), rs.getString("staff_name"), rs.getString("gender"), rs.getString("address"), rs.getString("telp_no"), rs.getString("password")));
            }

            staff_id.setCellValueFactory(new PropertyValueFactory<>("staff_id"));
            staff_name.setCellValueFactory(new PropertyValueFactory<>("staff_name"));
            staff_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            staff_address.setCellValueFactory(new PropertyValueFactory<>("address"));
            staff_telp.setCellValueFactory(new PropertyValueFactory<>("Staff_telp"));
            password.setCellValueFactory(new PropertyValueFactory<>("password"));

            tableStaff.setItems(data);
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
            String staff_id_ = staffId.getText();
            String staff_name_ = staffName.getText();
            String staff_gender_ = gender.getText();
            String staff_address_ = address.getText();
            String staff_telp_ = telephone.getText();

            int staff_id_int;
            String staff_name_string;
            String staff_gender_string;
            String staff_address_string;
            String staff_telp_string;


            if (!staff_id_.equals("")) {
                Connection con = db.Connect();
                if (staff_name_.equals("")) {
                    String query = "SELECT staff_name FROM staff_details WHERE staff_id=" + staff_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    staff_name_string = rs.getString("staff_name");
                    rs.close();
                } else {
                    staff_name_string = staff_name_;
                }
                if (staff_gender_.equals("")) {
                    String query = "SELECT gender FROM staff_details WHERE staff_id=" + staff_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    staff_gender_string = rs.getString("gender");
                    rs.close();
                } else {
                    staff_gender_string = staff_gender_;
                }
                if (staff_address_.equals("")) {
                    String query = "SELECT address FROM staff_details WHERE staff_id=" + staff_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    staff_address_string = rs.getString("address");
                    rs.close();
                } else {
                    staff_address_string = staff_address_;
                }
                if (staff_telp_.equals("")) {
                    String query = "SELECT telp_no FROM staff_details WHERE staff_id=" + staff_id_;
                    preparedStatement = con.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    staff_telp_string = rs.getString("telp_no");
                    rs.close();
                } else {
                    staff_telp_string = staff_telp_;
                }
                String query = "UPDATE staff_details SET staff_name=?, gender=?, address=?, telp_no=? " + "WHERE staff_id=?";
                staff_id_int = Integer.parseInt(staff_id_);

                preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, staff_name_string);
                preparedStatement.setString(2, staff_gender_string);
                preparedStatement.setString(3, staff_address_string);
                preparedStatement.setString(4, staff_telp_string);
                preparedStatement.setInt(5, staff_id_int);

                if (preparedStatement.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Staff Data Updated");
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Something is missing!");
                alert.setContentText("Check your syntax!");

                alert.showAndWait();
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            staffId.clear();
            staffName.clear();
            gender.clear();
            address.clear();
            telephone.clear();
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
