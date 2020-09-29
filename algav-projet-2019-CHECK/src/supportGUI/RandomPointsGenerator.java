// 
// Decompiled by Procyon v0.5.36
// 

package supportGUI;

import java.io.FileNotFoundException;
import java.util.Random;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;

public class RandomPointsGenerator
{
    private static String filename;
    private static int numberOfPoints;
    private static int maxWidth;
    private static int maxHeight;
    private static int radius;
    
    static {
        RandomPointsGenerator.filename = "input.points";
        RandomPointsGenerator.numberOfPoints = 10000;
        RandomPointsGenerator.maxWidth = 1400;
        RandomPointsGenerator.maxHeight = 900;
        RandomPointsGenerator.radius = 200;
    }
    
    public static double distanceToCenter(final int x, final int y) {
        return Math.sqrt(Math.pow(x - RandomPointsGenerator.maxWidth / 2, 2.0) + Math.pow(y - RandomPointsGenerator.maxHeight / 2, 2.0));
    }
    
    public static void main(final String[] args) {
        try {
            final PrintStream output = new PrintStream(new FileOutputStream(RandomPointsGenerator.filename));
            final Random generator = new Random();
            for (int i = 0; i < RandomPointsGenerator.numberOfPoints; ++i) {
                int x;
                int y;
                do {
                    x = generator.nextInt(RandomPointsGenerator.maxWidth);
                    y = generator.nextInt(RandomPointsGenerator.maxHeight);
                } while (distanceToCenter(x, y) >= RandomPointsGenerator.radius * 1.4 && (distanceToCenter(x, y) >= RandomPointsGenerator.radius * 1.6 || generator.nextInt(5) != 1) && (distanceToCenter(x, y) >= RandomPointsGenerator.radius * 1.8 || generator.nextInt(10) != 1) && (RandomPointsGenerator.maxHeight / 5 >= x || x >= 4 * RandomPointsGenerator.maxHeight / 5 || RandomPointsGenerator.maxHeight / 5 >= y || y >= 4 * RandomPointsGenerator.maxHeight / 5 || generator.nextInt(100) != 1));
                output.println(String.valueOf(Integer.toString(x)) + " " + Integer.toString(y));
            }
            output.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("I/O exception: unable to create " + RandomPointsGenerator.filename);
        }
    }
    
    public static void generate(final int nbPoints) {
        try {
            final PrintStream output = new PrintStream(new FileOutputStream(RandomPointsGenerator.filename));
            final Random generator = new Random();
            for (int i = 0; i < nbPoints; ++i) {
                int x;
                int y;
                do {
                    x = generator.nextInt(RandomPointsGenerator.maxWidth);
                    y = generator.nextInt(RandomPointsGenerator.maxHeight);
                } while (distanceToCenter(x, y) >= RandomPointsGenerator.radius * 1.4 && (distanceToCenter(x, y) >= RandomPointsGenerator.radius * 1.6 || generator.nextInt(5) != 1) && (distanceToCenter(x, y) >= RandomPointsGenerator.radius * 1.8 || generator.nextInt(10) != 1) && (RandomPointsGenerator.maxHeight / 5 >= x || x >= 4 * RandomPointsGenerator.maxHeight / 5 || RandomPointsGenerator.maxHeight / 5 >= y || y >= 4 * RandomPointsGenerator.maxHeight / 5 || generator.nextInt(100) != 1));
                output.println(String.valueOf(Integer.toString(x)) + " " + Integer.toString(y));
            }
            output.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("I/O exception: unable to create " + RandomPointsGenerator.filename);
        }
    }
}
