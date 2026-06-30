package org.example.Customer;

import org.example.Book.BookPage;
import org.example.Controller.controllers;
import org.example.Model.CartModel;
import org.example.Model.orderModels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrderFrame extends JFrame {

    public OrderFrame() {
        SwingUtilities.invokeLater(()->{
            // Set up the frame
            setTitle("Order Details");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(800, 600);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Create column names for the table
            String[] columnNames = {"Title", "Price", "Quantity", "Total Amount", "Payment Type"};

            CartModel cart = CartModel.getInstance();

            // Prepare data for the table
            Object[][] data = new Object[cart.getItems().size()][5];
            int index = 0;
            for (CartModel.CartItem item : cart.getItems().values()) {
                data[index][0] = item.getTitle();
                data[index][1] = item.getPrice();
                data[index][2] = item.getQuantity();
                data[index][3] = item.getPrice() * item.getQuantity();
                data[index][4] = cart.getPaymentType();
                index++;
            }
            

            // Create the table model and table
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(tableModel);
            table.setFillsViewportHeight(true);
            table.setFont(new Font("Helvetica", Font.PLAIN, 14));
            table.setRowHeight(30);

            // Create a scroll pane for the table
            JScrollPane scrollPane = new JScrollPane(table);

            // Add the scroll pane to the frame
            add(scrollPane, BorderLayout.CENTER);

            // Create a panel for the confirm button
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the button

            // Create the confirm button
            JButton confirmButton = new JButton("Confirm");
            confirmButton.setFont(new Font("Helvetica", Font.PLAIN, 16));
            confirmButton.setBackground(Color.decode("#F7C45F")); // Set background color
            confirmButton.setForeground(Color.BLACK); // Set foreground color
            confirmButton.setFocusPainted(false);
            confirmButton.addActionListener(_ -> {
                /// Create an Order object
                orderModels order = new orderModels(cart.getPaymentType());
                for (CartModel.CartItem cartItem : cart.getItems().values()) {
                    order.addItem(new orderModels.OrderItem(cartItem.getTitle(), cartItem.getPrice(), cartItem.getQuantity()));
                }

                // Save the order to the database using OrderDAO
                controllers orderDAO = new controllers();
                orderDAO.saveOrder(order);

                JOptionPane.showMessageDialog(OrderFrame.this, "Order Confirmed and Saved to Database!");
                CartModel.getInstance().clear();
                dispose();
            });

            //go to book page
            JButton gotoBook = new JButton("Go To Book Page");
            gotoBook.setFont(new Font("Helvetica", Font.PLAIN, 16));
            gotoBook.setBackground(Color.decode("#F7C45F")); // Set background color
            gotoBook.setForeground(Color.BLACK); // Set foreground color
            gotoBook.setFocusPainted(false);
            gotoBook.addActionListener(_ -> {
                // Handle the details button click (open the Book Details frame)
                new BookPage();
            });

            // Add the confirm button to the button panel
            buttonPanel.add(gotoBook);
            buttonPanel.add(confirmButton);

            // Add the button panel to the frame
            add(buttonPanel, BorderLayout.SOUTH);
            setVisible(true);
        });
    }
}

