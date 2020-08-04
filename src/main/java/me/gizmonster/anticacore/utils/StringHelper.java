package me.gizmonster.anticacore.utils;

public class StringHelper {
    public static String capitailizeWord(String str) {
        StringBuffer buffer = new StringBuffer();
        char ch = ' ';
        for (int i = 0; i < str.length(); i++) {
            if (ch == ' ' && str.charAt(i) != ' ')
                buffer.append(Character.toUpperCase(str.charAt(i)));
            else
                buffer.append(str.charAt(i));
            ch = str.charAt(i);
        }
        return buffer.toString().trim();
    }
}
