
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;



/**
 *
 * @author Tomas Salgado
 */
public class Receptor {
    private static Cipher rsa;

    public static void main(String[] args) throws FileNotFoundException{
        File inFile       = new File("hola.txt");//archivo cifrado
        File keyStoreFile = new File("almacenDeLLaves.jks");//almacen de llaves
        String password   = ("seguridad123");//storepass

        try{
            
            
            byte [] encriptado = new byte[1000];
            FileInputStream input = new FileInputStream("hola.txt");
            input.read(encriptado);
            input.close();

            for(int i = 0 ; i<=13 ; i++){
                System.out.print(encriptado[i]);
            }
           
           

            
    
        }catch(Exception e){
            
        }
        
       

        
        

            
    }
}
