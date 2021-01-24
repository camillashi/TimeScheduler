/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xemacscode.demo;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author camil
 */
public class Calendar extends javax.swing.JFrame {

    /**
     * Creates new form Calendar
     */
    public Calendar() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jLabel1 = new javax.swing.JLabel();
        btnAddAppoint = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        exportSchedule = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(37, 116, 169));

        jPanel2.setBackground(new java.awt.Color(107, 184, 240));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jCalendar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jCalendar1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Time Scheduler");

        btnAddAppoint.setText("+add Appointment");
        btnAddAppoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAppointActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(37, 116, 169));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        exportSchedule.setText("Export Weekly Schedule");
        exportSchedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportScheduleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(202, 202, 202)
                .addComponent(btnAddAppoint, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(exportSchedule, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnAddAppoint)
                    .addComponent(exportSchedule))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddAppointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAppointActionPerformed
        // TODO add your handling code here:
        Appointment r = new Appointment(); //open Appointment
        r.setLocationRelativeTo(null);
        r.setVisible(true);
    }//GEN-LAST:event_btnAddAppointActionPerformed

    private void exportScheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportScheduleActionPerformed

        try (FileWriter writer = new FileWriter("appointments.txt")){
            java.util.Calendar calendar = java.util.Calendar.getInstance();

            calendar.set(java.util.Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
            LocalDate startOfWeek = LocalDate.ofInstant(calendar.getTime().toInstant(), ZoneId.systemDefault());
            LocalDate endOfWeek = startOfWeek.plusDays(7);
            Integer userId = Login.getId();

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
            
            while(appointments.next()) {
                writer.write(String.format("Appointment: %s\n\n", appointments.getString("name")));
                writer.write(String.format("Start: %s %s\n",
                        appointments.getDate("beginDate").toLocalDate().format(DateTimeFormatter.ISO_DATE),
                        appointments.getTime("beginTime").toLocalTime().format(DateTimeFormatter.ISO_TIME)));
                writer.write(String.format("End: %s %s\n",
                        appointments.getDate("endDate").toLocalDate().format(DateTimeFormatter.ISO_DATE),
                        appointments.getTime("endTime").toLocalTime().format(DateTimeFormatter.ISO_TIME)));
                String location = appointments.getString("location");
                if (!location.isBlank()) {
                    writer.write(String.format("Location: %s \n", location));
                }
                String participants = appointments.getString("participants");
                if (!participants.isBlank()) {
                    writer.write(String.format("Participants: %s \n", participants));
                }
                writer.write(String.format("Priority: %s \n\n", appointments.getString("priority")));
            }
            writer.flush();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_exportScheduleActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Calendar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Calendar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Calendar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Calendar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calendar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAppoint;
    private javax.swing.JButton exportSchedule;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
