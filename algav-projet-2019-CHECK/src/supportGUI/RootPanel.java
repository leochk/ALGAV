// 
// Decompiled by Procyon v0.5.36
// 

package supportGUI;

import java.awt.Point;
import java.util.ArrayList;
import java.awt.Container;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.CardLayout;
import javax.swing.JPanel;

public class RootPanel extends JPanel
{
    private static final long serialVersionUID = -7573425545656207548L;
    protected CardLayout layout;
    protected MainPanel mainPanel;
    
    public RootPanel() {
        super(new CardLayout());
        this.layout = (CardLayout)this.getLayout();
        this.add(this.mainPanel = new MainPanel(), "1664");
        this.layout.show(this, "1664");
    }
    
    public void drawPoints(final ArrayList<Point> points) {
        this.mainPanel.drawPoints(points);
    }
    
    public void shiftLeftAll() {
        this.mainPanel.shiftLeftAll();
    }
    
    public void shiftUpAll() {
        this.mainPanel.shiftUpAll();
    }
    
    public void shiftDownAll() {
        this.mainPanel.shiftDownAll();
    }
    
    public void shiftRightAll() {
        this.mainPanel.shiftRightAll();
    }
    
    public ArrayList<Point> getPoints() {
        return this.mainPanel.getPoints();
    }
    
    public void addLine(final Line l) {
        this.mainPanel.addLine(l);
    }
    
    public void addLineAndT(final Line l, final long t) {
        this.mainPanel.addLineAndT(l, t);
    }
    
    public void refreshLine() {
        this.mainPanel.refreshLine();
    }
    
    public void addCircle(final Circle c) {
        this.mainPanel.addCircle(c);
    }
    
    public void addCircleAndT(final Circle c, final long t) {
        this.mainPanel.addCircleAndT(c, t);
    }
    
    public void addPolygoneAndT(final ArrayList<Point> env, final long t) {
        this.mainPanel.addPolygoneAndT(env, t);
    }

	public void addRectangleAndT(ArrayList<Point> p, long t) {
        this.mainPanel.addRectangleAndT(p,t);
	}
}
