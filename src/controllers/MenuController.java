package controllers;

import fp_ui.Connector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MenuController
{

    @FXML
    private Button btnTransaction;
    @FXML
    private Button btnTransDetail;
    @FXML
    private Button btnProduct;
    @FXML
    private Button btnCustomer;
    @FXML
    private Button btnStaff;
    @FXML
    private Button btnSupplier;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnSupplierProduct;

    private Connector db;
    private Connection conn1 = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
//
//    static String transaction(String username){
//        return username;
//    }

    @FXML
    public void transactionButtonAction(MouseEvent event) throws IOException
    {
        if (event.getSource() == btnTransaction)
        {
            try
            {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fp_ui/Transaction.fxml")));
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

    @FXML
    public void transDetailButtonAction(MouseEvent event) throws IOException
    {
        if (event.getSource() == btnTransDetail)
        {
            try
            {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fp_ui/TransactionDetail.fxml")));
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

    @FXML
    public void productButtonAction(MouseEvent event) throws IOException
    {
        if (event.getSource() == btnProduct)
        {
            try
            {
                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fp_ui/FXMLDocument.fxml")));
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

    @FXML
    public void customerButtonAction(MouseEvent event) throws IOException
    {
        if (event.getSource() == btnCustomer)
        {
            try
            {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fp_ui/Customer.fxml")));
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

    @FXML
    public void staffButtonAction(MouseEvent event) throws IOException
    {
        if (event.getSource() == btnStaff)
        {
            try
            {
                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fp_ui/Staff.fxml")));
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

    @FXML
    public void supplierButtonAction(MouseEvent event) throws IOException
    {
        if (event.getSource() == btnSupplier)
        {
            try
            {
                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fp_ui/Supplier.fxml")));
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

    @FXML
    public void supplierProductButtonAction(MouseEvent event) throws IOException
    {
        if (event.getSource() == btnSupplierProduct)
        {
            try
            {
                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fp_ui/Supplier_Product.fxml")));
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

    @FXML
    public void exitButtonAction(MouseEvent event) throws IOException
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
}
