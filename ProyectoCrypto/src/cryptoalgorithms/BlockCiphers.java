package cryptoalgorithms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import tools.Message;
import tools.MyFile;

public class BlockCiphers {
    private String operationMode;
    private String padding;
    private String pathToSave, blockCipher, fileExtension;
    private Message msg;
    private int bits, ivSize;
    
    private SecureRandom sr;
    private IvParameterSpec ivps;
    private ObjectOutputStream oos;
    private SecretKey key;
    private DESedeKeySpec desks;
    private SecretKeySpec sks;
    private CipherInputStream cis;
    
    private File kf, ivf;
    
    public BlockCiphers(String om, String padding, String Blowfish_or_DESede){ //Constructor for 3DES and Blowfish
        msg= new Message();
        operationMode= om;
        this.padding= padding;
        blockCipher= Blowfish_or_DESede;
        if(Blowfish_or_DESede.equals("DESede"))
            fileExtension="3des";
        else
            fileExtension="bf";
    }
    
    public BlockCiphers(String om, String padding, int bits){ //Constructor for AES
        msg= new Message();
        operationMode= om;
        this.padding= padding;
        this.bits= bits;
        blockCipher= "AES";
        fileExtension= "aes";
    }
    
    //Method called when encrypt operation is required, it makes an init vector
    private void makeInitVector(){
        try{
            if(blockCipher.equals("AES"))
                ivSize= 16;
            else
                ivSize= 8;
        
            byte[] iv= new byte[ivSize];
            sr= new SecureRandom();
            sr.nextBytes(iv);
            ivps= new IvParameterSpec(iv);
            oos = new ObjectOutputStream( new FileOutputStream(pathToSave+"\\iv."+fileExtension)); 
            oos.writeObject(ivps.getIV()); 
            oos.flush();
        }catch(IOException ex){
            msg.showMessage(Message.ERROR, "Failed making Initialization Vector");
        }
    }
    
    //Method called when decrypt operation is required, it loads an init vector file
    private void makeInitVector(File cipherFile){
        try{
            ObjectInputStream oisIv = new ObjectInputStream( new FileInputStream(cipherFile)); 
            ivps= new IvParameterSpec((byte[]) oisIv.readObject());
        }catch(Exception ex){
            msg.showMessage(Message.ERROR, "Error loading IV file.\n"+ex);
        }
    }
    
    //Method called when cipher operation is required, it makes an secret key
    private void makeSecretKey(){
        try{
            KeyGenerator kg = KeyGenerator.getInstance(blockCipher); 
            if(blockCipher.equals("AES"))
                kg.init(bits, new SecureRandom()); 
            else
                kg.init(new SecureRandom()); 
            key = kg.generateKey();
            if(blockCipher.equals("DESede")){
                SecretKeyFactory skf = SecretKeyFactory.getInstance(blockCipher); 
                Class spec = Class.forName("javax.crypto.spec.DESedeKeySpec"); 
                desks = (DESedeKeySpec) skf.getKeySpec(key, spec);
            }
            else{
                byte[] encFormat= key.getEncoded();
                sks= new SecretKeySpec(encFormat, blockCipher);
            }
            
            //Write the key
            oos = new ObjectOutputStream( new FileOutputStream(pathToSave+"\\keyfile."+fileExtension)); 
            if(blockCipher.equals("DESede"))
                oos.writeObject(desks.getKey()); 
            else
                oos.writeObject(sks); 
            oos.flush();
        }catch(IOException | NoSuchAlgorithmException | ClassNotFoundException | InvalidKeySpecException ex){
            msg.showMessage(Message.ERROR, "Failed making secret key.\n"+ex);
        }
    }
    
    //Method called when decrypt operation is required, it loads a secret key file
    private void makeSecretKey(File keyFile){
        try{
            ObjectInputStream ois = new ObjectInputStream( new FileInputStream(keyFile));
            if(blockCipher.equals("DESede")){
                DESedeKeySpec ks = new DESedeKeySpec((byte[]) ois.readObject()); 
                SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede"); //It'll be a DES ciphered message 
                key = skf.generateSecret(ks);
            }
            else
                sks= (SecretKeySpec) ois.readObject();
        }catch(Exception ex){
            msg.showMessage(Message.ERROR, "Error loading key file.\n"+ex);
        }
    }
    
    
    public void cipher(File mensaje){
        makeInitVector();
        makeSecretKey();
        try{
            //Prepare cipher and output stream
            Cipher c = Cipher.getInstance(blockCipher+"/"+operationMode+"/"+padding); 
            c.init(Cipher.ENCRYPT_MODE, key, ivps); 
            CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(pathToSave+"\\ciphertext."+fileExtension), c); //Associate a file whose content will be ciphered previously
            
            BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(cos));
            FileInputStream fis= new FileInputStream(mensaje);
            copy(fis, cos);
            bw.close(); 
            oos.close(); 
            msg.showMessage(Message.INFO, "Files created at "+pathToSave, blockCipher);
        }catch(Exception ex){
            msg.showMessage(Message.ERROR, "Error in ciphering process:\n"+ex);
        }
    }
    
    public void decipher(File cipherFile){
        makeInitVector(ivf);
        makeSecretKey(kf);
        try{
            Cipher c = Cipher.getInstance(blockCipher+"/"+operationMode+"/"+padding); 
            if(blockCipher.equals("DESede"))
                c.init(Cipher.DECRYPT_MODE, key, ivps);
            else
                c.init(Cipher.DECRYPT_MODE, sks, ivps);
            cis = new CipherInputStream( new FileInputStream(cipherFile), c); //Associate a file whose content will be ciphered previously
            byte[] b= new byte[8];
            cis.read(b, 0, b.length-8);
            
            writePlaintext();
        }catch(Exception ex){
            msg.showMessage(Message.ERROR, "Error in deciphering process.\n"+ex);
        }
    }
    
    
    
    private void writePlaintext(){
        try{
            FileOutputStream os = new FileOutputStream(pathToSave+"\\plaintext."+fileExtension);
            
            copy(cis, os);

            os.flush();
            os.close();
            msg.showMessage(Message.INFO, "Plaintext created at "+pathToSave, blockCipher);
            
        }catch(Exception ex){
            msg.showMessage(Message.ERROR, "Error making the plaintext.\n"+ex);
            ex.printStackTrace();
        }
    }
    
    //Necessary to specify where files will be located, call before encrypt or decrypt
    public void setDestinationPath(String path){
        pathToSave= path;
    }
    
    public String getDestinationPath(){
        return pathToSave;
    }
    
    //This method load a key file when user wants to decrypt
    public void loadKeyFile(String path){
        kf= new File(path);
    }
    
    //This method load an initialization vector file when user wants to decrypt
    public void loadIvFile(String path){
        ivf= new File(path);
    }
    
    //Method which copies content from InputStream to an OutputStream
    private void copy(InputStream is, OutputStream os) throws IOException {
        int i;
        byte[] b = new byte[1024];
        while ((i = is.read(b)) != -1) {
            os.write(b, 0, i);
        }
    }
    
}
