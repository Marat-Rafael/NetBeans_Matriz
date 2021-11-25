package Main;

public class Resultado extends Thread {

    //atributos
    private Matriz matriz1;
    private Matriz matriz2;
    private int fila;
    private int columna;
    private int resultado;
    // Matriz matrizResultado = new Matriz(fila, columna);

    //constructor
    public Resultado(Matriz matriz1, Matriz matriz2, int Nfila, int Ncolumna) {
        this.matriz1 = matriz1;
        this.matriz2 = matriz2;
        this.fila=Nfila;
        this.columna = Ncolumna;
    }

    // setter y getter
    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    //
    @Override
    public void run() {
        for (int k = 0; k < matriz1.getMatriz()[0].length; k++) {
            resultado += matriz1.getMatriz()[fila][k] * matriz2.getMatriz()[k][columna];
        }
    }
}
