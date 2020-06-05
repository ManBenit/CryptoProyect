package proyectocrypto;

import tools.Message;
import tools.MyFile;

public class ProyectoCrypto {

    public static void main(String[] args) {
        Message m= new Message();
        MyFile mf= new MyFile();
        
//        WordWriter ww= new WordWriter();
        WordReader wr;
        
//        ww.newParagraph("LA verdad no estoy has more low-level support for text manipulation, but you'd have to write a considerable amount of code to get text extraction." +
//"iText in Action contains a good overview of the limitations of text extraction from PDF, regardless of the library used (Section 18.2: Extracting and editing text), and a convincing explanation why the library does not have text extraction support. In short, it's relatively easy to write a code that will handle simple cases, but it's basically impossible to extract text from PDF in general.", "Times New Roman", 20, null, 'c', true, true, true);
//        
//        ww.newLine();
//        ww.newParagraph("Naaa", "Times New Roman", 20, null, 'j', true, true, true);
//        String name= m.enterInfo(Message.QUESTION, "Enter filename", "Filename");
//        ww.writeFile(new MyFile().selectDestinationPath()+"\\"+name+".docx");


//        wr= new WordReader(mf.select("docx").getAbsolutePath());
        wr= new WordReader("C:\\Users\\emili\\Documents\\huella.docx");
        String[] a= wr.readFile();
        
        PdfWriter pdfw= new PdfWriter();
        //String name= m.enterInfo(Message.QUESTION, "Enter filename", "Filename");
        String name= "PruebaEsta";
//        pdfw.writeFile(new MyFile().selectDestinationPath()+"\\"+name+".pdf");
        pdfw.writeFile("C:\\Users\\emili\\Documents\\"+name+".pdf", a);
            
    }
    
}
