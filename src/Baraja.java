import java.util.Random;

import static constants.Constantes.TAMAÑO_BARAJA;

public class Baraja {
    private Carta[] cartas;
    private Random r = new Random();
    private int cartasDisponibles;

    public Baraja(int cantidadBarajas) {
        cartasDisponibles = TAMAÑO_BARAJA * cantidadBarajas;
        // se inicializa el array de cartas con el tamaño correspondiente al número de barajas
        cartas = new Carta[cartasDisponibles];
        inicializarBaraja(cantidadBarajas);
    }

    private void inicializarBaraja(int cantidadBarajas) {
        int counter = 0;

        for (int i = 0; i < cantidadBarajas; i++) 
            for (int j = 0; j < TAMAÑO_BARAJA; j++) {
                cartas[counter] = new Carta(j + 1);
                counter++;
            }

        // se deja este método para mostrar la baraja en consola
        // le facilito la revisión un poquito profe :P
        mostrar();
    }

    private void mostrar() {
        System.out.println("Cartas disponibles: " + cartasDisponibles);
        for (int i = 0; i < cartas.length; i++) {
            System.out.println(i + ": " + cartas[i].getNombre() + " de " + cartas[i].getPinta() + " (#: " + cartas[i].getIndice() + ")");
        }
        System.out.println("--------------------------------------------------");
    }

    public Carta[] tomarCartas(int cantidad) {
        Carta[] cartasTomadas = new Carta[cantidad];

        // si la cantida de cartas que se pide es superior a la cantidad de cartas disponibles se retorna null
        if (cantidad > cartasDisponibles) {
            return null;
        }

        System.out.println("Cartas tomadas: " + cantidad);

        for (int i = 0; i < cantidad; i++) {
            // se genera un índice aleatorio entre 0 y cartasDisponibles - 1
            int indice = r.nextInt(0, cartasDisponibles - 1);

            // Esto muestra que cartas se toman del array cuando se reparten, facilita la revisión del código :PPP
            // NOTA: el '# en la baraja' puede aparecer repetido por la forma como está diseñado el método, pues se toma una carta aleatoria y se reemplaza por la última carta disponible, es decir, es técnicamente posible que toda las cartas tomen el mismo # en la baraja mientras que este sea <= cantidad de cartas disponibles -  cantidad de cartas que se toman.
            System.out.println(i + ": " + cartas[indice].getNombre() + " de " + cartas[indice].getPinta() + " (#: " + cartas[indice].getIndice() + ") [ # en la baraja: " + indice + "]");

            // se toma una carta
            cartasTomadas[i] = cartas[indice];

            // y el hueco que deja esta carta se rellena con la última carta disponible
            cartas[indice] = cartas[cartasDisponibles - 1];
            // y se elimina la última carta disponible
            cartas[cartasDisponibles - 1] = null;
            // finalmente se decrementa el contador de cartas disponibles
            cartasDisponibles--;
        }
        System.out.println("Cartas disponibles después de repartir: " + cartasDisponibles);
        System.out.println("--------------------------------------------------");

        return cartasTomadas;
    }
}
