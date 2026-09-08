package enums;

// se modifica este enum para asignarle un valor numérico a cada carta. Se usa para calcular los puntajes de cada jugador.
public enum NombreCarta {
    A(10),
    DOS(2),
    TRES(3),
    CUATRO(4),
    CINCO(5),
    SEIS(6),
    SIETE(7),
    OCHO(8),
    NUEVE(9),
    DIEZ(10),
    JACK(10),
    QUEEN(10),
    KING(10);

    private int valor;

    private NombreCarta(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
