import java.util.Random;

import static constants.Constantes.TAMAÑO_BARAJA;

public class Baraja {
    private Carta[] cartas = new Carta[TAMAÑO_BARAJA];
    private Random r = new Random();
    private int cartasDisponibles;

    public Baraja() {
        inicializarBaraja();
    }

    private void inicializarBaraja() {
        // Este método reinicia la baraja a su estado original, con todas las cartas disponibles
        cartasDisponibles = TAMAÑO_BARAJA;
        for (int i = 0; i < TAMAÑO_BARAJA; i++) {
            cartas[i] = new Carta(i + 1);
        }
    }

    public void reiniciarBaraja() {
        // se hace este check para verificar si la baraja ya ha sido inicializada, si no lo ha sido, se inicializa
        if (cartasDisponibles != TAMAÑO_BARAJA)
            inicializarBaraja();
    }

    public Carta[] tomarCartas(int cantidad) {
        Carta[] cartasTomadas = new Carta[cantidad];

        // si la cantida de cartas que se pide es superior a la cantidad de cartas disponibles se retorna null
        if (cantidad > cartasDisponibles) {
            return null;
        }

        for (int i = 0; i < cantidad; i++) {
            // se genera un índice aleatorio entre 0 y cartasDisponibles - 1
            int indice = r.nextInt(0, cartasDisponibles - 1);

            // se toma una carta
            cartasTomadas[i] = cartas[indice];

            // y el hueco que deja esta carta se rellena con la última carta disponible
            cartas[indice] = cartas[cartasDisponibles - 1];
            // y se elimina la última carta disponible
            cartas[cartasDisponibles - 1] = null;
            // finalmente se decrementa el contador de cartas disponibles
            cartasDisponibles--;
        }

        return cartasTomadas;
    }
}
