import java.util.Random;

import javax.swing.JPanel;

import enums.NombreCarta;
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

        // arreglo de contadores para cada nombre de carta
        int[] contadores = new int[NombreCarta.values().length];

        boolean hayGrupos = false;
        for (Carta crt: cartas) {
            int posicion = crt.getNombre().ordinal();
            contadores[posicion]++;

            if (!hayGrupos && contadores[posicion] >= 2) 
                hayGrupos = true;
        }

        if (hayGrupos){
            respuesta = "Se han encontrado los siguientes grupos:\n";
            int index = 0;
            for (int contador : contadores) {
                if (contador >= 2) 
                    respuesta += Grupo.values()[contador] + " de " + NombreCarta.values()[index] + "\n";
                index++;
            }
        }

        return respuesta;
    }

    // este método toma un approch muy parecido al de getGrupos, para que se reinventar la rueda?
    public String getEscaleras() {
        String respuesta = "No se han encontrado escaleras.";

        // int[] contadores = new int[NombreCarta.values().length];

        // boolean hayEscaleras = false;
        // for (Carta crt: cartas) {
        //     int posicion = crt.getNombre().ordinal();
        //     contadores[posicion]++;

        //     if (!hayEscaleras && contadores[posicion] >= 2) 
        //         hayEscaleras = true;
        // }

        // if (hayEscaleras){
        //     respuesta = "Se han encontrado las siguientes escaleras:\n";
        //     int index = 0;
        //     for (int contador : contadores) {
        //         if (contador >= 2) 
        //             respuesta += Grupo.values()[contador] + " de " + NombreCarta.values()[index] + "\n";
        //         index++;
        //     }
        // }

        return respuesta;
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
