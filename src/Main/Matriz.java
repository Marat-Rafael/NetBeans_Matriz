package Main;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Matriz extends Thread {

    private int[][] matriz;
    private int min = 0; // valor minimo de un elemento
    private int max = 9; // valor maximo de un elemento
    private int NumFilas; // numero de filas del matriz
    private int NumColumnas; // numero de columnas de la matriz
    ArrayList<Resultado> listaHilos;

    private long inicioSinHilo;
    private long inicioConHilo;
    private long finSinHilo;
    private long finConHilo;
    public long tiempoSinHilo;
    public long tiempoConHilo;

    public long getTiempoSinHilo() {
        return tiempoSinHilo;
    }

    public long getTiempoConHilo() {
        return tiempoConHilo;
    }

    public Matriz(int NumFilas, int NumColumnas) {
        this.listaHilos = new ArrayList<Resultado>();
        this.matriz = generarNuevoMatriz(NumFilas, NumColumnas, min, max);
    }

    public Matriz(int[][] m) {
        this.listaHilos = new ArrayList<Resultado>();
        this.matriz = m;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    /**
     * metodo para rellenar un matriz con numeros random de min a max
     *
     * @return
     */
    protected Matriz llenarMatrizConNumerosRandom() {
        Random random = new Random();
        for (int i = 0; i < this.matriz.length; i++) {
            for (int j = 0; j < this.matriz[0].length; j++) {
                this.matriz[i][j] = generarNumeroRandom(min, max);
            }
        }
        return null;
    }

    /**
     * metodo para mostrar matriz bidimencional
     */
    protected void mostrarMatrizBidimencional() {
        System.out.println("-----mostrando matriz-----");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println(" ");
        }
        System.out.println("--------------------------");
    }

    /**
     *
     * @param filas
     * @param columnas
     * @param min
     * @param max
     * @return
     */
    private int[][] generarNuevoMatriz(int filas, int columnas, int min, int max) {
        int[][] matrizX = new int[filas][columnas]; // declaramos matriz
        for (int i = 0; i < matrizX.length; i++) {
            for (int j = 0; j < matrizX.length; j++) {
                matrizX[i][j] = generarNumeroRandom(min, max);
            }
        }
        return matrizX;
    }

    private int generarNumeroRandom(int min, int max) {
        Random random = new Random(); // creamos objeto random
        int randomNum = random.nextInt((max - min) + 1) + min; // generamos un numero
        return randomNum;
    }

    /**
     * metodo para multiplicar dos matrizes
     *
     * @param matriz un objeto Matriz
     * @return
     */
    public Matriz multiplicarMatrizConHilos(Matriz matriz) {

        // empezamos medir tiempo
        inicioConHilo = System.currentTimeMillis();

        // sacamos matriz del objeto Matriz que pasamos como parametro
        int[][] matriz2 = matriz.getMatriz();

        // si matrizes no son compatibles devolvemos null
        if (this.matriz.length != matriz2[0].length) {
            System.out.println("numero de filas de matriz 1 debe ser igual a numero de columnas de matriz 2");
            return null;
        }

        int filaPrimeraMatriz = this.matriz.length;
        int columnaSegundaMatriz = matriz2[0].length;

        int[][] matrizResultado = new int[filaPrimeraMatriz][columnaSegundaMatriz];

        for (int fila = 0; fila < filaPrimeraMatriz; fila++) { // bucle por filas
            for (int columna = 0; columna < columnaSegundaMatriz; columna++) {    // bucle por columnas

                int producto = 0;// declaramos variable producto

                Resultado resultado = new Resultado(this, matriz, fila, columna);

                resultado.start();
                listaHilos.add(resultado);

//                for (int k = 0; k < matrizResultado.length; k++) { // llenamos matriz resultado
//                    producto += this.matriz[fila][k] * matriz2[k][columna];
                this.run();
                matrizResultado[fila][columna] = producto;
            }

        }
        for (int i = 0; i < listaHilos.size(); i++) {
            matrizResultado[listaHilos.get(i).getFila()][listaHilos.get(i).getColumna()] = listaHilos.get(i).getResultado();
        }
        // medimos tiempo
        finConHilo = System.currentTimeMillis();
        tiempoConHilo = (long) ((finConHilo - inicioConHilo));
        return new Matriz(matrizResultado);
    }

    public Matriz multiplicarMatrizSinHilos(Matriz matriz) {
        // empezamos medir tiempo
        inicioSinHilo = System.currentTimeMillis();

        // sacamos matriz del objeto Matriz que pasamos como parametro
        int[][] matriz2 = matriz.getMatriz();

        // si matrizes no son compatibles devolvemos null
        if (this.matriz.length != matriz2[0].length) {
            System.out.println("numero de filas de matriz 1 debe ser igual a numero de columnas de matriz 2");
            return null;
        }

        int filaPrimeraMatriz = this.matriz.length;
        int columnaSegundaMatriz = matriz2[0].length;

        int[][] matrizResultado = new int[filaPrimeraMatriz][columnaSegundaMatriz];

        for (int fila = 0; fila < filaPrimeraMatriz; fila++) { // bucle por filas
            for (int columna = 0; columna < columnaSegundaMatriz; columna++) {    // bucle por columnas

                int producto = 0;// declaramos variable producto

                for (int k = 0; k < matrizResultado.length; k++) {  // llenamos matriz resultado
                    producto += this.matriz[fila][k] * matriz2[k][columna];
                    matrizResultado[fila][columna] = producto;
                }
            }
        }
        // medimos tiempo
        finSinHilo = System.currentTimeMillis();
        tiempoSinHilo = (long) ((finSinHilo - inicioSinHilo));

        return new Matriz(matrizResultado);
    }

    @Override
    public void run() {
        for (int i = 0; i < listaHilos.size(); i++) {
            try {
                listaHilos.get(i).join();

            } catch (InterruptedException ex) {
                Logger.getLogger(Matriz.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
