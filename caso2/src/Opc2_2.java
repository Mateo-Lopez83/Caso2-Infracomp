import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;


public class Opc2_2 extends Thread{
    public static final double tiempoAcessRAM= 0.000025;
    public static final int tiempoAcessSwap= 10;
    private static int hits = 0;
    private static int fallas = 0;
    private double totalTiempo;
    private static String ruta;
    private int numMarcos;
    private static boolean corrert2 = true;
    private int id;
    private static Object objetMonitor = new Object();
    private static ArrayList<Pagina> marcos = new ArrayList<Pagina>();
    BufferedReader br;

    public Opc2_2(int numMarcos, String brA, int id){
        this.numMarcos = numMarcos;
        ruta = brA;
        this.id = id;
    }
    @Override
    public void run(){
        if (id==1){
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
                    Pagina pagprueba;
                    String tipo = datos[3];
                    if (tipo.equals("R")) pagprueba= new Pagina(pagina, true, false);
                    else pagprueba = new Pagina(pagina, true, true);
                    //int posPagina;
                    boolean encontrado = false;
                    synchronized(objetMonitor){
                        for (int k=0; k<marcos.size();k++){
                            Pagina p = marcos.get(k);
                            if (p.getNumero()== pagina){
                                encontrado = true;
                                hits++;
                                if (tipo.equals("R")) p.setBit1(true);
                                if (tipo.equals("R")) {
                                    p.setBit1(true);
                                    p.setBit2(true);
                                }
                            }
                        }
                        if (!encontrado && marcos.size()<numMarcos){
                            fallas++;
                            marcos.add(pagprueba);
                        }
                        else if (!encontrado && marcos.size()==numMarcos){
                            fallas++;
                            boolean encontrado2 = false;
                            int posRemov = -1;
                            for (int u=0; u<marcos.size();u++){
                                Pagina pagClas0 = marcos.get(u);
                                if (!pagClas0.isBit1() && !pagClas0.isBit2()){
                                    encontrado2 = true;
                                    posRemov=u;
                                    break;
                                }
                            }
                            if (!encontrado2){
                                for (int u=0; u<marcos.size();u++){
                                    Pagina pagClas0 = marcos.get(u);
                                    if (!pagClas0.isBit1() && pagClas0.isBit2()){
                                        encontrado2 = true;
                                        posRemov=u;
                                        break;
                                    }
                                }
                            }
                            else if (!encontrado2){
                                for (int u=0; u<marcos.size();u++){
                                    Pagina pagClas0 = marcos.get(u);
                                    if (pagClas0.isBit1() && !pagClas0.isBit2()){
                                        encontrado2 = true;
                                        posRemov=u;
                                        break;
                                    }
                                }
                            }
                            else if (!encontrado2){
                                for (int u=0; u<marcos.size();u++){
                                    Pagina pagClas0 = marcos.get(u);
                                    if (pagClas0.isBit1() && pagClas0.isBit2()){
                                        encontrado2 = true;
                                        posRemov=u;
                                        break;
                                    }
                                }
                            }
                            if (posRemov==-1) posRemov = 0;
                            marcos.remove(posRemov);
                            marcos.add(pagprueba);
                        }
                    }
                    linea = br.readLine();
                    Thread.sleep(1);
                    //System.out.println("no muerto th1");
                }
                br.close();
                corrert2 = false;
                System.out.println("Número de hits: "+hits);
                System.out.println("Número de fallas: "+fallas);
                double total = hits+fallas;
                double porcentaje = ((double) hits/total)*100;
                System.out.println("Porcentaje de éxito (hits): "+porcentaje+"%");
                totalTiempo = (tiempoAcessRAM*hits ) + (tiempoAcessSwap*fallas);
                System.out.println("Tiempo total: "+ totalTiempo + " ms");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (id ==2){
            //correr thread 2
            try {
                while (corrert2){
                    synchronized(objetMonitor){
                        for (Pagina pag0:marcos){
                            pag0.setBit1(false);
                        }
                    }
                    Thread.sleep(2);
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        

    }
    

}
class  Pagina {
    private int numero;
    private boolean bit1;
    private boolean bit2;

    public Pagina(int num, boolean bit1, boolean bit2){
        this.numero= num;
        this.bit1= bit1;
        this.bit2= bit2;
    }
    public void setBit1(boolean bit1) {
        this.bit1 = bit1;
    }
    public void setBit2(boolean bit2) {
        this.bit2 = bit2;
    }
    public int getNumero() {
        return numero;
    }
    public boolean isBit1() {
        return bit1;
    }
    public boolean isBit2() {
        return bit2;
    }
}
