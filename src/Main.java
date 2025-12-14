import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        dictionary dict = new dictionary();
        TopicAnalyzer analyzer = new TopicAnalyzer(dict);

        while (true) {
            System.out.println("\nМЕНЮ");
            System.out.println("1 — Анализировать текстовый файл (.txt, .doc, .docx)");
            System.out.println("2 — Ввести текст вручную");
            System.out.println("0 — Выход");
            System.out.print("Выберите вариант: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("0")) break;

            if (choice.equals("1")) {
                System.out.print("Введите путь к файлу: ");
                Path file = Paths.get(scanner.nextLine().trim());

                try {
                    analyzer.analyzeText(TextParser.parse(file));
                } catch (Exception e) {
                    System.out.println("Ошибка при чтении файла: " + e.getMessage());
                }

                pause(scanner);

            } else if (choice.equals("2")) {
                System.out.println("Введите текст для анализа.");

                StringBuilder text = new StringBuilder();
                while (true) {
                    String line = scanner.nextLine();
                    if (line.isEmpty()) break;
                    text.append(line).append("\n");
                }

                analyzer.analyzeText(text.toString());
                pause(scanner);

            } else {
                System.out.println("Неизвестная команда!");
            }
        }

        System.out.println("Выход");
    }

    private static void pause(Scanner scanner) {
        System.out.println("\nНажмите Enter, чтобы вернуться в меню");
        scanner.nextLine();
    }
}