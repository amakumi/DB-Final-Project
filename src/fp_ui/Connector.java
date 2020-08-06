/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp_ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author m
 */
public class Connector {
    public Connection Connect() {
        try {
            String url = "jdbc:mysql://localhost/elfataa" + "?autoReconnect=true&useSSL=false";
            String user = "root";
            String password = "";

            String ivan_url = "jdbc:mysql://dbta.1ez.xyz/Elfataa" + "?autoReconnect=true&useSSL=false";
            String pw = "dnytaj2b";
            String ivan_user = "NAM8420";

            Connection conn;
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return null;
    }
}

