/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wieklingteinbild.controller;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import wieklingteinbild.view.FrmHauptfenster;

/**
 *
 * @author Philipp // Max
 */
public class WieKlingtEinBild {

    /**
     * @param args nothing to give
     */
    
    public static void main(String[] args) {
           try
                {
                    UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
                }
           catch(ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e)
                {
                }
        PictureDirectory.updatePictureDir();
        SoundDirectory.updateSoundDir();
        EventQueue.invokeLater(() -> { // Lambda Expression for the Annonymous functions no need of Params.
                FrmHauptfenster frmHauptfenster = new FrmHauptfenster();
                frmHauptfenster.setVisible(true);
                
           } 
           );
    }

}
