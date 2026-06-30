package org.example.Book;

import org.example.Model.CartModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookDetailsFrame extends JFrame {
    
    private DefaultTableModel tableModel;
    
    public BookDetailsFrame(String Name, String Author, String Year, double Price) {
        // Set up the frame
        SwingUtilities.invokeLater(() ->{
            setTitle("Book Details");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(400, 400); // Reduced size to fit only the information
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Book Information Panel
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adjust padding for better alignment

            // Title
            JLabel titleLabel = new JLabel("Book Title: " + Name);
            titleLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
            titleLabel.setAlignmentX(CENTER_ALIGNMENT);

            // Author
            JLabel authorLabel = new JLabel("Author: " + Author);
            authorLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
            authorLabel.setAlignmentX(CENTER_ALIGNMENT);

            // Year
            JLabel yearLabel = new JLabel("Year: " + Year);
            yearLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
            yearLabel.setAlignmentX(CENTER_ALIGNMENT);

            // Price
            JLabel priceLabel = new JLabel("Price: $" + Price);
            priceLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
            priceLabel.setAlignmentX(CENTER_ALIGNMENT);

            // Add to Cart Button
            JButton addToCartButton = new JButton("Add to Cart");
            addToCartButton.setFont(new Font("Helvetica", Font.PLAIN, 16));
            addToCartButton.setAlignmentX(CENTER_ALIGNMENT);
            addToCartButton.setBackground(Color.decode("#F7C45F")); // Set background color
            addToCartButton.setForeground(Color.BLACK); // Set foreground color

            addToCartButton.addActionListener(_ -> {
                CartModel.getInstance().addItem(Name, Price);
//                updateCartTable();
                JOptionPane.showMessageDialog(this, "Book added to cart!");
            });
            
            // Add components to the info panel
            infoPanel.add(titleLabel);
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(authorLabel);
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(yearLabel);
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(priceLabel);
            infoPanel.add(Box.createVerticalStrut(20));
            infoPanel.add(addToCartButton);

            // Add components to the frame
            add(infoPanel, BorderLayout.CENTER);
            setVisible(true);
        });
    }
}
