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

/**
 *
 * @author Philipp
 */
public class PictureDirectory {
    
    private static String pictureDir;

    public static String getPictureDir() {
        return pictureDir;
    }

    public static void setPictureDir(String pictureDir) {
        PictureDirectory.pictureDir = pictureDir;
    }
    
    public static void updatePictureDir(){
        
        String path = System.getProperty("user.dir");
        System.out.println(path);
        String dirPath = path + "\\src\\wieklingteinbild\\Algorithmus\\Programm\\";
        System.out.println(dirPath);
        setPictureDir(dirPath);
    }
    
        public void addPicturesFromDir() {
        String dir = getPictureDir();
        File file = new File(dir);
        Object[] files = file.list();
        List fileListe = Arrays.asList(files);
            System.out.println(dir);
            System.out.println(fileListe.toString());


        for (int i = 0; i < fileListe.size(); i++) {
            Picture pic = new Picture(fileListe.get(i).toString());
            if (Check.checkType(fileListe.get(i).toString()) == true) {
                PictureListe.pictureListe.add(pic);
            }
        }
    }
    
}
