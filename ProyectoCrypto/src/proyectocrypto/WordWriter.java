package proyectocrypto;

import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import tools.Message;

public class WordWriter {
    private Message msg;
    private XWPFDocument document;
    private XWPFParagraph breakParagraph;
    private XWPFRun runBreak;

    // clase para eclipse 
    public WordWriter() {
        msg= new Message();
        document= new XWPFDocument();
    }
    
    public void newParagraph(String content, String fontStyle, int fontSize,
            String hexDecColor, char alignment, boolean bold, boolean italic, boolean underline){
        /*
        Each explicit parameter is evident.
        hexDecColor: Hexadecimal color expression (without #).
        alignment: r=Rigth, l=Left, j=Justify, c=Center
        */
        XWPFParagraph paragraph= document.createParagraph();
        XWPFRun run = paragraph.createRun();
        
        if(content == null || content.equals(""))
            msg.showMessage(Message.ERROR, "You must put a content");
        else
            run.setText(content);
        
        if(fontStyle==null || fontStyle.equals(""))
            run.setFontFamily("Arial");
        else
            run.setFontFamily(fontStyle);
        
        if(fontSize<8)
            run.setFontSize(12);
        else
            run.setFontSize(fontSize);
        
        if(bold) run.setBold(true);
        if(italic) run.setItalic(true);
        if(underline) run.setUnderline(UnderlinePatterns.SINGLE);
        
        if(hexDecColor==null || hexDecColor.equals(""))
            run.setColor("000000");
        else
            run.setColor(hexDecColor);
        
        switch(alignment){
                case 'r':
                paragraph.setAlignment(ParagraphAlignment.RIGHT);
                    break;
                    
                case 'l':
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                    break;
                    
                case 'j':
                paragraph.setAlignment(ParagraphAlignment.BOTH);
                    break;
                    
                case 'c':
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                    break;
                    
                default:
                paragraph.setAlignment(ParagraphAlignment.RIGHT);
                    break;
        }
    }
    
    public void newLine(){
        breakParagraph= document.createParagraph();
        runBreak= breakParagraph.createRun();
        runBreak.addBreak();
    }
    
    public void writeFile(String path){
        try{
            FileOutputStream output = new FileOutputStream(path);
            document.write(output);
            output.close();
            msg.showMessage(Message.INFO, "Word exported succesfully");
        }catch(Exception ex){
            msg.showMessage(Message.ERROR, "Fail operation\n"+ex);
        }
    }
}
