package org.xemacscode.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import org.xemacscode.demo.database.AppointmentDatabaseService;
import org.xemacscode.demo.email.MailService;

/**
 * Provides the content of the appointment pane dynamically based on the
 * appointments of the user
 *
 * @author nikoa
 */
public class AppointmentPaneProvider {

    private final MailService mailService = new MailService();
    private final AppointmentDatabaseService appointmentDatabaseService = new AppointmentDatabaseService();

    /**
     * Initializes the scrollable appointments pane on the calendar page
     *
     * @param calendar
     * @param appointmentsPane
     */
    public void initAppointments(Calendar calendar, JScrollPane appointmentsPane) {
        try {
            ResultSet appointments = appointmentDatabaseService.getAllAppointmentsForUser(UserProvider.getId());

            JPanel appointmentsPanel = new JPanel();

            while (appointments.next()) {
                createSingleAppointment(calendar, appointments, appointmentsPanel);
            }

            // Configure layout for appointments pane
            BoxLayout layout = new BoxLayout(appointmentsPanel, BoxLayout.Y_AXIS);
            appointmentsPanel.setLayout(layout);

            // add appointments panel to pane and update panel
            appointmentsPane.getViewport().add(appointmentsPanel, null);
            appointmentsPane.getViewport().validate();
            appointmentsPane.getViewport().repaint();
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentPaneProvider.class.getName()).log(Level.SEVERE, "Could not initialize appointment pane", ex);
        }
    }

    /**
     * Creates a single Appointment in the appointmentsPanel
     *
     * @param calendar
     * @param appointments
     * @param appointmentsPanel
     * @throws SQLException
     */
    private void createSingleAppointment(Calendar calendar, ResultSet appointments, JPanel appointmentsPanel) throws SQLException {
        JPanel singleAppointmentPanel = new JPanel();

        // Color appointments panel based on priority
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

        // Set maximum size to prevent entries from becoming too big on the calendar
        singleAppointmentPanel.setMaximumSize(new Dimension(100000, 40));

        //Add text and buttons to single appointment panel
        singleAppointmentPanel.add(appointmentTextLabel);
        singleAppointmentPanel.add(editButton);
        singleAppointmentPanel.add(deleteButton);

        // add single appointment to appointments panel
        appointmentsPanel.add(singleAppointmentPanel);
    }

    /**
     * Creates the text label for an appointment
     *
     * @param appointments
     * @return JLabel containing the text for the appointment
     * @throws SQLException
     */
    private JLabel createAppointmentTextLabel(ResultSet appointments) throws SQLException {
        StringBuilder appointmentText = new StringBuilder();

        // Get date and time from database entry and add to appointment text
        LocalDate beginDate = appointments.getDate("beginDate").toLocalDate();
        LocalDate endDate = appointments.getDate("endDate").toLocalDate();
        LocalTime beginTime = appointments.getTime("beginTime").toLocalTime();
        LocalTime endTime = appointments.getTime("endTime").toLocalTime();

        appointmentText
                .append(beginDate.format(DateTimeFormatter.ISO_DATE))
                .append(" ")
                .append(beginTime.format(DateTimeFormatter.ofPattern("HH:mm")))
                .append(" - ").append(endDate.format(DateTimeFormatter.ISO_DATE))
                .append(" ").append(endTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        appointmentText
                .append(" ")
                .append(appointments.getString("name"));

        // Add location to appointment text if it exists
        String location = appointments.getString("location");
        if (!location.isEmpty()) {
            appointmentText
                    .append(" - ")
                    .append(appointments.getString("location"));
        }

        // Create appointment text label and configure the font
        JLabel appointmentTextLabel = new JLabel(appointmentText.toString());
        appointmentTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

        // Make appointments gray if they are before today or before todays current time
        if (beginDate.isBefore(LocalDate.now())
                || (beginDate.isEqual(LocalDate.now())) && beginTime.isBefore(LocalTime.now())) {
            appointmentTextLabel.setForeground(Color.gray);
        }

        return appointmentTextLabel;
    }

    /**
     * Creates delete button for an appointment
     *
     * @param appointments
     * @param appointmentsPanel
     * @param singleAppointmentPanel
     * @return JButton which deletes appointments from the database on click
     * @throws SQLException
     */
    private JButton createDeleteButton(ResultSet appointments, JPanel appointmentsPanel, JPanel singleAppointmentPanel) throws SQLException {
        JButton deleteButton = new JButton("Delete");

        int id = appointments.getInt("id");
        String participants = appointments.getString("participants");
        String name = appointments.getString("name");

        // Specify action on click for delete button
        deleteButton.addActionListener((e) -> {
            this.appointmentDatabaseService.deleteAppointment(id);

            // Remove deleted appointment from the appointments panel and update it
            appointmentsPanel.remove(singleAppointmentPanel);
            appointmentsPanel.revalidate();
            appointmentsPanel.repaint();

            // Generate a list of recipients for the email notification
            List<String> recipients;
            if (participants.isBlank()) {
                recipients = List.of(UserProvider.getEmail());
            } else {
                recipients = Arrays.asList((participants + "," + UserProvider.getEmail()).split(","));
            }

            // Send email notification to recipients
            mailService.sendEmail(recipients, "Appointment cancelled: " + name, "Your appointment '" + name + "' has been cancelled");
        });
        return deleteButton;
    }

    /**
     * Creates edit button for an appointment
     *
     * @param calendar
     * @param appointments
     * @return JButton which opens the edit view on click
     * @throws SQLException
     */
    private JButton createEditButton(Calendar calendar, ResultSet appointments) throws SQLException {
        JButton editButton = new JButton("Edit");

        // Get all data from the appointment
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

        // Specify action on click for edit button
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

            // close the calendar
            calendar.dispose();
        });

        return editButton;
    }
}
