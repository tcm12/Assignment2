package report;

import com.opencsv.CSVWriter;
import model.Book;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvReport extends Report {
    @Override
    public void generateReport() throws IOException {

        CSVWriter csvWriter = null;
        List<Book> bookList = generalController.getBookList();

        csvWriter = new CSVWriter(new FileWriter("outOfStock.csv"));

        for (Book tempBook : bookList) {
            if (tempBook.getQuantity() == 0) {
                csvWriter.writeNext(new String[]{tempBook.getTitle(), tempBook.getAuthor(), tempBook.getGenre()});
            }
        }

        csvWriter.close();
    }
}
