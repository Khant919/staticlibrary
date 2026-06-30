package org.example.LoginForm;

import org.example.Controller.controllers;
import org.example.Model.Models;
import org.example.Model.adminModel;
import org.example.RegistrationForm.AdminRegistrationFrame;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.RoundRectangle2D;

public class AdminLoginFrame extends JFrame {

    public AdminLoginFrame() {
        SwingUtilities.invokeLater(()->{
            // Set up the frame
            setTitle("Login");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close the current frame
            setSize(550, 450); // Size of the frame
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Set the frame icon
            ImageIcon frameIcon = new ImageIcon("user-interface.png"); // Replace with your icon path
            setIconImage(frameIcon.getImage());

            // Title label at the top
            JLabel titleLabel = new JLabel("PLEASE LOGIN INTO YOUR ACCOUNT", JLabel.CENTER);
            titleLabel.setFont(new Font("Helvetica", Font.BOLD, 20)); // Font style and size
            titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Padding
            add(titleLabel, BorderLayout.NORTH);

            // Right side: Login form
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15, 15, 15, 15); // Spacing
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Define a low-opacity color for the underline
            Color underlineColor = new Color(0, 0, 0, 50); // Black color with low opacity

            // Email Label and Input Box
            JLabel emailLabel = new JLabel("Email:");
            emailLabel.setFont(new Font("Helvetica", Font.BOLD, 16)); // Font style
            gbc.gridx = 0;
            gbc.gridy = 0;
            formPanel.add(emailLabel, gbc);

            JTextField emailField = new JTextField(25); // Width of the text field
            emailField.setFont(new Font("Helvetica", Font.PLAIN, 16)); // Font style
            emailField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, underlineColor)); // Underline style
            emailField.setOpaque(false);
            gbc.gridx = 1;
            formPanel.add(emailField, gbc);

            // Password Label and Input Box
            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setFont(new Font("Helvetica", Font.BOLD, 16)); // Font style
            gbc.gridx = 0;
            gbc.gridy = 1;
            formPanel.add(passwordLabel, gbc);

            JPasswordField passwordField = new JPasswordField(25); // Width of the password field
            passwordField.setFont(new Font("Helvetica", Font.PLAIN, 16)); // Font style
            passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, underlineColor)); // Underline style
            passwordField.setOpaque(false);
            gbc.gridx = 1;
            formPanel.add(passwordField, gbc);

            // Checkbox for show/hide password
            JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
            showPasswordCheckbox.setFont(new Font("Helvetica", Font.PLAIN, 14));
            showPasswordCheckbox.setOpaque(false); // Transparent background
            showPasswordCheckbox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        passwordField.setEchoChar((char) 0); // Show password
                    } else {
                        passwordField.setEchoChar('\u2022'); // Hide password
                    }
                }
            });
            gbc.gridx = 1;
            gbc.gridy = 2;
            formPanel.add(showPasswordCheckbox, gbc);

            // Login and Clear buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centered buttons
            JButton loginButton = new JButton("Login");
            JButton clearButton = new JButton("Clear");

            // Style the buttons
            loginButton.setPreferredSize(new Dimension(130, 40)); // Size of the button
            clearButton.setPreferredSize(new Dimension(120, 40)); // Size of the button
            loginButton.setBackground(Color.decode("#F7C45F"));
            loginButton.setForeground(Color.BLACK);
            clearButton.setBackground(Color.decode("#D65353"));
            clearButton.setForeground(Color.WHITE);
            loginButton.setFont(new Font("Helvetica", Font.PLAIN, 16)); // Font style
            clearButton.setFont(new Font("Helvetica", Font.PLAIN, 16)); // Font style
            loginButton.setFocusPainted(false);
            clearButton.setFocusPainted(false);

            // Apply rounded corners
            loginButton.setBorder(new RoundedBorder(20)); // Rounded corners
            clearButton.setBorder(new RoundedBorder(20)); // Rounded corners

            // Action listener for clear button
            clearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    emailField.setText("");
                    passwordField.setText("");
                }
            });

            // Action listener for login button
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String email = emailField.getText();
                    String password = passwordField.getText();

                    if (email.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "Please fill all the fields",
                                "Try Again",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        adminModel adminmodel = new adminModel();
                        adminmodel.setAdminEmail(email);
                        adminmodel.setAdminPassword(password);
                        new controllers().S_admin(adminmodel);
                    }
                }
            });

            // Add buttons to the button panel
            buttonPanel.add(loginButton);
            buttonPanel.add(clearButton);

            // Add label and register link
            JLabel noAccountLabel = new JLabel("Doesn't have an account?");
            noAccountLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
            noAccountLabel.setForeground(Color.BLACK);

            JLabel registerLink = new JLabel("Register");
            registerLink.setFont(new Font("Helvetica", Font.PLAIN, 14));
            registerLink.setForeground(Color.BLUE);
            registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
            registerLink.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    new AdminRegistrationFrame();
                    dispose(); // Close the current frame
                }
            });

            JPanel registerPanel = new JPanel();
            registerPanel.add(noAccountLabel);
            registerPanel.add(registerLink);
            registerPanel.setOpaque(false);

            // Container for buttons and register panel
            JPanel southPanel = new JPanel();
            southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
            southPanel.add(buttonPanel);
            southPanel.add(registerPanel);
            southPanel.setOpaque(false);

            // Add components to the right side panel
            JPanel rightPanel = new JPanel(new BorderLayout());
            rightPanel.add(formPanel, BorderLayout.CENTER);
            rightPanel.add(southPanel, BorderLayout.SOUTH);

            add(rightPanel, BorderLayout.CENTER);
            setVisible(true);
        });
    }

    // Inner class for rounded border
    private static class RoundedBorder extends AbstractBorder {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getBackground()); // Set border color to match background
            g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
        }
    }
}
