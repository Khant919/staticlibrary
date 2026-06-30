package org.example.AdminSelectionFrame;

import org.example.AdminRoll.adminTbl;
import org.example.AdminRoll.bookTbl;
import org.example.AdminRoll.userTbl;
import org.example.homePage.EbookGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectionFrame extends JFrame {
    public SelectionFrame() {
        // Set the frame title and size
        setTitle("Selection Frame");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        // Create the main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        // Define constraints for the left panel (caption)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(45, 45, 48)); // Dark background color
        JLabel captionLabel = new JLabel("Admin Selection Role:");
        captionLabel.setHorizontalAlignment(JLabel.CENTER);
        captionLabel.setFont(new Font("Sans Serif", Font.BOLD, 24)); // Modern font and size
        captionLabel.setForeground(Color.WHITE); // White font color
        leftPanel.add(captionLabel, BorderLayout.CENTER);
        mainPanel.add(leftPanel, gbc);

        // Define constraints for the right panel (buttons and logout)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 1.0;
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(240, 240, 240)); // Light background color

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.fill = GridBagConstraints.HORIZONTAL;
        buttonGbc.insets = new Insets(5, 5, 5, 5); // Padding around buttons

        // Create and add buttons
        JButton userTableButton = createStyledButton("User Table", new Color(60, 179, 113)); // Green
        JButton adminTableButton = createStyledButton("Admin Table", new Color(30, 144, 255)); // Blue
        JButton orderTableButton = createStyledButton("Order Table", new Color(255, 165, 0)); // Orange
        JButton bookTableButton = createStyledButton("Book Table", new Color(255, 105, 180)); // Light pink

        // Add buttons to the button panel with GridBagLayout
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 0;
        buttonGbc.weightx = 1.0;
        buttonPanel.add(userTableButton, buttonGbc);

        buttonGbc.gridy = 1;
        buttonPanel.add(adminTableButton, buttonGbc);

        buttonGbc.gridy = 2;
        buttonPanel.add(orderTableButton, buttonGbc);

        buttonGbc.gridy = 3;
        buttonPanel.add(bookTableButton, buttonGbc);

        rightPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add logout panel to the upper right corner
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(new Color(240, 240, 240)); // Same background as right panel
        JLabel logoutLabel = new JLabel("<HTML><U>Logout</U></HTML>");
        logoutLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        logoutLabel.setForeground(Color.BLUE);
        logoutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutPanel.add(logoutLabel);

        rightPanel.add(logoutPanel, BorderLayout.NORTH);

        // Add panels to the main panel
        mainPanel.add(rightPanel, gbc);

        // Add main panel to the frame
        add(mainPanel);

        // Logout label mouse click event
        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "You have logged out.");
                new EbookGUI();
                dispose();
            }
        });

        // Action listeners for buttons
        userTableButton.addActionListener(_ ->
                new userTbl()
        );

        adminTableButton.addActionListener(_ -> {
            new adminTbl();
        });

        orderTableButton.addActionListener(_ -> {
            JOptionPane.showMessageDialog(null, "Order Table button clicked.");
        });

        bookTableButton.addActionListener(_ -> {
            new bookTbl();
        });

        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFocusPainted(false);

        // Add a hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(button.getBackground().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SelectionFrame::new);
    }
}
