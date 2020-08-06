/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

//import com.mysql.jdbc.Connection;
import fp_ui.*;
//import models.Staff_details;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author m
 */
public class SignUpController implements Initializable
{

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtTelp_no;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnExit;
    @FXML
    private ComboBox<String> txtGender;
    @FXML
    private TableView <Staff_details> tblData;
    @FXML
    private TableColumn<Staff_details, Integer> staff_id;
    @FXML
    private TableColumn<Staff_details, String> staff_name;
    @FXML
    private TableColumn<Staff_details, String> gender;
    @FXML
    private TableColumn<Staff_details, String> address;
    @FXML
    private TableColumn<Staff_details, Integer> telp_no;
    @FXML
    private TableColumn<Staff_details, String> password;
    @FXML
    Label lblStatus;
    private ObservableList<Staff_details> data;
    private PreparedStatement preparedStatement;
    private Connector db;

    //private Connection connection = null;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        txtGender.getItems().addAll("M", "F", "Other");
        txtGender.getSelectionModel().select("M");
        refresh();
    }

    public SignUpController()
    {
        db = new Connector();
        System.out.println("Hello");
        //refresh();
    }

    @FXML
    private void HandleEvents(MouseEvent event)
    {
        //check if not empty
        if (txtAddress.getText().isEmpty() || txtName.getText().isEmpty() || txtTelp_no.getText().isEmpty())
        {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Enter all details");
        }
        else
            {
            saveData();
        }

    }

    private void alertMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Hi there, "+message);
        alert.showAndWait();
    }

    private void clearFields()
    {
        txtName.clear();
        txtID.clear();
        txtAddress.clear();
        txtTelp_no.clear();
        txtPassword.clear();
    }

    private String saveData()
    {
        try
        {
            Connection con = db.Connect();
            String st = "INSERT INTO staff_details (staff_id, staff_name, gender, address, telp_no, password) VALUES (?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) con.prepareStatement(st);
            preparedStatement.setString(1, txtID.getText());
            preparedStatement.setString(2, txtName.getText());
            preparedStatement.setString(3, txtGender.getValue());
            preparedStatement.setString(4, txtAddress.getText());
            preparedStatement.setString(5, txtTelp_no.getText());
            preparedStatement.setString(6, txtPassword.getText());

            preparedStatement.executeUpdate();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Added Successfully");

            //clear fields
            clearFields();
            refresh();
            alertMessage("Welcome! Staff Successfully Added!");
            return "Success";

        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());
            return "Exception";
        }
    }

    @FXML
    public void exitButtonAction(MouseEvent event) throws SQLException
    {
        if (event.getSource() == btnExit)
        {
            try
            {
                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Login.fxml")));
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



    public void refresh()
    {
        try
        {
            Connection con = db.Connect();
            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM staff_details");
            while (rs.next())
            {
                data.add(new Staff_details(rs.getInt("staff_id"), rs.getString("staff_name"), rs.getString("gender"), rs.getString("address"), rs.getString("telp_no"), rs.getString("password")));
            }

            staff_id.setCellValueFactory(new PropertyValueFactory<>("staff_id"));
            staff_name.setCellValueFactory(new PropertyValueFactory<>("staff_name"));
            gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));
            telp_no.setCellValueFactory(new PropertyValueFactory<>("telp_no"));
            password.setCellValueFactory(new PropertyValueFactory<>("password"));

            tblData.setItems(data);
        } catch (SQLException ex) {
            System.out.println("Invalid");
            System.err.println("Error" + ex);
        }
    }


}
