package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author e
 */

public class MenuController2
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
    private Button btnExit;

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
                System.out.println("Wrong action");

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
                System.out.println("Wrong action");

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
                System.out.println("Wrong action");
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
                System.out.println("Wrong action");

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
                System.out.println("Wrong action");

            }
        }
    }
}
