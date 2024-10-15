import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Op2 {
    private int cantPaginas;
    private int cantFrames;
    private int[] paginas;
    private int[][] matriz;
    private int[] fallos;
    private int[] distancia;
    private int hits;
    private int fallas;
    private double tiempoAcessRAM; 
    private double tiempoAcessSwap; 
    private double totalTiempo;
    
    
    public Op2() {
        this.tiempoAcessRAM = 5;
        this.tiempoAcessSwap = 10;
        this.hits = 0;
        this.fallas = 0;
        this.totalTiempo = 0;
    }

    public void ejecutar_Opcion(int cantFrames, String rutaArchivo) throws IOException {
        this.cantFrames = cantFrames;

        // Leer el archivo de referencias
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            List<Integer> listaPaginas = new ArrayList<>(); 
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Imagen") || linea.startsWith("Mensaje")) {
                    break; // Nos detenemos cuando encontramos la primera referencia válida
                }
            }

            while (linea != null) {
                // Procesar línea de referencia
                String[] partes = linea.split(",");
                int paginaVirtual = Integer.parseInt(partes[1]); // Página virtual correspondiente
                listaPaginas.add(paginaVirtual);// Almacenar la página virtual
                linea = br.readLine();
            }
            paginas = new int[listaPaginas.size()];
            for (int i = 0; i < listaPaginas.size(); i++) {
                paginas[i] = listaPaginas.get(i);
            }

            // Actualizar la cantidad de páginas
            cantPaginas = paginas.length;
            lru(); 
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de referencias: " + e.getMessage());
        }
    }

    public void setPaginas(int[] paginas) {
        this.paginas = paginas;
    }

    public void setCantidadPaginas(int cantPaginas) {
        this.cantPaginas = cantPaginas;
    }

    public void setCantidadFrames(int cantFrames) {
        this.cantFrames = cantFrames;
    }

    public void iniciarFallos() {
        for (int i = 0; i < cantPaginas; i++) {
            fallos[i] = 0;
        }
    }

    private void iniciarMatriz() {
        for (int i = 0; i < cantFrames; i++) {
            for (int j = 0; j < cantPaginas; j++) {
                matriz[i][j] = -1;
            }
        }
    }

    private boolean buscar(int pagActual) {
        boolean encontrado = false;
        for (int i = 0; i < cantFrames; i++) {
            if (matriz[i][0] == paginas[pagActual]) {
                encontrado = true;
                hits++;
                totalTiempo += tiempoAcessRAM;
            }
        }
        return encontrado;
    }

    private void llenarFila(int pagActual, int frame) {
        matriz[frame][pagActual] = paginas[pagActual];
    }

    private int mayorDistancia(int pagActual) {
        int mayorDist = 0;
        for (int i = 0; i < cantFrames; i++) {
            for (int j = pagActual; j >= 0; j--) {
                if (matriz[i][pagActual] == paginas[j]) {
                    distancia[i] = pagActual - j;
                    break;
                }
            }
        }
        for (int i = 0; i < cantFrames; i++) {
            if (distancia[i] > distancia[mayorDist]) {
                mayorDist = i;
            }
        }
        return mayorDist;
    }

    private void modificar(int pagActual) {
        boolean encontradoFrameLibre = false;
        int i;
        for (i = 0; i < cantFrames; i++) {
            if (matriz[i][pagActual] == -1) {
                encontradoFrameLibre = true;
                break;
            }
        }

        if (!encontradoFrameLibre) {
            llenarFila(pagActual, mayorDistancia(pagActual));
        } else {
            llenarFila(pagActual, i);
        }

        fallos[pagActual] = 1;
        fallas ++;
        totalTiempo += tiempoAcessSwap;
    }

    public void lru() {
        matriz = new int[cantFrames][cantPaginas];
        fallos = new int[cantPaginas];
        distancia = new int[cantFrames];
        iniciarFallos();
        iniciarMatriz();

        for (int j = 0; j < cantPaginas; j++) {
            if (!buscar(j)) {
                modificar(j);
            }
        }
        Resultados();
    }

    public void Resultados() {
        int cantFallos = 0;
        for (int i = 0; i < cantFrames; i++) {
            for (int j = 0; j < cantPaginas; j++) {
                if (matriz[i][j] == -1) {
                    System.out.print(" "); // para que no se muestre el -1 en la matriz
                } else {
                    System.out.print("" + matriz[i][j]);
                }
            }
            System.out.println();
        }

        for (int i = 0; i < cantPaginas; i++) {
            if (fallos[i] == 1) {
                cantFallos++;
            }
            System.out.print("" + fallos[i]);
        }

        System.out.println("\n\nFallos encontrados: " + cantFallos);
        System.out.println("Hits: " + hits);
        System.out.println("Porcentaje de Hits: " + ((double) hits / cantPaginas) * 100 + "%");
        System.out.println("Porcentaje de Fallos: " + ((double) cantFallos / cantPaginas) * 100 + "%");
        System.out.println("Tiempo total de acceso (ms): " + totalTiempo);
    }     

}
