import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import enums.NombreCarta;
import enums.Pinta;

public class Carta {
    private int indice;

    public Carta(Random r) {
        indice = r.nextInt(52) + 1;
    }

    // overloading de constructor para crear cartas específicas en lugar de aleatorias
    public Carta(int indice) {
        this.indice = indice;
    }

    public void mostrar(JPanel pnl, int x, int y) {
        String path = "images/CARTA" + indice + ".JPG";
        ImageIcon imgCarta = new ImageIcon(getClass().getResource(path));
        JLabel lblCarta = new JLabel(imgCarta);
        lblCarta.setBounds(x, y, imgCarta.getIconWidth(), imgCarta.getIconHeight());
        pnl.add(lblCarta);

        lblCarta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, getNombre() + " de " + getPinta());
            }
        });
    }

    // Getters
    public Pinta getPinta() {
        if (indice <= 13) 
            return Pinta.TREBOL;
        else if (indice <= 26) 
            return Pinta.PICA;
        else if (indice <= 39) 
            return Pinta.CORAZON;
        else 
            return Pinta.DIAMANTE;  
    }

    public NombreCarta getNombre() {
        int residuo = indice % 13;
        if (residuo == 0) {
            residuo = 13; // Ajustar para que el índice 13 corresponda a KING
        }
        return NombreCarta.values()[residuo - 1];
    }
}
