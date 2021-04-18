package view;

import com.itextpdf.text.DocumentException;
import controller.AdminController;
import controller.GuiController;
import model.Book;
import model.User;
import report.GetReportFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.List;

public class AdministratorPage extends JFrame {
    private JPanel adminPanel;
    private JTabbedPane tabbedPane1;
    private JTable userTable;
    private JTable bookTable;
    private JPanel userPanel;
    private JPanel bookPanel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton addBookButton;
    private JButton deleteBookButton;
    private JButton updateBookButton;
    private JTextField titleTextField;
    private JTextField genreTextField;
    private JTextField authorTextField;
    private JTextField quantityTextField;
    private JTextField priceTextField;
    private JTextField emailTextField;
    private JTextField nameTextField;
    private JButton logOutButton;
    private JPasswordField passwordTextField;
    private JButton CSVOutOfStockButton;
    private JButton PDFOutOfStockButton;

    private final GuiController guiController;
    private final AdminController adminController;

    private final GetReportFactory getReportFactory = new GetReportFactory();

    public AdministratorPage() {
        super("Admin Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(adminPanel);
        this.pack();

        guiController = new GuiController();
        adminController = new AdminController();

        guiController.setAdministratorPage(this);

        updateBookData();
        updateUserData();

        activateActionListeners();
    }

    public void updateUserData() {
        List<User> userList = adminController.getUserList();

        DefaultTableModel modelUser = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        modelUser.addColumn("e-Mail");
        modelUser.addColumn("Name");

        for (User tempUser : userList) {
            modelUser.addRow(new Object[]{tempUser.getMail(), tempUser.getName()});
        }

        userTable.setModel(modelUser);
    }

    public void updateBookData() {
        List<Book> bookList = adminController.getBookList();

        DefaultTableModel modelBook = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        modelBook.addColumn("ID");
        modelBook.addColumn("Title");
        modelBook.addColumn("Author");
        modelBook.addColumn("Genre");
        modelBook.addColumn("Quantity");
        modelBook.addColumn("Price");

        for (Book tempBook : bookList) {
            modelBook.addRow(new Object[]{tempBook.getId(), tempBook.getTitle(), tempBook.getAuthor(), tempBook.getGenre(), tempBook.getQuantity(), tempBook.getPrice()});
        }

        bookTable.setModel(modelBook);

    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private String getUserMail() {
        String email;

        try {
            email = (String) userTable.getModel().getValueAt(userTable.getSelectedRow(), 0);
        } catch (ArrayIndexOutOfBoundsException e1) {
            showErrorMessage("Please select the user!");
            return "";
        }

        return email;
    }

    private int getBookId() {
        int id;

        try {
            id = (int) bookTable.getModel().getValueAt(bookTable.getSelectedRow(), 0);
        } catch (ArrayIndexOutOfBoundsException e1) {
            showErrorMessage("Please select the book!");
            return -1;
        }

        return id;
    }

    private boolean failedToExecute(int errorCode) {
        switch (errorCode) {
            case 1:
                showErrorMessage("Email not ok!");
                return true;
            case 2:
                showErrorMessage("Name not ok!");
                return true;
            case 3:
                showErrorMessage("No such user!");
                return true;
            case 4:
                showErrorMessage("Minimum password length is 4!");
                return true;
            case 5:
                showErrorMessage("Title should have at least one letter!");
                return true;
            case 6:
                showErrorMessage("Author name should only have letters and be at least 2 letters long!");
                return true;
            case 7:
                showErrorMessage("Genre should only have letters and be at least 2 letters long!");
                return true;
            case 8:
                showErrorMessage("Quantity should be a positive number!");
                return true;
            case 9:
                showErrorMessage("Price should be a positive number!");
                return true;
            case 10:
                showErrorMessage("User already exists!");
                return true;
            case 11:
                showErrorMessage("Book already exists!");
                return true;
            case 12:
                showErrorMessage("Book doesn't exists!");
                return true;
            case 13:
                showErrorMessage("The Quantity field should be a number!");
                return true;
            case 14:
                showErrorMessage("The Price field should be a number!");
                return true;
            default:
                return false;
        }
    }

    private void emptyTextFields() {
        passwordTextField.setText("");
        emailTextField.setText("");
        nameTextField.setText("");
        titleTextField.setText("");
        authorTextField.setText("");
        genreTextField.setText("");
        quantityTextField.setText("");
        priceTextField.setText("");
    }

    private void logOut(){
        guiController.closeAllPages();
        LoginPage loginPage = new LoginPage();
    }

    private void addUser(){
        String password = new String(passwordTextField.getPassword());
        String newMail = emailTextField.getText();
        String newName = nameTextField.getText().replaceAll("\\s+", " ");

        if (failedToExecute(adminController.addUser(newMail.toLowerCase(), password, newName.toUpperCase()))) {
            return;
        }

        emptyTextFields();
        updateUserData();
    }

    private void deleteUser(){
        String email = getUserMail();

        if (email.isEmpty()) {
            return;
        }

        if (failedToExecute(adminController.deleteUser(email))) {
            return;
        }

        updateUserData();
    }

    private void updateUser(){
        String email = getUserMail();
        String newMail = emailTextField.getText();
        String newName = nameTextField.getText().replaceAll("\\s+", " ");
        String newPassword = new String(passwordTextField.getPassword());

        if (email.isEmpty()) {
            return;
        }

        if (failedToExecute(adminController.updateUser(email, newMail.toLowerCase(), newPassword, newName.toUpperCase()))) {
            return;
        }

        emptyTextFields();
        updateUserData();
    }

    private void addBook(){
        String title = titleTextField.getText().toUpperCase();
        String author = authorTextField.getText().toUpperCase();
        String genre = genreTextField.getText().toUpperCase();
        String quantity = quantityTextField.getText();
        String price = priceTextField.getText();

        if (failedToExecute(adminController.addBook(title, author,
                genre, quantity, price))) {
            return;
        }

        emptyTextFields();
        updateBookData();
    }

    private void deleteBook(){
        int bookId = getBookId();

        if (bookId == -1) {
            return;
        }

        if (failedToExecute(adminController.deleteBook(bookId))) {
            return;
        }

        updateBookData();
    }

    private void updateBook(){
        int bookId = getBookId();

        if (bookId == -1) {
            return;
        }

        String title = titleTextField.getText().toUpperCase();
        String author = authorTextField.getText().toUpperCase();
        String genre = genreTextField.getText().toUpperCase();
        String quantity = quantityTextField.getText();
        String price = priceTextField.getText();

        if (failedToExecute(adminController.updateBook(bookId, title, author, genre, quantity, price))) {
            return;
        }

        emptyTextFields();
        updateBookData();
    }

    private void createPdfReport(){
        try {
            getReportFactory.getReport("PDF").generateReport();
        } catch (IOException | DocumentException ioException) {
            ioException.printStackTrace();
        }
        JOptionPane.showMessageDialog(
                null,
                "PDF was created",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void createCsvReport(){
        try {
            getReportFactory.getReport("CSV").generateReport();
        } catch (IOException | DocumentException ioException) {
            ioException.printStackTrace();
        }
        JOptionPane.showMessageDialog(
                null,
                "CSV was created",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void activateActionListeners() {
        logOutButton.addActionListener(e -> { logOut(); });

        addButton.addActionListener(e -> { addUser(); });

        deleteButton.addActionListener(e -> { deleteUser(); });

        updateButton.addActionListener(e -> { updateUser(); });

        addBookButton.addActionListener(e -> { addBook(); });

        deleteBookButton.addActionListener(e -> { deleteBook(); });

        updateBookButton.addActionListener(e -> { updateBook(); });

        PDFOutOfStockButton.addActionListener(e -> { createPdfReport(); });

        CSVOutOfStockButton.addActionListener(e -> { createCsvReport(); });
    }
}
