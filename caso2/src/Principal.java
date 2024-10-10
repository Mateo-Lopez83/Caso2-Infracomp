import java.io.BufferedReader;
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
            System.out.println("3. Salir");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String resp = reader.readLine();
            if (resp.equals("1")) {
                System.out.print("Ingrese el tamaño de cada página (en bytes): ");
                int tamanio = Integer.parseInt(reader.readLine());

                System.out.print("Ingrese la ruta de la imagen: ");
                String ruta = reader.readLine(); 
                ruta = "image/"+ruta;
                opcion1.ejecutar_Opcion(tamanio, ruta);
            }
            else if (resp.equals("2"))  {
                System.out.print("Ingrese el número de marcos de página: ");
                int marcos = Integer.parseInt(reader.readLine());
                System.out.print("Ingrese el nombre del archivo de referencias: ");
                String ruta = reader.readLine(); 
                opcion2.ejecutar_Opcion(marcos, ruta);
            }
            else if (resp.equals("3"))  runtime=false;
        }
        
        
    }
}
