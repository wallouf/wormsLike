/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wormslike_game;

import wormslike_sound.Game_Sound;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import wormslike.WormsLike_Main;
import wormslike_IA.IA_Motor;

/**
 *
 * @author wallouf
 */
public class Game_Calque extends JPanel {
    //DECLARATION DES VARIABLES

    Color cG = new Color(175, 163, 149);
    Color cM = new Color(62, 41, 36);
    Color mF = new Color(82, 41, 8);
    Color rL = new Color(188, 0, 46);
    Color bL = new Color(0, 85, 229);
    Color bC = new Color(150, 150, 230);
    static int size = 1000;
    int[] water = new int[size];
    int indice = 0;
    int mapNumber = 7;
    HashMap<Integer, Integer> mapPos;
    int positionPied_1 = 1630;
    int[] positionPied = new int[2];
    int[] lastPositionPied = new int[2];
    int[] positionTete = new int[2];
    int[] lastPositionTete = new int[2];
    boolean[] rightOrientation = new boolean[2];
    boolean[] firstUse = new boolean[2];
    int player = 0;
    int playerShoot = 0;
    int XAim = 0;
    int YAim = 0;
    int XStart = 0;
    int YStart = 0;
    int XShoot = 0;
    int YShoot = 0;
    int[] XCircle = new int[10];
    int[] YCircle = new int[10];
    int XShootAnim = 0;
    int YShootAnim = 0;
    int XLifeAnim = 0;
    int YLifeAnim = 0;
    int[] lifeLoose = new int[2];
    int tailleAnim = 0;
    int wind = 0;
    int avanceMissil = 0;
    int V0 = 50;
    //0 - DEFAULT / 1 - LUNE / 2 - JUNGLE
    int THEME = 0;
    double angle = 0;
    double g = 0;
    double pente = 0;
    double alpha = 0;
    //indique si on monte ou on descend la visée
    int verticalTir = 0;
    //permet de savoir le quel a ete ajoute en dernier
    int last = 0;
    Jump jp;
    Image head_left_1;
    Image foot_left_1;
    Image head_right_1;
    Image foot_right_1;
    Image head_left_2;
    Image foot_left_2;
    Image head_right_2;
    Image foot_right_2;
    Image background;
    Image B0;
    Image B1;
    Image B2;
    Image B3;
    Image B4;
    Image B6;
    Image B7;
    PowerOfShoot pow;
    //VARIABLE DETAT
    boolean weaponMod = false;
    boolean fall = false;
    boolean shoot = false;
    boolean shootAnim = false;
    boolean lifeAnim = false;
    boolean partyEnd = false;
    boolean jump = false;
    boolean IA = false;
    boolean rendering;
    private boolean setPower = false;
    int power = 0;
    int looser = -1;
    Game_Frame viewParent;
    int[] lifeOfPlayer = new int[2];
    boolean[] alreadyDamage = new boolean[2];
    long time = 0;
    Font font = new Font("Courier", Font.BOLD, 30);
    Font life = new Font("Courier", Font.BOLD, 12);
    IA_Motor Calcul;
    TimeCount t;
    AnimIA animationIA;

