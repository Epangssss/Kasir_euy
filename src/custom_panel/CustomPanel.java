/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package custom_panel;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class CustomPanel extends JPanel {
    private int roundTopLeft = 0;
    private int roundTopRight = 0;
    private int roundBottomLeft = 0;
    private int roundBottomRight = 0;

    private Color startColor = Color.RED;
    private Color endColor = Color.WHITE;
    
    

    public CustomPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw gradient background
        int width = getWidth();
        int height = getHeight();
        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, 0, height, endColor);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height);

        // Draw rounded corners
        Area area = new Area(createRoundedRectangle());
        g2d.setColor(getBackground());
        g2d.fill(area);

        g2d.dispose();
    }

    private Shape createRoundedRectangle() {
        int width = getWidth();
        int height = getHeight();

        // Calculate roundness for each corner
        int roundXTopLeft = Math.min(width, roundTopLeft);
        int roundYTopLeft = Math.min(height, roundTopLeft);
        int roundXTopRight = Math.min(width, roundTopRight);
        int roundYTopRight = Math.min(height, roundTopRight);
        int roundXBottomLeft = Math.min(width, roundBottomLeft);
        int roundYBottomLeft = Math.min(height, roundBottomLeft);
        int roundXBottomRight = Math.min(width, roundBottomRight);
        int roundYBottomRight = Math.min(height, roundBottomRight);

        // Create rounded rectangle with specified roundness
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(0, 0, width, height,
                roundXTopLeft, roundYTopLeft);
        Area area = new Area(roundedRectangle);
        area.add(new Area(new Rectangle2D.Double(0, roundYTopLeft / 2, width - roundXTopRight / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundYBottomLeft / 2)));
        area.add(new Area(new Rectangle2D.Double(roundXBottomRight / 2, 0, width, height)));
        return area;
    }

    // Getter and setter methods for roundness and gradient colors
    // ...


  

    // Konstruktor dan metode paintComponent tetap sama seperti sebelumnya

    // Getter dan setter untuk roundness
    public int getRoundTopLeft() {
        return roundTopLeft;
    }

    public void setRoundTopLeft(int roundTopLeft) {
        this.roundTopLeft = roundTopLeft;
    }

    public int getRoundTopRight() {
        return roundTopRight;
    }

    public void setRoundTopRight(int roundTopRight) {
        this.roundTopRight = roundTopRight;
    }

    public int getRoundBottomLeft() {
        return roundBottomLeft;
    }

    public void setRoundBottomLeft(int roundBottomLeft) {
        this.roundBottomLeft = roundBottomLeft;
    }

    public int getRoundBottomRight() {
        return roundBottomRight;
    }

    public void setRoundBottomRight(int roundBottomRight) {
        this.roundBottomRight = roundBottomRight;
    }

    // Getter dan setter untuk warna gradient
    public Color getStartColor() {
        return startColor;
    }

    public void setStartColor(Color startColor) {
        this.startColor = startColor;
    }

    public Color getEndColor() {
        return endColor;
    }

    public void setEndColor(Color endColor) {
        this.endColor = endColor;
    }
}

 

