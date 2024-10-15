import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Principal {
    static Op1 opcion1 = new Op1();
    static Op2 opcion2 = new Op2();
    static boolean runtime= true;
    public static void main(String[] args) throws Exception {
        while (runtime){
            System.out.println("Bienvenido al menú principal del caso 2. Selecciona una de las siguientes opciones:");
            System.out.println("1. Opción 1");
            System.out.println("2. Opción 2");
            System.out.println("3. Esconder un mensaje en una imagen");
            System.out.println("4. Salir");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String resp = reader.readLine();
            if (resp.equals("1")) {
                //System.out.println("Directorio de trabajo actual: " + System.getProperty("user.dir"));
                System.out.print("Ingrese el tamaño de cada página (en bytes): ");
                int tamanio = Integer.parseInt(reader.readLine());

                System.out.print("Ingrese la ruta de la imagen: ");
                String ruta = reader.readLine(); 
                ruta = "image_modificada/"+ruta;
                opcion1.ejecutar_Opcion(tamanio, ruta);
            }
            else if (resp.equals("2"))  {
                System.out.print("Ingrese el número de marcos de página: ");
                int marcos = Integer.parseInt(reader.readLine());
                System.out.print("Ingrese la ruta del archivo de referencias: ");
                String ruta = reader.readLine(); 
                //ruta = "../"+ruta;
                //opcion2.ejecutar_Opcion(marcos, ruta);
                
                Opc2_2 opcion2 = new Opc2_2(marcos, ruta);
                opcion2.start();

                opcion2.join();

            }
            else if (resp.equals("3")) esconderTextoImagen(reader);
            else if (resp.equals("4"))  runtime=false;
        }
        
        
    }

    public static void esconderTextoImagen(BufferedReader reader) throws IOException{
        System.out.print("Ingrese el nombre de la imagen en formato bmp: ");
        String imagen = reader.readLine();
        System.out.print("Ingrese el nombre del texto en formato .txt: ");
        String texto = reader.readLine(); 
        System.out.print("Ingrese el número de caracteres que desea esconder: ");
        int leng = Integer.parseInt(reader.readLine());
        Imagen img = new Imagen("imagenes_base/"+imagen);
        FileReader fr = new FileReader("textos/"+texto);
        BufferedReader br = new BufferedReader(fr);
        char[] caracteres = new char[leng];
        int numCararcteres = br.read(caracteres, 0, leng);
        if (numCararcteres<leng) System.out.println("El texto tenía menos caracteres, solo se guardaron "+numCararcteres+" caracteres");
        img.esconder(caracteres, numCararcteres);
        System.out.print("Ingrese el nombre de la imagen que desea crear: ");
        String nomImagen = reader.readLine(); 
        img.escribirImagen("image_modificada/"+nomImagen);
        System.out.println("imagen modificada exitosamente");
        br.close();
    }
}
