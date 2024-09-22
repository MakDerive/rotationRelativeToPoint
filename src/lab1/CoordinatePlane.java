package lab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

public class CoordinatePlane extends JPanel {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int PADDING = 20;
    private List<Point> points = new ArrayList<>();
    private List<Point> rotatedPoints = new ArrayList<>();
    private Point rotationPoint = null;

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

        // Точки
        g2d.setColor(Color.RED);
        for (Point point : points) {
            int x = (int) (WIDTH / 2 + point.x * (WIDTH / 20));
            int y = (int) (HEIGHT / 2 - point.y * (HEIGHT / 20) + PADDING);
            g2d.fillOval(x - 3, y - 3, 6, 6);
        }

        // Треугольник
        if (points.size() == 3) {
            g2d.setColor(Color.BLUE);
            drawPolygon(g2d, points);
        }

        // Точка поворота
        if (rotationPoint != null) {
            g2d.setColor(Color.ORANGE);
            int x = (int) (WIDTH / 2 + rotationPoint.x * (WIDTH / 20));
            int y = (int) (HEIGHT / 2 - rotationPoint.y * (HEIGHT / 20) + PADDING);
            g2d.fillOval(x - 3, y - 3, 6, 6);
        }

        // Повернутый треугольник
        if (rotatedPoints.size() == 3) {
            g2d.setColor(Color.GREEN);
            drawPolygon(g2d, rotatedPoints);
        }
    }

    private void drawPolygon(Graphics2D g2d, List<Point> points) {
        if (points.size() < 2) return;

        GeneralPath path = new GeneralPath();
        Point firstPoint = points.get(0);
        path.moveTo(WIDTH / 2 + firstPoint.x * (WIDTH / 20), HEIGHT / 2 - firstPoint.y * (HEIGHT / 20) + PADDING);

        for (int i = 1; i < points.size(); i++) {
            Point point = points.get(i);
            path.lineTo(WIDTH / 2 + point.x * (WIDTH / 20), HEIGHT / 2 - point.y * (HEIGHT / 20) + PADDING);
        }

        path.closePath();
        g2d.draw(path);
    }

    public void addPoint(double x, double y) {
        points.add(new Point(x, y));
        repaint();
    }

    public void setRotationPoint(double x, double y) {
        rotationPoint = new Point(x, y);
        repaint();
    }

    public void rotatePoints(double cx, double cy, double angle) {
        rotatedPoints.clear();
        for (Point point : points) {
            double x = point.x;
            double y = point.y;
            double newX = cx + (x - cx) * Math.cos(angle * Math.PI / 180) - (y - cy) * Math.sin(angle * Math.PI / 180);
            double newY = cy + (x - cx) * Math.sin(angle * Math.PI / 180) + (y - cy) * Math.cos(angle * Math.PI / 180);
            rotatedPoints.add(new Point(newX, newY));
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Coordinate Plane");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());

        CoordinatePlane coordinatePlane = new CoordinatePlane();
        contentPane.add(coordinatePlane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(10, 3));

        JTextField aXField = new JTextField();
        JTextField aYField = new JTextField();
        JTextField bXField = new JTextField();
        JTextField bYField = new JTextField();
        JTextField cXField = new JTextField();
        JTextField cYField = new JTextField();
        JButton addButton = new JButton("Add Points");

        JTextField rotateXField = new JTextField();
        JTextField rotateYField = new JTextField();
        JTextField angleField = new JTextField();
        JButton rotateButton = new JButton("Rotate");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double ax = Double.parseDouble(aXField.getText());
                    double ay = Double.parseDouble(aYField.getText());
                    double bx = Double.parseDouble(bXField.getText());
                    double by = Double.parseDouble(bYField.getText());
                    double cx = Double.parseDouble(cXField.getText());
                    double cy = Double.parseDouble(cYField.getText());

                    coordinatePlane.points.clear();
                    coordinatePlane.addPoint(ax, ay);
                    coordinatePlane.addPoint(bx, by);
                    coordinatePlane.addPoint(cx, cy);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter numbers.");
                }
            }
        });

        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double rx = Double.parseDouble(rotateXField.getText());
                    double ry = Double.parseDouble(rotateYField.getText());
                    double angle = Double.parseDouble(angleField.getText());
                    coordinatePlane.setRotationPoint(rx, ry);
                    coordinatePlane.rotatePoints(rx, ry, angle);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter numbers and a valid angle.");
                }
            }
        });

        controlPanel.add(new JLabel("Задайте значение точек треугольника"));
        controlPanel.add(new JLabel(""));
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
        controlPanel.add(new JLabel(""));

        controlPanel.add(new JLabel("Задайте точку и угол поворота"));
        controlPanel.add(new JLabel(""));
        controlPanel.add(new JLabel(""));
        controlPanel.add(new JLabel("Rotate Point (x, y):"));
        controlPanel.add(rotateXField);
        controlPanel.add(rotateYField);
        controlPanel.add(new JLabel("Angle (degrees):"));
        controlPanel.add(angleField);
        controlPanel.add(new JLabel(""));
        controlPanel.add(rotateButton);

        contentPane.add(controlPanel, BorderLayout.SOUTH);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static class Point {
        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}