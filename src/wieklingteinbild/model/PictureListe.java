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
public class PictureListe {


    public static ArrayList<Picture> pictureListe = new ArrayList();

    public List<Picture> getImageListe() {
        if (pictureListe.isEmpty()) {
            System.out.println("Liste ist leer");
        }
        return pictureListe;
    }

    public void setImageListe(ArrayList<Picture> imageListe) {
        this.pictureListe = imageListe;
    }

}
