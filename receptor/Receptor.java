
import java.io.FileInputStream;
import java.io.FileNotFoundException;



/**
 *
 * @author Tomas Salgado
 */
public class Receptor {

    public static void main(String[] args) throws FileNotFoundException{
        try{
            //FileReader leemos caracteres y FileInputStream lee bytes.
            FileInputStream fis=new FileInputStream("hola.txt");

			int valor=fis.read();
			while(valor!=-1){
				System.out.print((char)valor);
				valor=fis.read();
                        }
        }catch(Exception e){
            
        }
    }
}
