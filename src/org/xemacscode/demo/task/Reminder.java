/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xemacscode.demo.task;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xemacscode.demo.DBConnection;
import org.xemacscode.demo.UserProvider;
import org.xemacscode.demo.email.MailService;

/**
 *
 * @author nikoa
 */
public class Reminder extends TimerTask {

    Connection connection;
    MailService mailService;

    public Reminder() {
        connection = DBConnection.connectDB();
        mailService = new MailService();
    }

    @Override
    public void run() {
        try {
            Logger.getLogger(Reminder.class.getName()).info("Checking for Due Appointments");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime inOneWeek = now.plusDays(7);
            LocalDateTime inThreeDays = now.plusDays(3);
            LocalDateTime inOneHour = now.plusHours(1);
            LocalDateTime inTenMinutes = now.plusMinutes(10);
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT a.*, u.email FROM `appointments` AS a ");
            query.append("JOIN users as u ON u.id = a.user_id ");
            query.append("WHERE (`beginDate` = ? AND `beginTime` = ? AND `reminder` = '1 week') ");
            query.append("OR (`beginDate` = ? AND `beginTime` = ? AND `reminder` = '3 days') ");
            query.append("OR (`beginDate` = ? AND `beginTime` = ? AND `reminder` = '1 hour') ");
            query.append("OR (`beginDate` = ? AND `beginTime` = ? AND `reminder` = '10 minutes')");
            PreparedStatement dueAppointmentsStatement = connection.prepareStatement(query.toString());
            
            dueAppointmentsStatement.setDate(1, Date.valueOf(inOneWeek.toLocalDate()));
            dueAppointmentsStatement.setTime(2, Time.valueOf(inOneWeek.toLocalTime().withSecond(0)));
            
            dueAppointmentsStatement.setDate(3, Date.valueOf(inThreeDays.toLocalDate()));
            dueAppointmentsStatement.setTime(4, Time.valueOf(inThreeDays.toLocalTime().withSecond(0)));
            
            dueAppointmentsStatement.setDate(5, Date.valueOf(inOneHour.toLocalDate()));
            dueAppointmentsStatement.setTime(6, Time.valueOf(inOneHour.toLocalTime().withSecond(0)));
            
            dueAppointmentsStatement.setDate(7, Date.valueOf(inTenMinutes.toLocalDate()));
            dueAppointmentsStatement.setTime(8, Time.valueOf(inTenMinutes.toLocalTime().withSecond(0)));
            
            ResultSet dueAppointments = dueAppointmentsStatement.executeQuery();
            
            while(dueAppointments.next()) { 
            Logger.getLogger(Reminder.class.getName()).info("Sending Reminder");               
                List<String> recipients;

                String participants = dueAppointments.getString("participants");
                if (participants.isBlank()) {
                    recipients = List.of(dueAppointments.getString("email"));
                } else {
                    recipients = Arrays.asList((participants + "," + dueAppointments.getString("email")).split(","));
                }
                StringBuilder reminderMessage = new StringBuilder();
                reminderMessage.append(String.format("Appointment: %s\n\n", dueAppointments.getString("name")));
                reminderMessage.append(String.format("Start: %s %s\n",
                        dueAppointments.getDate("beginDate").toLocalDate().format(DateTimeFormatter.ISO_DATE),
                        dueAppointments.getTime("beginTime").toLocalTime().format(DateTimeFormatter.ISO_TIME)));
                reminderMessage.append(String.format("End: %s %s\n",
                        dueAppointments.getDate("endDate").toLocalDate().format(DateTimeFormatter.ISO_DATE),
                        dueAppointments.getTime("endTime").toLocalTime().format(DateTimeFormatter.ISO_TIME)));
                String location = dueAppointments.getString("location");
                if (!location.isBlank()) {
                    reminderMessage.append(String.format("Location: %s \n", location));
                }
                if (!participants.isBlank()) {
                    reminderMessage.append(String.format("Participants: %s \n", participants));
                }
                reminderMessage.append(String.format("Priority: %s \n\n", dueAppointments.getString("priority")));
                mailService.sendEmail(recipients, "Reminder: " + dueAppointments.getString("name"), reminderMessage.toString());
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Reminder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
