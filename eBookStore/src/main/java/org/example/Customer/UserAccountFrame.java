package org.example.Customer;

import org.example.UpdateForm.UpdateForm;
import org.example.homePage.EbookGUI;
import javax.swing.*;
import java.awt.*;

public class UserAccountFrame extends JFrame {

    public UserAccountFrame() {
        SwingUtilities.invokeLater(()->{
            // Set up the frame
            setTitle("User Account");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(400, 300);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Create a panel for the buttons with padding
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Add space between buttons
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add space between panel and frame edges

            // Logout Button
            JButton logoutButton = new JButton("Logout");
            logoutButton.setFont(new Font("Helvetica", Font.PLAIN, 16));
            logoutButton.setBackground(Color.decode("#F7C45F")); // Set background color
            logoutButton.setForeground(Color.BLACK); // Set foreground color
            logoutButton.setFocusPainted(false);
            logoutButton.addActionListener(_ -> {
                JOptionPane.showMessageDialog(null, "You are successfully Logout.");
                new EbookGUI();
            });

            // Update Button
            JButton updateButton = new JButton("Update");
            updateButton.setFont(new Font("Helvetica", Font.PLAIN, 16));
            updateButton.setBackground(Color.decode("#F7C45F")); // Set background color
            updateButton.setForeground(Color.BLACK); // Set foreground color
            updateButton.setFocusPainted(false);
            updateButton.addActionListener(_ -> {
                new UpdateForm();
            });

            // Add buttons to panel
            buttonPanel.add(logoutButton);
            buttonPanel.add(updateButton);

            // Add button panel to the frame
            add(buttonPanel, BorderLayout.CENTER);
            setVisible(true);
        });
    }
}
