package org.example.AdminRoll;

import org.example.Controller.controllers;
import org.example.Model.bookModels;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.BreakIterator;
import java.util.List;

public class bookTbl extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private List<bookModels> bookList;

    public bookTbl() {
        setTitle("Book Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        fetchBooksFromDatabase();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        // Create panel for the Add button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Create Add button
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(_ -> showAddBookDialog());
        buttonPanel.add(addButton);

        // Add the button panel to the bottom of the frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize the table model and table
        String[] columnNames = {"Book Id","Book Name", "Author", "Year", "Price", "Update", "Delete"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 6;
            }
        };

        table = new JTable(tableModel);
        table.getColumn("Update").setCellRenderer(new ButtonRenderer());
        table.getColumn("Update").setCellEditor(new ButtonEditor(new JCheckBox(), "Update", this));
        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), "Delete", this));
        
        add(new JScrollPane(table), BorderLayout.CENTER);
        populateTableWithBooks();
        setVisible(true);
    }

    // Fetch books from the database
    private void fetchBooksFromDatabase() {
        controllers controller = new controllers();
        bookList = controller.S_book();
    }

    // Method to populate the table with books
    private void populateTableWithBooks() {
        tableModel.setRowCount(0);
        for (bookModels book : bookList) {
            tableModel.addRow(new Object[]{
                    book.getBookId(),
                    book.getBookName(),
                    book.getBookAuthor(),
                    book.getBookYear(),
                    book.getBookPrice(),
                    "Update",
                    "Delete"
            });
        }
    }

    // Method to show the "Add Book" dialog
    private void showAddBookDialog() {
        JTextField bookIdField = new JTextField(Integer.toString(bookList.size()));
        JTextField bookNameField = new JTextField(10);
        JTextField bookAuthorField = new JTextField(10);
        JTextField bookYearField = new JTextField(10);
        JTextField bookPriceField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Book Name:"));
        panel.add(bookNameField);
        panel.add(new JLabel("Author:"));
        panel.add(bookAuthorField);
        panel.add(new JLabel("Year:"));
        panel.add(bookYearField);
        panel.add(new JLabel("Price:"));
        panel.add(bookPriceField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (bookNameField.getText().trim().isEmpty() ||
                    bookAuthorField.getText().trim().isEmpty() ||
                    bookYearField.getText().trim().isEmpty() ||
                    bookPriceField.getText().trim().isEmpty()) {

                // Show warning message
                JOptionPane.showMessageDialog(this, "Please fill all fields before your action.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                // Proceed to add the book to the database
                addBookToDatabase(Integer.parseInt(bookIdField.getText()), bookNameField.getText(), bookAuthorField.getText(), bookYearField.getText(), bookPriceField.getText());
            }
            refreshTable();
        }
    }

    // Method to add a book to the database
    private void addBookToDatabase(int id, String name, String author, String year, String price) {
        controllers controller = new controllers();
        bookModels newBook = new bookModels();
        newBook.setBookId(id);
        newBook.setBookName(name);
        newBook.setBookAuthor(author);
        newBook.setBookYear(year);
        newBook.setBookPrice(Double.parseDouble(price));
        bookList.add(newBook);
        controller.C_book(newBook);
        refreshTable();
    }

    // update a book
    private void updateBook(int row, String name, String author, String year, String price) {
        controllers controller = new controllers();
        bookModels book = bookList.get(row);
        book.setBookName(name);
        book.setBookAuthor(author);
        book.setBookYear(year);
        book.setBookPrice(Double.parseDouble(price));
        controller.U_book(book);
        refreshTable();
    }

    // delete a book
    private void deleteBook(int row) {
        controllers controller = new controllers();
        bookModels book = bookList.get(row);
        controller.D_book(book);
        refreshTable();
    }

    // Method to refresh the table
    private void refreshTable() {
        fetchBooksFromDatabase(); // Fetch the latest data
        populateTableWithBooks(); // Update the table with the latest data
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(bookTbl::new);
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
        private final JButton button;
        private final String label;
        private final bookTbl bookTable;

        public ButtonEditor(JCheckBox checkBox, String label, bookTbl bookTable) {
            super(checkBox);
            this.label = label;
            this.bookTable = bookTable;
            button = new JButton(label);
            button.setOpaque(true);

            button.addActionListener(_ -> {
                fireEditingStopped(); // Stop editing
                int row = bookTable.table.getSelectedRow();
                if (label.equals("Delete")) {
                    bookTable.deleteBook(row);
                } else if (label.equals("Update")) {
                    bookTable.showUpdateDialog(row);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }

    // Method to show the "Update Book" dialog
    private void showUpdateDialog(int row) {
        bookModels book = bookList.get(row);
        JTextField bookNameField = new JTextField(book.getBookName(), 10);
        JTextField bookAuthorField = new JTextField(book.getBookAuthor(), 10);
        JTextField bookYearField = new JTextField(book.getBookYear(), 10);
        JTextField bookPriceField = new JTextField(String.valueOf(book.getBookPrice()), 10);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Book Name:"));
        panel.add(bookNameField);
        panel.add(new JLabel("Author:"));
        panel.add(bookAuthorField);
        panel.add(new JLabel("Year:"));
        panel.add(bookYearField);
        panel.add(new JLabel("Price:"));
        panel.add(bookPriceField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Update Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            updateBook(row, bookNameField.getText(), bookAuthorField.getText(), bookYearField.getText(), bookPriceField.getText());
        }
    }
}
