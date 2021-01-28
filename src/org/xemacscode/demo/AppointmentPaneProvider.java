/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xemacscode.demo;

import org.xemacscode.demo.database.DBConnection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.xemacscode.demo.database.UpdateAppointmentService;
import org.xemacscode.demo.email.MailService;

/**
 *
 * @author nikoa
 */
public class AppointmentPaneProvider {
    private final static MailService mailService = new MailService();

    public static void initAppointments(Calendar calendar, JScrollPane appointmentsPane) {
        try {
            JPanel appointmentsPanel = new JPanel();
            Connection dbconn = DBConnection.connectDB();
            PreparedStatement selectAppointments = dbconn.prepareStatement("SELECT * FROM appointments WHERE user_id = ? ORDER BY beginDate, beginTime");
            selectAppointments.setInt(1, UserProvider.getId());
            ResultSet appointments = selectAppointments.executeQuery();

            while (appointments.next()) {
                createSingleAppointment(calendar, appointments, appointmentsPanel);
            }

            BoxLayout layout = new BoxLayout(appointmentsPanel, BoxLayout.Y_AXIS);
            appointmentsPanel.setLayout(layout);
            appointmentsPane.getViewport().add(appointmentsPanel, null);
            appointmentsPane.getViewport().validate();
            appointmentsPane.getViewport().repaint();
        } catch (SQLException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void createSingleAppointment(Calendar calendar, ResultSet appointments, JPanel appointmentsPanel) throws SQLException {
        JPanel singleAppointmentPanel = new JPanel();

        String priority = appointments.getString("priority");
        if (priority.equals("high")) {
            singleAppointmentPanel.setBackground(Color.red);
        }
        if (priority.equals("medium")) {
            singleAppointmentPanel.setBackground(Color.yellow);
        }
        if (priority.equals("low")) {
            singleAppointmentPanel.setBackground(Color.green);
        }

        JLabel appointmentTextLabel = createAppointmentTextLabel(appointments);
        JButton deleteButton = createDeleteButton(appointments, appointmentsPanel, singleAppointmentPanel);
        JButton editButton = createEditButton(calendar, appointments);
        

        singleAppointmentPanel.setMaximumSize(new Dimension(100000, 40));
        singleAppointmentPanel.add(appointmentTextLabel);
        singleAppointmentPanel.add(editButton);
        singleAppointmentPanel.add(deleteButton);
        appointmentsPanel.add(singleAppointmentPanel);
    }

    private static JLabel createAppointmentTextLabel(ResultSet appointments) throws SQLException {
        LocalDate beginDate = appointments.getDate("beginDate").toLocalDate();
        LocalDate endDate = appointments.getDate("endDate").toLocalDate();
        LocalTime beginTime = appointments.getTime("beginTime").toLocalTime();
        LocalTime endTime = appointments.getTime("endTime").toLocalTime();

        StringBuilder appointmentText = new StringBuilder();
        appointmentText
                .append(beginDate.format(DateTimeFormatter.ISO_DATE))
                .append(" ")
                .append(beginTime.format(DateTimeFormatter.ofPattern("HH:mm")))
                .append(" - ").append(endDate.format(DateTimeFormatter.ISO_DATE))
                .append(" ").append(endTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        appointmentText
                .append(" ")
                .append(appointments.getString("name"));

        String location = appointments.getString("location");
        if (!location.isEmpty()) {
            appointmentText
                    .append(" - ")
                    .append(appointments.getString("location"));
        }
        JLabel appointmentTextLabel = new JLabel(appointmentText.toString());
        appointmentTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        if (beginDate.isBefore(LocalDate.now())) {
            appointmentTextLabel.setForeground(Color.gray);
        }
        return appointmentTextLabel;
    }
    
    private static JButton createDeleteButton(ResultSet appointments, JPanel appointmentsPanel, JPanel singleAppointmentPanel) throws SQLException{
        JButton deleteButton = new JButton("Delete");
        
        int id = appointments.getInt("id");
        String participants = appointments.getString("participants");
        String name = appointments.getString("name");
        deleteButton.addActionListener((e) -> {
            UpdateAppointmentService.deleteAppointment(id);
            appointmentsPanel.remove(singleAppointmentPanel);
            appointmentsPanel.revalidate();
            appointmentsPanel.repaint();
            List<String> recipients;
            if (participants.isBlank()) {
                recipients = List.of(UserProvider.getEmail());
            } else {
                recipients = Arrays.asList((participants + "," + UserProvider.getEmail()).split(","));
            }
            mailService.sendEmail(recipients, "Appointment cancelled: " + name, "Your appointment '" + name + "' has been cancelled");
        });
        return deleteButton;
    }

    private static JButton createEditButton(Calendar calendar, ResultSet appointments) throws SQLException {
        JButton editButton = new JButton("Edit");
        
        int id = appointments.getInt("id");
        String name = appointments.getString("name");
        LocalDate beginDate = appointments.getDate("beginDate").toLocalDate();
        LocalDate endDate = appointments.getDate("endDate").toLocalDate();
        LocalTime beginTime = appointments.getTime("beginTime").toLocalTime();
        LocalTime endTime = appointments.getTime("endTime").toLocalTime();
        String location = appointments.getString("location");
        String priority = appointments.getString("priority");
        String reminder = appointments.getString("reminder");
        String participants = appointments.getString("participants");
        
        editButton.addActionListener((e) -> {
            Appointment r = new Appointment(
                    id,
                    name,
                    beginDate,
                    endDate,
                    beginTime,
                    endTime,
                    location,
                    priority,
                    reminder,
                    participants
            ); //open Appointment view with existing appointment
            r.setLocationRelativeTo(null);
            r.setVisible(true);
            calendar.dispose();
        });
        return editButton;
    }
}