    //APPEL INITIAL DE LA FONCTION SANS MAP QUI GENERE PAR DEFAUT UNE MAP(sert pour les tests et le dev)
    public Game_Calque(Game_Frame viewParent, boolean IA) {
        this.viewParent = viewParent;
        this.THEME = WormsLike_Main.THEME;
        Calcul = new IA_Motor(this);
        this.IA = IA;
        this.setSize(1000, 1000);
        mapPos = new HashMap();
        try {
            head_left_1 = ImageIO.read(new File("./bin/icons/head_left_1.png"));
            foot_left_1 = ImageIO.read(new File("./bin/icons/foot_left.png"));
            head_right_1 = ImageIO.read(new File("./bin/icons/head_right_1.png"));
            foot_right_1 = ImageIO.read(new File("./bin/icons/foot_right.png"));
            head_left_2 = ImageIO.read(new File("./bin/icons/head_left_2.png"));
            foot_left_2 = ImageIO.read(new File("./bin/icons/foot_left.png"));
            head_right_2 = ImageIO.read(new File("./bin/icons/head_right_2.png"));
            foot_right_2 = ImageIO.read(new File("./bin/icons/foot_right.png"));
            if (THEME == 0) {
                B0 = ImageIO.read(new File("./bin/icons/0_th0.jpg"));
                B1 = ImageIO.read(new File("./bin/icons/1_th0.jpg"));
                B2 = ImageIO.read(new File("./bin/icons/2_th0.jpg"));
                B3 = ImageIO.read(new File("./bin/icons/3_th0.jpg"));
                B6 = ImageIO.read(new File("./bin/icons/6_th0.jpg"));
            } else if (THEME == 1) {
                try {
                    background = ImageIO.read(new File("./bin/icons/background_1.jpg"));
                } catch (IOException ex) {
                    System.out.println("[INFO]Image de fond introuvable!");
                }
                B0 = ImageIO.read(new File("./bin/icons/0_th1.jpg"));
                B1 = ImageIO.read(new File("./bin/icons/1_th1.jpg"));
                B2 = ImageIO.read(new File("./bin/icons/2_th1.jpg"));
                B3 = ImageIO.read(new File("./bin/icons/3_th1.jpg"));
                B6 = ImageIO.read(new File("./bin/icons/6_th1.jpg"));
            } else if (THEME == 2) {
                try {
                    background = ImageIO.read(new File("./bin/icons/background_2.jpg"));
                } catch (IOException ex) {
                    System.out.println("[INFO]Image de fond introuvable!");
                }
                B0 = ImageIO.read(new File("./bin/icons/0_th2.jpg"));
                B1 = ImageIO.read(new File("./bin/icons/1_th2.jpg"));
                B2 = ImageIO.read(new File("./bin/icons/2_th2.jpg"));
                B3 = ImageIO.read(new File("./bin/icons/3_th2.jpg"));
                B6 = ImageIO.read(new File("./bin/icons/6_th2.jpg"));
            }
        } catch (IOException ex) {
            System.out.println("[ERROR]Images introuvables !");
        }
        int x = 0;
        int y = 0;
        for (int i = 0; i < 2450; i++) {
            if (x > 49) {
                x = 0;
            }
            y = (int) i / 50;
            //mapPos[i] = new HashMap();
            if (i < 50 || i > 2399 || x == 0 || x == 49 || ((x > 0 && x < 49) && y > 33) || ((y * 50 + x) == 1570)) {
                mapPos.put((50 * y) + x, 6);
            } else if ((x > 0 && x < 49) && y == 33) {
                mapPos.put((50 * y) + x, 0);
            } else {
                mapPos.put((50 * y) + x, 7);
            }
            x++;
        }
        lifeOfPlayer[0] = 100;
        lifeOfPlayer[1] = 100;
        looser = -1;
        RandomSet_1();
        RandomSet_2();
        if (t != null && t.isAlive()) {
            partyEnd = true;
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Game_Calque.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        partyEnd = false;
        Game_Sound.playStart();
        Game_Sound.playAmbiance();
        t = new TimeCount();
        t.start();
    }

    //APPEL INITIAL DE LA FONCTION AVEC LE PARENT, LA MAP, SI ON FAIS JUSTE UN REND et SI ON VEUT L'IA
    public Game_Calque(Game_Frame viewParent, HashMap<Integer, Integer> mapPos, boolean rendering, boolean IA) {
        this.viewParent = viewParent;
        this.IA = IA;
        this.THEME = WormsLike_Main.THEME;
        Calcul = new IA_Motor(this);
        this.setSize(1000, 1000);
        this.rendering = rendering;
        this.mapPos = mapPos;
        //ON OUVRE LES IMAGES (textures)
        try {
            head_left_1 = ImageIO.read(new File("./bin/icons/head_left_1.png"));
            foot_left_1 = ImageIO.read(new File("./bin/icons/foot_left.png"));
            head_right_1 = ImageIO.read(new File("./bin/icons/head_right_1.png"));
            foot_right_1 = ImageIO.read(new File("./bin/icons/foot_right.png"));
            head_left_2 = ImageIO.read(new File("./bin/icons/head_left_2.png"));
            foot_left_2 = ImageIO.read(new File("./bin/icons/foot_left.png"));
            head_right_2 = ImageIO.read(new File("./bin/icons/head_right_2.png"));
            foot_right_2 = ImageIO.read(new File("./bin/icons/foot_right.png"));
            if (THEME == 0) {
                B0 = ImageIO.read(new File("./bin/icons/0_th0.jpg"));
                B1 = ImageIO.read(new File("./bin/icons/1_th0.jpg"));
                B2 = ImageIO.read(new File("./bin/icons/2_th0.jpg"));
                B3 = ImageIO.read(new File("./bin/icons/3_th0.jpg"));
                B6 = ImageIO.read(new File("./bin/icons/6_th0.jpg"));
            } else if (THEME == 1) {
                try {
                    background = ImageIO.read(new File("./bin/icons/background_1.jpg"));
                } catch (IOException ex) {
                    System.out.println("[INFO]Image de fond introuvable!");
                }
                B0 = ImageIO.read(new File("./bin/icons/0_th1.jpg"));
                B1 = ImageIO.read(new File("./bin/icons/1_th1.jpg"));
                B2 = ImageIO.read(new File("./bin/icons/2_th1.jpg"));
                B3 = ImageIO.read(new File("./bin/icons/3_th1.jpg"));
                B6 = ImageIO.read(new File("./bin/icons/6_th1.jpg"));
            } else if (THEME == 2) {
                try {
                    background = ImageIO.read(new File("./bin/icons/background_2.jpg"));
                } catch (IOException ex) {
                    System.out.println("[INFO]Image de fond introuvable!");
                }
                B0 = ImageIO.read(new File("./bin/icons/0_th2.jpg"));
                B1 = ImageIO.read(new File("./bin/icons/1_th2.jpg"));
                B2 = ImageIO.read(new File("./bin/icons/2_th2.jpg"));
                B3 = ImageIO.read(new File("./bin/icons/3_th2.jpg"));
                B6 = ImageIO.read(new File("./bin/icons/6_th2.jpg"));
            }
        } catch (IOException ex) {
            System.out.println("Images introuvables !");
        }
        //on initialise la vie des joueurs ainsi que les variables
        lifeOfPlayer[0] = 100;
        lifeOfPlayer[1] = 100;
        if (rendering) {
            return;
        }
        alreadyDamage[0] = false;
        alreadyDamage[1] = false;
        looser = -1;
        //on positionne aleatoirement les joueurs
        RandomSet_1();
        RandomSet_2();
        if (t != null && t.isAlive()) {
            partyEnd = true;
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Game_Calque.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        partyEnd = false;
        //on lance le son
        Game_Sound.playStart();
        Game_Sound.playAmbiance();
        //on lance le decompte
        t = new TimeCount();
        t.start();
    }
    //ici se trouve toute la partie graphique du jeu avec differentes parties active ou non qui dessine sur le calque

    @Override
    public void paintComponent(Graphics g) {
        int[] X = new int[4];
        int[] Y = new int[4];//fond
        g.setColor(Color.DARK_GRAY);
        //Si aucun theme on met un fond noir
        if (THEME == 0) {
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        } else if (THEME >= 1) {
            g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
        }
        int x = 0;
        int y = 0;
        for (int i = 0; i < 2450; i++) {
            if (x > 49) {
                x = 0;
            }
            y = (int) i / 50;
            //DESSIN DES DIFFERENTES TEXTURES en fonction de la HASHMAP
            int type = mapPos.get(i);
            if (type == 6) {
                g.setColor(cM);
                g.drawImage(B6, x * 20, y * 20, 20, 20, this);

            } else if (type == 7) {
                g.setColor(Color.DARK_GRAY);
                if (THEME == 0) {
                    g.fillRect(x * 20, y * 20, 20, 20);
                }
            } else if (type == 10) {
                if (!rightOrientation[0]) {
                    g.drawImage(head_left_1, x * 20, y * 20, 20, 20, this);
                } else {
                    g.drawImage(head_right_1, x * 20, y * 20, 20, 20, this);
                }
            } else if (type == 11) {
                if (!rightOrientation[0]) {
                    g.drawImage(foot_left_1, x * 20, y * 20, 20, 20, this);
                } else {
                    g.drawImage(foot_right_1, x * 20, y * 20, 20, 20, this);
                }
            } else if (type == 12) {
                if (!rightOrientation[1]) {
                    g.drawImage(head_left_2, x * 20, y * 20, 20, 20, this);
                } else {
                    g.drawImage(head_right_2, x * 20, y * 20, 20, 20, this);
                }
            } else if (type == 13) {
                if (!rightOrientation[1]) {
                    g.drawImage(foot_left_2, x * 20, y * 20, 20, 20, this);
                } else {
                    g.drawImage(foot_right_2, x * 20, y * 20, 20, 20, this);
                }
            } else if (type == 14) {
                g.setColor(bL);
                g.fillRect(x * 20, y * 20, 20, 20);
            } else if (type == 0) {
                g.drawImage(B0, x * 20, y * 20, 20, 20, this);
            } else if (type == 1) {
                g.drawImage(B1, x * 20, y * 20, 20, 20, this);
            } else if (type == 2) {
                g.drawImage(B2, x * 20, y * 20, 20, 20, this);
            } else if (type == 3) {
                g.drawImage(B3, x * 20, y * 20, 20, 20, this);
            }
            x++;
        }
        //VENT
        g.setColor(Color.GREEN);
        if (wind != 0) {
            if (wind > 0) {
                g.fillRect((this.getWidth() / 2), 70 - 50, (10 * wind), 3);
            } else {
                g.fillRect((this.getWidth() / 2) + (10 * wind), 70 - 50, -(10 * wind), 3);
            }
            if (wind > 0) {
                X[0] = ((this.getWidth() / 2) + (10 * wind) - 10);
                X[1] = (this.getWidth() / 2) + (10 * wind);
                X[2] = ((this.getWidth() / 2) + (10 * wind) - 2);
                X[3] = ((this.getWidth() / 2) + (10 * wind) - 12);
                Y[0] = 60 - 50;
                Y[1] = 70 - 50;
                Y[2] = 72 - 50;
                Y[3] = 62 - 50;
                g.fillPolygon(X, Y, 4);
                X[0] = ((this.getWidth() / 2) + (10 * wind) - 10);
                X[1] = (this.getWidth() / 2) + (10 * wind);
                X[2] = ((this.getWidth() / 2) + (10 * wind) - 2);
                X[3] = ((this.getWidth() / 2) + (10 * wind) - 12);
                Y[0] = 83 - 50;
                Y[1] = 73 - 50;
                Y[2] = 71 - 50;
                Y[3] = 81 - 50;
                g.fillPolygon(X, Y, 4);
            } else {
                X[0] = ((this.getWidth() / 2) + (10 * wind) + 10);
                X[1] = (this.getWidth() / 2) + (10 * wind);
                X[2] = ((this.getWidth() / 2) + (10 * wind) + 2);
                X[3] = ((this.getWidth() / 2) + (10 * wind) + 12);
                Y[0] = 60 - 50;
                Y[1] = 70 - 50;
                Y[2] = 72 - 50;
                Y[3] = 62 - 50;
                g.fillPolygon(X, Y, 4);
                X[0] = ((this.getWidth() / 2) + (10 * wind) + 10);
                X[1] = (this.getWidth() / 2) + (10 * wind);
                X[2] = ((this.getWidth() / 2) + (10 * wind) + 2);
                X[3] = ((this.getWidth() / 2) + (10 * wind) + 12);
                Y[0] = 83 - 50;
                Y[1] = 73 - 50;
                Y[2] = 71 - 50;
                Y[3] = 81 - 50;
                g.fillPolygon(X, Y, 4);
            }
        }
        //VIE
        g.setFont(font);
        g.setColor(Color.BLUE);
        g.drawString("" + lifeOfPlayer[0], 10, 30);
        g.setColor(Color.RED);
        g.drawString("" + lifeOfPlayer[1], 70, 30);
        g.setFont(font);
        g.setColor(Color.RED);
        g.drawString((time / 1000) + " s", this.getWidth() - 70, 30);
        //MODE DE VISEE
        if (weaponMod && !shoot) {
            g.setColor(Color.RED);
            //g.drawLine(XStart, YStart, XAim, YAim);
            g.fillOval((int) (XStart + Math.cos(angle) * 12) - 3, (int) (YStart + Math.sin(angle) * 12) - 3, 6, 6);
            g.fillOval((int) (XStart + Math.cos(angle) * 22) - 4, (int) (YStart + Math.sin(angle) * 22) - 4, 8, 8);
            g.fillOval((int) (XStart + Math.cos(angle) * 36) - 6, (int) (YStart + Math.sin(angle) * 36) - 6, 12, 12);
        }
        //PERTE DE VIE ANIME
        if (lifeAnim) {
            g.setFont(life);
            g.setColor(Color.ORANGE);
            if (alreadyDamage[0]) {
                int YS = (positionTete[0] / 50);
                int XS = positionTete[0] - (YS * 50);
                g.drawString("-" + lifeLoose[0], XS * 20, (YS * 20) - YLifeAnim);
            }
            if (alreadyDamage[1]) {
                int YS = (positionTete[1] / 50);
                int XS = positionTete[1] - (YS * 50);
                g.drawString("-" + lifeLoose[1], XS * 20, (YS * 20) - YLifeAnim);
            }
        }
        //DEPLACEMENT PROJECTILE
        if (shoot) {
            if (shootAnim) {
                g.setColor(Color.WHITE);
                g.fillOval(XShoot + XShootAnim, YShoot + YShootAnim, 9 + tailleAnim, 9 + tailleAnim);
                g.fillOval(XShoot + XShootAnim, YShoot - YShootAnim, 9 + tailleAnim, 9 + tailleAnim);
                g.fillOval(XShoot - XShootAnim, YShoot + YShootAnim, 9 + tailleAnim, 9 + tailleAnim);
                g.fillOval(XShoot - XShootAnim, YShoot - YShootAnim, 9 + tailleAnim, 9 + tailleAnim);
                //EXPLOSION
            } else {
                //Fumée
                g.setColor(Color.WHITE);
                int K = last;
                for (int i = 0; i < 10; i++) {
                    if (K > 9) {
                        K = 0;
                    }
                    //si on a bien rempli et que on est pas collé a la balle par le "last"
                    if (XCircle[K] != 0 && YCircle[K] != 0 && K != last) {
                        g.setColor(Color.BLACK);
                        g.fillOval(XCircle[K], YCircle[K], 10, 10);
                        g.setColor(Color.WHITE);
                        g.fillOval(XCircle[K] + 1, YCircle[K] + 1, 8, 8);
                    }
                    K++;
                }
                //Projectile (après la fumée sinon on ne le voit pas
                g.setColor(Color.BLACK);
                g.fillOval(XShoot, YShoot, 8, 8);
            }
        }
        //BARRE DE FORCE DE TIR
        if (setPower) {
            g.setColor(Color.BLACK);
            g.drawRect(this.getWidth() - 31, (this.getHeight() / 2) - 61, 11, 60 + 1);
            g.setColor(Color.RED);
            g.fillRect(this.getWidth() - 30, (this.getHeight() / 2) - (30 + power), 10, 60 - (30 - power));
        }
        //FIN DE PARTIE AFFICHE
        if (partyEnd) {
            if (looser == 0) {
                g.setFont(font);
                g.setColor(Color.RED);
                g.drawString("Red team win !", (this.getWidth() / 2) - 120, this.getHeight() / 2);
            } else if (looser == 1) {
                g.setFont(font);
                g.setColor(Color.BLUE);
                g.drawString("Blue team win !", (this.getWidth() / 2) - 120, this.getHeight() / 2);
            }
            //ARRET DE LA PARTIE
        } else {
            if (lifeOfPlayer[0] <= 0) {
                looser = 0;
                lifeOfPlayer[0] = 0;
                partyEnd = true;
                stopAmbiance();
                Game_Sound.playEnd();
            } else if (lifeOfPlayer[1] <= 0) {
                looser = 1;
                lifeOfPlayer[1] = 0;
                partyEnd = true;
                stopAmbiance();
                Game_Sound.playEnd();
            }
        }
        //WATER EFFECT
        for (int i = 0; i < size; i++) {
            g.setColor(bL);
            g.fillRect(i * 2, water[i] + 950, 2, 50);
            g.setColor(bC);
            g.fillRect(i * 2, water[i] + 950, 2, 3);
        }
    }
    //CALCULE ALEATOIREMENT LE VENT (reduit la force du tir)

    public int calculWind() {
        //entre -8 et 8
        return (int) ((Math.random() - 0.5) * 16);
    }
    //CALCUL ET TIR DE L'IA

    public class AnimIA extends Thread {

        public AnimIA() {
        }

        @Override
        public void run() {
            while (player == 1 && IA && viewParent.isVisible() && !shoot) {
                int Y0 = (positionPied[0] / 50);
                int Y1 = (positionPied[1] / 50);
                int X0 = positionPied[0] - (Y0 * 50);
                int X1 = positionPied[1] - (Y1 * 50);
                int DeltaX = X0 - X1;
                int DeltaY = Y0 - Y1;
                if (!weaponMod && !shoot) {
                    boolean okL = false;
                    boolean okR = false;
                    if (mapPos.containsKey((positionPied[1] + 101)) && mapPos.containsKey((positionPied[1] + 1)) && mapPos.get(positionPied[1] + 101) != 7 && mapPos.get(positionPied[1] + 101) != 14 && mapPos.get(positionPied[1] + 1) == 7 && mapPos.containsKey((positionTete[1] + 2)) && mapPos.get(positionTete[1] + 2) == 7) {
                        okR = true;
                    }
                    if (mapPos.containsKey((positionPied[1] + 99)) && mapPos.containsKey((positionPied[1] - 1)) && mapPos.get(positionPied[1] + 99) != 7 && mapPos.get(positionPied[1] + 99) != 14 && mapPos.get(positionPied[1] - 1) == 7 && mapPos.containsKey((positionTete[1] - 2)) && mapPos.get(positionTete[1] - 2) == 7) {
                        okL = true;
                    }
                    Calcul.MoveIA(DeltaX, DeltaY, okR, okL, weaponMod, wind, (int) (time / 1000));
                    try {
                        AnimIA.sleep(400);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Game_Calque.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (!shoot) {
                    double angleE = 0;
                    if ((Y0 - Y1) == 0) {
                        angleE = angle;
                    } else {
                        double w = 0;
                        double h = 0;
                        double hyp = 0;
                        w = Math.abs((X0 - X1));
                        h = Math.abs((Y0 - Y1));
                        hyp = Math.abs(Math.pow((Math.pow(h, 2) + Math.pow(w, 2)), 0.5));
                        angleE = Math.acos(w / hyp);
                        if (X0 > X1 && Y0 < Y1) {
                        } else if (X0 < X1 && Y0 < Y1) {
                            angleE = Math.PI - angleE;
                        } else if (X0 > X1 && Y0 > Y1) {
                            angleE = -angleE;
                        } else if (X0 < X1 && Y0 > Y1) {
                            angleE = Math.PI + angleE;
                        }
                    }
                    if (Double.isNaN(angleE)) {
                        angleE = angle;
                    }
                    if (Math.abs(angle) > (Math.PI / 2) && Math.abs(angleE) < (Math.PI / 2)) {
                        Move(KeyEvent.VK_SPACE);
                        Move(KeyEvent.VK_RIGHT);
                        Move(KeyEvent.VK_SPACE);
                    } else if (Math.abs(angle) < (Math.PI / 2) && Math.abs(angleE) > (Math.PI / 2)) {
                        Move(KeyEvent.VK_SPACE);
                        Move(KeyEvent.VK_LEFT);
                        Move(KeyEvent.VK_SPACE);
                    } else if (rightOrientation[player]) {
                        if (Math.abs(DeltaX) < Math.abs(DeltaY)) {
                            Calcul.ShootIA(-angle, angleE, weaponMod, wind, Math.abs(DeltaY * 20));
                        } else {
                            Calcul.ShootIA(-angle, angleE, weaponMod, wind, Math.abs(DeltaX * 20));
                        }
                    } else {
                        if (Math.abs(DeltaX) < Math.abs(DeltaY)) {
                            Calcul.ShootIA((Math.PI + (Math.PI - angle)), angleE, weaponMod, wind, Math.abs(DeltaY * 20));
                        } else {
                            Calcul.ShootIA((Math.PI + (Math.PI - angle)), angleE, weaponMod, wind, Math.abs(DeltaX * 20));
                        }
                    }
                    try {
                        AnimIA.sleep(100 + Calcul.getPowerOfShoot());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Game_Calque.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    //ICI ON RECEUILLE TOUTES LES ACTIONS SOIT DEPUIS LE CLAVIER SOIT DEPUIS LA CLASSE IA QUI ENVOIE LE CODE DES BONNES TOUCHES

    public void Move(int where) {
        if (rendering || partyEnd || fall) {
            return;
        }
        //ON PASSE LE TOUR
        if (where == KeyEvent.VK_P) {
            if (t != null) {
                t.pass();
            }
            return;
        }
        //ON PASSE EN MODE TIR
        if (where == KeyEvent.VK_SPACE && !shoot && !jump) {
            weaponMod = !weaponMod;
            YStart = (positionPied[player] / 50);
            XStart = positionPied[player] - (YStart * 50);
            YStart = YStart * 20;
            XStart = XStart * 20;
            if (rightOrientation[player]) {
                angle = 0;
                XStart += 10;
                XAim = XStart + 20;
                YAim = YStart;
            } else {
                XStart += 10;
                angle = Math.PI;
                XAim = XStart - 20;
                YAim = YStart;
            }
            this.repaint();
        }
        //ON SE DEPLACE SI ON EST PAS EN MODE TIR
        if (!weaponMod && !shoot) {
            if (where == KeyEvent.VK_UP) {
                //protection contre le double saut
                if (jp != null && jp.isAlive()) {
                } else {
                    //lancement du processus saut
                    jp = new Jump();
                    jp.start();
                }
            } else if (where == KeyEvent.VK_LEFT) {
                if (rightOrientation[player]) {
                    rightOrientation[player] = false;
                    this.repaint();
                } else {
                    Game_Sound.playWalk();
                    lastPositionPied[player] = positionPied[player];
                    positionPied[player] = positionPied[player] - 1;
                    lastPositionTete[player] = positionTete[player];
                    positionTete[player] = positionTete[player] - 1;
                    verifyAndTrace();
                    //on desactive la grativé tant que le saut n'est pas fini
                    //sinon probleme de saut sur obstacle
                    if (jp == null) {
                        gravity grav = new gravity();
                        grav.start();
                    } else if (!jp.isAlive()) {
                        gravity grav = new gravity();
                        grav.start();
                    }
                }
            } else if (where == KeyEvent.VK_RIGHT) {
                if (!rightOrientation[player]) {
                    rightOrientation[player] = true;
                    this.repaint();
                } else {
                    Game_Sound.playWalk();
                    lastPositionPied[player] = positionPied[player];
                    positionPied[player] = positionPied[player] + 1;
                    lastPositionTete[player] = positionTete[player];
                    positionTete[player] = positionTete[player] + 1;
                    verifyAndTrace();
                    //on desactive la grativé tant que le saut n'est pas fini
                    //sinon probleme de saut sur obstacle
                    if (jp == null) {
                        gravity grav = new gravity();
                        grav.start();
                    } else if (!jp.isAlive()) {
                        gravity grav = new gravity();
                        grav.start();
                    }
                }
            }
            //ON TIR SI ON EST EN MODE ATTAQUE
        } else if (!shoot) {
            if (where == KeyEvent.VK_ENTER) {
                if (pow == null || !pow.isAlive()) {
                    pow = new PowerOfShoot();
                    pow.start();
                }
                return;
            }
            if (rightOrientation[player]) {
                if (where == KeyEvent.VK_DOWN) {
                    angle += (Math.PI * 2 * 0.01);
                    if (angle > (Math.PI / 2) - (Math.PI * 2 * 0.02)) {
                        angle = (Math.PI / 2) - (Math.PI * 2 * 0.01);
                    }
                    XAim = (int) (XStart + Math.cos(angle) * 20);
                    YAim = (int) (YStart + Math.sin(angle) * 20);
                    this.repaint();
                } else if (where == KeyEvent.VK_UP) {
                    angle -= (Math.PI * 2 * 0.01);
                    if (angle < -(Math.PI / 2) + (Math.PI * 2 * 0.02)) {
                        angle = -(Math.PI / 2) + (Math.PI * 2 * 0.01);
                    }
                    XAim = (int) (XStart + Math.cos(angle) * 20);
                    YAim = (int) (YStart + Math.sin(angle) * 20);
                    this.repaint();
                }
            } else {
                if (where == KeyEvent.VK_DOWN) {
                    angle -= (Math.PI * 2 * 0.01);
                    if (angle < (Math.PI / 2) + (Math.PI * 2 * 0.02)) {
                        angle = (Math.PI / 2) + (Math.PI * 2 * 0.01);
                    }
                    XAim = (int) (XStart + Math.cos(angle) * 20);
                    YAim = (int) (YStart + Math.sin(angle) * 20);
                    this.repaint();
                } else if (where == KeyEvent.VK_UP) {
                    angle += (Math.PI * 2 * 0.01);
                    if (angle > (3 * Math.PI / 2) - (Math.PI * 2 * 0.02)) {
                        angle = (3 * Math.PI / 2) - (Math.PI * 2 * 0.01);
                    }
                    XAim = (int) (XStart + Math.cos(angle) * 20);
                    YAim = (int) (YStart + Math.sin(angle) * 20);
                    this.repaint();
                }
            }
        }
    }
    //POSITIONNEMENT ALEATOIRE JOUEUR 1

    public void RandomSet_1() {
        int position = 0;
        /**********PARTIE ALEATOIRE******************/
        //On essaye d'abord de cherche du sol avec deux cases en hauteur libres mais aleatoirement
        for (int i = 0; i < 10000; i++) {
            position = (int) (Math.random() * 2450);
            if (mapPos.containsKey(position) && mapPos.get(position) == 0) {
                if (mapPos.containsKey(position - 50) && mapPos.get(position - 50) == 7 && mapPos.containsKey(position - 100) && mapPos.get(position - 100) == 7) {
                    //On a le morceau existant avec deux libre au dessus, alors on met le personnage
                    positionPied[0] = (position - 50);
                    lastPositionPied[0] = (position - 50);
                    positionTete[0] = (position - 100);
                    lastPositionTete[0] = (position - 100);
                    verifyAndTrace();
                    return;
                }
            }
        }
        //ensuite avec du bloc full
        for (int i = 0; i < 10000; i++) {
            position = (int) (Math.random() * 2450);
            if (mapPos.containsKey(position) && mapPos.get(position) == 6) {
                if (mapPos.containsKey(position - 50) && mapPos.get(position - 50) == 7 && mapPos.containsKey(position - 100) && mapPos.get(position - 100) == 7) {
                    //On a le morceau existant avec deux libre au dessus, alors on met le personnage
                    positionPied[0] = (position - 50);
                    lastPositionPied[0] = (position - 50);
                    positionTete[0] = (position - 100);
                    lastPositionTete[0] = (position - 100);
                    verifyAndTrace();
                    return;
                }
            }
        }
        /**********FIN ALEATOIRE******************/
        /**********PARTIE EMPIRIQUE******************/
        //Sinon méthode sans aleatoire ou on regarde chaque bloc
        for (int i = 0; i < 2450; i++) {
            if (mapPos.containsKey(i) && mapPos.get(i) == 0 || mapPos.containsKey(i) && mapPos.get(i) == 6) {
                if (mapPos.containsKey(i - 50) && mapPos.get(i - 50) == 7 && mapPos.containsKey(i - 100) && mapPos.get(i - 100) == 7) {
                    //On a le morceau existant avec deux libre au dessus, alors on met le personnage
                    positionPied[0] = (i - 50);
                    lastPositionPied[0] = (i - 50);
                    positionTete[0] = (i - 100);
                    lastPositionTete[0] = (i - 100);
                    verifyAndTrace();
                    return;
                }
            }
        }
        //On ne trouve rien, on ferme
        int option = JOptionPane.showConfirmDialog(null, "This map has no place to set player.\nYou need to set two bloc 7 vertically in the map editor", "No place find", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            System.exit(0);
        }

    }

    //POSITIONNEMENT ALEATOIRE JOUEUR 2
    public void RandomSet_2() {
        int position = 0;
        /**********PARTIE ALEATOIRE******************/
        //On essaye d'abord de cherche du sol avec deux cases en hauteur libres mais aleatoirement
        for (int i = 0; i < 10000; i++) {
            position = (int) (Math.random() * 2450);
            if (mapPos.containsKey(position) && mapPos.get(position) == 0) {
                if (mapPos.containsKey(position - 50) && mapPos.get(position - 50) == 7 && mapPos.containsKey(position - 100) && mapPos.get(position - 100) == 7) {
                    //On a le morceau existant avec deux libre au dessus, alors on met le personnage
                    positionPied[1] = (position - 50);
                    lastPositionPied[1] = (position - 50);
                    positionTete[1] = (position - 100);
                    lastPositionTete[1] = (position - 100);
                    verifyAndTrace();
                    return;
                }
            }
        }
        //ensuite avec du bloc full
        for (int i = 0; i < 10000; i++) {
            position = (int) (Math.random() * 2450);
            if (mapPos.containsKey(position) && mapPos.get(position) == 6) {
                if (mapPos.containsKey(position - 50) && mapPos.get(position - 50) == 7 && mapPos.containsKey(position - 100) && mapPos.get(position - 100) == 7) {
                    //On a le morceau existant avec deux libre au dessus, alors on met le personnage
                    positionPied[1] = (position - 50);
                    lastPositionPied[1] = (position - 50);
                    positionTete[1] = (position - 100);
                    lastPositionTete[1] = (position - 100);
                    verifyAndTrace();
                    return;
                }
            }
        }
        /**********FIN ALEATOIRE******************/
        /**********PARTIE EMPIRIQUE******************/
        //Sinon méthode sans aleatoire ou on regarde chaque bloc
        for (int i = 0; i < 2450; i++) {
            if (mapPos.containsKey(i) && mapPos.get(i) == 0 || mapPos.containsKey(i) && mapPos.get(i) == 6) {
                if (mapPos.containsKey(i - 50) && mapPos.get(i - 50) == 7 && mapPos.containsKey(i - 100) && mapPos.get(i - 100) == 7) {
                    //On a le morceau existant avec deux libre au dessus, alors on met le personnage
                    positionPied[1] = (i - 50);
                    lastPositionPied[1] = (i - 50);
                    positionTete[1] = (i - 100);
                    lastPositionTete[1] = (i - 100);
                    verifyAndTrace();
                    return;
                }
            }
        }
        //On ne trouve rien, on ferme
        int option = JOptionPane.showConfirmDialog(null, "This map has no place to set player.\nYou need to set two bloc 7 vertically in the map editor", "No place find", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }
    //ANIMATION DU SAUT DU JOUEUR

    public class Jump extends Thread {

        public Jump() {
        }

        @Override
        public void run() {
            jump = true;
            Game_Sound.playJump();
            boolean indic = false;
            for (int i = 0; i < 10; i++) {
                if (i < 5) {
                    if (indic) {
                        indic = false;
                        lastPositionPied[player] = positionPied[player];
                        positionPied[player] = positionPied[player] - 50;
                        lastPositionTete[player] = positionTete[player];
                        positionTete[player] = positionTete[player] - 50;
                        verifyAndTrace();
                    } else {
                        indic = true;
                    }
                } else {
                    if (indic) {
                        indic = false;
                        lastPositionPied[player] = positionPied[player];
                        positionPied[player] = positionPied[player] + 50;
                        lastPositionTete[player] = positionTete[player];
                        positionTete[player] = positionTete[player] + 50;
                        verifyAndTrace();
                    } else {
                        indic = true;
                    }
                }
                //evite la pause du dernier, ce qui ralenti le process pour rien car le saut est fini
                if (i >= 9) {
                    break;
                }
                try {
                    Jump.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game_Calque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            gravity g = new gravity();
            jump = false;
            g.start();
        }
    }

    //contient la mise à jour des map en fonction des déplacements
    public void verifyAndTrace() {
        //premier coup [player]
        if (!firstUse[0] && positionPied[0] == lastPositionPied[0] && positionTete[0] == lastPositionTete[0]) {
            mapPos.put(positionPied[0], 11);
            mapPos.put(positionTete[0], 10);
            firstUse[0] = true;
            this.repaint();
            return;
        }
        //premier coup [1]
        if (!firstUse[1] && positionPied[1] == lastPositionPied[1] && positionTete[1] == lastPositionTete[1]) {
            mapPos.put(positionPied[1], 13);
            mapPos.put(positionTete[1], 12);
            firstUse[1] = true;
            this.repaint();
            return;
        }
        //gauche ou droite
        if ((positionPied[player] - lastPositionPied[player]) == 1 || (positionPied[player] - lastPositionPied[player]) == -1) {
            if (mapPos.get(positionTete[player]) != 7 || mapPos.get(positionPied[player]) != 7) {
                positionPied[player] = lastPositionPied[player];
                positionTete[player] = lastPositionTete[player];
                return;
            }
            int yD = lastPositionPied[player] / 50;
            int yA = positionPied[player] / 50;
            //si on va en dehors a droite ou a gauche on ne sera plus sur le même Y
            if (yD != yA) {
                positionPied[player] = lastPositionPied[player];
                positionTete[player] = lastPositionTete[player];
                return;
            }
            //Sinon on peut bouger
            if (player == 0) {
                mapPos.put(positionTete[player], 10);
                mapPos.put(lastPositionTete[player], 7);
                mapPos.put(positionPied[player], 11);
                mapPos.put(lastPositionPied[player], 7);
            } else {
                mapPos.put(positionTete[player], 12);
                mapPos.put(lastPositionTete[player], 7);
                mapPos.put(positionPied[player], 13);
                mapPos.put(lastPositionPied[player], 7);
            }
        } //vers le haut
        else if ((lastPositionTete[player] - positionTete[player]) == 50) {
            if (mapPos.get(positionTete[player]) != 7) {
                positionPied[player] = lastPositionPied[player];
                positionTete[player] = lastPositionTete[player];
                return;
            }
            if (player == 0) {
                mapPos.put(positionTete[player], 10);
                mapPos.put(lastPositionTete[player], 7);
                mapPos.put(positionPied[player], 11);
                mapPos.put(lastPositionPied[player], 7);
            } else {
                mapPos.put(positionTete[player], 12);
                mapPos.put(lastPositionTete[player], 7);
                mapPos.put(positionPied[player], 13);
                mapPos.put(lastPositionPied[player], 7);
            }
            //vers le bas
        } else if ((lastPositionTete[player] - positionTete[player]) == -50) {
            if (mapPos.get(positionPied[player]) != 7) {
                positionPied[player] = lastPositionPied[player];
                positionTete[player] = lastPositionTete[player];
                return;
            }
            if (player == 0) {
                mapPos.put(positionPied[player], 11);
                mapPos.put(lastPositionPied[player], 7);
                mapPos.put(positionTete[player], 10);
                mapPos.put(lastPositionTete[player], 7);
            } else {
                mapPos.put(positionPied[player], 13);
                mapPos.put(lastPositionPied[player], 7);
                mapPos.put(positionTete[player], 12);
                mapPos.put(lastPositionTete[player], 7);
            }
        }
        //Trace
        this.repaint();
    }
    //GESTION DE LA GRAVITEE, ON VERIFIE QUON TOUCHE DU "SOL" SINON ON DESCEND

    public class gravity extends Thread {

        @Override
        public void run() {
            fall = true;
            repaint();
            for (int i = 0; i < 2; i++) {
                int chute = 0;
                while ((positionPied[i] + 50 < 2450) && (mapPos.get(positionPied[i] + 50) == 7 || mapPos.get(positionPied[i] + 50) == 14)) {
                    if (mapPos.get(positionPied[i] + 50) == 14) {
                        lifeLoose[i] = lifeOfPlayer[i];
                        lifeOfPlayer[i] = 0;
                        playerShoot = i;
                        lifeAnimThread loose = new lifeAnimThread();
                        loose.start();
                        fall = false;
                        return;
                    }
                    lastPositionPied[i] = positionPied[i];
                    positionPied[i] += 50;
                    lastPositionTete[i] = positionTete[i];
                    positionTete[i] = positionTete[i] + 50;
                    if (i == 0) {
                        mapPos.put(positionPied[i], 11);
                        mapPos.put(lastPositionPied[i], 7);
                        mapPos.put(positionTete[i], 10);
                        mapPos.put(lastPositionTete[i], 7);
                    } else {
                        mapPos.put(positionPied[i], 13);
                        mapPos.put(lastPositionPied[i], 7);
                        mapPos.put(positionTete[i], 12);
                        mapPos.put(lastPositionTete[i], 7);
                    }
                    repaint();
                    chute++;
                    try {
                        gravity.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Game_Calque.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (chute > 3) {
                    Game_Sound.playFall();
                    alreadyDamage[i] = true;
                    lifeOfPlayer[i] -= 10;
                    lifeLoose[i] = 10;
                    playerShoot = i;
                    lifeAnimThread loose = new lifeAnimThread();
                    loose.start();
                    //Il faut que ce soit le joueur jouant qui tombe pour changer
                    if (player == 0 && i == 0) {
                        Game_Sound.playNext();
                        player = 1;
                        if (IA) {
                            if (animationIA == null) {
                                animationIA = new AnimIA();
                                animationIA.start();
                            } else if (!animationIA.isAlive()) {
                                animationIA = new AnimIA();
                                animationIA.start();
                            }
                        }
                    } else if (player == 1 && i == 1) {
                        Game_Sound.playNext();
                        player = 0;
                    }
                }
            }
            fall = false;
        }
    }
    //ANIMATION DE LA BARRE DE FORCE

    public class PowerOfShoot extends Thread {

        boolean down = false;

        public PowerOfShoot() {
        }

        @Override
        synchronized public void run() {
            setPower = true;
            power = -30;
            while (setPower && viewParent.isVisible()) {
                if (power >= 30) {
                    down = true;
                } else if (power <= -30) {
                    down = false;
                }
                if (!down) {
                    power++;
                } else {
                    power--;
                }
                repaint();
                try {
                    PowerOfShoot.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game_Calque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Missile mis = new Missile();
            mis.start();
            weaponMod = !weaponMod;
        }
    }
    //GESTION DU TEMPS DES JOUEURS

    public class TimeCount extends Thread {

        long begin = 0;
        boolean wait = false;

        public TimeCount() {
            time = 0;
            begin = 0;
            wait = false;
            indice = 0;
        }

        public void pass() {
            begin = 0;
        }

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

        @Override
        public void run() {
            begin = System.currentTimeMillis();
            time = 25000;
            wind = calculWind();
            while (!partyEnd && viewParent.isVisible()) {
                setValue();
                repaint();
                if (shoot || lifeAnim) {
                    begin = System.currentTimeMillis();
                    time = 25000;
                    wait = true;
                } else {
                    if (wait) {
                        wind = calculWind();
                        wait = false;
                    }
                    time = 25000 - (System.currentTimeMillis() - begin);
                    if (time < 0) {
                        if (player == 0) {
                            Game_Sound.playNext();
                            player = 1;
                            if (IA) {
                                if (animationIA == null) {
                                    animationIA = new AnimIA();
                                    animationIA.start();
                                } else if (!animationIA.isAlive()) {
                                    animationIA = new AnimIA();
                                    animationIA.start();
                                }
                            }
                        } else {
                            Game_Sound.playNext();
                            player = 0;
                        }
                        if (weaponMod) {
                            weaponMod = false;
                            repaint();
                        }
                        begin = System.currentTimeMillis();
                        time = 25000;
                        wind = calculWind();
                    }
                }
                repaint();
                try {
                    TimeCount.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game_Calque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    //ANIMATION DE LA VIE QUI MONTE

    public class lifeAnimThread extends Thread {

        public lifeAnimThread() {
        }

        @Override
        public void run() {
            lifeAnim = true;
            for (int c = 0; c < 3; c++) {
                YLifeAnim = (c * 6) + 10;
                repaint();
                //Pause generale
                try {
                    lifeAnimThread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game_Calque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            lifeAnim = false;
            alreadyDamage[0] = false;
            alreadyDamage[1] = false;
            repaint();
        }
    }
    //ANIMATION DU MISSILE

    public class Missile extends Thread {

        int X;
        int Y;
        float a;
        int yD;
        int xD;

        public Missile() {
            shoot = true;
            alreadyDamage[0] = false;
            alreadyDamage[1] = false;
            V0 = 50;
            for (int i = 0; i < 10; i++) {
                XCircle[i] = 0;
                YCircle[i] = 0;
                last = 0;
            }
        }

        @Override
        public void run() {
            Game_Sound.playShoot();
            for (avanceMissil = 0; avanceMissil < 10000; avanceMissil = avanceMissil + 2) {
                if (avanceMissil > 0 && avanceMissil % 16 == 0) {
                    if (last > 9) {
                        last = 0;
                    }
                    XCircle[last] = (int) (XShoot + ((Math.random() - 0.5) * 3));
                    YCircle[last] = (int) (YShoot + ((Math.random() - 0.5) * 3));
                    last++;
                }
                XShoot = (int) (Math.cos(angle) * avanceMissil);
                //prise en compte du vent
                if (wind <= 0 && rightOrientation[player]) {
                    V0 = (int) (50 + (3 * wind));
                } else if (wind <= 0 && !rightOrientation[player]) {
                    V0 = (int) (50 - (3 * wind));
                } else if (wind > 0 && rightOrientation[player]) {
                    V0 = (int) (50 + (3 * wind));
                } else if (wind > 0 && !rightOrientation[player]) {
                    V0 = (int) (50 - (3 * wind));
                }
                V0 += ((power + 15) * 2);

                YShoot = (int) ((((-0.5 * 9.8 * Math.pow(XShoot, 2)) / (Math.pow(V0, 2) * Math.pow(Math.cos(angle), 2))) + (XShoot * Math.tan(-angle))));
                g = YShoot;
                XShoot += XStart;
                YShoot = YStart - YShoot;
                Y = (int) YShoot / 20;
                X = (int) XShoot / 20;
                if (X < 0 || Y < 0 || X > 49 || Y > 49) {
                    shoot = false;
                    repaint();
                    if (player == 0) {
                        Game_Sound.playNext();
                        player = 1;
                        if (IA) {
                            if (animationIA == null) {
                                animationIA = new AnimIA();
                                animationIA.start();
                            } else if (!animationIA.isAlive()) {
                                animationIA = new AnimIA();
                                animationIA.start();
                            }
                        }
                    } else {
                        Game_Sound.playNext();
                        player = 0;
                    }
                    return;
                }
                if (player == 0 && mapPos.get((Y * 50) + X) != 10 && mapPos.get((Y * 50) + X) != 11 && mapPos.get((Y * 50) + X) != 7) {
                    break;
                } else if (player == 1 && mapPos.get((Y * 50) + X) != 12 && mapPos.get((Y * 50) + X) != 13 && mapPos.get((Y * 50) + X) != 7) {
                    break;
                }
                repaint();
                try {
                    Missile.sleep(5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game_Calque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (mapPos.get((Y * 50) + X) == 10 || mapPos.get((Y * 50) + X) == 11) {
                if (!alreadyDamage[0]) {
                    lifeOfPlayer[0] -= 30;
                    lifeLoose[0] = 30;
                }
                alreadyDamage[0] = true;
            } else if (mapPos.get((Y * 50) + X) == 12 || mapPos.get((Y * 50) + X) == 13) {
                if (!alreadyDamage[1]) {
                    lifeOfPlayer[1] -= 30;
                    lifeLoose[1] = 30;
                }
                alreadyDamage[1] = true;
            } else if (mapPos.get((Y * 50) + X) != 14) {
                mapPos.put((Y * 50) + X, 7);
            }
            if (mapPos.containsKey(((Y * 50) + X) + 50) && (mapPos.get(((Y * 50) + X) + 50) == 10 || mapPos.get(((Y * 50) + X) + 50) == 11)) {
                if (!alreadyDamage[0]) {
                    lifeOfPlayer[0] -= 20;
                    lifeLoose[0] = 20;
                }
                alreadyDamage[0] = true;
            } else if (mapPos.containsKey(((Y * 50) + X) + 50) && (mapPos.get(((Y * 50) + X) + 50) == 12 || mapPos.get(((Y * 50) + X) + 50) == 13)) {
                if (!alreadyDamage[1]) {
                    lifeOfPlayer[1] -= 20;
                    lifeLoose[1] = 20;
                }
                alreadyDamage[1] = true;
            } else if (mapPos.containsKey(((Y * 50) + X) + 50) && mapPos.get(((Y * 50) + X) + 50) != 14) {
                mapPos.put(((Y * 50) + X) + 50, 7);
            }
            if (mapPos.containsKey(((Y * 50) + X) - 50) && (mapPos.get(((Y * 50) + X) - 50) == 10 || mapPos.get(((Y * 50) + X) - 50) == 11)) {
                if (!alreadyDamage[0]) {
                    lifeOfPlayer[0] -= 20;
                    lifeLoose[0] = 20;
                }
                alreadyDamage[0] = true;
            } else if (mapPos.containsKey(((Y * 50) + X) - 50) && (mapPos.get(((Y * 50) + X) - 50) == 12 || mapPos.get(((Y * 50) + X) - 50) == 13)) {
                if (!alreadyDamage[1]) {
                    lifeOfPlayer[1] -= 20;
                    lifeLoose[1] = 20;
                }
                alreadyDamage[1] = true;
            } else if (mapPos.containsKey(((Y * 50) + X) - 50) && mapPos.get(((Y * 50) + X) - 50) != 14) {
                mapPos.put(((Y * 50) + X) - 50, 7);
            }
            if (mapPos.containsKey(((Y * 50) + X) + 1) && (mapPos.get(((Y * 50) + X) + 1) == 10 || mapPos.get(((Y * 50) + X) + 1) == 11)) {
                if (!alreadyDamage[0]) {
                    lifeOfPlayer[0] -= 20;
                    lifeLoose[0] = 20;
                }
                alreadyDamage[0] = true;
            } else if (mapPos.containsKey(((Y * 50) + X) + 1) && (mapPos.get(((Y * 50) + X) + 1) == 12 || mapPos.get(((Y * 50) + X) + 1) == 13)) {
                if (!alreadyDamage[1]) {
                    lifeOfPlayer[1] -= 20;
                    lifeLoose[1] = 20;
                }
                alreadyDamage[1] = true;
            } else if (mapPos.containsKey(((Y * 50) + X) + 1) && mapPos.get(((Y * 50) + X) + 1) != 14) {
                mapPos.put(((Y * 50) + X) + 1, 7);
            }
            if (mapPos.containsKey(((Y * 50) + X) - 1) && (mapPos.get(((Y * 50) + X) - 1) == 10 || mapPos.get(((Y * 50) + X) - 1) == 11)) {
                if (!alreadyDamage[0]) {
                    lifeOfPlayer[0] -= 20;
                    lifeLoose[0] = 20;
                }
                alreadyDamage[0] = true;
            } else if (mapPos.containsKey(((Y * 50) + X) - 1) && (mapPos.get(((Y * 50) + X) - 1) == 12 || mapPos.get(((Y * 50) + X) - 1) == 13)) {
                if (!alreadyDamage[1]) {
                    lifeOfPlayer[1] -= 20;
                    lifeLoose[1] = 20;
                }
                alreadyDamage[1] = true;
            } else if (mapPos.containsKey(((Y * 50) + X) - 1) && mapPos.get(((Y * 50) + X) - 1) != 14) {
                mapPos.put(((Y * 50) + X) - 1, 7);
            }
            shootAnim = true;
            //nuage d'explosion
            Game_Sound.playExplo();
            for (int i = 0; i < 3; i++) {
                tailleAnim = i * 3;
                XShootAnim = (i * 6) + 5;
                YShootAnim = (i * 6) + 5;
                repaint();
                try {
                    Missile.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game_Calque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            shootAnim = false;
            gravity g = new gravity();
            shoot = false;
            g.start();
            try {
                g.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Game_Calque.class.getName()).log(Level.SEVERE, null, ex);
            }
            //vie qui défile au dessus du joueur
            lifeAnimThread loose = new lifeAnimThread();
            loose.start();
            if (player == 0) {
                Game_Sound.playNext();
                player = 1;
                if (IA) {
                    if (animationIA == null) {
                        animationIA = new AnimIA();
                        animationIA.start();
                    } else if (!animationIA.isAlive()) {
                        animationIA = new AnimIA();
                        animationIA.start();
                    }
                }
            } else {
                Game_Sound.playNext();
                player = 0;
            }
            repaint();
        }
    }

    public boolean isSetPower() {
        return setPower;
    }

    public void setSetPower(boolean setPower) {
        this.setPower = setPower;
    }
    //indique si c'est au tour de l'IA ou non

    public boolean isIA() {
        if (IA && player == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void stopAmbiance() {
        Game_Sound.stopAmbiance();
    }
}
