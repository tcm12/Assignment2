package controller;

import model.Book;
import model.User;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminController extends GeneralController {

    public static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern NAME_REGEX =
            Pattern.compile("^[A-Z]+[A-Z ]{2,20}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern LETTER_REGEX =
            Pattern.compile("^[A-Z]+[A-Z ]{1,20}$", Pattern.CASE_INSENSITIVE);

    private boolean incorrectInput(String input, int mode) {
        Matcher matcher = null;

        switch (mode) {
            case 0:
                matcher = EMAIL_REGEX.matcher(input);
                break;
            case 1:
                matcher = NAME_REGEX.matcher(input);
                break;
            case 2:
                matcher = LETTER_REGEX.matcher(input);
                break;
        }

        assert matcher != null;

        return !matcher.find();
    }

    public int addUser(String newEmail, String password, String newName) {

        if (incorrectInput(newEmail, 0)) {
            return 1;
        }
        if (incorrectInput(newName, 1)) {
            return 2;
        }
        if (password.length() < 4) {
            return 4;
        }

        User user = new User(newEmail, password, newName);
        List<User> userList = getUserList();

        for (User tempUser : userList) {
            if (tempUser.getMail().equals(newEmail)) {
                return 10;
            }
        }

        userList.add(user);
        setUserList(userList);
        writeData();

        return 0;
    }

    public int updateUser(String oldEmail, String newEmail, String newPassword, String newName) {

        if (incorrectInput(oldEmail, 0)) {
            return 1;
        }
        if (incorrectInput(newEmail, 0) && !newEmail.isEmpty()) {
            return 1;
        }
        if (incorrectInput(newName, 1) && !newName.isEmpty()) {
            return 2;
        }
        if (newPassword.length() < 4 && !newPassword.isEmpty()) {
            return 4;
        }

        List<User> userList = getUserList();

        for (User tempUser : userList) {
            if (tempUser.getMail().equals(oldEmail)) {
                userList.remove(tempUser);

                if (!newEmail.isEmpty()) {
                    tempUser.setMail(newEmail);
                }
                if (!newName.isEmpty()) {
                    tempUser.setName(newName);
                }
                if (!newPassword.isEmpty()) {
                    tempUser.setPassword(newPassword);
                }

                userList.add(tempUser);
                setUserList(userList);
                writeData();
                return 0;
            }
        }

        return 3;
    }

    public int deleteUser(String mail) {

        if (incorrectInput(mail, 0)) {
            return 1;
        }

        List<User> userList = getUserList();

        for (User tempUser : userList) {
            if (tempUser.getMail().equals(mail)) {
                userList.remove(tempUser);
                setUserList(userList);
                writeData();
                return 0;
            }
        }

        return 3;
    }

    public int addBook(String title, String author, String genre, String sQuantity, String sPrice) {
        int id;
        int quantity;
        float price;

        try {
            quantity = Integer.parseInt(sQuantity);
        } catch (NumberFormatException e1) {
            return 13;
        }
        try {
            price = Float.parseFloat(sPrice);
        } catch (NumberFormatException e1) {
            return 14;
        }

        if (title.isEmpty()) {
            return 5;
        }
        if (incorrectInput(author, 2)) {
            return 6;
        }
        if (incorrectInput(genre, 2)) {
            return 7;
        }
        if (quantity < 1) {
            return 8;
        }
        if (price < 0) {
            return 9;
        }

        List<Book> bookList = getBookList();

        if (!bookList.isEmpty()) {

            int max = bookList.get(0).getId();

            for (Book tempBook : bookList) {
                if (tempBook.getTitle().equals(title) && tempBook.getAuthor().equals(author)) {
                    return 11;
                }
                if (tempBook.getId() > max) {
                    max = tempBook.getId();
                }
            }

            id = max + 1;
        } else {
            id = 1;
        }

        Book book = new Book(id, title, author, genre, quantity, price);

        bookList.add(book);
        setBookList(bookList);
        writeData();

        return 0;
    }

    public int deleteBook(int id) {

        List<Book> bookList = getBookList();

        for (Book tempBook : bookList) {
            if (tempBook.getId() == id) {
                bookList.remove(tempBook);
                setBookList(bookList);
                writeData();
                return 0;
            }
        }

        return 12;
    }

    public int updateBook(int id, String title, String author, String genre, String quantity, String price) {
        int parsedQuantity = 0;
        float parsedPrice = 0.0f;

        if (incorrectInput(author, 2) && !author.isEmpty()) {
            return 6;
        }
        if (incorrectInput(genre, 2) && !genre.isEmpty()) {
            return 7;
        }

        if (!quantity.isEmpty()) {
            try {
                parsedQuantity = Integer.parseInt(quantity);
            } catch (NumberFormatException e1) {
                return 13;
            }
            if (parsedQuantity < 1) {
                return 8;
            }
        }

        if (!price.isEmpty()) {
            try {
                parsedPrice = Float.parseFloat(price);
            } catch (NumberFormatException e1) {
                return 14;
            }
            if (parsedPrice < 0) {
                return 9;
            }
        }

        List<Book> bookList = getBookList();

        for (Book tempBook : bookList) {
            if (tempBook.getId() == id) {
                bookList.remove(tempBook);

                if (!title.isEmpty()) {
                    tempBook.setTitle(title);
                }
                if (!author.isEmpty()) {
                    tempBook.setAuthor(author);
                }
                if (!genre.isEmpty()) {
                    tempBook.setGenre(genre);
                }
                if (!quantity.isEmpty()) {
                    tempBook.setQuantity(parsedQuantity);
                }
                if (!price.isEmpty()) {
                    tempBook.setPrice(parsedPrice);
                }
                bookList.add(tempBook);
                setBookList(bookList);
                writeData();
                return 0;
            }

        }

        return 12;
    }

}
