package Main;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("numero de filas: ");
        int filas = scanner.nextInt();
        System.out.println("numero de columnas: ");
        int columnas = scanner.nextInt();


        Matriz matrizConHilos = new Matriz(filas, columnas);
        Matriz matrizSinHilos = new Matriz(filas, columnas);
        
        Matriz matrizMultiplicado = new Matriz(filas, columnas);

        matrizConHilos.mostrarMatrizBidimencional();
        matrizMultiplicado.mostrarMatrizBidimencional();
        
        Matriz resultadoConHilos =  matrizConHilos.multiplicarMatrizConHilos(matrizMultiplicado);
        resultadoConHilos.mostrarMatrizBidimencional();
        
        Matriz resultadoSinHilos = matrizSinHilos.multiplicarMatrizSinHilos(matrizMultiplicado);
        resultadoSinHilos.mostrarMatrizBidimencional();


        
        System.out.println("Tiempos:");
        System.out.println("Con hilos: "+matrizConHilos.getTiempoConHilo());
        System.out.println("Sin hilos: "+matrizSinHilos.getTiempoSinHilo());
    }

}
