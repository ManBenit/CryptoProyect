package proyectocrypto;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class WordReader {
    private File loadedFile;
    
    public WordReader(String path){
        loadedFile= new File(path);
    }

    String[] readFile() {
        String[] lines= null;
        int lineIndex=0;
        try {
            FileInputStream fis = new FileInputStream(loadedFile);
            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            lines= new String[paragraphs.size()];
            for (XWPFParagraph para : paragraphs) {
                lines[lineIndex]= para.getText();
                lineIndex+=1;
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lines;
    }
}
