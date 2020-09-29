package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {
	static int cpt = 0;

	// calculDiametre: ArrayList<Point> --> Line
	// renvoie une pair de points de la liste, de distance maximum.
	public Line calculDiametre(ArrayList<Point> points) {
		if (points.size() < 3) {
			return null;
		}

		double tmp = 0;
		Point p1 = points.get(0);
		Point p2 = points.get(1);
		for (Point p : points) {
			for (Point p_ : points) {
				if (p == p_)
					continue;
				if (p.distance(p_) > tmp) {
					tmp = p.distance(p_);
					p1 = p;
					p2 = p_;
				}
			}
		}

		return new Line(p1, p2);
	}

	// calculDiametreOptimise: ArrayList<Point> --> Line
	// renvoie une pair de points de la liste, de distance maximum.
	public Line calculDiametreOptimise(ArrayList<Point> points) {
		return tme7exercice2(points);
	}

	// calculCercleMin: ArrayList<Point> --> Circle
	// renvoie un cercle couvrant tout point de la liste, de rayon minimum.
	public Circle calculCercleMin(ArrayList<Point> points) {
		Circle cercle = ritter(points);

		return cercle;
	}

	// enveloppeConvexe: ArrayList<Point> --> ArrayList<Point>
	// renvoie l'enveloppe convexe de la liste.
	public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points) {
		if (points.size() < 3) {
			return null;
		}
		return (tme6ex5(points));

	}

	/*
	 * TME 7 =====================================================================
	 */

	public static ArrayList<Point> rectMini(ArrayList<Point> points) {
		if (points.size() < 4) {
			return null;
		}

		// etape 1 : On cherche les points nord, sud, ouest et est

		// indexs nord, sud, ouest et est
		int west = 0, south = 0, east = 0, north = 0;
		for (int i = 1; i < points.size(); i++) {
			if (points.get(i).y < points.get(north).y)
				north = i;
			if (points.get(i).y > points.get(south).y)
				south = i;
			if (points.get(i).x < points.get(west).x)
				west = i;
			if (points.get(i).x > points.get(east).x)
				east = i;
		}

		// etape 2 : On initialise les lignes du rectangle passant par le nord, sud,
		// ouest et est
		Line westLine, southLine, eastLine, northLine;
		northLine = new Line(points.get(north), new Point(points.get(north).x - +1, points.get(north).y));
		southLine = new Line(points.get(south), new Point(points.get(south).x + 1, points.get(south).y));
		westLine = new Line(points.get(west), new Point(points.get(west).x, points.get(west).y + 1));
		eastLine = new Line(points.get(east), new Point(points.get(east).x, points.get(east).y - 1));

		// pour verifier qu'on effectue un tour complet
		boolean allPointsPassed = false;
		boolean westMoved = false, southMoved = false, eastMoved = false, northMoved = false;
		// compte le nombre de fois que l'un des points cardinaux fait un tour
		int count = 0;
		// index originaux des points cardinaux
		int west0, south0, east0, north0;
		north0 = north;
		south0 = south;
		west0 = west;
		east0 = east;

		// cos max => angle min
		double MAX_COS;
		int INDEX_MAX_COS;
		// les cosinus entre les lignes du rectangle et le prochain coté de l'enveloppe
		// convexe
		double west_cos, south_cos, east_cos, north_cos;

		// rectangle minimum
		ArrayList<Point> res = new ArrayList<Point>();

		res.clear();
		res.add(lineLineIntersection(westLine, southLine));
		res.add(lineLineIntersection(southLine, eastLine));
		res.add(lineLineIntersection(eastLine, northLine));
		res.add(lineLineIntersection(northLine, westLine));
		// l'aire du rectangle munimum
		double areaMin = Double.MAX_VALUE;
		// rectangle actuel
		ArrayList<Point> rect = new ArrayList<Point>(res);

		while (!allPointsPassed) {
			/*
			 * calcule le cosinus des angles respectifs des points cadinaux, avec le côté
			 * suivant de l'enveloppe convex. on cherche le cos max pour trouver quel point
			 * a l'angle minimal
			 */

			north_cos = cos(northLine, new Line(points.get(north), points.get((north + 1) % points.size())));
			south_cos = cos(southLine, new Line(points.get(south), points.get((south + 1) % points.size())));
			west_cos = cos(westLine, new Line(points.get(west), points.get((west + 1) % points.size())));
			east_cos = cos(eastLine, new Line(points.get(east), points.get((east + 1) % points.size())));

			if (north_cos > south_cos) {
				MAX_COS = north_cos;
				INDEX_MAX_COS = north;
			} else {
				MAX_COS = south_cos;
				INDEX_MAX_COS = south;
			}
			if (east_cos > MAX_COS) {
				MAX_COS = east_cos;
				INDEX_MAX_COS = east;
			}
			if (west_cos > MAX_COS) {
				MAX_COS = west_cos;
				INDEX_MAX_COS = west;
			}

			/*
			 * etape 4 : rotation de l'angle minimal trouvé précédement des cotés du
			 * rectangle
			 */

			if (INDEX_MAX_COS == west) { // WEST
				westLine = new Line(points.get(west), points.get((west + 1) % points.size()));
				southLine = makeLineWithVec(points.get(south), invert(normal(director(westLine))));
				eastLine = makeLineWithVec(points.get(east), invert(director(westLine)));
				northLine = makeLineWithVec(points.get(north), invert(director(southLine)));
				west = (west + 1) % points.size();
				westLine = makeLineWithVec(points.get(west), director(westLine));

				westMoved = true;
			} else if (INDEX_MAX_COS == east) { // EST
				eastLine = new Line(points.get(east), points.get((east + 1) % points.size()));
				northLine = makeLineWithVec(points.get(north), invert(normal(director(eastLine))));
				westLine = makeLineWithVec(points.get(west), invert(director(eastLine)));
				southLine = makeLineWithVec(points.get(south), invert(director(northLine)));
				east = (east + 1) % points.size();
				eastLine = makeLineWithVec(points.get(east), director(eastLine));

				eastMoved = true;

			} else if (INDEX_MAX_COS == north) { // NORTH
				northLine = new Line(points.get(north), points.get((north + 1) % points.size()));
				westLine = makeLineWithVec(points.get(west), invert(normal(director(northLine))));
				southLine = makeLineWithVec(points.get(south), invert(director(northLine)));
				eastLine = makeLineWithVec(points.get(east), invert(director(westLine)));
				north = (north + 1) % points.size();
				northLine = makeLineWithVec(points.get(north), director(northLine));

				northMoved = true;

			} else { // SUD
				southLine = new Line(points.get(south), points.get((south + 1) % points.size()));
				eastLine = makeLineWithVec(points.get(east), invert(normal(director(southLine))));
				northLine = makeLineWithVec(points.get(north), invert(director(southLine)));
				westLine = makeLineWithVec(points.get(west), invert(director(eastLine)));
				south = (south + 1) % points.size();
				southLine = makeLineWithVec(points.get(south), director(southLine));

				southMoved = true;

			}

			// etape 5 : creation du nouveau rectangle, mise à jour du rectangle minimal si
			// besoin, et tour de boucle si on n'a pas traité tous les points de l'enveloppe
			// convexe

			rect.clear();
			rect.add(lineLineIntersection(westLine, southLine));
			rect.add(lineLineIntersection(southLine, eastLine));
			rect.add(lineLineIntersection(eastLine, northLine));
			rect.add(lineLineIntersection(northLine, westLine));

			if (airePolygon(rect) < areaMin) {
				res = rect;
				areaMin = airePolygon(rect);
			}

			// Si l'un des points a bougé et qu'il est revenu à son point de
			// départ
			if (northMoved && north == north0 || southMoved && south == south0 || westMoved && west == west0
					|| eastMoved && east == east0) {
				count++;
			}

			allPointsPassed = (count >= 4);
		}
		return res;
	}

	public static Circle ritter(ArrayList<Point> points) {
		// etape 1 : On prend un point dummy quelconque
		Random random = new Random();
		Point dummy = points.get(random.nextInt(points.size()));
		double distance = Integer.MIN_VALUE;
		// etape 2 : p est le point le plus éloigné de q
		Point p = null;
		for (Point temp : points) {
			double d = dummy.distance(temp);
			if (d >= distance) {
				distance = d;
				p = temp;
			}
		}
		// etape 3 : q est le point le plus éloigné de p
		Point q = null;
		for (Point temp : points) {
			double d = p.distance(temp);
			if (d >= distance) {
				distance = d;
				q = temp;
			}
		}

		Point c, c2 = null;
		double cp = 0;

		// etape 4 : cercle de centre c, de rayon CP, passant par P et Q.
		c = new Point((p.x + q.x) / 2, (p.y + q.y) / 2);
		cp = c.distance(p);
		
		// etape 5 : On parcourt retire tous points compris dans le cercle c
		ArrayList<Point> l = new ArrayList<Point>();
		for (Point temp : points) {
			if (c.distance(temp) > cp) {
				l.add(temp);
			}
		}
		// S'il ne reste plus de points dans la liste
		if (l.isEmpty()) {
			// On renvoie le cercle centré en C, et de rayon CP
			return new Circle(c, (int) cp);
		}
		// etape 6 : S'il reste des points, soit s un de ces points
		Point s = null;
		// Tant que la liste n'est pas vide
		while (!l.isEmpty()) {
			ArrayList<Point> l1 = new ArrayList<Point>();
			// S est un point quelconque dans la liste des points.
			s = l.get(random.nextInt(l.size()));
			// On l'enlève de la liste.
			l.remove(s);
			// 8.

			double alpha, beta;
			// Distance du centre du cercle à S
			double sc = Point.distance(s.x, s.y, c.x, c.y);
			// T est sur le cercle,
			double st = sc + cp;
			double sc2 = st / 2;
			double cc2 = sc - sc2;
			alpha = sc2 / sc;
			beta = cc2 / sc;

			// 9.
			c2 = new Point((int) (alpha * c.x + beta * s.x), (int) (alpha * c.y + beta * s.y));
			for (Point temp : l) {
				if (c2.distance(temp) > sc2) {
					l1.add(temp);
				}
			}
			if (l1.isEmpty()) {
				return new Circle(c2, (int) sc2);
			} else {
				l = (ArrayList<Point>) l1.clone();
				c.x = c2.x;
				c.y = c2.y;
				cp = sc2;
			}
			// 10.

		}
		return new Circle(c2, (int) cp);
	}


	public static Line tme7exercice2(ArrayList<Point> points) {
		if (points.size() < 2) {
			return null;
		}

		ArrayList<Line> antipodales = calculPairesAntipodales(points);

		Point p = antipodales.get(0).getP();
		Point q = antipodales.get(0).getQ();

		for (Line a : antipodales)
			if (a.getP().distance(a.getQ()) > p.distance(q)) {
				p = a.getP();
				q = a.getQ();
			}

		return new Line(p, q);
	}

	private static ArrayList<Line> calculPairesAntipodales(ArrayList<Point> points) {
		ArrayList<Point> p = tme6ex6(points); // p est l'enveloppe convexe de points
		int n = p.size();
		ArrayList<Line> antipodales = new ArrayList<Line>();
		int k = 1;
		while (distance(p.get(k), p.get(n - 1), p.get(0)) < distance(p.get((k + 1) % n), p.get(n - 1), p.get(0)))
			k++;
		int i = 0;
		int j = k;
		while (i <= k && j < n) {
			while (distance(p.get(j), p.get(i), p.get(i + 1)) < distance(p.get((j + 1) % n), p.get(i), p.get(i + 1))
					&& j < n - 1) {
				antipodales.add(new Line(p.get(i), p.get(j)));
				j++;
			}
			antipodales.add(new Line(p.get(i), p.get(j)));
			i++;
		}
		return antipodales;
	}

	/*
	 * TME 6 =====================================================================
	 */

	/* NAIF */
	private static ArrayList<Point> tme6ex1(ArrayList<Point> points) {
		if (points.size() < 3) {
			return null;
		}

		ArrayList<Point> enveloppe = new ArrayList<Point>();

		for (Point p : points) {
			for (Point p2 : points) {
				if (p == p2)
					continue;

				boolean subzero = false;
				boolean init = false;
				boolean ok = true;
				for (Point p3 : points) {

					if (p == p3 || p2 == p3)
						continue;

					int[] pp2 = { p.x - p2.x, p.y - p2.y };
					int[] pp3 = { p.x - p3.x, p.y - p3.y };

					int prod = vect(pp2, pp3);
					if (!init) {
						if (prod == 0) {
							if (p.distance(p2) < Math.max(p.distance(p3), p2.distance(p3))) {
								ok = false;
								break;
							}

						}
						init = true;
						subzero = prod < 0;
						continue;
					}

					if (prod == 0) {
						if (p.distance(p2) < Math.max(p.distance(p3), p2.distance(p3))) {
							ok = false;
							break;
						}

					} else {
						if (prod < 0 != subzero) {
							ok = false;
							break;
						}
					}
				}

				if (ok) {
					enveloppe.add(p);
					enveloppe.add(p2);
				}
			}
		}

		return enveloppe;
	}

	/* NAIF + TRI PAR PIXEL */
	private static ArrayList<Point> tme6ex2(ArrayList<Point> points) {
		if (points.size() < 4)
			return points;
		int maxX = points.get(0).x;
		for (Point p : points)
			if (p.x > maxX)
				maxX = p.x;
		Point[] maxY = new Point[maxX + 1];
		Point[] minY = new Point[maxX + 1];
		for (Point p : points) {
			if (maxY[p.x] == null || p.y > maxY[p.x].y)
				maxY[p.x] = p;
			if (minY[p.x] == null || p.y < minY[p.x].y)
				minY[p.x] = p;
		}
		ArrayList<Point> result = new ArrayList<Point>();
		for (int i = 0; i < maxX + 1; i++)
			if (maxY[i] != null)
				result.add(maxY[i]);
		for (int i = maxX; i >= 0; i--)
			if (minY[i] != null && !result.get(result.size() - 1).equals(minY[i]))
				result.add(minY[i]);

		if (result.get(result.size() - 1).equals(result.get(0)))
			result.remove(result.size() - 1);

		return result;
	}

	/* FILTRE AKL TOUSSAINT */
	private ArrayList<Point> tme6ex3(ArrayList<Point> points) {
		if (points.size() < 4)
			return points;

		Point ouest = points.get(0);
		Point sud = points.get(0);
		Point est = points.get(0);
		Point nord = points.get(0);
		for (Point p : points) {
			if (p.x < ouest.x)
				ouest = p;
			if (p.y > sud.y)
				sud = p;
			if (p.x > est.x)
				est = p;
			if (p.y < nord.y)
				nord = p;
		}
		ArrayList<Point> result = (ArrayList<Point>) points.clone();
		for (int i = 0; i < result.size(); i++) {
			if (triangleContientPoint(ouest, sud, est, result.get(i))
					|| triangleContientPoint(ouest, est, nord, result.get(i))) {
				result.remove(i);
				i--;
			}
		}
		return result;
	}

	/* JARVIS */
	@SuppressWarnings("unused")
	private ArrayList<Point> tme6ex4(ArrayList<Point> points) {
		if (points.size() < 4)
			return points;

		Point ouest = points.get(0);
		for (Point p : points)
			if (p.x < ouest.x || (p.x == ouest.x && p.y > ouest.x))
				ouest = p;
		ArrayList<Point> enveloppe = new ArrayList<Point>();
		enveloppe.add(ouest);
		for (Point q : points) {
			if (q.equals(ouest))
				continue;
			Point ref = q;
			for (Point r : points)
				if (crossProduct(ouest, q, ouest, r) != 0) {
					ref = r;
					break;
				}
			if (ref.equals(q)) {
				enveloppe.add(q);
				continue;
			}
			double signeRef = crossProduct(ouest, q, ouest, ref);
			boolean estCote = true;
			for (Point r : points)
				if (signeRef * crossProduct(ouest, q, ouest, r) < 0) {
					estCote = false;
					break;
				}
			if (estCote) {
				enveloppe.add(q);
				break;
			}
		}

		do {
			Point p = enveloppe.get(enveloppe.size() - 2);
			Point q = enveloppe.get(enveloppe.size() - 1);
			Point r = points.get(0);
			for (Point s : points)
				if (!s.equals(p) && !s.equals(q)) {
					r = s;
					break;
				}
			for (Point s : points) {
				if (s.equals(p) || s.equals(q))
					continue;
				if (angle(p, q, q, s) < angle(p, q, q, r))
					r = s;
			}
			enveloppe.add(r);
		} while (!enveloppe.get(enveloppe.size() - 1).equals(enveloppe.get(0)));
		enveloppe.remove(0);
		return enveloppe;
	}

	/* GRAHAM */
	public static ArrayList<Point> tme6ex5(ArrayList<Point> points) {
		if (points.size() < 4)
			return points;

		ArrayList<Point> result = tme6ex2(points);
		for (int i = 1; i < result.size() + 2; i++) {
			Point p = result.get((i - 1) % result.size());
			Point q = result.get(i % result.size());
			Point r = result.get((i + 1) % (result.size()));
			if (crossProduct(p, q, p, r) > 0) {
				result.remove(i % result.size());
				if (i == 2)
					i = 1;
				if (i > 2)
					i -= 2;
			}
		}
		return result;
	}

	/* QUICKHULL */
	public static ArrayList<Point> tme6ex6(ArrayList<Point> points) {
		if (points.size() < 4)
			return points;

		Point ouest = points.get(0);
		Point sud = points.get(0);
		Point est = points.get(0);
		Point nord = points.get(0);
		for (Point p : points) {
			if (p.x < ouest.x)
				ouest = p;
			if (p.y > sud.y)
				sud = p;
			if (p.x > est.x)
				est = p;
			if (p.y < nord.y)
				nord = p;
		}
		ArrayList<Point> result = new ArrayList<Point>();
		result.add(ouest);
		result.add(sud);
		result.add(est);
		result.add(nord);

		ArrayList<Point> rest = (ArrayList<Point>) points.clone();
		for (int i = 0; i < rest.size(); i++) {
			if (triangleContientPoint(ouest, sud, est, rest.get(i))
					|| triangleContientPoint(ouest, est, nord, rest.get(i))) {
				rest.remove(i);
				i--;
			}
		}

		for (int i = 0; i < result.size(); i++) {
			Point a = result.get(i);
			Point b = result.get((i + 1) % result.size());
			Point ref = result.get((i + 2) % result.size());

			double signeRef = crossProduct(a, b, a, ref);
			double maxValue = 0;
			Point maxPoint = a;

			for (Point p : points) {
				double piki = crossProduct(a, b, a, p);
				if (signeRef * piki < 0 && Math.abs(piki) > maxValue) {
					maxValue = Math.abs(piki);
					maxPoint = p;
				}
			}
			if (maxValue != 0) {
				for (int j = 0; j < rest.size(); j++) {
					if (triangleContientPoint(a, b, maxPoint, rest.get(j))) {
						rest.remove(j);
						j--;
					}
				}
				result.add(i + 1, maxPoint);
				i--;
			}
		}
		return result;
	}

	@SuppressWarnings("unused")
	private ArrayList<Point> akltoussaint(ArrayList<Point> points) {

		if (points.size() < 4)
			return points;

		Point ouest = points.get(0);
		Point sud = points.get(0);
		Point est = points.get(0);
		Point nord = points.get(0);
		for (Point p : points) {
			if (p.x < ouest.x)
				ouest = p;
			if (p.y > sud.y)
				sud = p;
			if (p.x > est.x)
				est = p;
			if (p.y < nord.y)
				nord = p;
		}
		ArrayList<Point> result = (ArrayList<Point>) points.clone();
		for (int i = 0; i < result.size(); i++) {
			if (triangleContientPoint(ouest, sud, est, result.get(i))
					|| triangleContientPoint(ouest, est, nord, result.get(i))) {
				result.remove(i);
				i--;
			}
		}
		return result;
	}

	/*
	 * TME 5 ======================================================================
	 */

	private Circle tme5ex4(ArrayList<Point> inputPoints) {
		ArrayList<Point> points = (ArrayList<Point>) inputPoints.clone();
		if (points.size() < 1)
			return null;
		double cX, cY, cRadius, cRadiusSquared;
		for (Point p : points) {
			for (Point q : points) {
				cX = .5 * (p.x + q.x);
				cY = .5 * (p.y + q.y);
				cRadiusSquared = 0.25 * ((p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y));
				boolean allHit = true;
				for (Point s : points)
					if ((s.x - cX) * (s.x - cX) + (s.y - cY) * (s.y - cY) > cRadiusSquared) {
						allHit = false;
						break;
					}
				if (allHit)
					return new Circle(new Point((int) cX, (int) cY), (int) Math.sqrt(cRadiusSquared));
			}
		}
		double resX = 0;
		double resY = 0;
		double resRadiusSquared = Double.MAX_VALUE;
		for (int i = 0; i < points.size(); i++) {
			for (int j = i + 1; j < points.size(); j++) {
				for (int k = j + 1; k < points.size(); k++) {
					Point p = points.get(i);
					Point q = points.get(j);
					Point r = points.get(k);
					// si les trois sont colineaires on passe
					if ((q.x - p.x) * (r.y - p.y) - (q.y - p.y) * (r.x - p.x) == 0)
						continue;
					// si p et q sont sur la meme ligne, ou p et r sont sur la meme ligne, on les
					// echange
					if ((p.y == q.y) || (p.y == r.y)) {
						if (p.y == q.y) {
							p = points.get(k); // ici on est certain que p n'est sur la meme ligne de ni q ni r
							r = points.get(i); // parce que les trois points sont non-colineaires
						} else {
							p = points.get(j); // ici on est certain que p n'est sur la meme ligne de ni q ni r
							q = points.get(i); // parce que les trois points sont non-colineaires
						}
					}
					// on cherche les coordonnees du cercle circonscrit du triangle pqr
					// soit m=(p+q)/2 et n=(p+r)/2
					double mX = .5 * (p.x + q.x);
					double mY = .5 * (p.y + q.y);
					double nX = .5 * (p.x + r.x);
					double nY = .5 * (p.y + r.y);
					// soit y=alpha1*x+beta1 l'equation de la droite passant par m et
					// perpendiculaire a la droite (pq)
					// soit y=alpha2*x+beta2 l'equation de la droite passant par n et
					// perpendiculaire a la droite (pr)
					double alpha1 = (q.x - p.x) / (double) (p.y - q.y);
					double beta1 = mY - alpha1 * mX;
					double alpha2 = (r.x - p.x) / (double) (p.y - r.y);
					double beta2 = nY - alpha2 * nX;
					// le centre c du cercle est alors le point d'intersection des deux droites
					// ci-dessus
					cX = (beta2 - beta1) / (double) (alpha1 - alpha2);
					cY = alpha1 * cX + beta1;
					cRadiusSquared = (p.x - cX) * (p.x - cX) + (p.y - cY) * (p.y - cY);
					if (cRadiusSquared >= resRadiusSquared)
						continue;
					boolean allHit = true;
					for (Point s : points)
						if ((s.x - cX) * (s.x - cX) + (s.y - cY) * (s.y - cY) > cRadiusSquared) {
							allHit = false;
							break;
						}
					if (allHit) {
						System.out.println("Found r=" + Math.sqrt(cRadiusSquared));
						resX = cX;
						resY = cY;
						resRadiusSquared = cRadiusSquared;
					}
				}
			}
		}
		return new Circle(new Point((int) resX, (int) resY), (int) Math.sqrt(resRadiusSquared));
	}

	public static Circle tme5ex5(ArrayList<Point> points) {
		if (points.size() < 1)
			return null;

		ArrayList<Point> rest = (ArrayList<Point>) points.clone();
		Point dummy = rest.get(0);
		Point p = dummy;
		for (Point s : rest)
			if (dummy.distance(s) > dummy.distance(p))
				p = s;
		Point q = p;
		for (Point s : rest)
			if (p.distance(s) > p.distance(q))
				q = s;
		double cX = .5 * (p.x + q.x);
		double cY = .5 * (p.y + q.y);
		double cRadius = .5 * p.distance(q);
		rest.remove(p);
		rest.remove(q);
		while (!rest.isEmpty()) {
			Point s = rest.remove(0);
			double distanceFromCToS = Math.sqrt((s.x - cX) * (s.x - cX) + (s.y - cY) * (s.y - cY));
			if (distanceFromCToS <= cRadius)
				continue;
			double cPrimeRadius = .5 * (cRadius + distanceFromCToS);
			double alpha = cPrimeRadius / (double) (distanceFromCToS);
			double beta = (distanceFromCToS - cPrimeRadius) / (double) (distanceFromCToS);
			double cPrimeX = alpha * cX + beta * s.x;
			double cPrimeY = alpha * cY + beta * s.y;
			cRadius = cPrimeRadius;
			cX = cPrimeX;
			cY = cPrimeY;
		}
		return new Circle(new Point((int) cX, (int) cY), (int) cRadius);
	}

	private Line tme5ex6(ArrayList<Point> points) {
		if (points.size() < 2)
			return null;
		Point p = points.get(0);
		Point q = points.get(1);
		for (Point s : points)
			for (Point t : points)
				if (s.distance(t) > p.distance(q)) {
					p = s;
					q = t;
				}
		return new Line(p, q);
	}

	/*
	 * UTILITAIRE =================================================================
	 */

	public static Line makeLineWithVec(Point point, double[] p) {
		return new Line(point, new Point((int) (point.x + p[0]), (int) (point.y + p[1])));
	}

	public static double airePolygon(ArrayList<Point> points) {
		if (points.size() < 3)
			return 0;

		double xsum = 0;
		double ysum = 0;
		int l = points.size();

		for (int i = 0; i < points.size(); i++) {
			xsum += (points.get(i).x * points.get((i + 1) % l).y);
			ysum += (points.get(i).y * points.get((i + 1) % l).x);
		}
		return Math.abs((xsum - ysum) * 0.5);
	}

	public static double cos(Line l1, Line l2) {
		double[] l1dir = director(l1);
		double[] l2dir = director(l2);
		double cos = (l1dir[0] * l2dir[0] + l1dir[1] * l2dir[1]) / (magnitude(l1dir) * magnitude(l2dir));

		return Math.abs(cos);
	}

	private static double[] normal(double[] ds) {
		double[] res = { -ds[1], ds[0] };
		return res;
	}

	public static double[] invert(double[] v) {
		return normal(normal(v));
	}

	private static double magnitude(double[] l1dir) {
		return Math.sqrt(l1dir[0] * l1dir[0] + l1dir[1] * l1dir[1]);
	}

	private static double[] director(Line l) {
		double[] res = { (l.getQ().x - l.getP().x), (l.getQ().y - l.getP().y) };

		return res;
	}

	private static void rotate(Line l, double angle) {
		int X1 = l.getP().x;
		int X2 = l.getQ().x;
		int Y1 = l.getP().y;
		int Y2 = l.getQ().y;
		X2 = (int) (X1 * Math.cos(angle) - Y1 * Math.sin(angle));
		Y2 = (int) (X1 * Math.sin(angle) + Y1 * Math.cos(angle));

		l.getQ().setLocation(X2, Y2);
	}

	private static double[] rotate(double[] vector, double angle) {

		int x1 = (int) (vector[0] * Math.cos(angle) - vector[1] * Math.sin(angle));

		int y1 = (int) (vector[0] * Math.sin(angle) + vector[1] * Math.cos(angle));

		double[] res = { x1, y1 };
		return res;
	}

	private static Point lineLineIntersection(Line a, Line b) {
		return lineLineIntersection(a.getP(), a.getQ(), b.getP(), b.getQ());
	}

	private static Point lineLineIntersection(Point A, Point B, Point C, Point D) {
		double a1 = B.y - A.y;
		double b1 = A.x - B.x;
		double c1 = a1 * (A.x) + b1 * (A.y);

		double a2 = D.y - C.y;
		double b2 = C.x - D.x;
		double c2 = a2 * (C.x) + b2 * (C.y);

		double determinant = a1 * b2 - a2 * b1;

		if (determinant == 0) {
			return new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		} else {
			int x = (int) ((b2 * c1 - b1 * c2) / determinant);
			int y = (int) ((a1 * c2 - a2 * c1) / determinant);
			return new Point(x, y);
		}
	}

	private static double distance(Point p, Point a, Point b) {
		return Math.abs(crossProduct(a, b, a, p));
	}

	private static boolean triangleContientPoint(Point a, Point b, Point c, Point x) {
		double l1 = ((b.y - c.y) * (x.x - c.x) + (c.x - b.x) * (x.y - c.y))
				/ (double) ((b.y - c.y) * (a.x - c.x) + (c.x - b.x) * (a.y - c.y));
		double l2 = ((c.y - a.y) * (x.x - c.x) + (a.x - c.x) * (x.y - c.y))
				/ (double) ((b.y - c.y) * (a.x - c.x) + (c.x - b.x) * (a.y - c.y));
		double l3 = 1 - l1 - l2;
		return (0 < l1 && l1 < 1 && 0 < l2 && l2 < 1 && 0 < l3 && l3 < 1);
	}

	private static double angle(Point p, Point q, Point s, Point t) {
		if (p.equals(q) || s.equals(t))
			return Double.MAX_VALUE;
		double cosTheta = dotProduct(p, q, s, t) / (double) (p.distance(q) * s.distance(t));
		return Math.abs(Math.acos(cosTheta));
	}

	private static double dotProduct(Point p, Point q, Point s, Point t) {
		return ((q.x - p.x) * (t.x - s.x) + (q.y - p.y) * (t.y - s.y));
	}

	private static int vect(int[] a, int[] b) {
		return a[0] * b[1] - a[1] * b[0];
	}

	public static List<Point> getCircleLineIntersectionPoint(Point pointA, Point pointB, Point center, double radius) {
		double baX = pointB.x - pointA.x;
		double baY = pointB.y - pointA.y;
		double caX = center.x - pointA.x;
		double caY = center.y - pointA.y;

		double a = baX * baX + baY * baY;
		double bBy2 = baX * caX + baY * caY;
		double c = caX * caX + caY * caY - radius * radius;

		double pBy2 = bBy2 / a;
		double q = c / a;

		double disc = pBy2 * pBy2 - q;
		if (disc < 0) {
			return Collections.emptyList();
		}
		// if disc == 0 ... dealt with later
		double tmpSqrt = Math.sqrt(disc);
		double abScalingFactor1 = -pBy2 + tmpSqrt;
		double abScalingFactor2 = -pBy2 - tmpSqrt;

		Point p1 = new Point((int) (pointA.x - baX * abScalingFactor1), (int) (pointA.y - baY * abScalingFactor1));
		if (disc == 0) { // abScalingFactor1 == abScalingFactor2
			return Collections.singletonList(p1);
		}
		Point p2 = new Point((int) (pointA.x - baX * abScalingFactor2), (int) (pointA.y - baY * abScalingFactor2));
		return Arrays.asList(p1, p2);
	}

	private static double crossProduct(Point p, Point q, Point s, Point t) {
		return ((q.x - p.x) * (t.y - s.y) - (q.y - p.y) * (t.x - s.x));
	}

}
