/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xemacscode.demo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikoa
 */
public class UpdateAppointmentService {
    
    public static void deleteAppointment(Integer id) {
        try {
            Connection connection = DBConnection.connectDB();
            
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM `appointments` WHERE `appointments`.`id` = ?");
            deleteStatement.setInt(1, id);
            
            deleteStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UpdateAppointmentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
