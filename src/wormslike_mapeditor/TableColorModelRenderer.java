/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wormslike_mapeditor;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * PERMET DE METTRE EN COULEUR LES CASES SELON LES BLOCS
 * @author wallouf
 */
public class TableColorModelRenderer extends DefaultTableCellRenderer {

    public TableColorModelRenderer() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
// Tu appelles la méthode par défaut, qui te construit la case
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
// Tu changes la couleur de la case
        if((""+table.getValueAt(row, column)).equalsIgnoreCase("null")){
            return c;
        }
        int valeur = Integer.parseInt("" + table.getValueAt(row, column));
        if (valeur == 0) {
            c.setForeground(Color.BLUE);
        } else if (valeur == 1 || valeur == 2 || valeur == 3) {
            c.setForeground(Color.RED);
        } else if (valeur == 6) {
            c.setForeground(new Color(160, 90, 91));
        } else if (valeur == 7) {
            c.setForeground(Color.GRAY);
        }
        table.validate();
        table.repaint();
        return c;
    }
}
