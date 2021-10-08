package ru.andreeva;
import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.InputStream;
import java.util.Scanner;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideFilesTest {


    @Test
    public void testForTXTFile() throws Exception {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("example.txt")) {
            assert stream != null;
            Scanner myReader = new Scanner(stream);
            while (myReader.hasNextLine()) {
                String file = myReader.nextLine();
                assertThat(file).contains("Hello, QA GURU!");
            }
        }
    }


    @Test
    void downloadPdfTest() throws Exception {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
        File download = $(byText("PDF download")).download();
        PDF parsed = new PDF(download);
        assertThat(parsed.author).contains("Marc Philip");

    }

    @Test
    public void testForXlsFile() throws Exception {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("175.xlsx")) {
            assert stream != null;
            XLS parsed = new XLS(stream);
            assertThat(parsed.excel.getSheetAt(1).getRow(4).getCell(1).getStringCellValue())
                    .isEqualTo("Алешина Ольга Валентиновна");
        }

    }


    @Test
    void parseZipFileTest() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("zip_2MB.zip")) {
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                System.out.println(entry.getName());
            }


        }
    }
}