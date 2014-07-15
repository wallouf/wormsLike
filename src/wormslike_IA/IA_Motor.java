/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Motor.java
 *
 * Created on 25 juil. 2011, 10:18:49
 */
package wormslike_IA;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import wormslike_game.Game_Calque;

/**
 *
 * @author wallouf
 */
public class IA_Motor extends javax.swing.JFrame {

    public HashMap<Integer, String> regles;
    public HashMap<Integer, String> fait;
    public HashMap<Integer, String> deduction;
    //donne l'ordre de la touche
    public HashMap<String, Integer> resultats;
    Game_Calque viewParent;
    public boolean shoot = false;
    public boolean weaponMod = false;
    public int up = 0;
    public int down = 0;
    private long powerOfShoot = 0;
    private int far = 0;
    int appel = 0;

    /** Creates new form Motor */
    public IA_Motor(Game_Calque viewParent) {
        this.viewParent = viewParent;
        regles = new HashMap();
        initComponents();
        this.setTitle("Chainage avant & arriere : Moteur d'inference");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(false);
        jPanelMotor.setVisible(false);
        setGameRules();
        fait = new HashMap();
        resultats = new HashMap();
        resultats.put("G", KeyEvent.VK_LEFT);
        resultats.put("D", KeyEvent.VK_RIGHT);
        resultats.put("A", KeyEvent.VK_SPACE);
        resultats.put("W", KeyEvent.VK_SPACE);
        deduction = new HashMap();
        up = 0;
        down = 0;
        powerOfShoot = 0;
        far = (int) (Math.random() * 10);
    }

    /** Creates new form Motor */
    //CONSTRUCTEUR POUR TESTER LES REGLES ET LE MOTEUR DEPUIS SETTING
    public IA_Motor() {
        initComponents();
        regles = new HashMap();
        jPanelMotor.setVisible(false);
        this.setTitle("Chainage avant & arriere : Moteur d'inference");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(false);
        setGameRules();
        fait = new HashMap();
        resultats = new HashMap();
        resultats.put("G", KeyEvent.VK_LEFT);
        resultats.put("D", KeyEvent.VK_RIGHT);
        resultats.put("A", KeyEvent.VK_SPACE);
        resultats.put("W", KeyEvent.VK_SPACE);
        deduction = new HashMap();
        up = 0;
        down = 0;
        powerOfShoot = 0;
        far = (int) (Math.random() * 10);
    }
    //REGLE DU MOTEUR DU JEU

    private void setGameRules() {
        regles.clear();
        //PRECISER LES REGLES ICI
        //move part
        regles.put(0, "D&R&M=A");
        regles.put(1, "G&L&M=A");
        regles.put(2, "D&M=D");
        regles.put(3, "G&M=G");
        regles.put(4, "T&M=A");
        //shoot part
        regles.put(5, "O&W=E");
        regles.put(6, "S&W=U");
        regles.put(7, "I&W=N");
        regles.put(8, "F&W=P");
    }

    //REGLE DU MOTEUR A PARTIR DU TESTING PANEL
    private boolean setGameRulesFromPanel() {
        regles.clear();
        if (jTextAreaTestRules.getText().isEmpty()) {
            return false;
        }
        String str = jTextAreaTestRules.getText();
        str = str.replaceAll(" ", "");
        for (int i = 0; i < jTextAreaTestRules.getLineCount(); i++) {
            int posOfRetourChariot = str.indexOf("\n");
            if (i == jTextAreaTestRules.getLineCount() - 1) {
                regles.put(i, str);
                break;
            } else if (posOfRetourChariot <= 0) {
                System.out.println("posChariot : " + posOfRetourChariot);
                return false;
            }
            regles.put(i, str.substring(0, posOfRetourChariot));
            str = str.substring(posOfRetourChariot+1);
            System.out.println("str : " + str);
        }
        System.out.println("" + regles);
        return true;
    }

