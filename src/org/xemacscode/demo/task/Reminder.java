package org.xemacscode.demo.task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xemacscode.demo.database.AppointmentDatabaseService;
import org.xemacscode.demo.database.DBConnection;
import org.xemacscode.demo.email.MailService;

/**
 * TimerTask to send reminder for due appointments This task is supposed to run
 * once every minute
 *
 * @author nikoa
 */
public class Reminder extends TimerTask {

    Connection connection;
    MailService mailService;
    AppointmentDatabaseService appointmentDatabaseService;

    public Reminder() {
        connection = DBConnection.connectDB();
        mailService = new MailService();
        appointmentDatabaseService = new AppointmentDatabaseService();
    }

    /**
     * Checks database for due appointments and sends reminder when task is
     * executed
     */
    @Override
    public void run() {
        try {
            Logger.getLogger(Reminder.class.getName()).info("Checking for Due Appointments");

            ResultSet dueAppointments = appointmentDatabaseService.getDueAppointments();

            while (dueAppointments.next()) {
                sendReminder(dueAppointments);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Reminder.class.getName()).log(Level.SEVERE, "Could not retrieve due appointments", ex);
        }
    }

    /**
     * Send an email reminder for one due appointment from the database
     *
     * @param dueAppointments result set from the database
     * @throws SQLException
     */
    private void sendReminder(ResultSet dueAppointments) throws SQLException {
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

}
