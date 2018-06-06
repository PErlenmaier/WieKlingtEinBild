/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wieklingteinbild.controller;

import wieklingteinbild.view.FrmHauptfenster;

/**
 *
 * @author Philipp
 */
public class WieKlingtEinBild {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PictureDirectory.updatePictureDir();
        SoundDirectory.updateSoundDir();
        FrmHauptfenster frmHauptfenster = new FrmHauptfenster();
        frmHauptfenster.setVisible(true);
    }

}
