import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Chart {

    public static void showTopicChart(Map<String, Integer> topicCounts) {
        if (topicCounts == null || topicCounts.isEmpty()) {
            System.out.println("Нет данных для построения графика.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            DefaultPieDataset dataset = new DefaultPieDataset();
            for (Map.Entry<String, Integer> entry : topicCounts.entrySet()) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }
            JFreeChart chart = ChartFactory.createPieChart(
                    "Распределение слов по темам",
                    dataset,
                    true,
                    true,
                    false
            );

            chart.getTitle().setFont(new Font("Arial", Font.BOLD, 16));
            chart.getLegend().setItemFont(new Font("Arial", Font.PLAIN, 12));

            JFrame frame = new JFrame("График тем текста");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(new ChartPanel(chart));
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}