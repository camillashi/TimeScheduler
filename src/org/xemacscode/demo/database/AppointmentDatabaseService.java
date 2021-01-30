/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xemacscode.demo.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikoa
 */
public class AppointmentDatabaseService {

    Connection connection;

    public AppointmentDatabaseService() {
        connection = DBConnection.connectDB();
    }

    /**
     * Deletes an appointment
     *
     * @param id of the appointment that should be deleted
     */
    public void deleteAppointment(Integer id) {
        try {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM `appointments` WHERE `appointments`.`id` = ?");
            deleteStatement.setInt(1, id);

            deleteStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDatabaseService.class.getName()).log(Level.SEVERE, "Failed to delete appointment", ex);
        }

    }

    /**
     * Fetches due appointments from database based on current date and time
     *
     * @throws SQLException when database query fails
     * @return ResultSet containing the due appointments
     */
    public ResultSet getDueAppointments() throws SQLException {
        LocalDateTime now = LocalDateTime.now(); // Standard Java Library
        LocalDateTime inOneWeek = now.plusDays(7);
        LocalDateTime inThreeDays = now.plusDays(3);
        LocalDateTime inOneHour = now.plusHours(1);
        LocalDateTime inTenMinutes = now.plusMinutes(10);

        StringBuilder query = new StringBuilder();
        // SQL query retrieving both user and appointment data (joined based on user id)
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
        return dueAppointments;
    }

    /**
     * Gets all weekly appointments from specified user from the database
     *
     * @param userId
     * @return ResultSet containing all weekly appointments for current user
     * @throws SQLException
     */
    public ResultSet getWeeklyAppointments(int userId) throws SQLException {
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        calendar.set(java.util.Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        LocalDate startOfWeek = LocalDate.ofInstant(calendar.getTime().toInstant(), ZoneId.systemDefault());
        LocalDate endOfWeek = startOfWeek.plusDays(7);

        Connection dbconn = DBConnection.connectDB();
        final Date dateStartOfWeek = Date.valueOf(startOfWeek);
        final Date dateEndOfWeek = Date.valueOf(endOfWeek);

        PreparedStatement appointmentsBeginDateQuery = dbconn.prepareStatement("SELECT * FROM `appointments` WHERE ((`beginDate` > ? AND `beginDate` <= ?) OR (`endDate` > ? AND `endDate` <= ?)) AND `user_id` = ?");

        appointmentsBeginDateQuery.setDate(1, dateStartOfWeek);
        appointmentsBeginDateQuery.setDate(2, dateEndOfWeek);
        appointmentsBeginDateQuery.setDate(3, dateStartOfWeek);
        appointmentsBeginDateQuery.setDate(4, dateEndOfWeek);
        appointmentsBeginDateQuery.setInt(5, userId);
        ResultSet appointments = appointmentsBeginDateQuery.executeQuery();
        return appointments;
    }

    /**
     * Gets all appointments for the specified user
     *
     * @param userId
     * @return ResultSet containing all appointments for the user
     * @throws SQLException 
     */
    public ResultSet getAllAppointmentsForUser(int userId) throws SQLException {
        PreparedStatement selectAppointments = connection.prepareStatement("SELECT * FROM appointments WHERE user_id = ? ORDER BY beginDate, beginTime");
        selectAppointments.setInt(1, userId);

        ResultSet appointments = selectAppointments.executeQuery();
        return appointments;
    }
}
