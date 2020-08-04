package me.gizmonster.anticacore.utils;

public class NumberUtils {
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static int getRandomInt (int min, int max) {
        int x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
}
