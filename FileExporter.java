import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.fontbox.*;
import java.io.*;

public class FileExporter {
    public static void exportToPDF(String text, File file) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(org.apache.pdfbox.pdmodel.font.PDType1Font.COURIER, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);

            // Split text into lines to prevent overflow
            String[] lines = text.split("\n");
            for (String line : lines) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -15); // Move to next line
            }

            contentStream.endText();
            contentStream.close();

            document.save(file);
            System.out.println("PDF saved: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
