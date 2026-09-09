import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import static constants.Constantes.TOTAL_CARTAS;

public class FrmJuego extends JFrame {

    private JPanel pnlJugador1, pnlJugador2;
    Jugador jugador1 = new Jugador();
    Jugador jugador2 = new Jugador();
    JTabbedPane tpJugadores;
    JButton btnVerificar, btnPuntaje;


    public FrmJuego() {
        setTitle("Juego");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        add(btnRepartir);

        // El botón está deshabilitado hasta que se haga la repartición de cartas
        // Este era un error en el código original, si de daba click en el botón de verificar antes de repartir, se lanzaba una excepción porque el arreglo de cartas estaba vacío.
        btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        btnVerificar.setEnabled(false);
        add(btnVerificar);

        // El botón está deshabilitado inicialmente y se habilitará después de repartir las cartas y verificar los grupos y escaleras
        btnPuntaje = new JButton("Calcular Puntajes");
        btnPuntaje.setBounds(230, 10, 150, 25);
        btnPuntaje.setEnabled(false);
        add(btnPuntaje);

        // paneles agrupados en pestañas
        tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(10, 45, 470, 200);
        add(tpJugadores);

        pnlJugador1 = new JPanel();
        tpJugadores.add("Jugador 1", pnlJugador1);
        pnlJugador1.setBackground(new Color(0, 255, 0));
        pnlJugador1.setLayout(null);

        pnlJugador2 = new JPanel();
        tpJugadores.add("Jugador 2", pnlJugador2);
        pnlJugador2.setBackground(new Color(0, 255, 255));
        pnlJugador2.setLayout(null);

        //eventos

        btnRepartir.addActionListener(e -> {
            repartir();
        });

        btnVerificar.addActionListener(e -> {
            verificar();
        });

        btnPuntaje.addActionListener(e -> {
            calcularPuntajes();
        });
    }

    private void repartir() {
        jugador1.repartirCartas();
        jugador1.mostrar(pnlJugador1);
        
        jugador2.repartirCartas();
        jugador2.mostrar(pnlJugador2);

        // Habilitar el botón de verificar después de repartir las cartas
        btnVerificar.setEnabled(true);

        // si se está repartiendo nuevamente, se deshabilita el botón de calcular puntajes para evitar que se calculen puntajes de una mano anterior
        btnPuntaje.setEnabled(false); 
    }

    private void verificar() {
        String gruposEncontrados = "";
        String escalerasEncontradas = "";
        switch (tpJugadores.getSelectedIndex()) {
            case 0:
                gruposEncontrados = jugador1.getGrupos();
                escalerasEncontradas = jugador1.getEscaleras();
                break;
            case 1:
                gruposEncontrados = jugador2.getGrupos();
                escalerasEncontradas = jugador2.getEscaleras();
                break;
        } 

        if (gruposEncontrados.isEmpty() && escalerasEncontradas.isEmpty()) {
            gruposEncontrados = "No se han encontrado grupos ni escaleras.";
        } 

        JOptionPane.showMessageDialog(null, gruposEncontrados + "\n" + escalerasEncontradas);

        // Habilitar el botón de calcular puntajes después de verificar los grupos y escaleras
        btnPuntaje.setEnabled(true);
    }

    private void calcularPuntajes() {
        String puntaje = "";

        switch (tpJugadores.getSelectedIndex()) {
            case 0:
                puntaje = jugador1.getPuntaje();
                break;
            case 1:
                puntaje = jugador2.getPuntaje();
                break;
        } 

        if (puntaje.isEmpty()) {
            puntaje = "El jugador tiene 0 puntos.";
        } 

        JOptionPane.showMessageDialog(null, puntaje);
    }
}
