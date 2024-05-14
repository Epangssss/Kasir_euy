package custom_panel;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.beans.*;

public class p_warnaa extends JPanel {
    private Color startColor = Color.red;
    private Color endColor = Color.white;

    public Color getStartColor() {
        return startColor;
    }

    public void setStartColor(Color startColor) {
        Color oldStartColor = this.startColor;
        this.startColor = startColor;
        firePropertyChange("startColor", oldStartColor, startColor);
        repaint();
    }

    public Color getEndColor() {
        return endColor;
    }

    public void setEndColor(Color endColor) {
        Color oldEndColor = this.endColor;
        this.endColor = endColor;
        firePropertyChange("endColor", oldEndColor, endColor);
        repaint();
    }

    public p_warnaa() {
        //   setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, getWidth(), getHeight(), endColor);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
    
    // Menambahkan anotasi untuk properti startColor
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        super.removePropertyChangeListener(listener);
    }

    // Menambahkan anotasi untuk properti endColor
    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        super.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        super.removePropertyChangeListener(propertyName, listener);
    }
    
    // Menambahkan metode ini agar properti endColor muncul di Properties
    public void setEndColorProperty(Color endColor) {
        setEndColor(endColor);
    }
}