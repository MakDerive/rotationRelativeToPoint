package lab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CoordinatePlane extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private List<Point> points = new ArrayList<>();

    public CoordinatePlane() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Настройка шрифта и цвета
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.setColor(Color.BLACK);

        // Рисуем оси координат
        g2d.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2); // Ось X
        g2d.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT); // Ось Y

        // Рисуем деления на осях
        for (int i = -10; i <= 10; i++) {
            int x = WIDTH / 2 + i * (WIDTH / 20);
            int y = HEIGHT / 2 - i * (HEIGHT / 20);

            // Деления на оси X
            g2d.drawLine(x, HEIGHT / 2 - 5, x, HEIGHT / 2 + 5);
            g2d.drawString(String.valueOf(i), x - 5, HEIGHT / 2 + 20);

            // Деления на оси Y
            g2d.drawLine(WIDTH / 2 - 5, y, WIDTH / 2 + 5, y);
            g2d.drawString(String.valueOf(i), WIDTH / 2 - 20, y + 5);
        }

        // Рисуем стрелки на концах осей
        g2d.drawLine(WIDTH - 5, HEIGHT / 2 - 5, WIDTH, HEIGHT / 2);
        g2d.drawLine(WIDTH - 5, HEIGHT / 2 + 5, WIDTH, HEIGHT / 2);
        g2d.drawLine(WIDTH / 2 - 5, 5, WIDTH / 2, 0);
        g2d.drawLine(WIDTH / 2 + 5, 5, WIDTH / 2, 0);

        // Рисуем точки
        g2d.setColor(Color.RED);
        for (Point point : points) {
            int x = WIDTH / 2 + point.x * (WIDTH / 20);
            int y = HEIGHT / 2 - point.y * (HEIGHT / 20);
            g2d.fillOval(x - 3, y - 3, 6, 6);
        }
    }

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Coordinate Plane");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CoordinatePlane coordinatePlane = new CoordinatePlane();
        frame.add(coordinatePlane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 2));

        JTextField xField = new JTextField();
        JTextField yField = new JTextField();
        JButton addButton = new JButton("Add Point");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int x = Integer.parseInt(xField.getText());
                    int y = Integer.parseInt(yField.getText());
                    coordinatePlane.addPoint(x, y);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter integers.");
                }
            }
        });

        controlPanel.add(new JLabel("X:"));
        controlPanel.add(xField);
        controlPanel.add(new JLabel("Y:"));
        controlPanel.add(yField);
        controlPanel.add(addButton);

        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}