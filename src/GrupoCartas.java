public class GrupoCartas {

    private int contador;
    private int[] indexCartas;
    private int index;

    public GrupoCartas(int numeroCartas){
        contador = 0;
        index = 0;
        indexCartas = new int[numeroCartas];
        inicializarIndexCartas();
    }

    // se llena el arreglo con -1 para evitar tener problemas con la carta de índice 0
    private void inicializarIndexCartas(){
        for (int i = 0; i < indexCartas.length; i++) 
            indexCartas[i] = -1;
    }

    public void incrementarContador(){
        contador++;
    }

    public int getContador() {
        return contador;
    }

    public void agregarIndexCarta(int indexCarta){
        indexCartas[index] = indexCarta;
        index++;
    }

    public int[] getIndexes(){
        return indexCartas;
    }

    public int getMaxIndex(){
        return index;
    }
}
