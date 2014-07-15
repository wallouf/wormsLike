/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Game_Main.java
 *
 * Created on 26 juin 2011, 18:53:41
 */
package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import wormslike.WormsLike_Main;

/**
 *
 * @author wallouf
 */
public class Game_Main extends javax.swing.JPanel {

    /** Creates new form Game_Main */
    public Game_Main(WormsLike_Main viewParent) {
        this.viewParent = viewParent;
        initComponents();
        this.setSize(1000, 1000);
        mainGrid = new GridBagLayout();
        this.setLayout(mainGrid);
        c.fill = GridBagConstraints.HORIZONTAL;
        y = 0;
        x = 0;
        for (int i = 0; i < 2500; i++) {
            map[i] = new HashMap();
        }
        initSimply();
        this.setBackground(Color.WHITE);
    }

    public void initSimply() {
        for (y = 0; y < 50; y++) {
            for (x = 0; x < 50; x++) {
                //barre du haut
                if ((x == 0 || x == 49) || (y == 0 || y == 49)) {
                    c.gridx = x;
                    c.gridy = y;
                    this.add(new Map(6), c, (y * 50) + x);
                    map[(y * 50) + x].put(new Dimension(x, y), 6);
                } else if (y == 34) {
                    c.gridx = x;
                    c.gridy = y;
                    this.add(new Map(0), c, (y * 50) + x);
                    map[(y * 50) + x].put(new Dimension(x, y), 0);
                } else if (y > 34) {
                    c.gridx = x;
                    c.gridy = y;
                    this.add(new Map(6), c, (y * 50) + x);
                    map[(y * 50) + x].put(new Dimension(x, y), 6);
                } else {
                    c.gridx = x;
                    c.gridy = y;
                    this.add(new Map(7), c, (y * 50) + x);
                    map[(y * 50) + x].put(new Dimension(x, y), 7);
                }
            }
        }
    }

    public void initWithSlope() {
        //CADRE
        this.add(new Map(2), c);
        for (int i = 0; i < 50; i++) {
            c.gridx = i;
            c.gridy = 0;
            this.add(new Map(6), c);
            c.gridx = 0;
            c.gridy = i;
            this.add(new Map(6), c);
            c.gridx = i;
            c.gridy = 49;
            this.add(new Map(6), c);
            c.gridx = 49;
            c.gridy = i;
            this.add(new Map(6), c);
        }
        //variation du sol
        int variation[] = new int[50];
        variation[0] = 0;
        variation[49] = 0;
        int s = (int) (Math.random() * 2);
        for (int i = 0; i < 49; i++) {
            if (i < 4) {
                variation[i] = s;
            } else {
                if (variation[i - 1] == variation[i - 2] && variation[i - 1] == variation[i - 3]) {
                    s = (int) (Math.random() * 2);
                    variation[i] = s;
                } else {
                    variation[i] = s;
                }
            }
        }
        //Sol
        for (int i = 1; i < 49; i++) {
            for (int j = 0; j < 15; j++) {
                if (j == 0) {
                    if (variation[i + 1] == variation[i]) {
                        c.gridx = i;
                        c.gridy = j + 33 + variation[i];
                        this.add(new Map(0), c);
                    } else {
                        if (variation[i + 1] > variation[i]) {
                            c.gridx = i;
                            c.gridy = j + 33 + variation[i];
                            this.add(new Map(3), c);
                        } else {
                            c.gridx = i;
                            c.gridy = j + 32 + variation[i];
                            this.add(new Map(2), c);
                            c.gridx = i;
                            c.gridy = j + 33 + variation[i];
                            this.add(new Map(6), c);
                        }
                    }
                    c.gridx = i;
                    c.gridy = j + 34 + variation[i];
                    this.add(new Map(6), c);
                } else {
                    c.gridx = i;
                    c.gridy = j + 34;
                    this.add(new Map(6), c);
                }
            }
        }
    }

    public void Move(int where) {
        if (where == KeyEvent.VK_DOWN) {
            System.out.println("Move to down");
        } else if (where == KeyEvent.VK_UP) {
            System.out.println("Move to up");
        } else if (where == KeyEvent.VK_LEFT) {
            System.out.println("Move to left");
            lastPosition.width = (int) (position.getWidth());
            position.width = (int) (position.getWidth() - 1);
            verifyAndTrace();
        } else if (where == KeyEvent.VK_RIGHT) {
            System.out.println("Move to right");
            lastPosition.width = (int) (position.getWidth());
            position.width = (int) (position.getWidth() + 1);
            verifyAndTrace();
        }
        this.repaint();
    }

    public void verifyAndTrace() {
        //gauche
        if (map[(int) (position.getWidth() + (position.getHeight() * 50))].get(new Dimension((int) position.getWidth(), (int) position.getHeight()))!=7){
            position.width = lastPosition.width;
            return;
        }
        //Trace
        c.gridx = (int) position.getWidth();
        c.gridy = (int) position.getHeight();
        this.remove((int) (position.getWidth() + (position.getHeight() * 50)));
        this.add(new Map(10), c, (int) (position.getWidth() + (position.getHeight() * 50)));
        c.gridx = (int) lastPosition.getWidth();
        c.gridy = (int) lastPosition.getHeight();
        this.remove((int) (lastPosition.getWidth() + (lastPosition.getHeight() * 50)));
        this.add(new Map(7), c, (int) (lastPosition.getWidth() + (lastPosition.getHeight() * 50)));
        map[(int) (position.getWidth() + (position.getHeight() * 50))].put(new Dimension((int) position.getWidth(), (int) position.getHeight()), 10);
        map[(int) (lastPosition.getWidth() + (lastPosition.getHeight() * 50))].put(new Dimension((int) lastPosition.getWidth(), (int) lastPosition.getHeight()), 7);
    }

    public Dimension verifyPosition(Dimension pos) {
        return pos;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    GridBagLayout mainGrid;
    WormsLike_Main viewParent;
    private Dimension position = new Dimension(25, 33);
    private Dimension lastPosition = new Dimension(26, 33);
    GridBagConstraints c = new GridBagConstraints();
    HashMap<Dimension, Integer>[] map = new HashMap[2500];
    int x = 0;
    int y = 0;
    public static final int ground = 6;
    public static final int ground_up = 0;
    public static final int ground_down = 1;
    public static final int ground_left = 2;
    public static final int ground_right = 3;
}
