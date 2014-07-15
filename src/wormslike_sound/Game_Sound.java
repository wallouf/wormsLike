/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wormslike_sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;
import javax.swing.JOptionPane;

/**
 * CLASSE QUI GERE LE SON
 * @author wallouf
 */
public class Game_Sound extends Applet {
    //VARIABLES STATIQUE POUR ETRE APPELLE PARTOUT ET ETRE UNIQUE
    private static AudioClip shootClip;
    private static AudioClip exploClip;
    private static AudioClip walkClip;
    private static AudioClip startClip;
    private static AudioClip jumpClip;
    private static AudioClip nextClip;
    private static AudioClip endClip;
    private static AudioClip fallClip;
    private static AudioClip ambianceClip;
    private static AudioClip menuClip;
    //MUTE
    private static boolean mute = false;
    
    public Game_Sound() {
        //ouverture des fichiers sons
        try {
            shootClip = Applet.newAudioClip(new URL("file:" + new File("./bin/sound/shoot.wav").getAbsolutePath() + ""));
            exploClip = Applet.newAudioClip(new URL("file:" + new File("./bin/sound/explo.wav").getAbsolutePath() + ""));
            walkClip = Applet.newAudioClip(new URL("file:" + new File("./bin/sound/walk.wav").getAbsolutePath() + ""));
            startClip = Applet.newAudioClip(new URL("file:" + new File("./bin/sound/start.wav").getAbsolutePath() + ""));
            jumpClip = Applet.newAudioClip(new URL("file:" + new File("./bin/sound/jump.wav").getAbsolutePath() + ""));
            nextClip = Applet.newAudioClip(new URL("file:" + new File("./bin/sound/next.wav").getAbsolutePath() + ""));
            endClip = Applet.newAudioClip(new URL("file:" + new File("./bin/sound/end.wav").getAbsolutePath() + ""));
            fallClip = Applet.newAudioClip(new URL("file:" + new File("./bin/sound/fall.wav").getAbsolutePath() + ""));
            ambianceClip = Applet.newAudioClip(new URL("file:" + new File("./bin/sound/ambiance.wav").getAbsolutePath() + ""));
            menuClip = Applet.newAudioClip(new URL("file:" + new File("./bin/sound/menu.wav").getAbsolutePath() + ""));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error with music \n" + e.getCause() + "\n" + e);
            System.exit(-1);
        }
    }
    
    public static boolean isMute() {
        return mute;
    }
    //mute le son et arrête ceux en cours
    public static void setMute(boolean mute) {
        //stop music if mute
        if (mute) {
            stopAll();
        }
        //relaunch music after unmute
        if (!mute && Game_Sound.mute) {
            Game_Sound.mute = mute;
            playMenu();
        }
        Game_Sound.mute = mute;
    }

    //**********PLAY ************
    public static void playFall() {
        if (!mute) {
            fallClip.play();
        }
    }

    public static void playNext() {
        if (!mute) {
            nextClip.play();
        }
    }

    public static void playJump() {
        if (!mute) {
            jumpClip.play();
        }
    }

    public static void playShoot() {
        if (!mute) {
            shootClip.play();
        }
    }

    public static void playExplo() {
        if (!mute) {
            exploClip.play();
        }
    }

    public static void playWalk() {
        if (!mute) {
            walkClip.play();
        }
    }

    public static void playStart() {
        if (!mute) {
            startClip.play();
        }
    }

    public static void playEnd() {
        if (!mute) {
            endClip.play();
        }
    }

    public static void playAmbiance() {
        if (!mute) {
            ambianceClip.loop();
        }
    }

    public static void playMenu() {
        if (!mute) {
            menuClip.loop();
        }
    }

    //**********STOP ************
    private static void stopAll() {
        stopShoot();
        stopExplo();
        stopWalk();
        stopStart();
        stopEnd();
        stopAmbiance();
        stopMenu();
    }

    public static void stopShoot() {
        shootClip.stop();
    }

    public static void stopExplo() {
        exploClip.stop();
    }

    public static void stopWalk() {
        walkClip.stop();
    }

    public static void stopStart() {
        startClip.stop();
    }

    public static void stopEnd() {
        endClip.stop();
    }

    public static void stopAmbiance() {
        ambianceClip.stop();
    }

    public static void stopMenu() {
        menuClip.stop();
    }
}