    public void MoveIA(int posX, int posY, boolean okR, boolean okL, boolean weapon, int wind, int timeLeft) {
        deduction.clear();
        fait.clear();
        //ON RENTRE TOUT LES FAITS
        if (weapon) {
            fait.put(fait.size(), "W");
        } else {
            fait.put(fait.size(), "M");
        }
        //On s'approche du joueur aleatoirement à 10 cases prés (arrêt avant si vide de plus d'une case)
        if (posX >= 0) {
            fait.put(fait.size(), "D");
            if (!okR || Math.abs(posX) <= far) {
                fait.put(fait.size(), "R");
            }
            //On s'approche du joueur aleatoirement à 10 cases prés (arrêt avant si vide de plus d'une case)
        } else {
            fait.put(fait.size(), "G");
            if (!okL || Math.abs(posX) <= far) {
                fait.put(fait.size(), "L");
            }
        }
        //si temps inférieur a 5 on passe le tour
        if (timeLeft <= 5) {
            fait.put(fait.size(), "T");
        }
        //APPEL DU MOTEUR POUR TIRER LES CONCLUSIONS
        RecurAvant();
        if (!deduction.isEmpty() && deduction.containsValue("A")) {
            viewParent.Move(KeyEvent.VK_SPACE);
            up = 0;
            down = 0;
            powerOfShoot = 0;
        } else if (!deduction.isEmpty() && deduction.containsValue("G")) {
            viewParent.Move(KeyEvent.VK_LEFT);
        } else if (!deduction.isEmpty() && deduction.containsValue("D")) {
            viewParent.Move(KeyEvent.VK_RIGHT);
        }
    }
    //Lorsqu'on est en mode shoot

