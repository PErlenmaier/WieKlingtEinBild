/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wieklingteinbild.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class SoundListe {
    
        public static ArrayList<WavSound> soundListe = new ArrayList();

    public List<WavSound> getSoundListe() {
        if (soundListe.isEmpty()) {
            System.out.println("Liste ist leer");
        }
        return soundListe;
    }

    public void setSoundListe(ArrayList<WavSound> imageListe) {
        this.soundListe = imageListe;
    }
    
}
