package com.patika.Helpers;

import javax.swing.*;

public class Helper {

    public static void setLayout(String themeName){

        for (UIManager.LookAndFeelInfo info:UIManager.getInstalledLookAndFeels()){

            if(info.getName().equals(themeName)){

                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    public static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }


}
