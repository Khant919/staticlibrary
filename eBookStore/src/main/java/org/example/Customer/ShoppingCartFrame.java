package org.example.Customer;

import org.example.Model.CartModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ShoppingCartFrame extends JFrame {

    private static DefaultTableModel model;

    public ShoppingCartFrame() {
        SwingUtilities.invokeLater(() -> {
            // Set up the frame
            setTitle("Shopping Cart");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(600, 400);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Table model
           model = new DefaultTableModel(){
               @Override
               public boolean isCellEditable(int row, int column) {
                   return column == 2;
               }
           };
            model.addColumn("Title");
            model.addColumn("Quantity");
            model.addColumn("Remove");

            
            // Create the table
            JTable table = new JTable(model);

            // Set up custom renderer and editor for the "Remove" button
            table.getColumn("Remove").setCellRenderer(new ButtonRenderer());
            table.getColumn("Remove").setCellEditor(new ButtonEditor(new JCheckBox(), model));

            // Set column widths
            table.getColumnModel().getColumn(0).setPreferredWidth(200);
            table.getColumnModel().getColumn(1).setPreferredWidth(100);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);

            // Add the table to a scroll pane
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);

            // Payment Type Panel
            JPanel paymentPanel = new JPanel();
            paymentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            paymentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            paymentPanel.setBackground(Color.WHITE);

            // Payment Type Label
            JLabel paymentLabel = new JLabel("Payment Type:");
            paymentLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));

            // Payment Radio Buttons
            JRadioButton kbzPayButton = new JRadioButton("KBZPay");
            JRadioButton visaButton = new JRadioButton("VISA");

            kbzPayButton.setFont(new Font("Helvetica", Font.PLAIN, 16));
            visaButton.setFont(new Font("Helvetica", Font.PLAIN, 16));

            // Group radio buttons
            ButtonGroup paymentGroup = new ButtonGroup();
            paymentGroup.add(kbzPayButton);
            paymentGroup.add(visaButton);

            // Add components to payment panel
            paymentPanel.add(paymentLabel);
            paymentPanel.add(kbzPayButton);
            paymentPanel.add(visaButton);

            // Confirm Button Panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JButton confirmButton = new JButton("Confirm");
            confirmButton.setFont(new Font("Helvetica", Font.PLAIN, 16));
            confirmButton.setBackground(Color.decode("#F7C45F"));
            confirmButton.setForeground(Color.BLACK);
            confirmButton.setFocusPainted(false);
            confirmButton.addActionListener(_ -> {
                if (kbzPayButton.isSelected()) {
                    CartModel.getInstance().setPaymentType("KBZPay");
                } else if (visaButton.isSelected()) {
                    CartModel.getInstance().setPaymentType("VISA");
                }
                new OrderFrame();
                dispose();
            });

            buttonPanel.add(confirmButton);

            // Combined Panel for Payment and Confirm Button
            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new BorderLayout());
            bottomPanel.add(paymentPanel, BorderLayout.CENTER);
            bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

            // Add combined bottom panel to the frame
            add(bottomPanel, BorderLayout.SOUTH);
            loadCartItems();
            setVisible(true);
        });
    }

    private void loadCartItems() {
        CartModel cart = CartModel.getInstance();
        for (CartModel.CartItem item : cart.getItems().values()) {
            model.addRow(new Object[]{item.getTitle(), item.getQuantity(), "Remove"});
        }
    }
    
    // Custom button renderer for JTable
    static class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Helvetica", Font.PLAIN, 12));
            setBackground(Color.decode("#F7C45F"));
            setForeground(Color.BLACK);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Custom button editor for JTable
    static class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private String label;
        private boolean isPushed;
        private final DefaultTableModel tableModel;
        private int row;

        public ButtonEditor(JCheckBox checkBox, DefaultTableModel model) {
            super(checkBox);
            this.tableModel = model;
            button = new JButton();
            button.setOpaque(true);
            button.setFont(new Font("Helvetica", Font.PLAIN, 12));
            button.setBackground(Color.decode("#F7C45F"));
            button.setForeground(Color.BLACK);
            button.addActionListener(_ -> {
                fireEditingStopped();
                    String title = (String) tableModel.getValueAt(row, 0);
                    tableModel.removeRow(row);
                    CartModel.getInstance().removeItem(title);
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
//            if (isPushed) {
//                // Additional action logic here, if needed
//            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}
