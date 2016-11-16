package com.example.mypc.mp3.Other;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by MyPC on 02/07/2016.
 */
public class StringUtils {
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
