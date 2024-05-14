
package custom_panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import javax.swing.JPanel;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;


public class panel_custom extends JPanel {
 
    private int roundTopLeft = 0;
   int roundTopRight = 0;
   int roundBottomLeft = 0;
   int roundBottomRIght = 0;
   
    public int getRoundeTopLeft() {
        return roundTopLeft;
    }

    public void setRoundeTopLeft(int roundeTopLeft) {
        this.roundTopLeft = roundeTopLeft;
    }

    public int getRoundfTopRight() {
        return roundTopRight;
    }

    public void setRoundfTopRight(int roundfTopRight) {
        this.roundTopRight = roundfTopRight;
    }

    public int getRoundBotoomLeft() {
        return roundBottomLeft;
    }

    public void setRoundBotoomLeft(int roundBotoomLeft) {
        this.roundBottomLeft = roundBotoomLeft;
    }

    public int getRoundBotoomRIght() {
        return roundBottomRIght;
    }

    public void setRoundBotoomRIght(int roundBotoomRIght) {
        this.roundBottomRIght = roundBotoomRIght;
    }
    

   
    
    
    

    public  panel_custom() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics graphic) {
     //   super.paintComponent(graphic);
        Graphics2D g2 = (Graphics2D) graphic.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Add your custom painting code here
     g2.setColor(getBackground());
     Area area = new Area (createRoundTopLeft());
  
    if (roundTopRight>0){
        area.intersect (new Area(createRoundTopRIght()));
    }
     if (roundTopLeft>0){
        area.intersect (new Area(createRoundBottomLeft()));
    }
        if (roundBottomRIght >0){
        area.intersect (new Area(createRoundBottomRight()));
    }
        
        g2.fill(area);
        g2.dispose();
        super.paintComponent(graphic);
    }
    
    

    private Shape createRoundTopLeft() {
   int width =  getWidth();
    int height = getHeight();
    int  roundX=Math.min(width, roundTopLeft);
    int  roundY=Math.min(height, roundTopLeft);
    
    Area area = new Area (new RoundRectangle2D.Double(0,0, width, height, roundX, roundY));
     area.add(new Area (new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
     area.add(new Area (new Rectangle2D.Double(0, roundY/ 2,   width, height - roundY / 2)));
     
    
    return area;
    }

    private Shape createRoundTopRIght() {
      int width =  getWidth();
    int height = getHeight();
    int  roundX=Math.min(width, roundTopRight);
    int  roundY=Math.min(height, roundTopRight);
    
    Area area = new Area (new RoundRectangle2D.Double(0,0, width, height, roundX, roundY));
     area.add(new Area (new Rectangle2D.Double(0,0, width - roundX / 2, height)));
     area.add(new Area (new Rectangle2D.Double(0, roundY / 2,  width, height - roundY / 2)));
     
    
    return area;
    
    }

    private Shape createRoundBottomLeft() {
    int width =  getWidth();
    int height = getHeight();
    int  roundX=Math.min(width, roundBottomLeft);
    int  roundY=Math.min(height, roundBottomLeft);
    
    Area area = new Area (new RoundRectangle2D.Double(0,0, width, height, roundX, roundY));
     area.add(new Area (new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
     area.add(new Area (new Rectangle2D.Double(0, 0,  width, height - roundY / 2)));
     
    
    return area;
    
    
    }

    private Shape createRoundBottomRight() {
    int width =  getWidth();
    int height = getHeight();
    int  roundX=Math.min(width, roundBottomRIght);
    int  roundY=Math.min(height, roundBottomRIght);
    
    Area area = new Area (new RoundRectangle2D.Double(0,0, width, height, roundX, roundY));
     area.add(new Area (new Rectangle2D.Double(0, 0,  width - roundX / 2, height)));
     area.add(new Area (new Rectangle2D.Double(0, 0,  width, height - roundY / 2)));
     
    
    return area;
    }
   }