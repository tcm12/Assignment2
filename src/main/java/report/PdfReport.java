package report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import model.Book;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class PdfReport extends Report {
    @Override
    public void generateReport() throws DocumentException, FileNotFoundException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("outOfStock.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Out of stock books: ", font);

        document.add(chunk);
        document.add(new Paragraph("\n"));


        List<Book> bookList = generalController.getBookList();
        boolean outOfStock = true;

        for (Book tempBook : bookList) {

            if (tempBook.getQuantity() == 0) {
                outOfStock = false;
                chunk = new Chunk(tempBook.getTitle() + "; " + tempBook.getAuthor() +
                        "; " + tempBook.getGenre(), font);
                document.add(new Paragraph("\n"));
                document.add(chunk);
            }
        }

        if (outOfStock) {
            chunk = new Chunk("No out of stock books", font);
            document.add(new Paragraph("\n"));
            document.add(chunk);
        }

        document.close();
    }
}
