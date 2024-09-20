package lab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CoordinatePlane extends JPanel {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int PADDING = 20; 
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
        g2d.drawLine(0, HEIGHT / 2 + PADDING, WIDTH, HEIGHT / 2 + PADDING); 
        g2d.drawLine(WIDTH / 2, PADDING, WIDTH / 2, HEIGHT + PADDING); 

        // Рисуем деления на осях
        for (int i = -10; i <= 10; i++) {
            int x = WIDTH / 2 + i * (WIDTH / 20);
            int y = HEIGHT / 2 - i * (HEIGHT / 20) + PADDING;

            // Деления на оси X
            g2d.drawLine(x, HEIGHT / 2 - 5 + PADDING, x, HEIGHT / 2 + 5 + PADDING);
            if (i != 0) {
                g2d.drawString(String.valueOf(i), x - 5, HEIGHT / 2 + 20 + PADDING);
            }

            // Деления на оси Y
            g2d.drawLine(WIDTH / 2 - 5, y, WIDTH / 2 + 5, y);
            if (i != 0) {
                g2d.drawString(String.valueOf(i), WIDTH / 2 - 20, y + 5);
            }
        }
        
        //точки
        g2d.setColor(Color.RED);
        for (Point point : points) {
            int x = WIDTH / 2 + point.x * (WIDTH / 20);
            int y = HEIGHT / 2 - point.y * (HEIGHT / 20) + PADDING;
            g2d.fillOval(x - 3, y - 3, 6, 6);
        }

        // треугльник
        if (points.size() == 3) {
            g2d.setColor(Color.BLUE);
            int[] xPoints = new int[3];
            int[] yPoints = new int[3];
            for (int i = 0; i < 3; i++) {
                xPoints[i] = WIDTH / 2 + points.get(i).x * (WIDTH / 20);
                yPoints[i] = HEIGHT / 2 - points.get(i).y * (HEIGHT / 20) + PADDING;
            }
            g2d.drawPolygon(xPoints, yPoints, 3);
        }
    }

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Coordinate Plane");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());

        CoordinatePlane coordinatePlane = new CoordinatePlane();
        contentPane.add(coordinatePlane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5, 3));

        JTextField aXField = new JTextField();
        JTextField aYField = new JTextField();
        JTextField bXField = new JTextField();
        JTextField bYField = new JTextField();
        JTextField cXField = new JTextField();
        JTextField cYField = new JTextField();
        JButton addButton = new JButton("Add Points");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int ax = Integer.parseInt(aXField.getText());
                    int ay = Integer.parseInt(aYField.getText());
                    int bx = Integer.parseInt(bXField.getText());
                    int by = Integer.parseInt(bYField.getText());
                    int cx = Integer.parseInt(cXField.getText());
                    int cy = Integer.parseInt(cYField.getText());

                    coordinatePlane.points.clear();
                    coordinatePlane.addPoint(ax, ay);
                    coordinatePlane.addPoint(bx, by);
                    coordinatePlane.addPoint(cx, cy);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter integers.");
                }
            }
        });

        controlPanel.add(new JLabel(""));
        controlPanel.add(new JLabel("Задайте значение точек треугольника"));
        controlPanel.add(new JLabel(""));
        controlPanel.add(new JLabel("A (x, y):"));
        controlPanel.add(aXField);
        controlPanel.add(aYField);
        controlPanel.add(new JLabel("B (x, y):"));
        controlPanel.add(bXField);
        controlPanel.add(bYField);
        controlPanel.add(new JLabel("C (x, y):"));
        controlPanel.add(cXField);
        controlPanel.add(cYField);
        controlPanel.add(new JLabel(""));
        controlPanel.add(addButton);

        contentPane.add(controlPanel, BorderLayout.SOUTH);

        frame.setContentPane(contentPane);
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