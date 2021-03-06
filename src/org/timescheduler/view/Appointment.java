package org.timescheduler.view;

import com.toedter.calendar.JTextFieldDateEditor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.timescheduler.UserProvider;
import org.timescheduler.database.DBConnection;
import org.timescheduler.email.MailService;

/**
 * Appointment frame
 * In this Form the user enters the Information about the appointment.
 * In this class, new appointments are read in, transferred to the database and saved.
 * @author camil
 * @version 1.0
 */
public class Appointment extends javax.swing.JFrame {

    private MailService mailService;

    /**
     * Creates new form Appointment
     */
    String filePath = null;
    Integer id = null;

    public Appointment() {
        this.initAppointment();
    }

    /**
     * Opens appointment frame with existing appointment
     *
     * @param id
     * @param name
     * @param beginDate
     * @param endDate
     * @param beginTime
     * @param endTime
     * @param location
     * @param priority
     * @param reminder
     * @param participants 
     */
    public Appointment(int id, String name, LocalDate beginDate, LocalDate endDate, LocalTime beginTime, LocalTime endTime, String location, String priority, String reminder, String participants) {
        this.initAppointment();

        // Set appointment id for update
        this.id = id;
        
        // Set data based on existing appointment
        tfName.setText(name);
        datechooserFrom.setDate(Date.from(beginDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        datechooserTo.setDate(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        timechooserFrom.setSelectedItem(beginTime.format(DateTimeFormatter.ofPattern("H:mm")));
        timechooserTo.setSelectedItem(endTime.format(DateTimeFormatter.ofPattern("H:mm")));
        tfLocation.setText(location);
        priorityChooser.setSelectedItem(priority);
        reminderChooser.setSelectedItem(reminder);
        tfParticipants.setText(participants);
        
        // Change text of add button and title
        btnAddAppoint.setText("+edit appointment");
        jAppointmentLabel.setText("Edit Appointment");
    }
    
    /**
     * initializes appointment frame
     */
    private void initAppointment() {
        initComponents();
        this.setLocationRelativeTo(null); // Appointment screen is shown in the center
        mailService = new MailService();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tfLocation = new javax.swing.JTextField();
        tfParticipants = new javax.swing.JTextField();
        datechooserFrom = new com.toedter.calendar.JDateChooser();
        datechooserTo = new com.toedter.calendar.JDateChooser();
        timechooserFrom = new javax.swing.JComboBox<>();
        timechooserTo = new javax.swing.JComboBox<>();
        btnFileChoose = new javax.swing.JButton();
        priorityChooser = new javax.swing.JComboBox<>();
        reminderChooser = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btnAddAppoint = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jLabel_ShowPath = new javax.swing.JLabel();
        jAppointmentLabel = new javax.swing.JLabel();
        btnMinimize = new javax.swing.JLabel();
        btnClose = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(37, 116, 169));

        jPanel2.setBackground(new java.awt.Color(107, 184, 240));

        jLabel2.setText("Name of Event");

        jLabel3.setText("From");

        jLabel4.setText("To");

        jLabel5.setText("Location of event");

        jLabel6.setText("Participants");

        jLabel7.setText("Attachment files");

        jLabel8.setText("Priority");

        jLabel9.setText("Reminder");

        jLabel10.setText("Date");

        jLabel11.setText("Date");

        jLabel12.setText("Time");

        jLabel13.setText("Time");

        datechooserFrom.setDateFormatString("yyyy-MM-dd");

        datechooserTo.setDateFormatString("yyyy-MM-dd");

        timechooserFrom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "6:00", "6:30", "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30", "0:00", "0:30", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00", "4:30", "5:00", "5:30" }));
        timechooserFrom.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        timechooserTo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "6:00", "6:30", "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30", "0:00", "0:30", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00", "4:30", "5:00", "5:30" }));
        timechooserTo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnFileChoose.setText("Choose File...");
        btnFileChoose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFileChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileChooseActionPerformed(evt);
            }
        });

        priorityChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "low", "medium", "high" }));
        priorityChooser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        reminderChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "no reminder", "1 week", "3 days", "1 hour", "10 minutes" }));
        reminderChooser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jPanel3.setBackground(new java.awt.Color(37, 116, 169));

        btnAddAppoint.setText("+add appointment");
        btnAddAppoint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddAppoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAppointActionPerformed(evt);
            }
        });

        btnBack.setText("Back");
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddAppoint, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddAppoint)
                    .addComponent(btnBack))
                .addGap(42, 42, 42))
        );

        jLabel_ShowPath.setText("No file chosen");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(68, 68, 68)
                                .addComponent(tfName))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(71, 71, 71)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(datechooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(datechooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(timechooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(timechooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(56, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(reminderChooser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(priorityChooser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfParticipants)
                                    .addComponent(tfLocation))
                                .addGap(123, 123, 123))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnFileChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel_ShowPath, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))))))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10)
                    .addComponent(datechooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(timechooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel11))
                    .addComponent(datechooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(timechooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tfParticipants, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_ShowPath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(btnFileChoose)))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(priorityChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(reminderChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jAppointmentLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jAppointmentLabel.setForeground(new java.awt.Color(255, 255, 255));
        jAppointmentLabel.setText("Add Appointment");

        btnMinimize.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnMinimize.setText("-");
        btnMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseClicked(evt);
            }
        });

        btnClose.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnClose.setText("X");
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jAppointmentLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMinimize)
                .addGap(18, 18, 18)
                .addComponent(btnClose)
                .addGap(27, 27, 27))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jAppointmentLabel)
                    .addComponent(btnMinimize)
                    .addComponent(btnClose))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddAppointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAppointActionPerformed
        // Gets data from the form        
        String name = tfName.getText();
        LocalDate datefrom = LocalDate.parse(((JTextFieldDateEditor) datechooserFrom.getDateEditor().getUiComponent()).getText());
        LocalDate dateto = LocalDate.parse(((JTextFieldDateEditor) datechooserTo.getDateEditor().getUiComponent()).getText());
        LocalTime timefrom = stringToLocalTime((String) timechooserFrom.getSelectedItem());
        LocalTime timeto = stringToLocalTime((String) timechooserTo.getSelectedItem());
        String location = tfLocation.getText();
        String participants = tfParticipants.getText();

        String priority = (String) priorityChooser.getSelectedItem();
        String reminder = (String) reminderChooser.getSelectedItem();

        // Show error if name or datefrom is empty
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in the name field.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (datefrom == null) {
            JOptionPane.showMessageDialog(this, "No Date selected.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            addAppointment(name, datefrom, dateto, timefrom, timeto, location, participants, priority, reminder);
        }
    }//GEN-LAST:event_btnAddAppointActionPerformed

    /**
     * Converts a time string to local time so it can be processed later
     *
     * @param timeString in the format (H:m, e.g. "6:00")
     * @return LocalTime based on the timeString
     */
    private LocalTime stringToLocalTime(String timeString) {
        return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("H:m"));
    }

    private void btnFileChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileChooseActionPerformed
        // Open file chooser
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        
        // Update label to show filename
        String filename = file.getAbsolutePath();
        jLabel_ShowPath.setText(filename);
        
        // store filePath
        filePath = filename;
    }//GEN-LAST:event_btnFileChooseActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // Create new calendar frame
        Calendar c=new Calendar();
        c.setLocationRelativeTo(null);
        c.setVisible(true);
        // Close this frame
        dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        System.exit(0);  // close application
    }//GEN-LAST:event_btnCloseMouseClicked

    private void btnMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseClicked
        this.setState(JFrame.ICONIFIED);  // minimize the screen
    }//GEN-LAST:event_btnMinimizeMouseClicked

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
            java.util.logging.Logger.getLogger(Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Appointment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAppoint;
    private javax.swing.JButton btnBack;
    private javax.swing.JLabel btnClose;
    private javax.swing.JButton btnFileChoose;
    private javax.swing.JLabel btnMinimize;
    private com.toedter.calendar.JDateChooser datechooserFrom;
    private com.toedter.calendar.JDateChooser datechooserTo;
    private javax.swing.JLabel jAppointmentLabel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_ShowPath;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JComboBox<String> priorityChooser;
    private javax.swing.JComboBox<String> reminderChooser;
    private javax.swing.JTextField tfLocation;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfParticipants;
    private javax.swing.JComboBox<String> timechooserFrom;
    private javax.swing.JComboBox<String> timechooserTo;
    // End of variables declaration//GEN-END:variables

    /**
     * Adds an appointment to the database
     *
     * @param name
     * @param datefrom
     * @param dateto
     * @param timefrom
     * @param timeto
     * @param location
     * @param participants
     * @param priority
     * @param reminder 
     */
    private void addAppointment(String name, LocalDate datefrom, LocalDate dateto, LocalTime timefrom, LocalTime timeto, String location, String participants, String priority, String reminder) {
        Connection dbconn = DBConnection.connectDB();
        if (dbconn != null) {
            try {
                PreparedStatement st;
                // If an id is set we need an update and not an add
                boolean isUpdate = this.id != null;
                if(isUpdate) {
                    st = (PreparedStatement) dbconn.prepareStatement("UPDATE appointments SET name = ? ,beginDate = ? ,endDate = ? ,beginTime = ? ,endTime = ? ,location = ? ,participants = ? ,file = ? ,priority = ? ,reminder = ? ,user_id = ? WHERE id = ?");
                } else {
                    st = (PreparedStatement) dbconn.prepareStatement("INSERT INTO appointments (name,beginDate,endDate,beginTime,endTime,location,participants,file,priority,reminder,user_id) VALUE(?,?,?,?,?,?,?,?,?,?,?)");
                }

                st.setString(1, name);
                st.setDate(2, localDateToSqlDate(datefrom));
                st.setDate(3, localDateToSqlDate(dateto));
                st.setTime(4, localTimeToSqlTime(timefrom));
                st.setTime(5, localTimeToSqlTime(timeto));
                st.setString(6, location);
                st.setString(7, participants);
                
                // If there is a file we set the file, if not we set null
                try {
                    if (filePath != null) {
                        InputStream file = new FileInputStream(new File(filePath));
                        st.setBlob(8, file);

                    } else {
                        st.setNull(8, java.sql.Types.NULL);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
                }

                st.setString(9, priority);
                st.setString(10, reminder);
                st.setInt(11, UserProvider.getId());

                // On update we set the appointment id
                if(isUpdate) {
                    st.setInt(12, this.id);
                }
                st.executeUpdate();

                // Notify recipients via email about the appointment
                List<String> recipients;
                if (participants.isBlank()) {
                    recipients = List.of(UserProvider.getEmail());
                } else {
                    recipients = Arrays.asList((participants + "," + UserProvider.getEmail()).split(","));
                }

                // Receiving email message based on appointment data
                String message = getMessage(datefrom, dateto, timefrom, timeto, location, participants, priority);
                mailService.sendEmail(recipients, "Appointment: " + name, message);

                // Show success message
                String updateMessage = "Appointment added.";
                if(isUpdate) {
                    updateMessage = "Appointment edited.";
                }
                JOptionPane.showMessageDialog(this, updateMessage, "Success", JOptionPane.INFORMATION_MESSAGE);

                // Create new calendar frame
                Calendar c=new Calendar();
                c.setLocationRelativeTo(null);
                c.setVisible(true);
                // Close this frame
                dispose();

            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, "Could not write appointment", ex);
            }
        } else {
            System.out.println("The connection not available.");
        }
    }

    /**
     * Convert LocalTime to Sql time
     *
     * @param timefrom
     * @return 
     */
    Time localTimeToSqlTime(LocalTime timefrom) {
        return java.sql.Time.valueOf(timefrom);
    }

    /**
     * Convert LocalDate to Sql date
     *
     * @param localDate
     * @return 
     */
    java.sql.Date localDateToSqlDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    /**
     * @param beginDate of the appointment
     * @param endDate of the appointment
     * @param beginTime of the appointment
     * @param endTime of the appointment
     * @param location of the appointment
     * @param participants of the appointment
     * @param priority of the appointment
     * @return a String with the message for the email based on the appointment information
     */
    private String getMessage(LocalDate beginDate, LocalDate endDate, LocalTime beginTime, LocalTime endTime, String location, String participants, String priority) {
        StringBuilder sb = new StringBuilder();
        sb.append("Appointment\n\n");
        sb.append(String.format("Start: %s %s\n", beginDate.format(DateTimeFormatter.ISO_DATE), beginTime.format(DateTimeFormatter.ISO_TIME)));
        sb.append(String.format("End: %s %s\n", endDate.format(DateTimeFormatter.ISO_DATE), endTime.format(DateTimeFormatter.ISO_TIME)));
        if (!location.isBlank()) {
            sb.append(String.format("Location: %s \n", location));
        }
        if (!participants.isBlank()) {
            sb.append(String.format("Participants: %s \n", participants));
        }
        sb.append(String.format("Priority: %s \n\n", priority));
        sb.append("Enjoy your meeting!\n");
        sb.append(String.format("Planned with: JavaTimeScheduler\n", priority));
        return sb.toString();
    }
}
