package report;

import com.itextpdf.text.DocumentException;
import controller.GeneralController;
import model.Book;

import java.io.IOException;
import java.util.List;

public abstract class Report {

    protected GeneralController generalController = new GeneralController();

    public abstract void generateReport() throws IOException, DocumentException;

}
