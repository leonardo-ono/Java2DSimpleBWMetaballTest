package javametaballtest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Simple B&W Metaball Demo.
 * 
 * @author Leonardo Ono (ono.leo80@gmail.com);
 */
public class View extends JPanel {

    private final BufferedImage offscr;
    private final BufferedImage offscr2;
    
    public View() {
        offscr = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        offscr2 = new BufferedImage(800, 600, BufferedImage.TYPE_BYTE_BINARY);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw((Graphics2D) offscr.getGraphics());
        Graphics g2 = offscr2.getGraphics();
        g2.drawImage(offscr, 0, 0, null);
        g.drawImage(offscr2, 0, 0, null);
        try {
            Thread.sleep(1000 / 60);
        } catch (InterruptedException ex) {
        }
        repaint();
    }
    
    private final float[] dist = new float[] { 0.0f, 1.0f };
    private final Color[] colors = new Color[] { 
        new Color(255, 255, 255, 255), new Color(0, 0, 0, 0) };
    
    private void draw(Graphics2D g) {
        g.setBackground(Color.BLACK);
        g.clearRect(0, 0, getWidth(), getHeight());
        for (int id = 1; id < 30; id++) {
            double t = System.nanoTime() * 0.000000001;
            double x = 400 + 200 * Math.cos(t / (1.45 * id)) 
                    - 200 * Math.sin(t / (2.18 * id));

            double y = 300 + 150 * Math.sin(t / (1.67 * id)) 
                    - 150 * Math.cos(t / (3.45 * id));
            
            RadialGradientPaint rgp = new RadialGradientPaint(
                    new Point2D.Double(x, y), 128.0f, dist, colors);
            
            g.setPaint(rgp);
            g.fillOval((int) (x - 128), (int) (y - 128), 256, 256);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            View view = new View();
            view.setPreferredSize(new Dimension(800, 600));
            JFrame frame = new JFrame();
            frame.setTitle("Java Simple B&W Metaball Demo");
            frame.getContentPane().add(view);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
    
}
