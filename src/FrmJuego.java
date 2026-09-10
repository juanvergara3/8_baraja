import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import static constants.Constantes.TOTAL_CARTAS;

public class FrmJuego extends JFrame {

    private JPanel pnlJugador1, pnlJugador2;
    Jugador jugador1 = new Jugador();
    Jugador jugador2 = new Jugador();
    JTabbedPane tpJugadores;
    JButton btnVerificar, btnPuntaje;
    JTextField txtBaraja;


    public FrmJuego() {
        setTitle("Juego");
        setSize(505, 335);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // primera fila de componentes
        
        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 150, 25);
        add(btnRepartir);

        // El botón está deshabilitado hasta que se haga la repartición de cartas
        // Este era un error en el código original, si de daba click en el botón de verificar antes de repartir, se lanzaba una excepción porque el arreglo de cartas estaba vacío.
        btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(170, 10, 150, 25);
        btnVerificar.setEnabled(false);
        add(btnVerificar);

        // El botón está deshabilitado inicialmente y se habilitará después de repartir las cartas y verificar los grupos y escaleras
        btnPuntaje = new JButton("Calcular Puntajes");
        btnPuntaje.setBounds(330, 10, 150, 25);
        btnPuntaje.setEnabled(false);
        add(btnPuntaje);

        // segunda fila de componentes

        JComboBox<String> cmbTipoReparto = new JComboBox<>();
        cmbTipoReparto.addItem("Baraja cerrada");
        cmbTipoReparto.addItem("Baraja abierta");
        cmbTipoReparto.setBounds(10, 45, 120, 25);
        add(cmbTipoReparto);

        JLabel lblBaraja = new JLabel("Cantidad de barajas:");
        lblBaraja.setBounds(140, 45, 120, 25);
        add(lblBaraja);

        txtBaraja = new JTextField();
        txtBaraja.setBounds(270, 45, 50, 25);
        txtBaraja.setText("1");
        add(txtBaraja);

        // paneles agrupados en pestañas
        tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(10, 85, 470, 200);
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

        cmbTipoReparto.addActionListener(e -> {
            if (cmbTipoReparto.getSelectedIndex() == 0) {
                txtBaraja.setEditable(true);
            } else {
                txtBaraja.setEditable(false);
            }
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
        int cantidadBarajas;
        try {
            cantidadBarajas = Integer.parseInt(txtBaraja.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un número válido de barajas.");
            return;
        }

        if (cantidadBarajas <= 0) {
            JOptionPane.showMessageDialog(null, "Ingrese un número de barajas superior a 0.");
            return;
        }

        Baraja baraja = new Baraja(cantidadBarajas); // Crea una nueva baraja

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
