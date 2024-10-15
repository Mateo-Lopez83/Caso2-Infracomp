import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;


public class Opc2_2 extends Thread{
    public static final int tiempoAcessRAM= 25;
    public static final int tiempoAcessSwap= (int) Math.pow(10,7);
    private static int hits = 0;
    private static int fallas = 0;
    private double totalTiempo;
    private static String ruta;
    private int numMarcos;
    private ArrayList<Integer> marcos = new ArrayList<Integer>();
    BufferedReader br;

    public Opc2_2(int numMarcos, String brA){
        this.numMarcos = numMarcos;
        ruta = brA;
    }
    @Override
    public void run(){
        try{
            br = new BufferedReader(new FileReader(ruta));
        }catch (FileNotFoundException e){
            ruta = "../"+ruta;
            try {
                br = new BufferedReader(new FileReader(ruta));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        try {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Imagen") || linea.startsWith("Mensaje")) {
                    break; // Nos detenemos cuando encontramos la primera referencia válida
                }
            }
            
            while (linea != null){
                
           
                String[] datos = linea.split(",");
                int pagina = Integer.parseInt(datos[1]);
                int posPagina = marcos.indexOf(pagina);
                if (posPagina!=-1){
                    hits++;
                    marcos.remove(posPagina);
                    marcos.add(pagina);

                }
                else{
                    fallas++;
                    if (marcos.size()<numMarcos) marcos.add(pagina);
                    else{
                        marcos.remove(0);
                        marcos.add(pagina);
                    }
                }
                linea = br.readLine();
                Thread.sleep(1);
            }
            br.close();
            System.out.println("Número de hits: "+hits);
            System.err.println("Número de fallas: "+fallas);
            totalTiempo = (tiempoAcessRAM*hits ) + (tiempoAcessSwap*fallas);
            System.out.println("Tiempo total: "+ totalTiempo + " ns");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    

}
