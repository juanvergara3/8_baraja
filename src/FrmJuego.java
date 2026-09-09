import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
    private Baraja baraja = new Baraja();


    public FrmJuego() {
        setTitle("Juego");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JComboBox<String> cmbTipoReparto = new JComboBox<>();
        cmbTipoReparto.addItem("Baraja cerrada");
        cmbTipoReparto.addItem("Baraja abierta");
        cmbTipoReparto.setBounds(10, 10, 120, 25);
        add(cmbTipoReparto);
        
        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(140, 10, 80, 25);
        add(btnRepartir);

        // El botón está deshabilitado hasta que se haga la repartición de cartas
        // Este era un error en el código original, si de daba click en el botón de verificar antes de repartir, se lanzaba una excepción porque el arreglo de cartas estaba vacío.
        btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(230, 10, 90, 25);
        btnVerificar.setEnabled(false);
        add(btnVerificar);

        // El botón está deshabilitado inicialmente y se habilitará después de repartir las cartas y verificar los grupos y escaleras
        btnPuntaje = new JButton("Calcular Puntajes");
        btnPuntaje.setBounds(330, 10, 140, 25);
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
            if (cmbTipoReparto.getSelectedIndex() == 0) {
                repartir_baraja_cerrada();
            } else {
                repartir_baraja_abierta();
            }
        });

        btnVerificar.addActionListener(e -> {
            verificar();
        });

        btnPuntaje.addActionListener(e -> {
            calcularPuntajes();
        });

        tpJugadores.addChangeListener(e -> {
            // Deshabilitar el botón de calcular puntajes al cambiar de jugador
            btnPuntaje.setEnabled(false);
        });
    }

    private void repartir_baraja_abierta() {
        jugador1.repartirCartas();
        jugador1.mostrar(pnlJugador1);
        
        jugador2.repartirCartas();
        jugador2.mostrar(pnlJugador2);

        // Habilitar el botón de verificar después de repartir las cartas
        btnVerificar.setEnabled(true);

        // si se está repartiendo nuevamente, se deshabilita el botón de calcular puntajes para evitar que se calculen puntajes de una mano anterior
        btnPuntaje.setEnabled(false); 
    }

    private void repartir_baraja_cerrada() {
        baraja.reiniciarBaraja(); // Reinicia la baraja antes de repartir las cartas

        Carta[] cartasJugador1 = baraja.tomarCartas(TOTAL_CARTAS);
        Carta[] cartasJugador2 = baraja.tomarCartas(TOTAL_CARTAS);

        if (cartasJugador1 != null && cartasJugador2 != null) {
            jugador1.entregarCartas(cartasJugador1);
            jugador1.mostrar(pnlJugador1);

            jugador2.entregarCartas(cartasJugador2);
            jugador2.mostrar(pnlJugador2);

            // Habilitar el botón de verificar después de repartir las cartas
            btnVerificar.setEnabled(true);

            // si se está repartiendo nuevamente, se deshabilita el botón de calcular puntajes para evitar que se calculen puntajes de una mano anterior
            btnPuntaje.setEnabled(false); 
        } else {
            JOptionPane.showMessageDialog(null, "No hay suficientes cartas disponibles para repartir.");
        }
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
