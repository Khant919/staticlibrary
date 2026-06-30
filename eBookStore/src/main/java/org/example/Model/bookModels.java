package org.example.Model;


public class bookModels {
    private int BookId;
    private String BookName;
    private String BookAuthor;
    private String BookYear;
    private double BookPrice;

    public bookModels() {}
    
    public bookModels(int bookId,String bookName, String bookAuthor, String bookYear, double bookPrice) {
        this.BookId = bookId;
        this.BookName = bookName;
        this.BookAuthor = bookAuthor;
        this.BookYear = bookYear;
        this.BookPrice = bookPrice;
    }

    public double getBookPrice() {
        return BookPrice;
    }

    public void setBookPrice(double bookPrice) {
        BookPrice = bookPrice;
    }

    public String getBookYear() {
        return BookYear;
    }

    public void setBookYear(String bookYear) {
        BookYear = bookYear;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        BookAuthor = bookAuthor;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public int getBookId() {
        return BookId;
    }

    public void setBookId(int bookId) {
        BookId = bookId;
    }

    // Model class to manage the cart
//    public static class Cart {
//        private Map<String, Integer> cartItems = new HashMap<>(); // Book name as key, quantity as value
//
//        public void addToCart(String bookName) {
//            cartItems.put(bookName, cartItems.getOrDefault(bookName, 0) + 1);
//        }
//
//        public Map<String, Integer> getCartItems() {
//            return cartItems;
//        }
//    }
}
