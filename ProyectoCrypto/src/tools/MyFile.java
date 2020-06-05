package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyFile implements Serializable{
    private BufferedReader br;
    private BufferedWriter bw;
    private JFileChooser jfchooser;
    private FileNameExtensionFilter filtroTXT;
    private File fileSelected;
    private String path;
    
    private String rutaGenerica;
    
    public MyFile(String ruta){
        rutaGenerica= ruta;
    }
    
    public MyFile(){
        
    }
    
    public void writeFile(File archivo, String valor){
        try {
            bw= new BufferedWriter(new FileWriter(archivo, true));
            bw.write(valor);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeFile(File archivo, String[] valores){
        try {
            bw= new BufferedWriter(new FileWriter(archivo, true));
            for(int i=0; i<valores.length; i++){
                bw.write(valores[i]);
                bw.newLine();
            }
                
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeFile(File archivo, ArrayList<String> valores){
        try {
            bw= new BufferedWriter(new FileWriter(archivo, true));
            for(int i=0; i<valores.size(); i++){
                bw.write(valores.get(i));
                bw.newLine();
            }
                
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeFile(String ruta, String valor){
        try {
            bw= new BufferedWriter(new FileWriter(ruta, true));
            bw.write(valor);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeFile(String ruta, String[] valores){
        try {
            bw= new BufferedWriter(new FileWriter(ruta, true));
            for(int i=0; i<valores.length; i++){
                bw.write(valores[i]);
                bw.newLine();
            }
                
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeFile(String ruta, ArrayList<String> valores){
        try {
            bw= new BufferedWriter(new FileWriter(ruta, true));
            for(int i=0; i<valores.size(); i++){
                bw.write(valores.get(i));
                bw.newLine();
            }
                
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String readFile(File archivo){
        StringBuilder ret= new StringBuilder("");
        try{
            br= new BufferedReader(new FileReader(archivo));
            
            String str;
            while((str= br.readLine()) != null)
                ret.append(str).append("\n");
            
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ret.toString();
    }
    
    public String readFile(String path){
        StringBuilder ret= new StringBuilder("");
        try{
            br= new BufferedReader(new FileReader(path));
            
            String str;
            while((str= br.readLine()) != null)
                ret.append(str).append("\n");
            
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ret.toString();
    }
    
    
    public File select(String filter){
        filtroTXT= new FileNameExtensionFilter("Specific file (."+filter+")", filter);
        jfchooser= new JFileChooser();
//        try{
            jfchooser.setFileSelectionMode(JFileChooser.FILES_ONLY); //¿Qué se puede seleccionar?
            jfchooser.setFileFilter(filtroTXT);
            jfchooser.showOpenDialog(jfchooser);
            fileSelected= jfchooser.getSelectedFile();
//        } catch (IOException ex) {
//            Logger.getLogger(MyFile.class.getName()).log(Level.SEVERE, null, ex);
//        }

        return fileSelected;
    }
    
    public String selectPath(String filter){
        filtroTXT= new FileNameExtensionFilter("Specific file (."+filter+")", filter);
        jfchooser= new JFileChooser();
//        try{
            jfchooser.setFileSelectionMode(JFileChooser.FILES_ONLY); //¿Qué se puede seleccionar?
            jfchooser.setFileFilter(filtroTXT);
            jfchooser.showOpenDialog(jfchooser);
            path= jfchooser.getSelectedFile().getPath();
//        } catch (IOException ex) {
//            Logger.getLogger(MyFile.class.getName()).log(Level.SEVERE, null, ex);
//        }

        return path;
    }
    
    public String selectDestinationPath(){
//        filtroTXT= new FileNameExtensionFilter("Text file (.txt)", "txt");
        jfchooser= new JFileChooser();
//        try{
            jfchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //¿Qué se puede seleccionar?
//            jfchooser.setFileFilter(filtroTXT);
            jfchooser.showOpenDialog(jfchooser);
            path= jfchooser.getSelectedFile().getPath();
//        } catch (IOException ex) {
//            Logger.getLogger(MyFile.class.getName()).log(Level.SEVERE, null, ex);
//        }

        return path;
    }
    
}
