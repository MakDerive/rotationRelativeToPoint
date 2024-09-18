package lab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoordinateSystem extends JPanel {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;
    private static final int ORIGIN_X = WIDTH / 2;
    private static final int ORIGIN_Y = HEIGHT / 2;
    private static final int UNIT = 15;
    private static final int MIN_COORD = -20;
    private static final int MAX_COORD = 20;

    private int xCoord = 0;
    private int yCoord = 0;
    private int numSides = 0;
    private double rotationAngle = 0;

    public CoordinateSystem() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void setShapeParameters(int xCoord, int yCoord, int numSides, double rotationAngle) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.numSides = numSides;
        this.rotationAngle = rotationAngle;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Рисуем оси координат
        g.setColor(Color.BLACK);
        g.drawLine(0, ORIGIN_Y, WIDTH, ORIGIN_Y); 
        g.drawLine(ORIGIN_X, 0, ORIGIN_X, HEIGHT); 

        // Рисуем деления на осях
        g.setColor(Color.GRAY);
        for (int x = ORIGIN_X % UNIT; x < WIDTH; x += UNIT) {
            g.drawLine(x, ORIGIN_Y - 5, x, ORIGIN_Y + 5);
            if (x != ORIGIN_X) {
                int coordX = (x - ORIGIN_X) / UNIT;
                if (coordX >= MIN_COORD && coordX <= MAX_COORD) {
                    g.drawString(Integer.toString(coordX), x-8, ORIGIN_Y + 15);
                }
            }
        }
        for (int y = ORIGIN_Y % UNIT; y < HEIGHT; y += UNIT) {
            g.drawLine(ORIGIN_X - 5, y, ORIGIN_X + 5, y);
            if (y != ORIGIN_Y) {
                int coordY = (ORIGIN_Y - y) / UNIT;
                if (coordY >= MIN_COORD && coordY <= MAX_COORD) {
                    g.drawString(Integer.toString(coordY), ORIGIN_X + 5, y + 5);
                }
            }
        }

        // Рисуем фигуру
        drawShape(g, xCoord, yCoord, numSides, rotationAngle);
    }

    private void drawShape(Graphics g, int xCoord, int yCoord, int numSides, double rotationAngle) {
        int radius = 50;
        int[] xPoints = new int[numSides];
        int[] yPoints = new int[numSides];

        for (int i = 0; i < numSides; i++) {
            double angle = 2 * Math.PI * i / numSides + Math.toRadians(rotationAngle);
            xPoints[i] = ORIGIN_X + xCoord * UNIT + (int) (radius * Math.cos(angle));
            yPoints[i] = ORIGIN_Y - yCoord * UNIT - (int) (radius * Math.sin(angle));
        }

        g.setColor(Color.RED);
        g.fillPolygon(xPoints, yPoints, numSides);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Coordinate System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CoordinateSystem drawer = new CoordinateSystem();
        frame.add(drawer, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        JTextField xCoordField = new JTextField();
        JTextField yCoordField = new JTextField();
        JTextField numSidesField = new JTextField();
        JTextField rotationAngleField = new JTextField();

        inputPanel.add(new JLabel("X Coordinate:"));
        inputPanel.add(xCoordField);
        inputPanel.add(new JLabel("Y Coordinate:"));
        inputPanel.add(yCoordField);
        inputPanel.add(new JLabel("Number of Sides:"));
        inputPanel.add(numSidesField);
        inputPanel.add(new JLabel("Rotation Angle:"));
        inputPanel.add(rotationAngleField);


        JButton drawButton = new JButton("Draw Shape");
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int xCoord = Integer.parseInt(xCoordField.getText());
                    int yCoord = Integer.parseInt(yCoordField.getText());
                    int numSides = Integer.parseInt(numSidesField.getText());
                    double rotationAngle = Double.parseDouble(rotationAngleField.getText());

                    drawer.setShapeParameters(xCoord, yCoord, numSides, rotationAngle);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers.");
                }
            }
        });

        inputPanel.add(drawButton);

        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
