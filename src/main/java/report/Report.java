package report;

import com.itextpdf.text.DocumentException;
import controller.GeneralController;

import java.io.IOException;

public abstract class Report {

    protected GeneralController generalController = new GeneralController();

    public abstract void generateReport() throws IOException, DocumentException;

}
