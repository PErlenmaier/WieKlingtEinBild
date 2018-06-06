/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wieklingteinbild.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import wieklingteinbild.model.Picture;
import wieklingteinbild.model.PictureListe;
import wieklingteinbild.model.SoundListe;
import wieklingteinbild.model.WavSound;

/**
 *
 * @author Philipp
 */
public class SoundDirectory {
    
        private static String soundDir;

    public static String getSoundDir() {
        return soundDir;
    }

    public static void setSoundDir(String soundDir) {
        SoundDirectory.soundDir = soundDir;
    }
    
    public static void updateSoundDir(){
        
        String path = System.getProperty("user.dir");
        System.out.println(path);
        String dirPath = path + "\\src\\wieklingteinbild\\Algorithmus\\Programm\\";
        System.out.println(dirPath);
        setSoundDir(dirPath);
    }
    
        public void addSoundsFromDir() {
        String dir = getSoundDir();
        File file = new File(dir);
        Object[] files = file.list();
        List fileListe = Arrays.asList(files);
            System.out.println(dir);
            System.out.println(fileListe.toString());


        for (int i = 0; i < fileListe.size(); i++) {
            WavSound sound = new WavSound(fileListe.get(i).toString());
            if (Check.checkWavType(fileListe.get(i).toString()) == true) {
                SoundListe.soundListe.add(sound);
            }
        }
    }
    
}
