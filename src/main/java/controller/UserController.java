package controller;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class UserController extends GeneralController {

    public int sellBook(int id, String sAmount) {
        int amount;

        if (sAmount.isEmpty()) {
            amount = 1;
        } else {

            try {
                amount = Integer.parseInt(sAmount);
            } catch (NumberFormatException e1) {
                return 4;
            }
        }

        List<Book> bookList = getBookList();

        if (amount < 1) {
            return 1;
        }

        for (Book tempBook : bookList) {
            if (tempBook.getId() == id) {
                if (tempBook.getQuantity() < amount) {
                    return 2;
                }

                bookList.remove(tempBook);
                tempBook.setQuantity(tempBook.getQuantity() - amount);
                bookList.add(tempBook);
                setBookList(bookList);
                writeData();
                return 0;
            }
        }

        return 3;
    }

    public List<Book> filteredList(String title, String author, String genre) {
        List<Book> bookList = getBookList();
        List<Book> filteredBookList = new ArrayList<>();

        for (Book tempBook : bookList) {
            String tempTitle = tempBook.getTitle().toLowerCase();
            String tempAuthor = tempBook.getAuthor().toLowerCase();
            String tempGenre = tempBook.getGenre().toLowerCase();

            title = title.toLowerCase();
            author = author.toLowerCase();
            genre = genre.toLowerCase();

            if (tempTitle.contains(title) && tempAuthor.contains(author) && tempGenre.contains(genre)) {
                filteredBookList.add(tempBook);
            }
        }

        return filteredBookList;
    }

}
