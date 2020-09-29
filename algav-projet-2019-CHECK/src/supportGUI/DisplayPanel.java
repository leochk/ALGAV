// 
// Decompiled by Procyon v0.5.36
// 

package supportGUI;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class DisplayPanel extends JPanel
{
    private static final long serialVersionUID = -1401707925288150149L;
    private static final int xBorder = 10;
    private static final int yBorder = 10;
    private static final int xStep = 10;
    private static final int yStep = 10;
    private int xModifier;
    private int yModifier;
    private Graphics2D g2d;
    private ArrayList<Point> points;
    private ArrayList<Line> lines;
    private ArrayList<Circle> circles;
    private long time;
    
    public DisplayPanel() {
        this.setPreferredSize(new Dimension(1500, 1000));
        this.xModifier = 10;
        this.yModifier = 10;
        this.points = new ArrayList<Point>();
        this.lines = new ArrayList<Line>();
        this.circles = new ArrayList<Circle>();
        this.time = 0L;
    }
    
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        (this.g2d = (Graphics2D)g.create()).setFont(new Font(this.g2d.getFont().getName(), 1, 18));
        this.g2d.drawString("Clavier:", 5, 20);
        this.g2d.drawString("- 'r' pour rafra\u00eechir le nuage de points", 15, 40);
        this.g2d.drawString("- 'd' pour lancer calculDiametre", 15, 60);
        this.g2d.drawString("- 'c' pour lancer calculCercleMin", 15, 80);
        this.g2d.drawString("- 'e' pour lancer enveloppeConvexe", 15, 100);
        this.g2d.drawString("- 'o' pour lancer calculDiametreOpitmise", 15, 120);
        this.g2d.drawString("- 'z' pour lancer rectMini", 15, 140);

        this.g2d.drawString("- 'h', 'j', 'k', 'l' pour d\u00e9placer les points", 15, 160);
        this.g2d.setColor(Color.BLUE);
        this.g2d.setStroke(new BasicStroke(6.0f, 1, 1));
        for (int i = 0; i < this.points.size(); ++i) {
            final int x;
            final int y;
            if ((x = (int)this.points.get(i).getX() + this.xModifier) >= 10 && (y = (int)this.points.get(i).getY() + this.yModifier) >= 10) {
                this.g2d.drawLine(x, y, x, y);
            }
        }
        this.g2d.setStroke(new BasicStroke(4.0f, 1, 1));
        for (int i = 0; i < this.lines.size(); ++i) {
            final int x = (int)this.lines.get(i).getP().getX() + this.xModifier;
            final int y = (int)this.lines.get(i).getP().getY() + this.yModifier;
            final int v = (int)this.lines.get(i).getQ().getX() + this.xModifier;
            final int w = (int)this.lines.get(i).getQ().getY() + this.yModifier;
            this.g2d.setColor(this.lines.get(i).getColor());
            this.g2d.drawLine(x, y, v, w);
        }
        for (int j = 0; j < this.circles.size(); ++j) {
            final int x = (int)this.circles.get(j).getCenter().getX() + this.xModifier;
            final int y = (int)this.circles.get(j).getCenter().getY() + this.yModifier;
            final int r = this.circles.get(j).getRadius();
            this.g2d.setColor(this.circles.get(j).getColor());
            final Ellipse2D.Double c = new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
            this.g2d.draw(c);
        }
        if (this.time != 0L) {
            this.g2d.setColor(Color.BLACK);
            this.g2d.setFont(new Font(this.g2d.getFont().getName(), 1, 32));
            this.g2d.drawString("Temps de calcul: " + Long.toString(this.time) + " ms", 20, 190);
        }
    }
    
    public void drawPoints(final ArrayList<Point> points) {
        this.points = points;
        this.repaint();
    }
    
    public void shiftLeftAll() {
        this.xModifier -= 10;
        this.repaint();
    }
    
    public void shiftUpAll() {
        this.yModifier -= 10;
        this.repaint();
    }
    
    public void shiftDownAll() {
        this.yModifier += 10;
        this.repaint();
    }
    
    public void shiftRightAll() {
        this.xModifier += 10;
        this.repaint();
    }
    
    public ArrayList<Point> getPoints() {
        return this.points;
    }
    
    public void addLine(final Line l) {
        this.lines.add(l);
        this.repaint();
    }
    
    public void addLineAndT(final Line l, final long t) {
        this.lines.add(l);
        this.time = t;
        this.repaint();
    }
    
    public void refreshLine() {
        this.lines.clear();
        this.circles.clear();
        this.time = 0L;
        this.repaint();
    }
    
    public void addCircle(final Circle c) {
        this.circles.add(c);
        this.repaint();
    }
    
    public void addCircleAndT(final Circle c, final long t) {
        this.circles.add(c);
        this.time = t;
        this.repaint();
    }
    
    public void addPolygoneAndT(final ArrayList<Point> env, final long t) {
        for (int i = 0; i < env.size() - 1; ++i) {
            this.lines.add(new Line(env.get(i), env.get(i + 1), Color.RED));
        }
        this.lines.add(new Line(env.get(env.size() - 1), env.get(0), Color.RED));
        this.time = t;
        this.repaint();
    }

	public void addRectangleAndT(ArrayList<Point> env, long t) {
		for (int i = 0; i < env.size() - 1; ++i) {
            this.lines.add(new Line(env.get(i), env.get(i + 1), Color.RED));
        }
        this.lines.add(new Line(env.get(env.size() - 1), env.get(0), Color.RED));
        this.time = t;
        this.repaint();
	}
}
