/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wormslike_menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import wormslike.WormsLike_Main;

/**
 * JPANEL REDEFINI AFIN DE METTRE EN PLACE LE MENU AVEC LES ANIMATION
 * @author wallouf
 */
public class Menu_Main extends JPanel {
    //VARIABLES
    Image W0;
    Image W1;
    Image B;
    WormsLike_Main viewParent;
    Color mF = new Color(82, 41, 8);
    Color mC = new Color(208, 122, 88);
    Color rL = new Color(188, 0, 46);
    Color bL = new Color(0, 85, 229);
    Color bC = new Color(150, 150, 230);
    //TEXTE DES MENU
    //FONT
    Font f = new java.awt.Font("Arial", 0, 24);
    //MAIN MENU
    Image m1;
    Image m2;
    Image m3;
    boolean M1 = false;
    boolean M2 = false;
    boolean M3 = false;
    int nbOfPos = 5;
    int[] XRing = new int[nbOfPos];
    int position = 0;
    static int size = 1000;
    int[] water = new int[size];
    int indice = 0;
    /*
     * 0 -> Main menu
     * 1 -> Game menu
     * 2 -> Settings menu
     */
    int menu_select = 0;

    /**
     * CREE LE NOM, LES OPTIONS, LA TAILLE ET CHARGE LES IMAGES
     * @param viewParent Use to call root method
     */
    public Menu_Main(WormsLike_Main viewParent) {
        this.viewParent = viewParent;
        try {
            W0 = ImageIO.read(new File("./bin/decors/right.png"));
            W1 = ImageIO.read(new File("./bin/decors/left.png"));
            B = ImageIO.read(new File("./bin/decors/banniere_1.png"));
        } catch (IOException ex) {
            Logger.getLogger(Menu_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        indice = 0;
        //pour l'animation lorsqu'on passe la souri
        XRing[0] = 230;
        XRing[1] = 260;
        XRing[2] = 290;
        XRing[3] = 320;
        XRing[4] = 350;
        setSelection(0);
        defineWater def = new defineWater();
        def.start();
        //listener de la souri
        this.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanelMouseReleased(evt);
            }
        });
        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelMouseMoved(evt);
            }
        });
        this.setSize(1000, 650);
        this.setVisible(true);
    }
    //permet de savoir ou se situe la souri
    private void jPanelMouseMoved(java.awt.event.MouseEvent evt) {
        if ((evt.getX() >= 375) && (evt.getX() <= 625)) {
            //PISTE M1
            if ((evt.getY() >= 250) && (evt.getY() <= 310)) {
                M1 = true;
            } else {
                M1 = false;
            }
            //PISTE M2
            if ((evt.getY() >= 360) && (evt.getY() <= 420)) {
                M2 = true;
            } else {
                M2 = false;
            }
            //PISTE M3
            if ((evt.getY() >= 470) && (evt.getY() <= 530)) {
                M3 = true;
            } else {
                M3 = false;
            }
        }
    }
    //on relache le bouton (plus efficace que d'appuyer car la on est sur d'être sur le bon endroit)
    //Si on est au bon endroit on selectionne le bon menu
    private void jPanelMouseReleased(java.awt.event.MouseEvent evt) {
        if ((evt.getX() >= 375) && (evt.getX() <= 625)) {
            if ((evt.getY() >= 250) && (evt.getY() <= 310)) {
                if (menu_select == 0) {
                    this.setSelection(1);
                } else if (menu_select == 1) {
                    viewParent.changeView("one");
                } else if (menu_select == 2) {
                    viewParent.changeView("map editor");
                }
            } else if ((evt.getY() >= 360) && (evt.getY() <= 420)) {
                if (menu_select == 0) {
                    this.setSelection(2);
                } else if (menu_select == 1) {
                    viewParent.changeView("two");
                } else if (menu_select == 2) {
                    viewParent.changeView("video settings");
                }
            } else if ((evt.getY() >= 470) && (evt.getY() <= 530)) {
                if (menu_select == 0) {
                    viewParent.changeView("exit");
                } else if (menu_select == 1) {
                    this.setSelection(0);
                } else if (menu_select == 2) {
                    this.setSelection(0);
                }
            }
        }
    }

    /**
     *
     * permet d'appliquer le choix du menu et de charger les images correspondantes
     */
    public void setSelection(int menu) {
        menu_select = menu;
        switch (menu) {
            case 0:
                try {
                    m1 = ImageIO.read(new File("./bin/decors/m1.png"));
                    m2 = ImageIO.read(new File("./bin/decors/m2.png"));
                    m3 = ImageIO.read(new File("./bin/decors/m3.png"));
                } catch (IOException ex) {
                    Logger.getLogger(Menu_Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 1:
                try {
                    m1 = ImageIO.read(new File("./bin/decors/g1.png"));
                    m2 = ImageIO.read(new File("./bin/decors/g2.png"));
                    m3 = ImageIO.read(new File("./bin/decors/g3.png"));
                } catch (IOException ex) {
                    Logger.getLogger(Menu_Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 2:
                try {
                    m1 = ImageIO.read(new File("./bin/decors/s1.png"));
                    m2 = ImageIO.read(new File("./bin/decors/s2.png"));
                    m3 = ImageIO.read(new File("./bin/decors/s3.png"));
                } catch (IOException ex) {
                    Logger.getLogger(Menu_Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

        }
    }

    /**
     * 
     * permet de redessiner le calques selon les variables et menus choisi
     */
    @Override
    public void paintComponent(Graphics g) {
        //BACKGROUND
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        //WORMS ON LEFT
        g.drawImage(W0, 10, 15, 44, 50, this);
        g.drawImage(W0, 10, 145, 44, 50, this);
        g.drawImage(W0, 10, 275, 44, 50, this);
        g.drawImage(W0, 10, 405, 44, 50, this);
        g.drawImage(W0, 10, 535, 44, 50, this);
        //WORMS ON RIGHT
        g.drawImage(W1, 940, 15, 44, 50, this);
        g.drawImage(W1, 940, 145, 44, 50, this);
        g.drawImage(W1, 940, 275, 44, 50, this);
        g.drawImage(W1, 940, 405, 44, 50, this);
        g.drawImage(W1, 940, 535, 44, 50, this);
        //BANNIERE
        g.drawImage(B, 150, 0, 700, 200, this);
        //CADRE
        g.setColor(mF);
        g.drawRect(200, 198, 600, 390);
        //TEXTE
        g.setFont(f);
        g.setColor(mC);
        g.drawImage(m1, 375, 250, 250, 60, this);
        g.drawImage(m2, 375, 360, 250, 60, this);
        g.drawImage(m3, 375, 470, 250, 60, this);
        //WATER EFFECT
        for (int i = 0; i < size; i++) {
            g.setColor(bL);
            g.fillRect(i * 2, water[i] + 600, 2, 50);
            g.setColor(bC);
            g.fillRect(i * 2, water[i] + 600, 2, 3);
        }
        //SELECTION EFFECT
        if (M1) {
            g.setColor(rL);
            g.fillOval(XRing[position], 270, 20, 20);
            g.fillOval(this.getWidth() - XRing[position], 270, 20, 20);
            g.setColor(Color.BLACK);
            g.fillOval(XRing[position] + 2, 270 + 2, 16, 16);
            g.fillOval(this.getWidth() - XRing[position] + 2, 270 + 2, 16, 16);
        } else if (M2) {
            g.setColor(rL);
            g.fillOval(XRing[position], 380, 20, 20);
            g.fillOval(this.getWidth() - XRing[position], 380, 20, 20);
            g.setColor(Color.BLACK);
            g.fillOval(XRing[position] + 2, 380 + 2, 16, 16);
            g.fillOval(this.getWidth() - XRing[position] + 2, 380 + 2, 16, 16);
        } else if (M3) {
            g.setColor(rL);
            g.fillOval(XRing[position], 490, 20, 20);
            g.fillOval(this.getWidth() - XRing[position], 490, 20, 20);
            g.setColor(Color.BLACK);
            g.fillOval(XRing[position] + 2, 490 + 2, 16, 16);
            g.fillOval(this.getWidth() - XRing[position] + 2, 490 + 2, 16, 16);
        }
    }

    /**
     * calcule l'eau et repaint
     */
    public class defineWater extends Thread {

        int change = 0;
        public defineWater() {
            indice = 0;
            change = 0;
            position = 0;
        }

        /**
         * on rempli le tableau des valeurs du cosinus
         */
        public void setValue() {
            for (int i = 0; i < size; i++) {
                if (indice >= size) {
                    indice = 0;
                }
                water[indice] = (int) (10 * Math.cos((double) ((12 * Math.PI / size) * i)));
                indice++;
            }
            if (indice >= size) {
                indice = 0;
            }
            indice += 4;
        }

        /**
         * on lance le repaint et le calcul indéfiniment
         */
        @Override
        public void run() {
            while (1 == 1) {
                setValue();
                if (change >= 5) {
                    change = 0;
                    if (M1 || M2 || M3) {
                        if (position >= (nbOfPos - 1)) {
                            position = 0;
                        } else {
                            position++;
                        }
                    } else {
                        position = 0;
                    }
                }
                repaint();
                change++;
                try {
                    defineWater.sleep(40);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Menu_Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
