// 
// Decompiled by Procyon v0.5.36
// 

package supportGUI;

import java.awt.Point;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.Component;
import javax.swing.JFrame;

public class FramedDiamRace extends JFrame
{
    private static final long serialVersionUID = 599149216192397088L;
    protected RootPanel rootPanel;
    
    public FramedDiamRace(final int width, final int height, final String title, final int nbPoints) {
        super(title);
        this.setDefaultCloseOperation(3);
        this.rootPanel = new RootPanel();
        this.getContentPane().add(this.rootPanel);
        this.addKeyListener(new Keymaps(this.rootPanel, nbPoints));
        if (width < 100 || height < 100) {
            this.pack();
        }
        else {
            this.setSize(new Dimension(width, height));
        }
        this.setVisible(true);
        synchronized (Variables.lock) {
            Variables.lock.notify();
        }
        // monitorexit(Variables.lock)
    }
    
    public void drawPoints(final ArrayList<Point> points) {
        this.rootPanel.drawPoints(points);
    }
}
