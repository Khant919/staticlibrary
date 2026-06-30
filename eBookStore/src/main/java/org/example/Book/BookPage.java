package org.example.Book;

import org.example.Controller.controllers;
import org.example.Customer.ShoppingCartFrame;
import org.example.Customer.UserAccountFrame;
import org.example.Model.Book;
//import javax.naming.Name;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BookPage extends JFrame {

    public BookPage() {
        SwingUtilities.invokeLater(()->{
            // Set up the frame
            setTitle("Book Page");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(800, 600);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Top panel containing search box, search buttons, and icons
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBackground(Color.WHITE);
            topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Search panel
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            searchPanel.setBackground(Color.WHITE);

            JTextField searchField = new JTextField(20);
            searchField.setFont(new Font("Helvetica", Font.PLAIN, 16));
            JButton searchTitleButton = new JButton("Search by Title");
            JButton searchAuthorButton = new JButton("Search by Author");
            
            searchTitleButton.addActionListener(_ -> {
                String Book_Name = searchField.getText().toLowerCase();
                controllers.search_title(Book_Name);
//                dispose();
            });
            
            searchAuthorButton.addActionListener(_ -> {
                String Author_Name = searchField.getText();
                new controllers().search_author(Author_Name);
//                dispose();
            });

            searchTitleButton.setFont(new Font("Helvetica", Font.PLAIN, 14));
            searchTitleButton.setBackground(Color.decode("#F7C45F"));
            searchTitleButton.setForeground(Color.BLACK);

            searchAuthorButton.setFont(new Font("Helvetica", Font.PLAIN, 14));
            searchAuthorButton.setBackground(Color.decode("#F7C45F"));
            searchAuthorButton.setForeground(Color.BLACK);

            searchPanel.add(searchField);
            searchPanel.add(searchTitleButton);
            searchPanel.add(searchAuthorButton);

            // Icons panel
            JPanel iconsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            iconsPanel.setBackground(Color.WHITE);

            ImageIcon cartIcon = new ImageIcon("shopping-cart.png");
            JButton cartButton = new JButton(cartIcon);
            cartButton.setBorder(BorderFactory.createEmptyBorder());
            cartButton.setContentAreaFilled(false);
            
            cartButton.addActionListener(_ -> {
                new ShoppingCartFrame();
            });

            ImageIcon userIcon = new ImageIcon("USER.png");
            JButton userButton = new JButton(userIcon);
            userButton.setBorder(BorderFactory.createEmptyBorder());
            userButton.setContentAreaFilled(false);
            
            //Add an ActionListener to handle the button click event
            userButton.addActionListener((_ -> {
                new UserAccountFrame();
            }));

            iconsPanel.add(cartButton);
            iconsPanel.add(userButton);

            topPanel.add(searchPanel, BorderLayout.CENTER);
            topPanel.add(iconsPanel, BorderLayout.EAST);

            add(topPanel, BorderLayout.NORTH);

            // Center panel for popular books and available books
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBackground(Color.WHITE);

            // Popular section
            JPanel popularPanel = new JPanel(new GridLayout(1, 3, 10, 10));
            popularPanel.setBorder(BorderFactory.createTitledBorder("Popular"));
            popularPanel.setBackground(Color.WHITE);

            // Example book items for popular section

            List<Book> bookList = controllers.getBooks();
   
            int i = 0;
            for (Book book : bookList) 
            {
                if(i < 3)
                {
                    JPanel bookPanel = createBookPanel(book.getBookTitle());
                    popularPanel.add(bookPanel);
                }
                i++;
            }

            centerPanel.add(popularPanel);

            // Available Books section
            JPanel availablePanel = new JPanel(new GridLayout(3, 3, 10, 10));
            availablePanel.setBorder(BorderFactory.createTitledBorder("Available Books"));
            availablePanel.setBackground(Color.WHITE);

            // Example book items for available books section
            bookList = mergeSortByTitle(bookList);
            
            assert bookList != null;
            for(Book book: bookList)
            {
                JPanel bookPanel = createBookPanel(book.getBookTitle());
                availablePanel.add(bookPanel);
            }

            centerPanel.add(availablePanel);

            add(centerPanel, BorderLayout.CENTER);
            setVisible(true);
        });
    }

    // Helper method to create a book panel
    private JPanel createBookPanel(String title) {
        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));
        bookPanel.setBackground(Color.WHITE);

        JLabel bookTitle = new JLabel(title, SwingConstants.CENTER);
        JButton detailsButton = new JButton("Details");

        bookTitle.setFont(new Font("Helvetica", Font.PLAIN, 14));
        detailsButton.setFont(new Font("Helvetica", Font.PLAIN, 12));

        // Set button colors
        detailsButton.setBackground(Color.decode("#F7C45F"));
        detailsButton.setForeground(Color.BLACK);

        bookTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailsButton.addActionListener(_ -> {
            // Handle the details button click (open the Book Details frame)
            controllers.search_title(title);
        });

        bookPanel.add(bookTitle);
        bookPanel.add(Box.createVerticalStrut(5)); // Space between title and button
        bookPanel.add(detailsButton);

        return bookPanel;
    }

    public static List<Book> mergeSortByTitle(List<Book> books) {
        if (books.size() <= 1) {
            return null;
        }

        int mid = books.size() / 2;
        List<Book> left = new ArrayList<>(books.subList(0, mid));
        List<Book> right = new ArrayList<>(books.subList(mid, books.size()));

        mergeSortByTitle(left);
        mergeSortByTitle(right);

        mergeByTitle(books, left, right);
        return books;
    }

    private static void mergeByTitle(List<Book> books, List<Book> left, List<Book> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getBookTitle().compareToIgnoreCase(right.get(j).getBookTitle()) <= 0) {
                books.set(k++, left.get(i++));
            } else {
                books.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            books.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            books.set(k++, right.get(j++));
        }
    }

}
