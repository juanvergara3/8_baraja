public class Baraja {
    private Carta[] cartas = new Carta[52];

    public Baraja() {
        // El constructor crea una baraja completa de 52 cartas, inicializando cada carta con un índice del 1 al 52
        for (int i = 0; i < 52; i++) {
            cartas[i] = new Carta(i + 1);
        }
    }
}
