/* Este programa encripta el archivo dado como argumento de la línea de comandos 
  * y el texto cifrado es guardado en el archivo dado como segundo argumento de línea de comandos.
  *
  * No podemos utilizar el cifrado de clave pública para archivos de gran tamaño, por lo tanto utilizamos AES
  * La clave AES se cifra con una clave pública RSA y se escribe al inicio 
  * Del archivo de salida. Por lo tanto, cualquier persona con la clave privada RSA puede
  * Descifrar la clave AES y luego leer el archivo.
  *
  */

// java.io.File gestiona la lectura y escritura de archivos.
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

// java.security.* se usa para gestionar llaves
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

// el paquete javax.crypto realiza la encriptacion y desencriptacion
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class EncryptWithKeyStore {

    public static void main (String[] args) {

      // leer archivos y contraseña desde la linea de comandos
      File inFile       = new File("clave.txt");
      File outFile      = new File("hola.txt");
      File keyStoreFile = new File("almacenDeLLaves.jks");
      String password   = ("seguridad123");//storepass

      try {
	System.out.println("Abriendo Archivo a leer: "+inFile);
	FileInputStream rawDataFromFile = new FileInputStream(inFile);
	byte[] plainText = new byte[(int)inFile.length()];

	System.out.println("Leyendo Datos");
	rawDataFromFile.read(plainText);

	// Generar una llave simetrica para encriptar datos
	KeyGenerator sKenGen = KeyGenerator.getInstance("AES");
	Key aesKey = sKenGen.generateKey();

	// Inicializar el Objeto Cipher AES 
	Cipher aesCipher = Cipher.getInstance("AES");
	aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);

        // Realiza la encriptacion			
	byte[] cipherText = aesCipher.doFinal(plainText);

	// Carga el keystore
	KeyStore myKeyStore = KeyStore.getInstance("JKS");
	FileInputStream inStream = new FileInputStream(keyStoreFile);
	myKeyStore.load(inStream, password.toCharArray());

        // Lee las llaves privada y publica del keystore.
	Certificate cert = myKeyStore.getCertificate("millave");//le pasamos nuestro alias de nuestro keystore
	PublicKey publicKey = cert.getPublicKey();
	@SuppressWarnings("unused")
	PrivateKey privatekey = (PrivateKey) myKeyStore.getKey("mykey", "password".toCharArray()); 
	
        // Inicializa el Objeto Cipher RSA
	Cipher rsaCipher = Cipher.getInstance("RSA");
	rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);

	// Encriptar llave simetrica AES con la llave publica RSA
	byte[] encodedKey = rsaCipher.doFinal(aesKey.getEncoded());

	System.out.println("Abriendo archivo a escribir: "+outFile);
	FileOutputStream outToFile = new FileOutputStream(outFile);

	System.out.println("Escribiendo Datos");
	//Escribir llave AES encriptada al archivo.
	outToFile.write(encodedKey);
	// Escribir el texto plano encriptado al archivo.
	outToFile.write(cipherText);
	System.out.println("Cerrando Archivos");
	rawDataFromFile.close();
	outToFile.close();
      }
      catch (Exception e) { 
        // Este codigo se ejecuta si hay un error en el archivo, un error de codificación, etc.
	System.out.println("Doh: "+e); }
    }


}
