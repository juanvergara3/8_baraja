import java.util.Random;

import javax.swing.JPanel;

import enums.NombreCarta;
import enums.Pinta;
import enums.Grupo;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int MARGEN = 10;
    private final int DISTANCIA = 40;

    private Random r = new Random();
    private Carta[] cartas = new Carta[TOTAL_CARTAS];

    public void repartirCartas() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int posX = MARGEN + (DISTANCIA * (TOTAL_CARTAS - 1));
        for (Carta crt: cartas) {
            crt.mostrar(pnl, posX, MARGEN);
            posX -= DISTANCIA;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String respuesta = "No se han encontrado grupos.";

        // se cambia totalmente este arreglo para usar una clase nueva, la cual tiene un contador y un arreglo de índices de cartas, para poder marcar las cartas que forman parte del grupo como no punteables
        GrupoCartas[] contadores = new GrupoCartas[NombreCarta.values().length];
        // se inicializan los contadores
        for (int i = 0; i < contadores.length; i++) {
            contadores[i] = new GrupoCartas(TOTAL_CARTAS);
        }

        boolean hayGrupos = false;
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            Carta crt = cartas[i];
            int posicion = crt.getNombre().ordinal();
            contadores[posicion].incrementarContador();
            contadores[posicion].agregarIndexCarta(i);

            if (!hayGrupos && contadores[posicion].getContador() >= 2) 
                hayGrupos = true;
        }

        if (hayGrupos){
            respuesta = "Se han encontrado los siguientes grupos:\n";
            int index = 0;
            for (GrupoCartas contador : contadores) {
                if (contador.getContador() >= 2) {
                    respuesta += Grupo.values()[contador.getContador()] + " de " + NombreCarta.values()[index] + "\n";
                    // se marcan las cartas que forman parte del grupo como no punteables
                    marcarCartasGrupo(contador);
                }
                index++;
            }
        }

        return respuesta;
    }

    private void marcarCartasGrupo(GrupoCartas contador) {
        // se toma el arreglo de índices de cartas del grupo y se marcan como no punteables
        int[] indexCartas = contador.getIndexes();

        for (int i = 0; i < contador.getMaxIndex(); i++) {
            cartas[indexCartas[i]].setPunteable(false);
        }
    }

    public String getEscaleras() {
        String respuesta = "No se han encontrado escaleras.";
        int numeroCartas = NombreCarta.values().length;

        // se crean los arreglos como matrices de 13x2, donde la primera columna es el nombre de la carta y la segunda columna es el índice de la carta en el arreglo de cartas del jugador. Este último se usa para marcar las cartas que hacen parte de la escalera
        int escalerasTrebol[][] = new int[numeroCartas][2];
        int escalerasPica[][] = new int[numeroCartas][2];
        int escalerasCorazon[][] = new int[numeroCartas][2];
        int escalerasDiamante[][] = new int[numeroCartas][2];

        // se clasifican las cartas por pinta y al mismo tiempo se ordenan por nombre
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            Carta crt = cartas[i];
            switch (crt.getPinta()) {
                case TREBOL:
                    escalerasTrebol[crt.getNombre().ordinal()][0] = crt.getNombre().ordinal();
                    escalerasTrebol[crt.getNombre().ordinal()][1] = i;
                    break;
                case PICA:
                    escalerasPica[crt.getNombre().ordinal()][0] = crt.getNombre().ordinal();
                    escalerasPica[crt.getNombre().ordinal()][1] = i;
                    break;
                case CORAZON:
                    escalerasCorazon[crt.getNombre().ordinal()][0] = crt.getNombre().ordinal();
                    escalerasCorazon[crt.getNombre().ordinal()][1] = i;
                    break;
                case DIAMANTE:
                    escalerasDiamante[crt.getNombre().ordinal()][0] = crt.getNombre().ordinal();
                    escalerasDiamante[crt.getNombre().ordinal()][1] = i;
                    break;
            }
        }

        String escalera = getEscalerasHelper(escalerasTrebol, Pinta.TREBOL) + 
                          getEscalerasHelper(escalerasPica, Pinta.PICA) + 
                          getEscalerasHelper(escalerasCorazon, Pinta.CORAZON) + 
                          getEscalerasHelper(escalerasDiamante, Pinta.DIAMANTE);

        if (!escalera.isEmpty())
            respuesta = "Se han encontrado las siguientes escaleras:\n" + escalera;

        return respuesta;
    }

    private String getEscalerasHelper(int[][] escaleras, Pinta pinta) {
        String escalera = "";
        String temp = "";
        // contador que se usa para contar cuántas cartas hay en la escalera, y así poder determinar si es un par, una terna, etc.
        int contador = 0;
        int numeroCartas = NombreCarta.values().length;
        // se itera sobre el arreglo que se llenó con los nombres de las cartas por pica
        for(int i = 0; i < numeroCartas; i++) {

            // si el valor del arreglo es diferente de cero, y el siguiente valor es diferente de cero, se ha encontrado una escalera
            // se pone una condición i!= 12 para evitar un error de índice fuera de rango
            if (i!= 12 && escaleras[i][0] != 0 && escaleras[i+1][0] != 0) {
                // se inicializa la escalera 
                temp += pinta + ": ";
                // se define una variable que va a ser el nuevo i cuando la escalera termine
                int new_i = i;
                // se recorre el arreglo hasta el final o hasta que el valor actual sea cero (se termine la escalera)
                for(int j = i; j < numeroCartas && escaleras[j][0] != 0; j++) {
                    // se agrega la carta a la escalera
                    temp += NombreCarta.values()[j] + "";
                    cartas[escaleras[j][1]].setPunteable(false);
                    // se suma 1 al contador de cartas en la escalera
                    contador++;
                    // se agrega una flecha si no es la última carta de la escalera
                    if (j != numeroCartas - 1 && escaleras[j+1][0] != 0) 
                        temp += "->";
                    // se actualiza el nuevo i para que cuando termine la escalera, el for principal continue desde la última carta de la escalera
                    new_i = j+1;
                }
                escalera += Grupo.values()[contador] + " de " + temp + "\n";
                i = new_i;
            }
        }
        return escalera;
    }

    public String getPuntaje() {
        String respuesta = "Puntaje del jugador: 0 puntos.";
        int totalPuntaje = 0;

        for (Carta crt: cartas) {
            if (crt.isPunteable()) {
                totalPuntaje += crt.getNombre().getValor();
            }
        }

        respuesta = "Puntaje del jugador: " + totalPuntaje + " puntos.";
        return respuesta;
    }
}
