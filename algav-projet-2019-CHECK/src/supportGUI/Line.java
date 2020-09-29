// 
// Decompiled by Procyon v0.5.36
// 

package supportGUI;

import java.awt.Color;
import java.awt.Point;

public class Line
{
    private Point p;
    private Point q;
    private Color c;
    
    protected Line(final Point p, final Point q, final Color c) {
        this.p = p;
        this.q = q;
        this.c = c;
    }
    
    public Line(final Point p, final Point q) {
        this.p = p;
        this.q = q;
        this.c = Color.RED;
    }
    
    public Point getP() {
        return this.p;
    }
    
    public Point getQ() {
        return this.q;
    }
    
    protected Color getColor() {
        return this.c;
    }
    
    protected void setP(final Point p) {
        this.p = p;
    }
    
    protected void setQ(final Point q) {
        this.q = q;
    }
    
    protected void setColor(final Color c) {
        this.c = c;
    }
    
    protected double distance() {
        return Math.sqrt(Math.pow(this.p.getX() - this.q.getX(), 2.0) + Math.pow(this.p.getY() - this.q.getY(), 2.0));
    }
    
    protected static double distance(final Point p, final Point q) {
        return Math.sqrt(Math.pow(p.getX() - q.getX(), 2.0) + Math.pow(p.getY() - q.getY(), 2.0));
    }
}
