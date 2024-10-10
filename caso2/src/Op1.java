import java.lang.Math;
import java.io.FileWriter;
import java.io.IOException;



public class Op1 {
    static String[] colores = {"R","G","B"};
    static int numPaginaImagen = 0;
    static int numPaginaMensaje;
    static int numDespImagen = 0;
    static int numDespMensaje = 0;
    static int vector1Img = 0;
    static int vector2Img = 0;
    static int contadorPixel = 0;
    static int contadorCaracteres=0;


    public void ejecutar_Opcion(int tamanioPag, String rutaImagen) throws IOException{
        Imagen image = new Imagen(rutaImagen);
        double numFilas = (double)image.alto;
        double numCols = (double)image.ancho;
        int lengMensaje = image.leerLongitud();
        //16 referencias de lectura de los primeros 16 bytes,
        //luego 2 referencias para lectura y escritura de cada bit de caracter, 
        //cada caracter son 8 bits, y la inicialización de cada elemento de Mensaje[i]
        // lleva a 16 + (2*8)lengMensaje + lengMensaje = 16+17*lengMensaje
        int numRefs = 16+17*lengMensaje;
        String archnombre = "Opc1_"+ String.valueOf(lengMensaje)+".txt";

        FileWriter archivo = new FileWriter(archnombre);

        archivo.write("P="+String.valueOf(tamanioPag)+"\n");
        archivo.write("NF="+String.valueOf(numFilas)+"\n");
        archivo.write("NC="+String.valueOf(numCols)+"\n");
        archivo.write("NR="+String.valueOf(numRefs)+"\n");
        int pagsImagen = (int) Math.ceil((numFilas * numCols * 3) / (double)tamanioPag);
        numPaginaMensaje = pagsImagen;
        int pagsMensaje = (int) Math.ceil((double)lengMensaje / (double)tamanioPag);

        archivo.write("NP="+String.valueOf(pagsImagen+pagsMensaje)+"\n");
        for (int i=0; i<16; i++){
            String color = colores[i%3];
            archivo.write("Imagen["+String.valueOf(vector1Img)+"]["+String.valueOf(vector2Img)+"]."+color+","+String.valueOf(numPaginaImagen)+","+String.valueOf(numDespImagen)+",R\n");
            contadorPixel++;
            if (contadorPixel==3){
                contadorPixel=0;
                vector2Img++;
            }
            if (vector2Img==numCols){
                vector1Img++;
                vector2Img=0;
            }
            numDespImagen++;
            if (numDespImagen == tamanioPag){
                numPaginaImagen++;
                numDespImagen=0;
            }
            //Fin de lectura de tamaño de archivo
        }
        //Siempre la lectura de leng va a terminar en .R,
        //El siguiente va a ser .G
        int referenciaColor = 1;
        int numBitsCaracter = 0;
        while (contadorCaracteres<lengMensaje){
            String color = colores[referenciaColor%3];
            if (numBitsCaracter==0) 
                archivo.write("Mensaje["+String.valueOf(contadorCaracteres)+"],"+String.valueOf(numPaginaMensaje)+","+String.valueOf(numDespMensaje)+",W\n");
            archivo.write("Imagen["+String.valueOf(vector1Img)+"]["+String.valueOf(vector2Img)+"]."+color+","+String.valueOf(numPaginaImagen)+","+String.valueOf(numDespImagen)+",R\n");
            contadorPixel++;
            if (contadorPixel==3){
                contadorPixel=0;
                vector2Img++;
            }
            if (vector2Img==numCols){
                vector1Img++;
                vector2Img=0;
            }
            numDespImagen++;
            if (numDespImagen == tamanioPag){
                numPaginaImagen++;
                numDespImagen=0;
            }
            archivo.write("Mensaje["+String.valueOf(contadorCaracteres)+"],"+String.valueOf(numPaginaMensaje)+","+String.valueOf(numDespMensaje)+",W\n");
            numBitsCaracter++;
            if (numBitsCaracter==8){
                numBitsCaracter=0;
                contadorCaracteres++;
                numDespMensaje++;
                if (numDespMensaje==tamanioPag){
                    numDespMensaje=0;
                    numPaginaMensaje++;
                }
            }
        }
        System.out.println("Archivo creado exitosamente");
        archivo.close();
    
        
    }
}
