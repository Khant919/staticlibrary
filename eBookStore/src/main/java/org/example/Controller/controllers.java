package org.example.Controller;

import org.example.AdminSelectionFrame.SelectionFrame;
import org.example.Connection.Connections;
import org.example.LoginForm.AdminLoginFrame;
import org.example.Model.*;
import org.example.Book.BookDetailsFrame;
import org.example.Book.BookPage;
import org.example.Customer.passwordHash;
import org.example.LoginForm.LoginFrame;
import org.example.RegistrationForm.AdminRegistrationFrame;
import org.example.RegistrationForm.RegisterFrame;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class controllers {
    
    // get user list of table for admin
    public List<Models> models() {
        List<Models> list = new ArrayList<>();
        String query = "SELECT * FROM userTbl_blog1";
        try{
            Connection connection = Connections.getConnection();
            System.out.println(connection);
            System.out.println("Connected to database successfully");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Models model = new Models();
                model.setUserId(resultSet.getInt("userId"));
                model.setUserName(resultSet.getString("userName"));
                model.setUserEmail(resultSet.getString("userEmail"));
                model.setUserPassword(resultSet.getString("userPassword"));
                list.add(model);
            }
        } catch (SQLException e) {
            System.out.println("Oop, there is an error");
            throw new RuntimeException(e);
        }
        return list;
    }
    
    // set user info form sign up form
    public void C_user(Models user){
        String query = "INSERT INTO userTbl_blog1 values(?,?,?,?)";
        try(Connection connection = Connections.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1,user.getUserName());
            statement.setString(2, user.getUserEmail());
            String pw = user.getUserPassword();
            
            passwordHash pwe = new passwordHash(pw);
            List<Character> keyList = pwe.newKey(); // Assuming it returns List<Character>
            StringBuilder keyBuilder = new StringBuilder();
            for (Character ch : keyList) {
                keyBuilder.append(ch);
            }
            String key = keyBuilder.toString(); // Convert List<Character> to String
            
            String encryption_message = pwe.encryption(pw);
            statement.setString(3, encryption_message);
            statement.setString(4, key);
            
            int rows = statement.executeUpdate();
            if(rows > 0) {
                JOptionPane.showMessageDialog(null,
                        "Registration Successfully Done.",
                        "Congratulations",
                        JOptionPane.PLAIN_MESSAGE);
                new BookPage();
            }else{
                JOptionPane.showMessageDialog(null,
                        "Registration Filled",
                        "Try Again",
                        JOptionPane.ERROR_MESSAGE);
                new RegisterFrame();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Edit user info in table
    public void U_user(String email, String oldPassword , String newPassword){
        String query = "SELECT userPassword,userKey FROM userTbl_blog1 WHERE userEmail = ?";
        try{
            Connection connection = Connections.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userKey = resultSet.getString("userKey");
                String userPassword = resultSet.getString("userPassword");

                // Convert userKey to ArrayList<Character>
                ArrayList<Character> keyArrayList = keyList(userKey);

                // Decrypt the stored password
                passwordHash pwe = new passwordHash(userPassword);
                String decryptedPassword = pwe.decryption(userPassword, keyArrayList);
                System.out.print(decryptedPassword);
                String password = pwe.encryption(newPassword);

                if (decryptedPassword.equals(oldPassword)) {
                    JOptionPane.showMessageDialog(null, "You're logged in successfully.", "Logged in successfully", JOptionPane.PLAIN_MESSAGE);
                    Update_password(email, password);
                    new BookPage(); // Proceed with further actions (e.g., opening a book selection)
                    System.out.println("Row has been selected successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    new LoginFrame();
                    System.out.println("Password mismatch.");
                }
            } else {
                // No user found with the provided email
                JOptionPane.showMessageDialog(null, "No user found with the provided email. Please sign up.", "User Not Found", JOptionPane.ERROR_MESSAGE);
                System.out.println("No rows were selected.");
            }
                    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred while updating the user.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }
    
    // delete user info form sign up form
    public void D_user(Models models){
        String query = "DELETE FROM userTbl_blog1 WHERE userName=?";
        try {
            Connection connection = Connections.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, models.getUserName());
            
            int rows = statement.executeUpdate();
            if(rows > 0) {
                System.out.println("Row has been delete successfully.");
                JOptionPane.showMessageDialog(null,
                        "Delete process are successfully done.",
                        "Delete successfully",
                        JOptionPane.PLAIN_MESSAGE);
            }else{
                System.out.println("No rows were inserted.");
                JOptionPane.showMessageDialog(null,
                        "Delete process are failed.",
                        "Delete failed",
                        JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // select user info form sign up form
    public void S_user(Models model) {
        String query = "SELECT * FROM userTbl_blog1 WHERE userEmail = ?";
        try {
            Connection connection = Connections.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, model.getUserEmail());
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                String userKey = resultSet.getString("userKey");
                String userPassword = resultSet.getString("userPassword");

                // Convert userKey to ArrayList<Character>
                String decryptedPassword = decryption(userKey, userPassword);

                if (decryptedPassword.equals(model.getUserPassword())) {
                    JOptionPane.showMessageDialog(null, "You're logged in successfully.", "Logged in successfully", JOptionPane.PLAIN_MESSAGE);
                    new BookPage(); // Proceed with further actions (e.g., opening a book selection)
                    System.out.println("Row has been selected successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    new LoginFrame();
                    System.out.println("Password mismatch.");
                }
            } else {
                // No user found with the provided email
                JOptionPane.showMessageDialog(null, "No user found with the provided email. Please sign up.", "User Not Found", JOptionPane.ERROR_MESSAGE);
                System.out.println("No rows were selected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    // update user info password
    public void Update_password(String email,String password){
        String query = "UPDATE userTbl_blog1 SET userPassword = ? WHERE userEmail = ?";
        try(Connection connection = Connections.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);
            
            int rows = preparedStatement.executeUpdate();
            String message  = rows > 0?"Password updated successfully":"Password update failed";
            JOptionPane.showMessageDialog(null,message);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // get user list of table for admin
    public List<adminModel> adminmodel() {
        List<adminModel> list = new ArrayList<>();
        String query = "SELECT * FROM adminTbl_blog";
        try{
            Connection connection = Connections.getConnection();
            System.out.println(connection);
            System.out.println("Connected to database successfully");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                adminModel adminmodel = new adminModel();
                adminmodel.setAdminId(resultSet.getInt("adminId"));
                adminmodel.setAdminName(resultSet.getString("adminName"));
                adminmodel.setAdminEmail(resultSet.getString("adminEmail"));
                adminmodel.setAdminPassword(resultSet.getString("adminPassword"));
                list.add(adminmodel);
            }
        } catch (SQLException e) {
            System.out.println("Oop, there is an error");
            throw new RuntimeException(e);
        }
        return list;
    }

    // set user info form sign up form
    public void C_admin(adminModel adminmodel){
        String query = "INSERT INTO adminTbl_blog values(?,?,?,?)";
        try(Connection connection = Connections.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1,adminmodel.getAdminName());
            statement.setString(2, adminmodel.getAdminEmail());
            String pw = adminmodel.getAdminPassword();

            passwordHash pwe = new passwordHash(pw);
            List<Character> keyList = pwe.newKey(); // Assuming it returns List<Character>
            StringBuilder keyBuilder = new StringBuilder();
            for (Character ch : keyList) {
                keyBuilder.append(ch);
            }
            String key = keyBuilder.toString(); // Convert List<Character> to String

            String encryption_message = pwe.encryption(pw);
            statement.setString(3, encryption_message);
            statement.setString(4, key);

            int rows = statement.executeUpdate();
            if(rows > 0) {
                JOptionPane.showMessageDialog(null,
                        "Registration Successfully Done.",
                        "Congratulations",
                        JOptionPane.PLAIN_MESSAGE);
                new SelectionFrame();
            }else{
                JOptionPane.showMessageDialog(null,
                        "Registration Filled",
                        "Try Again",
                        JOptionPane.ERROR_MESSAGE);
                new AdminRegistrationFrame();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Edit user info in table
    public void U_admin(String email, String oldPassword , String newPassword){
        String query = "SELECT adminPassword,adminKey FROM adminTbl_blog WHERE adminEmail = ?";
        try{
            Connection connection = Connections.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String adminKey = resultSet.getString("adminKey");
                String adminPassword = resultSet.getString("adminPassword");

                // Convert userKey to ArrayList<Character>
                ArrayList<Character> keyArrayList = keyList(adminKey);

                // Decrypt the stored password
                passwordHash pwe = new passwordHash(adminPassword);
                String decryptedPassword = pwe.decryption(adminPassword, keyArrayList);
                System.out.print(decryptedPassword);
                String password = pwe.encryption(newPassword);

                if (decryptedPassword.equals(oldPassword)) {
                    JOptionPane.showMessageDialog(null, "You're logged in successfully.", "Logged in successfully", JOptionPane.PLAIN_MESSAGE);
                    Update_password(email, password);
                    new BookPage(); // Proceed with further actions (e.g., opening a book selection)
                    System.out.println("Row has been selected successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    new LoginFrame();
                    System.out.println("Password mismatch.");
                }
            } else {
                // No user found with the provided email
                JOptionPane.showMessageDialog(null, "No user found with the provided email. Please sign up.", "User Not Found", JOptionPane.ERROR_MESSAGE);
                System.out.println("No rows were selected.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred while updating the user.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    // select user info form sign up form
    public void S_admin(adminModel model) {
        String query = "SELECT * FROM adminTbl_blog WHERE adminEmail = ?";
        try {
            Connection connection = Connections.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, model.getAdminEmail());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String adminKey = resultSet.getString("adminKey");
                String adminPassword = resultSet.getString("adminPassword");

                String decryptedPassword = decryption(adminKey, adminPassword);

                if (decryptedPassword.equals(model.getAdminPassword())) {
                    JOptionPane.showMessageDialog(null, "You're logged in successfully.", "Logged in successfully", JOptionPane.PLAIN_MESSAGE);
                    new SelectionFrame();
                    System.out.println("Row has been selected successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    new AdminLoginFrame();
                    System.out.println("Password mismatch.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No user found with the provided email. Please sign up.", "User Not Found", JOptionPane.ERROR_MESSAGE);
                System.out.println("No rows were selected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // delete user info form sign up form
    public void D_admin(adminModel models){
        String query = "DELETE FROM adminTbl_blog WHERE adminName=?";
        try {
            Connection connection = Connections.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, models.getAdminName());

            int rows = statement.executeUpdate();
            if(rows > 0) {
                System.out.println("Row has been delete successfully.");
                JOptionPane.showMessageDialog(null,
                        "Delete process are successfully done.",
                        "Delete successfully",
                        JOptionPane.PLAIN_MESSAGE);
            }else{
                System.out.println("No rows were inserted.");
                JOptionPane.showMessageDialog(null,
                        "Delete process are failed.",
                        "Delete failed",
                        JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Selecting Book List form database
    public static List<bookModels> bookModels() {
        List<bookModels> list = new ArrayList<>();
        String query = "SELECT * FROM bookTbl_blog";
        try{
            Connection connection = Connections.getConnection();
            System.out.println(connection);
            System.out.println("Connected to database successfully");

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                bookModels bookmodels = new bookModels();
                bookmodels.setBookId(resultSet.getInt("BookId"));
                bookmodels.setBookName(resultSet.getString("BookName"));
                bookmodels.setBookAuthor(resultSet.getString("BookAuthor"));
                bookmodels.setBookYear(resultSet.getString("BookYear"));
                bookmodels.setBookPrice(resultSet.getDouble("BookPrice"));
                list.add(bookmodels);
            }
        } catch (SQLException e) {
            System.out.println("Oop, there is an error");
            throw new RuntimeException(e);
        }
        return list;
    }

    // Method to fetch books from the database
    public List<bookModels> S_book() {
        List<bookModels> books = new ArrayList<>();
        String query = "SELECT * FROM bookTbl_blog"; // Adjust with your table name

        try (Connection connection = Connections.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("BookId");
                String name = resultSet.getString("BookName");
                String author = resultSet.getString("BookAuthor");
                String year = resultSet.getString("BookYear");
                double price = resultSet.getDouble("BookPrice");

                books.add(new bookModels(id,name, author, year, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }
    
    // Add book
    public void C_book(bookModels book) {
        String query = "INSERT INTO bookTbl_blog (BookName, BookAuthor, BookYear, BookPrice) VALUES (?, ?, ?, ?)";

        try (Connection connection = Connections.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getBookAuthor());
            preparedStatement.setString(3, book.getBookYear());
            preparedStatement.setDouble(4, book.getBookPrice());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setBookId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Update book
    public void U_book(bookModels book) {
        String query = "UPDATE bookTbl_blog SET BookName = ?, BookAuthor = ?, BookYear = ?, BookPrice = ? WHERE BookId = ?";

        try (Connection connection = Connections.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getBookAuthor());
            preparedStatement.setString(3, book.getBookYear());
            preparedStatement.setDouble(4, book.getBookPrice());
            preparedStatement.setInt(5, book.getBookId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Delete book
    public void D_book(bookModels book){
        String query = "DELETE FROM bookTbl_blog WHERE BookId = ?";
        try{
            Connection connection = Connections.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, book.getBookId());
            int rows = statement.executeUpdate();
            if (rows > 0){
                System.out.println("Rows were deleted.");
            }else {
                System.out.println("No rows were deleted.");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    // Show book list in book catalogs
    public List<bookModels> Show_book() {
        List<bookModels> list = new ArrayList<>();
        String query = "SELECT BookName FROM bookTbl_blog";
        try{
            Connection connection = Connections.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                bookModels bookmodels = new bookModels();
                bookmodels.setBookName(resultSet.getString("BookName"));
                list.add(bookmodels);
                System.out.println(resultSet.getString("BookName"));
            }
        } catch (SQLException e) {
            System.out.println("Oop, there is an error");
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public static void search_title(String title){
        String query = "SELECT * FROM bookTbl_blog WHERE UPPER(bookName) = UPPER(?)";
        try {
            Connection connection = Connections.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("bookName");
                String author =  resultSet.getString("bookAuthor");
                String year = resultSet.getString("bookYear");
                double price = resultSet.getDouble("bookPrice");

                new BookDetailsFrame(name, author, year, price);
//                JOptionPane.showMessageDialog(null, "Book is successfully selected.", "successfully selected book", JOptionPane.PLAIN_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Oops..., there's no book in database", "successfully failed", JOptionPane.ERROR_MESSAGE);
                new BookPage();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void search_author(String Author){
        String query = "SELECT * FROM bookTbl_blog WHERE UPPER(bookAuthor) = UPPER(?)";
        try {
            Connection connection = Connections.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, Author);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("bookName");
                String author =  resultSet.getString("bookAuthor");
                String year = resultSet.getString("bookYear");
                double price = resultSet.getDouble("bookPrice");
                
                new BookDetailsFrame(name, author, year, price);
//                JOptionPane.showMessageDialog(null, "Book is successfully selected.", "successfully selected book", JOptionPane.PLAIN_MESSAGE);
//                new BookPage();
                
            }else{
                JOptionPane.showMessageDialog(null, "Oops..., there's no book in database", "successfully failed", JOptionPane.ERROR_MESSAGE);
                new BookPage();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Book> getBooks() {
        List<Book> list = new ArrayList<>();
        String query = "SELECT * FROM bookTbl_blog";
        try{
            Connection connection = Connections.getConnection();
            System.out.println(connection);
            System.out.println("Connected to database successfully");

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Book book = new Book();
                book.setBookTitle(resultSet.getString("bookName"));
                book.setBookAuthor(resultSet.getString("bookAuthor"));
                book.setBookPrice(resultSet.getDouble("bookPrice"));
                list.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Oop, there is an error");
            throw new RuntimeException(e);
        }
        return list;
    }
    
    private String decryption(String userKey, String userPassword)
    {
        // Convert userKey to ArrayList<Character>
        ArrayList<Character> keyArrayList = keyList(userKey);

        // Decrypt the stored password
        passwordHash pwe = new passwordHash(userPassword);
        String decryptedPassword = pwe.decryption(userPassword, keyArrayList);
        System.out.print(decryptedPassword);

        return decryptedPassword;
    }
    
    private ArrayList<Character> keyList(String userKey)
    {
        ArrayList<Character> keyArrayList = new ArrayList<>();
        for (char c : userKey.toCharArray()) {
            keyArrayList.add(c);
        }
        return keyArrayList;
    }
    
    // Method to save the order to the database
    public void saveOrder(orderModels order) {
        String orderSQL = "INSERT INTO ordersTbl_blog (PaymentStatic, Total_amount) VALUES (?, ?)";
        String itemSQL = "INSERT INTO orderTbl_blog (BookTitle, BookPrice, BookQty) VALUES (?, ?, ?)";

        try (Connection connection = Connections.getConnection();
             PreparedStatement orderStmt = connection.prepareStatement(orderSQL);
             PreparedStatement itemStmt = connection.prepareStatement(itemSQL)) {
             // Save the order
            orderStmt.setString(1, order.getPaymentType());
            orderStmt.setDouble(2, order.getTotalAmount()); // Set the total amount
            orderStmt.executeUpdate();

            // Retrieve generated order ID
//               try (var rs = orderStmt.getGeneratedKeys()) {
//                   if (rs.next()) {
//                       int orderId = rs.getInt(1);
//                       order.setOrderId(orderId);
//                   }
//               }
            // Save each order item
            for (orderModels.OrderItem item : order.getItems()) {
                itemStmt.setString(1, item.getTitle());
                itemStmt.setDouble(2, item.getPrice());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.addBatch();
            }
            itemStmt.executeBatch(); // Execute batch insert for order items

        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
    }
}
