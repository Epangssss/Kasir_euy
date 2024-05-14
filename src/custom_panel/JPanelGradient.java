/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package custom_panel;

/**
 *
 * @author edwar
 */
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class JPanelGradient extends JPanel {
    private Color Colorstart = Color.red;
    private Color Colorend   = Color.white;

   public JPanelGradient(Color startColor, Color endColor) {
        this.Colorstart    = startColor;
        this.Colorend     = endColor;
    }

    public Color getStartColor() {
        return Colorstart ;
    }

    public void setStartColor(Color startColor) {
        this.Colorstart  = startColor;
        repaint(); // Memperbarui tampilan saat warna berubah
    }

    public Color getEndColor() {
        return  Colorend   ;
    }

    public void setEndColor(Color endColor) {
        this. Colorend   = endColor;
       repaint(); 
    }
    
     public  JPanelGradient() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();

        GradientPaint gradientPaint = new GradientPaint(0, 0, Colorstart , 0, height,  Colorend   );
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height);

        g2d.dispose();
    }
}