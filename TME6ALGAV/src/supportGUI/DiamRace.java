// 
// Decompiled by Procyon v0.5.36
// 

package supportGUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class DiamRace
{
    private static int width;
    private static int height;
    private static String title;
    private static String filename;
    private static FramedDiamRace framedGUI;
    private static int nbPoints;
    
    static {
        DiamRace.width = 0;
        DiamRace.height = 0;
        DiamRace.title = "Diameter Racer";
        DiamRace.filename = "input.points";
        DiamRace.nbPoints = 10000;
    }
    
    public static void main(final String[] args) {
        for (int i = 0; i < args.length; ++i) {
            if (args[i].charAt(0) == '-') {
                if (args[i + 1].charAt(0) == '-') {
                    System.err.println("Option " + args[i] + " expects an argument but received none");
                    return;
                }
                final String s;
                switch (s = args[i]) {
                    case "-nbPoints": {
                        Label_0170: {
                            try {
                                DiamRace.nbPoints = Integer.parseInt(args[i + 1]);
                                break Label_0170;
                            }
                            catch (Exception e2) {
                                System.err.println("Invalid argument for option " + args[i] + ": Integer type expected");
                                return;
                            }
                        }
                        ++i;
                        continue;
                    }
                    default:
                        break;
                }
                System.err.println("Unknown option " + args[i]);
                return;
            }
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DiamRace.access$4(new FramedDiamRace(DiamRace.width, DiamRace.height, DiamRace.title, DiamRace.nbPoints));
            }
        });
        synchronized (Variables.lock) {
            try {
                Variables.lock.wait();
            }
            catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        // monitorexit(Variables.lock)
        readFile();
    }
    
    public static void readFile() {
        final ArrayList<Point> points = new ArrayList<Point>();
        try {
            final BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(DiamRace.filename)));
            try {
                String line;
                while ((line = input.readLine()) != null) {
                    final String[] coordinates = line.split("\\s+");
                    points.add(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
                }
                DiamRace.framedGUI.drawPoints(points);
                synchronized (Variables.lock2) {
                    Variables.lock2.notify();
                }
                // monitorexit(Variables.lock2)
            }
            catch (IOException e) {
                System.err.println("Exception: interrupted I/O.");
                try {
                    input.close();
                }
                catch (IOException e2) {
                    System.err.println("I/O exception: unable to close " + DiamRace.filename);
                }
                return;
            }
            finally {
                try {
                    input.close();
                }
                catch (IOException e2) {
                    System.err.println("I/O exception: unable to close " + DiamRace.filename);
                }
            }
            try {
                input.close();
            }
            catch (IOException e2) {
                System.err.println("I/O exception: unable to close " + DiamRace.filename);
            }
        }
        catch (FileNotFoundException e3) {
            System.err.println("Input file not found.");
        }
    }
    
    static /* synthetic */ void access$4(final FramedDiamRace framedGUI) {
        DiamRace.framedGUI = framedGUI;
    }
}
