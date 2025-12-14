import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class TextParser {

    public static String parse(Path file) throws IOException {
        String name = file.getFileName().toString().toLowerCase();
        if (name.endsWith(".txt")) {
            return Files.readString(file);
        } else if (name.endsWith(".docx")) {
            try (FileInputStream fis = new FileInputStream(file.toFile());
                 XWPFDocument docx = new XWPFDocument(fis)) {
                return docx.getParagraphs().stream()
                        .map(p -> p.getText())
                        .reduce("", (a, b) -> a + "\n" + b);
            }
        } else if (name.endsWith(".doc")) {
            try (FileInputStream fis = new FileInputStream(file.toFile());
                 HWPFDocument doc = new HWPFDocument(fis)) {
                WordExtractor extractor = new WordExtractor(doc);
                return extractor.getText();
            }
        } else {
            throw new IOException("Неподдерживаемый формат файла!");
        }
    }

    // Метод для получения массива слов
    public static String[] parseWords(Path file) throws IOException {
        String text = parse(file);
        text = text.toLowerCase().replaceAll("[^а-яёa-z0-9\\s]", " ");
        return text.split("\\s+");
    }

    // Метод для извлечения словосочетаний длиной 2 и 3 слова
    public static List<String> extractPhrases(String[] words) {
        List<String> phrases = new ArrayList<>();
        for (int i = 0; i < words.length - 1; i++) {
            phrases.add(words[i] + " " + words[i + 1]); // двусловное сочетание
            if (i < words.length - 2) {
                phrases.add(words[i] + " " + words[i + 1] + " " + words[i + 2]); // трёхсловное
            }
        }
        return phrases;
    }

    public static void main(String[] args) throws IOException {
        Path file = Path.of("example.docx");
        String[] words = parseWords(file);
        List<String> phrases = extractPhrases(words);

        System.out.println("Слова:");
        for (String w : words) {
            System.out.println(w);
        }

        System.out.println("\nСловосочетания:");
        for (String p : phrases) {
            System.out.println(p);
        }
    }
}