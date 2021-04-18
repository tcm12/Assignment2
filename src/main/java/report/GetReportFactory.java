package report;

public class GetReportFactory {

    public Report getReport(String reportType) {
        if (reportType == null) {
            return null;
        }

        if (reportType.equalsIgnoreCase("PDF")) {
            return new PdfReport();
        }

        if (reportType.equalsIgnoreCase("CSV")) {
            return new CsvReport();
        }

        return null;
    }

}
