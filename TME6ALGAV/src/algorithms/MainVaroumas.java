package algorithms;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import supportGUI.Circle;

public class MainVaroumas {
	public static void main(String[] args) {
		System.out.println("Processing Varoumas test, please wait...");
		// appelle toutes les méthodes pour "echauffer" la jvm : les premiers appels
		// prennent du temps, on évite donc de fausser les stats
		warmupJVM();

		try {
			FileManager fm = new FileManager("samples/");
			String file;
			int cpt = 0;
			ArrayList<Double> qualitiesC = new ArrayList<Double>();
			ArrayList<Double> qualitiesR = new ArrayList<Double>();
			ArrayList<Double> timeC = new ArrayList<Double>();
			ArrayList<Double> timeR = new ArrayList<Double>();

			double start, end, aireC, aireR, aireH;
			while ((file = fm.getNextFile()) != null) {
				//System.out.println("Processing " + file);
				// archive qui fait planter la lecture de fichier
				if (file.equals("src/samples/test-1.points"))
					continue;

				ArrayList<Point> points = parse(file);

				start = System.nanoTime();
				ArrayList<Point> hull = DefaultTeam.tme6ex6(points);
				ArrayList<Point> rect = DefaultTeam.rectMini(hull);
				end = System.nanoTime() - start;
				timeR.add(end);

				start = System.nanoTime();
				Circle cercle = DefaultTeam.ritter(points);
				end = System.nanoTime() - start;
				timeC.add(end);

				aireC = cercle.getRadius() * cercle.getRadius() * Math.PI;
				aireR = rect.get(0).distance(rect.get(1)) * rect.get(1).distance(rect.get(2));
				if (aireR <= 0.0)
					System.out.println("ZBEUUUUUUUUUUUUUUUUUUUUB");
				qualitiesC.add(aireC / DefaultTeam.airePolygon(hull));
				qualitiesR.add(aireR / (DefaultTeam.airePolygon(hull)));
			}

			System.out.println("#================================#");
			System.out.println("SHAMOS - CERCLE MINIMUM\n");
			System.out.println("Temps d'execution moyen : " + ((mean(timeC) / 10e6) + " ms"));
			System.out.println("Ecart type temps d'execution : " + (ecartType(timeC) / 10e6 + " ms"));
			System.out.println("Plus long temps d'execution : " + (getMax(timeC) / 10e6) + " ms");
			System.out.println("Plus court temps d'execution : " + (getMin(timeC) / 10e6) + " ms\n");

			System.out.println("Qualité moyenne : " + (mean(qualitiesC)));
			System.out.println("Ecart type qualité : " + (ecartType(qualitiesC)));
			System.out.println("Plus haute qualité : " + getMax(qualitiesC));
			System.out.println("Plus basse qualité : " + getMin(qualitiesC));

			System.out.println("#================================#");
			System.out.println("TOUSSAINT - RECTANGLE MINIMUM\n");
			System.out.println("Temps d'execution moyen : " + (mean(timeR) / 10e6 + " ms"));
			System.out.println("Ecart type temps d'execution : " + (ecartType(timeR) / 10e6 + " ms"));
			System.out.println("Plus long temps d'execution : " + (getMax(timeR) / 10e6) + " ms");
			System.out.println("Plus court temps d'execution : " + (getMin(timeR) / 10e6) + " ms\n");

			System.out.println("Qualité moyenne : " + mean(qualitiesR));
			System.out.println("Ecart type qualité : " + (ecartType(qualitiesR)));
			System.out.println("Plus haute qualité : " + getMax(qualitiesR));
			System.out.println("Plus basse qualité : " + getMin(qualitiesR));
			System.out.println("#================================#");

			File dataQualityR = new File("output/qualityR.csv");
			File dataTimeR = new File("output/timeR.csv");
			File dataQualityC = new File("output/qualityC.csv");
			File dataTimeC = new File("output/timeC.csv");
			FileWriter writerQR = new FileWriter(dataQualityR);
			FileWriter writerTR = new FileWriter(dataTimeR);
			FileWriter writerQC = new FileWriter(dataQualityC);
			FileWriter writerTC = new FileWriter(dataTimeC);

			dataQualityR.createNewFile();
			dataTimeR.createNewFile();
			dataQualityC.createNewFile();
			dataTimeC.createNewFile();

			for (int i = 0; i < qualitiesC.size(); i++) {
				writerQR.write(i + " " + qualitiesR.get(i) + "\n");
				writerTR.write(i + " " + timeR.get(i) / 10e6 + "\n");
				writerQC.write(i + " " + qualitiesC.get(i) + "\n");
				writerTC.write(i + " " + timeC.get(i) / 10e6 + "\n");
			}

			writerQC.close();
			writerQR.close();
			writerTC.close();
			writerTR.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<Point> parse(String filename) throws FileNotFoundException {
		ArrayList<Point> points = new ArrayList<Point>();
		File file = new File(filename);
		Scanner lecteur;
		lecteur = new Scanner(file);
		while (lecteur.hasNextLine()) {
			String coords = lecteur.nextLine();
			String[] coords2 = coords.split(" ");

			points.add(new Point(Integer.parseInt(coords2[0]), Integer.parseInt(coords2[1])));
		}
		lecteur.close();
		return points;
	}

	public static Double ecartType(ArrayList<Double> l) {
		Double mean = mean(l);
		Double sum = 0.0;
		for (Double d : l) {
			sum += Math.pow((d - mean), 2);
		}
		return Math.sqrt(sum / l.size());
	}

	public static Double mean(ArrayList<Double> l) {
		return l.stream().mapToDouble(val -> val).average().orElse(0.0);
	}

	public static Double getMax(ArrayList<Double> l) {
		return Collections.max(l);
	}

	public static Double getMin(ArrayList<Double> l) {
		return Collections.min(l);
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
