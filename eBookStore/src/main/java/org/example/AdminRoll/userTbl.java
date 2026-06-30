//package org.example.AdminRoll;
//
//import javax.swing.*;
//import javax.swing.table.*;
//import org.example.Controller.controllers;
//import org.example.Model.Models;
//import java.awt.*;
//import java.util.List;
//
//public class adminTbl extends JFrame {
//
//    public static void main(String[] args) {
//        // Create a new JFrame
//        JFrame frame = new JFrame("User Table Example");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(600, 300);
//
//        controllers controller = new controllers();
//        List<Models> models = controller.models();
//        String[][] data = new String[models.size()][6];
//        for (int i = 0; i < models.size(); i++) {
//            Models model = models.get(i);
//            data[i][0] = String.valueOf(model.getUserId());
//            data[i][1] = model.getUserName();
//            data[i][2] = model.getUserEmail();
//            data[i][3] = model.getUserPassword();
//            data[i][4] = "Delete";
//        }
//
//        // Column Names
//        String[] columnNames = {"User ID", "Name", "Email", "Password", "Delete"};
//
//        // Create the table model
//        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return column == 4; // Only Edit and Delete columns are editable
//            }
//        };
//
//        // Create the JTable
//        JTable table = new JTable(model);
//
//        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
//        table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), "Delete", table));
//
//        // Add the table to a JScrollPane and then add it to the frame
//        frame.add(new JScrollPane(table));
//
//        // Set the frame to be visible
//        frame.setVisible(true);
//    }
//
//    // Renderer for the buttons
//    static class ButtonRenderer extends JButton implements TableCellRenderer {
//        public ButtonRenderer() {
//            setOpaque(true);
//        }
//
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//            setText((value == null) ? "" : value.toString());
//            return this;
//        }
//    }
//
//    // Editor for the buttons
//    static class ButtonEditor extends DefaultCellEditor {
//        private final String label;
//        private final JTable table;
//
//        public ButtonEditor(JCheckBox checkBox, String action, JTable table) {
//            super(checkBox);
//            this.label = action;
//            this.table = table;
//        }
//
//        @Override
//        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//            JButton button = new JButton(label);
//            button.setOpaque(true);
//            button.addActionListener(_ -> {
//                fireEditingStopped();
//                if (label.equals("Delete")) {
//                    deleteRow(row);
//                }
//            });
//            return button;
//        }
//
//        @Override
//        public Object getCellEditorValue() {
//            return label;
//        }
//
//        private void deleteRow(int row) {
//            System.out.println("Delete row: " + row);
//            // Implement your delete logic here
//            ((DefaultTableModel) table.getModel()).removeRow(row);
//
//            controllers controller = new controllers();
//            Models model = controller.models().get(row);
//            String[][] data = new String[row][6];
//            data[row][0] = String.valueOf(model.getUserId());
//            controller.D_user(model);
//        }
//    }
//}


package org.example.AdminRoll;

import javax.swing.*;
import javax.swing.table.*;
import org.example.Controller.controllers;
import org.example.Model.Models;
import java.awt.*;
import java.util.List;

public class userTbl extends JFrame {

    public userTbl() {
        // Create a new JFrame
        JFrame frame = new JFrame("User Table Example");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);

        // Fetch the controller and model data
        controllers controller = new controllers();
        List<Models> models = controller.models();
        String[][] data = new String[models.size()][6];

        // Fill table data with model values
        for (int i = 0; i < models.size(); i++) {
            Models model = models.get(i);
            data[i][0] = String.valueOf(model.getUserId());
            data[i][1] = model.getUserName();
            data[i][2] = model.getUserEmail();
            data[i][3] = model.getUserPassword();
            data[i][4] = "Delete";
        }

        // Column Names
        String[] columnNames = {"User ID", "Name", "Email", "Password", "Delete"};

        // Create the table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only Delete column is editable
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
            Models model = controller.models().get(row);

            // Remove from database using controller method
            controller.D_user(model);
            System.out.println("Deleted from database: " + model.getUserId());

            // Remove the row from the table model
            ((DefaultTableModel) table.getModel()).removeRow(row);
        }
    }
}
