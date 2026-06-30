package org.example.homePage;

import org.example.LoginForm.AdminLoginFrame;
import org.example.LoginForm.LoginFrame;
import org.example.RegistrationForm.AdminRegistrationFrame;
import org.example.RegistrationForm.RegisterFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EbookGUI extends JFrame {

    public EbookGUI() {
        // Setting up the main frame of the application
        setTitle("EBook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Load and scale the background image
        ImageIcon originalImageIcon = new ImageIcon("photo_2024-09-04_18-48-31.jpg");
        if (originalImageIcon.getIconWidth() == -1) {
            System.out.println("Error: Could not load background image. Please check the file path.");
            return;
        }

        int desiredWidth = 600;
        double aspectRatio = (double) originalImageIcon.getIconHeight() / originalImageIcon.getIconWidth();
        int desiredHeight = (int) (desiredWidth * aspectRatio);

        Image scaledImage = originalImageIcon.getImage().getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        JLabel backgroundLabel = new JLabel(scaledImageIcon);
        setSize(desiredWidth, desiredHeight);

        ImageIcon image = new ImageIcon("stack-of-books.png");
        setIconImage(image.getImage());

        backgroundLabel.setLayout(null);

//         Create "Login" and "Register" labels with updated style and positioning
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setForeground(Color.decode("#F7C45F"));
        loginLabel.setBounds(desiredWidth - 130, 10, 50, 30); // Adjusted position and size
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 10)); // Use plain font
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering
        loginLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Login as:",
                        "Select Login Type",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"User", "Admin"},
                        "User"
                );

                // Check the user's choice
                if (choice == JOptionPane.YES_OPTION) {
                    // Open User login frame
                    new LoginFrame();
                }else if (choice == JOptionPane.NO_OPTION) {
//                     Open Admin login frame
                    new AdminLoginFrame();
                }
            }
        });

        JLabel registerLabel = new JLabel("Register");
        registerLabel.setForeground(Color.decode("#F7C45F"));
        registerLabel.setBounds(desiredWidth - 80, 10, 70, 30); // Adjusted position and size
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 10)); // Use plain font
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering
        registerLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
//                new RegisterFrame();
                // Show a dialog with options to choose between User and Admin login
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Login as:",
                        "Select Login Type",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"User", "Admin"},
                        "User"
                );

                // Check the user's choice
                if (choice == JOptionPane.YES_OPTION) {
                    // Open User login frame
                    new RegisterFrame();
                } else if (choice == JOptionPane.NO_OPTION) {
                    // Open Admin login frame
                    new AdminRegistrationFrame();
                }
            }
        });

        // Add the labels to the background label
        backgroundLabel.add(loginLabel);
        backgroundLabel.add(registerLabel);

        setContentPane(backgroundLabel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EbookGUI().setVisible(true);
    }
}
