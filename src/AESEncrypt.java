import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;


public class AESEncrypt {

   public static void main(String[] args) throws Exception {
      // Generamos una clave de 128 bits adecuada para AES
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(128);
      Key key = keyGenerator.generateKey();
      
      // Alternativamente, una clave que queramos que tenga al menos 16 bytes
      // y nos quedamos con los bytes 0 a 15
      key = new SecretKeySpec("111111111111111111".getBytes(),  0, 16, "AES");
      //pasamos la clave a byte
      byte[] bkey=key.getEncoded();
      //pasamos la clave a  string
      String str1 = new String(bkey);
      //creamos un objeto FileWrite pasandole la ruta y el nombre 
      FileWriter archivo = null;
      //archivo = new FileWriter("clave.txt",true);
      archivo = new FileWriter("C:\\Users\\Tomas Salgado\\Documents\\seguridad\\clave.txt");
      //insertamos el string que contiene la clave
      archivo.write(str1);
      //cerramos el fichero
      archivo.close();
      
      // Texto a encriptar
      String texto = "Este es el texto que queremos encriptar";
		
      // Se obtiene un cifrador AES
      Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");

      // Se inicializa para encriptacion y se encripta el texto,
      // que debemos pasar como bytes.
      aes.init(Cipher.ENCRYPT_MODE, key);
      byte[] encriptado = aes.doFinal(texto.getBytes());

      // Se escribe byte a byte en hexadecimal el texto
      // encriptado para ver su pinta.
      System.out.print("Texto encriptado:");
      for (byte b : encriptado) {
         System.out.print(Integer.toHexString(0xFF & b));
      }
        
      //creamos un objeto FileReader para la lecctura del archivo
      FileReader fr = new FileReader ("C:\\Users\\Tomas Salgado\\Documents\\seguridad\\clave.txt");
      //creamos un objeto bufferedReader que ira leyendo los datos
      BufferedReader br = new BufferedReader(fr);
      String linea;
      linea = br.readLine();
      System.out.println("\nllave:"+linea);
      byte[] b = linea.getBytes();
      Key alKey = new SecretKeySpec(b, 0, b.length, "AES"); 

         
         
      // Se iniciliza el cifrador para desencriptar, con la
      // misma clave y se desencripta. 
      aes.init(Cipher.DECRYPT_MODE,alKey);
      byte[] desencriptado = aes.doFinal(encriptado);

      // Texto obtenido, igual al original.
      System.out.println("Texto desencriptado:"+new String(desencriptado));
   }


}
