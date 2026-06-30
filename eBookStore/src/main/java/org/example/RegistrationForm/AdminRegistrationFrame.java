package org.example.RegistrationForm;

import org.example.Controller.controllers;
import org.example.LoginForm.AdminLoginFrame;
import org.example.Model.adminModel;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.RoundRectangle2D;

public class AdminRegistrationFrame extends JFrame {

    public AdminRegistrationFrame() {

        SwingUtilities.invokeLater(()-> {
            // Set up the frame
            setTitle("Register");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close the current frame
            setSize(700, 500); // Increased size for better visibility
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Set the frame icon
            ImageIcon frameIcon = new ImageIcon("user-interface.png"); // Replace with your icon path
            setIconImage(frameIcon.getImage());

            // Title Label
            JLabel titleLabel = new JLabel("CREATE AN ACCOUNT TO CONTROL THE BOOKS AS ADMIN", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Helvetica", Font.BOLD, 20)); // Title font and size
            titleLabel.setForeground(Color.BLACK);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Padding around title

            add(titleLabel, BorderLayout.NORTH);

            // Right side: Survey form
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15, 15, 15, 15); // Increased spacing
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Define a low-opacity color for the underline
            Color underlineColor = new Color(0, 0, 0, 50); // Black color with low opacity (alpha value = 50)

            // Name Label and Input Box
            JLabel nameLabel = new JLabel("Name:");
            nameLabel.setFont(new Font("Helvetica", Font.BOLD, 16)); // Changed to Helvetica
            gbc.gridx = 0;
            gbc.gridy = 0;
            formPanel.add(nameLabel, gbc);

            JTextField nameField = new JTextField(25); // Increased width
            nameField.setFont(new Font("Helvetica", Font.PLAIN, 16)); // Changed to Helvetica
            nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, underlineColor)); // Transparent background
            nameField.setOpaque(false);
            gbc.gridx = 1;
            formPanel.add(nameField, gbc);

            // Email Label and Input Box
            JLabel emailLabel = new JLabel("Email:");
            emailLabel.setFont(new Font("Helvetica", Font.BOLD, 16)); // Changed to Helvetica
            gbc.gridx = 0;
            gbc.gridy = 1;
            formPanel.add(emailLabel, gbc);

            JTextField emailField = new JTextField(25); // Increased width
            emailField.setFont(new Font("Helvetica", Font.PLAIN, 16)); // Changed to Helvetica
            emailField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, underlineColor)); // Transparent background
            emailField.setOpaque(false);
            gbc.gridx = 1;
            formPanel.add(emailField, gbc);

            // Password Label and Input Box
            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setFont(new Font("Helvetica", Font.BOLD, 16)); // Changed to Helvetica
            gbc.gridx = 0;
            gbc.gridy = 2;
            formPanel.add(passwordLabel, gbc);

            JPasswordField passwordField = new JPasswordField(25); // Increased width
            passwordField.setFont(new Font("Helvetica", Font.PLAIN, 16)); // Changed to Helvetica
            passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, underlineColor)); // Transparent background
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
            gbc.gridy = 3;
            formPanel.add(showPasswordCheckbox, gbc);

            // Confirm Password Label and Input Box
            JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
            confirmPasswordLabel.setFont(new Font("Helvetica", Font.BOLD, 16)); // Changed to Helvetica
            gbc.gridx = 0;
            gbc.gridy = 4;
            formPanel.add(confirmPasswordLabel, gbc);

            JPasswordField confirmPasswordField = new JPasswordField(25); // Increased width
            confirmPasswordField.setFont(new Font("Helvetica", Font.PLAIN, 16)); // Changed to Helvetica
            confirmPasswordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, underlineColor)); // Transparent background
            confirmPasswordField.setOpaque(false);
            gbc.gridx = 1;
            formPanel.add(confirmPasswordField, gbc);

            // Register and Clear buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centered buttons
            JButton registerButton = new JButton("Register");
            JButton clearButton = new JButton("Clear");

            // Style the buttons
            registerButton.setPreferredSize(new Dimension(130, 40)); // Increased size
            clearButton.setPreferredSize(new Dimension(120, 40)); // Increased size
            registerButton.setBackground(Color.decode("#F7C45F"));
            registerButton.setForeground(Color.BLACK);
            clearButton.setBackground(Color.decode("#D65353"));
            clearButton.setForeground(Color.WHITE);
            registerButton.setFont(new Font("Helvetica", Font.PLAIN, 16)); // Changed to Helvetica
            clearButton.setFont(new Font("Helvetica", Font.PLAIN, 16)); // Changed to Helvetica
            registerButton.setFocusPainted(false);
            clearButton.setFocusPainted(false);

            // Apply rounded corners
            registerButton.setBorder(new RoundedBorder(20)); // Increased radius
            clearButton.setBorder(new RoundedBorder(20)); // Increased radius

            // Action listener for clear button
            clearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                    confirmPasswordField.setText("");
                }
            });

            //Action listener for registration button
            registerButton.addActionListener(_ -> {
                String name = nameField.getText();
                String email = emailField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String confirmPassword = String.valueOf(confirmPasswordField.getPassword());

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Please fill all the fields",
                            "Try Again",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if (!password.equals(confirmPassword)){
                    JOptionPane.showMessageDialog(null,
                            "Confirm Password doesn't match",
                            "Try Again",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    adminModel adminmodel = new adminModel();
                    adminmodel.setAdminName(name);
                    adminmodel.setAdminEmail(email);
                    adminmodel.setAdminPassword(confirmPassword);
                    new controllers().C_admin(adminmodel);
                }
            });

            // Add buttons to the button panel
            buttonPanel.add(registerButton);
            buttonPanel.add(clearButton);

            // Add label and login link
            JLabel accountLabel = new JLabel("Already have an account?");
            accountLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
            accountLabel.setForeground(Color.BLACK);

            JLabel loginLink = new JLabel("Login");
            loginLink.setFont(new Font("Helvetica", Font.PLAIN, 14));
            loginLink.setForeground(Color.BLUE);
            loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
            loginLink.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    new AdminLoginFrame();
                    dispose(); // Close the current frame
                }
            });

            JPanel loginPanel = new JPanel();
            loginPanel.add(accountLabel);
            loginPanel.add(loginLink);
            loginPanel.setOpaque(false);

            // Container for buttons and login panel
            JPanel southPanel = new JPanel();
            southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
            southPanel.add(buttonPanel);
            southPanel.add(loginPanel);
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
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = this.radius;
            return insets;
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

