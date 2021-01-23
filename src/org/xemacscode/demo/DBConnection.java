/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xemacscode.demo;
import java.sql.*;
/**
 *
 * @author camil
 */
public class DBConnection {
    static final String DB_URL="jdbc:mysql://localhost/mytimescheduler";
    static final String USER="root";
    static final String PASS="";
    public static Connection connectDB()
    {
        try{
            Connection conn=null;
            //Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(DB_URL,USER,PASS);
            return conn;
        }catch(SQLException ex)
        {
            System.out.println("There were errors while connecting to db.");
            return null;
        }
    }
}
