package controller;

import model.Administrator;
import model.Book;
import model.User;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeneralController {

    private List<Administrator> adminList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private List<Book> bookList = new ArrayList<>();

    public GeneralController() { readData(); }

    private void readData(){

        try {
        File adminFile = new File("D:\\Faculta\\SD\\Assignment2\\src\\main\\administrator.xml");
        File userFile = new File("D:\\Faculta\\SD\\Assignment2\\src\\main\\user.xml");
        File bookFile = new File("D:\\Faculta\\SD\\Assignment2\\src\\main\\book.xml");

        FileInputStream adminInputStream = new FileInputStream(adminFile);
        FileInputStream userInputStream = new FileInputStream(userFile);
        FileInputStream bookInputStream = new FileInputStream(bookFile);

        XMLDecoder adminDecoder = new XMLDecoder(adminInputStream);
        XMLDecoder userDecoder = new XMLDecoder(userInputStream);
        XMLDecoder bookDecoder = new XMLDecoder(bookInputStream);


        adminList = (List<Administrator>) adminDecoder.readObject();
        userList = (List<User>) userDecoder.readObject();
        bookList = (List<Book>) bookDecoder.readObject();

        adminDecoder.close();
        adminInputStream.close();

        userDecoder.close();
        userInputStream.close();

        bookDecoder.close();
        bookInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeData(){
        try {
            File adminFile = new File("D:\\Faculta\\SD\\Assignment2\\src\\main\\administrator.xml");
            File userFile = new File("D:\\Faculta\\SD\\Assignment2\\src\\main\\user.xml");
            File bookFile = new File("D:\\Faculta\\SD\\Assignment2\\src\\main\\book.xml");

            FileOutputStream adminOutputStream = new FileOutputStream(adminFile);
            FileOutputStream userOutputStream = new FileOutputStream(userFile);
            FileOutputStream bookOutputStream = new FileOutputStream(bookFile);

            XMLEncoder adminEncoder = new XMLEncoder(adminOutputStream);
            XMLEncoder userEncoder = new XMLEncoder(userOutputStream);
            XMLEncoder bookEncoder = new XMLEncoder(bookOutputStream);

            adminEncoder.writeObject(adminList);
            userEncoder.writeObject(userList);
            bookEncoder.writeObject(bookList);

            bookEncoder.close();
            bookOutputStream.close();

            adminEncoder.close();
            adminOutputStream.close();

            userEncoder.close();
            userOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Administrator> getAdminList() { return adminList; }

    public List<User> getUserList() {
        return userList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setAdminList(List<Administrator> adminList) {
        this.adminList = adminList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
