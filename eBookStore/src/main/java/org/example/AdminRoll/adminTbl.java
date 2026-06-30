package org.example.AdminRoll;

import javax.swing.*;
import javax.swing.table.*;
import org.example.Controller.controllers;
import org.example.Model.Models;
import org.example.Model.adminModel;
import java.awt.*;
import java.util.List;

public class adminTbl extends JFrame {

    public adminTbl() {
        // Create a new JFrame
        JFrame frame = new JFrame("Admin Table Example");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);

        // Fetch the controller and model data
        controllers controller = new controllers();
        List<adminModel> adminmodel = controller.adminmodel();
        String[][] data = new String[adminmodel.size()][6];

        // Fill table data with model values
        for (int i = 0; i < adminmodel.size(); i++) {
            adminModel model = adminmodel.get(i);
            data[i][0] = String.valueOf(model.getAdminId());
            data[i][1] = model.getAdminName();
            data[i][2] = model.getAdminEmail();
            data[i][3] = "Delete";
        }

        // Column Names
        String[] columnNames = {"User ID", "Name", "Email", "Delete"};

        // Create the table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only Delete column is editable
            }
        };

        // Create the JTable
        JTable table = new JTable(model);

        // Add Button Renderer and Editor for the "Delete" button
        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), table));

        frame.add(new JScrollPane(table));
        frame.setVisible(true);
    }

    // Renderer for the buttons
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Editor for the buttons
    static class ButtonEditor extends DefaultCellEditor {
        private final JTable table;
        private JButton button;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;
            this.button = new JButton("Delete");
            button.setOpaque(true);

            // Set action listener for the delete button
            button.addActionListener(e -> {
                fireEditingStopped(); // Stop cell editing
                deleteRow(table.getSelectedRow()); // Call deleteRow method
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return "Delete";
        }

        // Method to delete the selected row
        private void deleteRow(int row) {
            if (row < 0) return; // Invalid row check

            // Fetch model data before removing it from the table
            controllers controller = new controllers();
            adminModel adminmodel = controller.adminmodel().get(row);

            // Remove from database using controller method
            controller.D_admin(adminmodel);
            System.out.println("Deleted from database: " + adminmodel.getAdminId());

            // Remove the row from the table model
            ((DefaultTableModel) table.getModel()).removeRow(row);
        }
    }
}