    public void ShootIA(double angleViseur, double angleE, boolean weapon, int wind, int distance) {
        deduction.clear();
        fait.clear();
        far = (int) (Math.random() * 10)+1;
        //ON RENTRE TOUT LES FAITS
        if (weapon) {
            fait.put(fait.size(), "W");
        } else {
            fait.put(fait.size(), "M");
        }//Si on a deja lancé le mode de tir 
        if (shoot) {
            fait.put(fait.size(), "O");
            //Si on vise en l'air
        } else if (Math.abs(angleViseur) >= ((Math.PI / 2) - (Math.PI / 2 * 0.04)) && Math.abs(angleViseur) <= ((Math.PI / 2) + (Math.PI / 2 * 0.04))) {
            fait.put(fait.size(), "O");
            //on vise a gauche
        } else if (down >= 2 && up >= 2) {
            fait.put(fait.size(), "O");
            //Si on bloque en l'air on tire
        } else if ((Math.abs(angleViseur) > Math.abs(angleE - (angleE * 0.02))) && (Math.abs(angleViseur) < Math.abs(angleE + (angleE * 0.02)))) {
            fait.put(fait.size(), "O");
            //si on regarde a gauche
        } else if (angleViseur > (Math.PI / 2)) {
            if (angleViseur - angleE > 0) {
                fait.put(fait.size(), "S");
            } else {
                fait.put(fait.size(), "I");
            }
            if (angleE > 4.35 || angleE == (Math.PI / 2)) {
                fait.put(fait.size(), "F");
            }
            //si on regarde a droite
        } else {
            if (angleViseur - angleE > 0) {
                fait.put(fait.size(), "I");
            } else {
                fait.put(fait.size(), "S");
            }
            if (angleE < -1.15) {
                fait.put(fait.size(), "F");
            }
        }
        //APPEL DU MOTEUR POUR TIRER LES CONCLUSIONS
        RecurAvant();
        //Si on doit passer le tour
        if (!deduction.isEmpty() && deduction.containsValue("P")) {
            viewParent.Move(KeyEvent.VK_P);
            //Si on doit tirer
        } else if (!deduction.isEmpty() && deduction.containsValue("E")) {
            if (!shoot) {
                shoot = true;
                //REHAUSSE DE LA TRAJECTOIRE A CAUSE DU PARABOLISME
                viewParent.Move(KeyEvent.VK_UP);
                viewParent.Move(KeyEvent.VK_UP);
                if (distance > 600) {
                    powerOfShoot = 840;
                } else {
                    powerOfShoot = (long) (distance * 1.4);
                }
                viewParent.Move(KeyEvent.VK_ENTER);
            } else {
                viewParent.setSetPower(false);
                shoot = false;
                powerOfShoot = 0;
            }
            //Si on doit monter le viseur
        } else if (!deduction.isEmpty() && deduction.containsValue("U")) {
            viewParent.Move(KeyEvent.VK_UP);
            up++;
            //Si on descendre monter le viseur
        } else if (!deduction.isEmpty() && deduction.containsValue("N")) {
            viewParent.Move(KeyEvent.VK_DOWN);
            down++;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaResults = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldEntry = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButtonAvant = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButtonArriere = new javax.swing.JButton();
        jPanelMotor = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaTestRules = new javax.swing.JTextArea();
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(702, 589));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextAreaResults.setColumns(20);
        jTextAreaResults.setRows(5);
        jScrollPane1.setViewportView(jTextAreaResults);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(409, 56, 281, 446));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12));
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("RESULTATS : ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(409, 35, 281, -1));
        getContentPane().add(jTextFieldEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 56, 287, 39));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12));
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Faits :");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 35, 287, -1));

        jButtonAvant.setText("Launch Avant");
        jButtonAvant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAvantActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonAvant, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, -1, -1));

        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 540, 62, -1));

        jButtonArriere.setText("Launch Arriere");
        jButtonArriere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonArriereActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonArriere, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 540, -1, -1));

        jPanelMotor.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Testing motor panel (entrez vos propres régles par ligne)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 0, 0))); // NOI18N

        jTextAreaTestRules.setColumns(20);
        jTextAreaTestRules.setRows(5);
        jTextAreaTestRules.setToolTipText("FORMAT : A&B&C=D");
        jScrollPane2.setViewportView(jTextAreaTestRules);

        javax.swing.GroupLayout jPanelMotorLayout = new javax.swing.GroupLayout(jPanelMotor);
        jPanelMotor.setLayout(jPanelMotorLayout);
        jPanelMotorLayout.setHorizontalGroup(
            jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
        );
        jPanelMotorLayout.setVerticalGroup(
            jPanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
        );

        getContentPane().add(jPanelMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 142, -1, -1));

        jCheckBox1.setText("Testing mode ?");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 112, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButtonAvantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAvantActionPerformed
        if (jCheckBox1.isSelected()) {
            if (!setGameRulesFromPanel()) {
                return;
            }
        } else {
            setGameRules();
        }
        jTextAreaResults.setText("");
        deduction.clear();
        fait.clear();
        searchAndFindRecurAvant();
    }//GEN-LAST:event_jButtonAvantActionPerformed

    private void jButtonArriereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonArriereActionPerformed
        if (jCheckBox1.isSelected()) {
            if (!setGameRulesFromPanel()) {
                return;
            }
        } else {
            setGameRules();
        }
        appel = 0;
        jTextAreaResults.setText("");
        deduction.clear();
        fait.clear();
        searchAndFindRecurArriere();
    }//GEN-LAST:event_jButtonArriereActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
            jPanelMotor.setVisible(true);
        } else {
            jPanelMotor.setVisible(false);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    //descend au plus bas et verifi si on l'as dans les faits
    public boolean checkArriere(String input) {
        boolean res = false;
        boolean floor = true;
        if (fait.containsValue(input)) {
            res = true;
            appel = 0;
            return res;
        }
        for (int i = 0; i < regles.size(); i++) {
            //Si on est pas tout en bas, on descend
            if (input.equalsIgnoreCase("" + regles.get(i).charAt(regles.get(i).length() - 1))) {
                floor = false;
                String in = regles.get(i).replaceAll(" ", "");
                in = in.replaceAll("&", "");
                appel++;
                //compte le nombre de parametres de la fonction regle
                int nbOfComp = in.indexOf("=");
                int valid = 0;
                for (int j = 0; j < nbOfComp; j++) {
                    if (appel > 200) {
                        return false;
                    }
                    if (checkArriere("" + in.charAt(j))) {
                        valid++;
                    }
                }
                //Si tout les parametre sont ok en remontant on valide
                if (valid == nbOfComp) {
                    res = true;
                    fait.put(fait.size(), input);
                }
            }
        }
        //Si on est tout en bas et que l'on a le fait on valide et on remonte
        if (floor && fait.containsValue(input)) {
            res = true;
            fait.put(fait.size(), input);
        }
        appel = 0;
        return res;
    }

    //FORMAT DENTRE X/X/X/X
    //CHAINAGE AVANT
    public void RecurAvant() {
        //for (int i = 0; i < nbOfCase; i++) {
        int find = 0;
        int findBefore = 0;
        int[] c = new int[regles.size()];
        for (int j = 0; j < regles.size(); j++) {
            c[j] = j;
        }
        String in = "";
        do {
            findBefore = find;
            find = 0;
            for (int j = 0; j < regles.size(); j++) {
                //Si on a deja valide cette regle, on passe
                if (c[j] == -1) {
                    continue;
                }
                //on enleve les espaces, et les '&'
                in = regles.get(j).replaceAll(" ", "");
                in = in.replaceAll("&", "");
                //compte le nombre de parametres de la fonction regle
                int nbOfComp = in.indexOf("=");
                //permet de savoir si on a validé les parametre de la fonction regle
                int valid = 0;
                //SELON LE NOMBRE DE PARAMETRE DE LA FONCTION REGLE
                for (int k = 0; k < nbOfComp; k++) {
                    boolean up = false;
                    //DEPUIS LES FAITS
                    for (int i = 0; i < fait.size(); i++) {
                        if (fait.get(i).equalsIgnoreCase("" + in.charAt(k))) {
                            //on ne retourne plus sur cette regle car valide
                            valid++;
                            up = true;
                            break;
                        }
                    }
                    //DEPUIS LES DEDUCTIONS et SI ON A TJ PAS TROUVE
                    if (!up) {
                        for (int i = 0; i < deduction.size(); i++) {
                            if (deduction.get(i).equalsIgnoreCase("" + in.charAt(k))) {
                                //on ne retourne plus sur cette regle car valide
                                valid++;
                                break;
                            }
                        }
                    }
                }
                //Si on a tous les parametre, on valide la regle, et on en deduit ca valeur
                if (valid == nbOfComp) {
                    deduction.put(deduction.size(), "" + in.charAt(in.length() - 1));
                    c[j] = -1;
                }
                //permet de savoir si on ne deduit plus rien, et si c'est le cas on arrête
                find++;
            }
        } while (find != findBefore);
        // }
        jTextAreaResults.setText(jTextAreaResults.getText() + "\nDeductions : " + deduction);
    }

    //FORMAT DENTRE X/X/X/X
    //CHAINAGE ARRIERE
    public void RecurArriere() {
        int find = 0;
        int findBefore = 0;
        int[] c = new int[regles.size()];
        for (int j = 0; j < regles.size(); j++) {
            c[j] = j;
        }
        String in = "";
        do {
            findBefore = find;
            find = 0;
            for (int j = 0; j < regles.size(); j++) {
                //Si on a deja valide cette regle, on passe
                if (c[j] == -1) {
                    continue;
                }
                //On appelle la fonction recursive qui parcours en descendant les regle pour chercher jusqu'a trouver la correspondance des faits
                if (checkArriere("" + regles.get(j).charAt(regles.get(j).length() - 1))) {
                    if (!deduction.containsValue("" + regles.get(j).charAt(regles.get(j).length() - 1))) {
                        deduction.put(deduction.size(), "" + regles.get(j).charAt(regles.get(j).length() - 1));
                    }
                    c[j] = -1;
                }
                //permet de savoir si on ne deduit plus rien, et si c'est le cas on arrête
                find++;
            }
        } while (find != findBefore);
        jTextAreaResults.setText(jTextAreaResults.getText() + "\nDeductions : " + deduction);
    }
    //FORMAT DENTRE X/X/X/X
    //CHAINAGE AVANT

    public void searchAndFindRecurAvant() {
        String text = jTextFieldEntry.getText();
        if (text.isEmpty()) {
            jTextAreaResults.setText("No Entry");
            return;
        }
        int size = text.length();
        int nbOfCase = 0;
        if (size > 2) {
            nbOfCase = size - (size / 2);
        } else {
            nbOfCase = 1;
        }
        text = text.replaceAll("/", "");
        for (int i = 0; i < nbOfCase; i++) {
            fait.put(i, "" + text.charAt(i));
        }
        //for (int i = 0; i < nbOfCase; i++) {
        int find = 0;
        int findBefore = 0;
        int[] c = new int[regles.size()];
        for (int j = 0; j < regles.size(); j++) {
            c[j] = j;
        }
        String in = "";
        do {
            findBefore = find;
            find = 0;
            for (int j = 0; j < regles.size(); j++) {
                //Si on a deja valide cette regle, on passe
                if (c[j] == -1) {
                    continue;
                }
                //on enleve les espaces, et les '&'
                in = regles.get(j).replaceAll(" ", "");
                in = in.replaceAll("&", "");
                //compte le nombre de parametres de la fonction regle
                int nbOfComp = in.indexOf("=");
                //permet de savoir si on a validé les parametre de la fonction regle
                int valid = 0;
                //SELON LE NOMBRE DE PARAMETRE DE LA FONCTION REGLE
                for (int k = 0; k < nbOfComp; k++) {
                    boolean up = false;
                    //DEPUIS LES FAITS
                    for (int i = 0; i < nbOfCase; i++) {
                        if (fait.get(i).equalsIgnoreCase("" + in.charAt(k))) {
                            //on ne retourne plus sur cette regle car valide
                            valid++;
                            up = true;
                            break;
                        }
                    }
                    //DEPUIS LES DEDUCTIONS et SI ON A TJ PAS TROUVE
                    if (!up) {
                        for (int i = 0; i < deduction.size(); i++) {
                            if (deduction.get(i).equalsIgnoreCase("" + in.charAt(k))) {
                                //on ne retourne plus sur cette regle car valide
                                valid++;
                                break;
                            }
                        }
                    }
                }
                //Si on a tous les parametre, on valide la regle, et on en deduit ca valeur
                if (valid == nbOfComp) {
                    deduction.put(deduction.size(), "" + in.charAt(in.length() - 1));
                    c[j] = -1;
                }
                //permet de savoir si on ne deduit plus rien, et si c'est le cas on arrête
                find++;
            }
        } while (find != findBefore);
        // }
        jTextAreaResults.setText(jTextAreaResults.getText() + "\nDeductions : " + deduction);
    }

    //FORMAT DENTRE X/X/X/X
    //CHAINAGE ARRIERE
    public void searchAndFindRecurArriere() {
        appel = 0;
        String text = jTextFieldEntry.getText();
        if (text.isEmpty()) {
            jTextAreaResults.setText("No Entry");
            return;
        }
        int size = text.length();
        int nbOfCase = 0;
        if (size > 2) {
            nbOfCase = size - (size / 2);
        } else {
            nbOfCase = 1;
        }
        text = text.replaceAll("/", "");
        for (int i = 0; i < nbOfCase; i++) {
            fait.put(i, "" + text.charAt(i));
        }
        int find = 0;
        int findBefore = 0;
        int[] c = new int[regles.size()];
        for (int j = 0; j < regles.size(); j++) {
            c[j] = j;
        }
        String in = "";
        do {
            findBefore = find;
            find = 0;
            for (int j = 0; j < regles.size(); j++) {
                //Si on a deja valide cette regle, on passe
                if (c[j] == -1) {
                    continue;
                }
                //On appelle la fonction recursive qui parcours en descendant les regle pour chercher jusqu'a trouver la correspondance des faits
                if (checkArriere("" + regles.get(j).charAt(regles.get(j).length() - 1))) {
                    if (!deduction.containsValue("" + regles.get(j).charAt(regles.get(j).length() - 1))) {
                        deduction.put(deduction.size(), "" + regles.get(j).charAt(regles.get(j).length() - 1));
                    }
                    c[j] = -1;
                }
                //permet de savoir si on ne deduit plus rien, et si c'est le cas on arrête
                find++;
            }
        } while (find != findBefore);
        jTextAreaResults.setText(jTextAreaResults.getText() + "\nDeductions : " + deduction);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonArriere;
    private javax.swing.JButton jButtonAvant;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanelMotor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaResults;
    private javax.swing.JTextArea jTextAreaTestRules;
    private javax.swing.JTextField jTextFieldEntry;
    // End of variables declaration//GEN-END:variables

    public long getPowerOfShoot() {
        return powerOfShoot;
    }
}
