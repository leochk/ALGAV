// 
// Decompiled by Procyon v0.5.36
// 

package supportGUI;

import java.awt.Point;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class MainPanel extends JPanel
{
    private static final long serialVersionUID = -662473925955493029L;
    protected DisplayPanel displayPanel;
    
    public MainPanel() {
        super(new BorderLayout());
        this.add(this.displayPanel = new DisplayPanel(), "Center");
    }
    
    public void drawPoints(final ArrayList<Point> points) {
        this.displayPanel.drawPoints(points);
    }
    
    public void shiftLeftAll() {
        this.displayPanel.shiftLeftAll();
    }
    
    public void shiftUpAll() {
        this.displayPanel.shiftUpAll();
    }
    
    public void shiftDownAll() {
        this.displayPanel.shiftDownAll();
    }
    
    public void shiftRightAll() {
        this.displayPanel.shiftRightAll();
    }
    
    public ArrayList<Point> getPoints() {
        return this.displayPanel.getPoints();
    }
    
    public void addLine(final Line l) {
        this.displayPanel.addLine(l);
    }
    
    public void addLineAndT(final Line l, final long t) {
        this.displayPanel.addLineAndT(l, t);
    }
    
    public void refreshLine() {
        this.displayPanel.refreshLine();
    }
    
    public void addCircle(final Circle c) {
        this.displayPanel.addCircle(c);
    }
    
    public void addCircleAndT(final Circle c, final long t) {
        this.displayPanel.addCircleAndT(c, t);
    }
    
    public void addPolygoneAndT(final ArrayList<Point> env, final long t) {
        this.displayPanel.addPolygoneAndT(env, t);
    }

	public void addRectangleAndT(ArrayList<Point> p, long t) {
        this.displayPanel.addRectangleAndT(p,t);
		
	}
}
