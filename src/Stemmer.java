package utils;

import java.util.List;
import java.util.stream.Collectors;

public class Stemmer {

    public static String stem(String word) {
        word = word.toLowerCase().replaceAll("[^а-яa-z0-9]", "");
        String[] endings = {
                "ами","ями","ов","ев","ей","ий","ый","ой","а","ы","и","у","ю","е","ём","ем","ах","ях","ам","ям"
        };

        for (String end : endings) {
            if (word.endsWith(end) && word.length() > end.length() + 2) {
                word = word.substring(0, word.length() - end.length());
                break;
            }
        }

        return word;
    }

    public static List<String> stemList(List<String> words) {
        return words.stream().map(Stemmer::stem).collect(Collectors.toList());
    }
}