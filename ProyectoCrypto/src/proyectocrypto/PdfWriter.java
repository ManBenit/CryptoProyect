package proyectocrypto;

import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import tools.Message;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class PdfWriter {
    private Message msg;
    private int posY;
    
    public PdfWriter(){
        msg= new Message();
        posY=707;
    }
    
    public void writeFile(String path, String[] lines) {
        try {
            try (PDDocument pdDoc = new PDDocument()) {
                PDPage page = new PDPage();
                pdDoc.addPage(page);
                
                for (String line: lines) {
                    writeLine(pdDoc, page, line);
                }
                
                pdDoc.save(path);
            }
        }catch(IOException ex) {
            msg.showMessage(Message.ERROR, "Impossible to write.\n"+ex);
        }
    }
    
    private void writeLine(PDDocument pdDoc, PDPage page, String content){
        try(PDPageContentStream cs = new PDPageContentStream(pdDoc, page, PDPageContentStream.AppendMode.APPEND, false)){
            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 12);
            
            //cs.setNonStrokingColor(Color.BLUE);
            
            //posY position of newLineAtOffset begin from document bottom to document top (not usually view)
            cs.newLineAtOffset(87, posY);
            cs.showText(content);
            cs.newLine();
            cs.endText();
            posY-=15;
        }catch(IOException ex) {
            msg.showMessage(Message.ERROR, "Impossible to write.\n"+ex);
        }
    }
}