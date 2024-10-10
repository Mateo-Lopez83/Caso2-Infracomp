import java.lang.Math;
public class Op1 {
    static String[] colores = {"R","G","B"};
    static int numPaginaImagen = 0;
    static int numPaginaMensaje;
    public void ejecutar_Opcion(int tamanioPag, String rutaImagen){
        /*Imagen image = new Imagen(rutaImagen);
        int numFilas = image.alto;
        int numCols = image.ancho;
        int lengMensaje = image.leerLongitud();*/
        double numFilas = 256;
        double numCols = 384;
        int lengMensaje = 5069;
        //16 referencias de lectura de los primeros 16 bytes,
        //luego 2 referencias para lectura y escritura de cada bit de caracter, 
        //cada caracter son 8 bits, y la inicialización de cada elemento de Mensaje[i]
        // lleva a 16 + (2*8)lengMensaje + lengMensaje = 16+17*lengMensaje
        int numRefs = 16+17*lengMensaje;

        int pagsImagen = (int) Math.ceil((numFilas * numCols * 3) / (double)tamanioPag);
        numPaginaMensaje = pagsImagen;
        int pagsMensaje = (int) Math.ceil((double)lengMensaje / (double)tamanioPag);
        System.out.println("numero de referencias: "+numRefs);
        System.out.println("numero de paginas totales: "+(pagsImagen+pagsMensaje));
        for (int i=0; i<16; i++){
            String color = colores[i%3];

        }
    
        
    }
}
