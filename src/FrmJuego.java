import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class FrmJuego extends JFrame {

    private JPanel pnlJugador1, pnlJugador2;
    Jugador jugador1 = new Jugador();
    Jugador jugador2 = new Jugador();
    JTabbedPane tpJugadores;

    public FrmJuego() {
        setTitle("Juego");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        add(btnRepartir);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        add(btnVerificar);

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
    }

    private void repartir() {
        jugador1.repartirCartas();
        jugador1.mostrar(pnlJugador1);
        
        jugador2.repartirCartas();
        jugador2.mostrar(pnlJugador2);
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
    }
}
