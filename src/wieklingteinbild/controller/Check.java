/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wieklingteinbild.controller;

/**
 *
 * @author Philipp
 */
public class Check {

    public static boolean checkType(String type) {
        boolean istOK = false;
        if (type.endsWith(".bmp")) {
            istOK = true;
        }
        return istOK;
    }
    
        public static boolean checkWavType(String type) {
        boolean istOK = false;
        if (type.endsWith(".wav")) {
            istOK = true;
        }
        return istOK;
    }
}
