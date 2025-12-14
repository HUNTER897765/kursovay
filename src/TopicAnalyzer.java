import java.util.*;
import utils.Stemmer;

public class TopicAnalyzer {

    private dictionary dictionary;

    public TopicAnalyzer(dictionary dict) {
        this.dictionary = dict;
    }

    public void analyzeText(String text) {
        Map<String, Integer> topicCounts = new HashMap<>();
        Map<String, Set<String>> topicWords = dictionary.getTopicWords();

        // Разбиваем текст на слова и приводим к нижнему регистру
        List<String> tokens = Arrays.asList(text.toLowerCase().split("[^а-яa-z0-9]+"));


        tokens = Stemmer.stemList(tokens);

        for (String topic : topicWords.keySet()) {
            topicCounts.put(topic, 0);
        }

        Map<String, Integer> wordStats = new HashMap<>();
        for (String token : tokens) {
            for (String topic : topicWords.keySet()) {
                for (String w : topicWords.get(topic)) {
                    if (token.equals(Stemmer.stem(w))) {
                        topicCounts.put(topic, topicCounts.get(topic) + 1);
                        wordStats.put(w, wordStats.getOrDefault(w, 0) + 1);
                    }
                }
            }
        }

        String mainTopic = topicCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Не определена");

        System.out.println("\nРезультат анализа");
        System.out.println("Основная тема: " + mainTopic);
        System.out.println("Статистика по темам: " + topicCounts);
        System.out.println("Количество вхождений ключевых слов: " + wordStats);

        Chart.showTopicChart(topicCounts);
    }
}