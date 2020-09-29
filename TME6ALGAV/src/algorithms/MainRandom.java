package algorithms;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainRandom {
	private static final int NB_POINTSMIN = 256;
	private static final int NB_POINTSMAX = 500000;

	private static final int MINBOUND = 0;
	private static final int MAXBOUND = 1000;

	public static void main(String[] args) {
		Random r = new Random();
		System.out.println("Compute Toussaint and Ritter on random points, please wait... ");
		// appelle toutes les méthodes pour "echauffer" la jvm : les premiers appels
		// prennent du temps, on évite donc de fausser les stats
		warmupJVM();
		
		ArrayList<Point> points = new ArrayList<Point>();
		ArrayList<Point> test = new ArrayList<Point>();

		ArrayList<Double> timeR = new ArrayList<Double>();
		ArrayList<Double> timeC = new ArrayList<Double>();

		// points random création
		for (int i = 0; i < NB_POINTSMAX; i++) {
			points.add(
					new Point(getRandomNumberInRange(MINBOUND, MAXBOUND), getRandomNumberInRange(MINBOUND, MAXBOUND)));
		}

		// 256 points au hasard à l'instance de test
		for (int i = 0; i < NB_POINTSMIN; i++) {
			test.add(points.remove(r.nextInt(points.size())));
		}

		// mesure de temps d'execution pour un ensemble de point de taille variant entre
		// 256 et 16 384
		long start, end;

		int step = 2000;
		ArrayList<Point> hull;
		for (int i = 0; i < NB_POINTSMAX - NB_POINTSMIN; i += step) {
			if (points.size() < step) {
				test.addAll(points);
			} else {
				for (int j = 0; j < step; j++)
					test.add(points.remove(r.nextInt(points.size())));
			}
			if (i%1000 == 0) System.out.println(points.size());
			start = System.nanoTime();
			hull = DefaultTeam.tme6ex5(test);
			DefaultTeam.rectMini(hull);
			end = System.nanoTime() - start;
			timeR.add((double) end);

			start = System.nanoTime();
			hull = DefaultTeam.tme6ex5(test);
			DefaultTeam.ritter(hull);
			end = System.nanoTime() - start;
			timeC.add((double) end);

		}

		File dataR = new File("output/randomTimeR.csv");
		try {
			FileWriter writer = new FileWriter(dataR);
			dataR.createNewFile();
			for (int i = 0; i < timeR.size(); i++) {
				writer.write(i * step + " " + timeR.get(i) / 10e6 + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		File dataC = new File("output/randomTimeC.csv");
		try {
			FileWriter writer = new FileWriter(dataC);
			dataC.createNewFile();
			for (int i = 0; i < timeC.size(); i++) {
				writer.write(i * step + " " + timeC.get(i) / 10e6 + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static void warmupJVM() {
		ArrayList<Point> p = new ArrayList<Point>();

		// points random création
		for (int i = 0; i < 10000; i++) {
			p.add(new Point(getRandomNumberInRange(0, 1000), getRandomNumberInRange(0, 1000)));
		}

		// jvm warming-up pour eviter de fausser les stats : les premiers appels
		// prennent beaucoup de temps
		for (int i = 0; i < 100; i++) {
			DefaultTeam.rectMini(DefaultTeam.tme6ex5(p));
			DefaultTeam.ritter(p);
			System.nanoTime();
		}
	}

	
	private static int getRandomNumberInRange(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
