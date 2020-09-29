// 
// Decompiled by Procyon v0.5.36
// 

package supportGUI;

import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import algorithms.DefaultTeam;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keymaps implements KeyListener
{
    private RootPanel rootPanel;
    private int nbPoints;
    
    public Keymaps(final RootPanel rootPanel, final int nbPoints) {
        this.rootPanel = rootPanel;
        this.nbPoints = nbPoints;
    }
    
    @Override
    public void keyPressed(final KeyEvent arg0) {
    }
    
    @Override
    public void keyReleased(final KeyEvent arg0) {
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
        switch (event.getKeyChar()) {
            case 'h': {
                this.rootPanel.shiftLeftAll();
                break;
            }
            case 'j': {
                this.rootPanel.shiftUpAll();
                break;
            }
            case 'k': {
                this.rootPanel.shiftDownAll();
                break;
            }
            case 'l': {
                this.rootPanel.shiftRightAll();
                break;
            }
            case 'z': {
                long t = System.currentTimeMillis();
                final ArrayList<Point> p = DefaultTeam.rectMini(DefaultTeam.tme6ex5(this.rootPanel.getPoints()));
                t = System.currentTimeMillis() - t;
                this.rootPanel.addRectangleAndT(p, t);
                break;
            }
            case 'd': {
                long t = System.currentTimeMillis();
                final Line l = new DefaultTeam().calculDiametre((ArrayList)this.rootPanel.getPoints());
                t = System.currentTimeMillis() - t;
                l.setColor(Color.RED);
                this.rootPanel.addLineAndT(l, t);
                break;
            }
            case 'o': {
                long t = System.currentTimeMillis();
                final Line l = new DefaultTeam().calculDiametreOptimise((ArrayList)this.rootPanel.getPoints());
                t = System.currentTimeMillis() - t;
                l.setColor(Color.GREEN);
                this.rootPanel.addLineAndT(l, t);
                break;
            }
            case 'c': {
                long t = System.currentTimeMillis();
                final Circle c = new DefaultTeam().calculCercleMin((ArrayList)this.rootPanel.getPoints());
                t = System.currentTimeMillis() - t;
                c.setColor(Color.RED);
                this.rootPanel.addCircleAndT(c, t);
                break;
            }
            case 'e': {
                try {
                    long t = System.currentTimeMillis();
                    final ArrayList<Point> env = (ArrayList<Point>)new DefaultTeam().enveloppeConvexe((ArrayList)this.rootPanel.getPoints());
                    t = System.currentTimeMillis() - t;
                    this.rootPanel.addPolygoneAndT(env, t);
                    break;
                }
                catch (Exception ex) {}
            }
            case 'r': {
                try {
                    RandomPointsGenerator.generate(this.nbPoints);
                    DiamRace.readFile();
                    this.rootPanel.refreshLine();
                }
                catch (Exception ex2) {}
                break;
            }
        }
    }
}
