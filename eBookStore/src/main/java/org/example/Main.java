package org.example;

import java.util.List;

import org.example.AdminRoll.bookTbl;
import org.example.AdminRoll.userTbl;
import org.example.Book.BookPage;
import org.example.Controller.controllers;
import org.example.Model.Models;
import org.example.Model.bookModels;
import org.example.homePage.EbookGUI;

public class Main {
    public static void main(String[] args) {
        controllers test = new controllers();
        List<Models> models = test.models();
        for(Models model : models){
            System.out.print("\t" + model.getUserId() + "\t");
            System.out.print("\t" +  model.getUserName() +  "\t");
            System.out.print("\t" + model.getUserEmail() + "\t");
            System.out.println("\t" + model.getUserPassword() + "\t");
        }
        
        controllers test2 = new controllers();
        List<bookModels> bookModels = test2.bookModels();
        for(bookModels model : bookModels){
            System.out.print("\t" + model.getBookId() + "\t");
            System.out.print("\t" + model.getBookName() +  "\t");
            System.out.print("\t" + model.getBookAuthor() + "\t");
            System.out.print("\t" + model.getBookYear() + "\t");
            System.out.println("\t" + "$" + model.getBookPrice() + "\t");
        }
        
//        new EbookGUI();
        new BookPage();
    }
}
