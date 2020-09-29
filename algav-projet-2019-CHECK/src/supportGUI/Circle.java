// 
// Decompiled by Procyon v0.5.36
// 

package supportGUI;

import java.awt.Color;
import java.awt.Point;

public class Circle
{
    private Point center;
    private int radius;
    private Color c;
    
    protected Circle(final Point center, final int r, final Color c) {
        this.center = center;
        this.radius = r;
        this.c = c;
    }
    
    public Circle(final Point center, final int r) {
        this.center = center;
        this.radius = r;
        this.c = Color.RED;
    }
    
    public Point getCenter() {
        return this.center;
    }
    
    public int getRadius() {
        return this.radius;
    }
    
    protected Color getColor() {
        return this.c;
    }
    
    protected void setColor(final Color c) {
        this.c = c;
    }
}
