import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;


public class closestpair {
    ArrayList<Point> pointsX = new ArrayList<>();
    int dimension;
    public static void main(String[] args) {
      //  new closestpair().run(new String[]{"/home/maximilian/IdeaProjects/matching-point/src/huge.tsp"});

        new closestpair().run(args);
    }

    private void run(String[] args) {
        parseWord(args[0]);
        /* for (int i = 0; i < dimension-1; i++) {
            if (pointsX.get(i).distanceTo(pointsX.get(i+1)) == 0) {
                System.out.println(args[0] + ": " + dimension + " " + 0);
                return;
            }
        } */

        System.out.println(args[0] + ": " + dimension + " " + closest(pointsX, 0, dimension-1));

    }


    private double closest(ArrayList<Point> pointsX, int start, int stop) {
        if (stop <= start)
            return  Double.POSITIVE_INFINITY;
        int half = start + (stop - start) / 2;

        double deltaR = closest(pointsX, start, half);
        double deltaL = closest(pointsX, half + 1, stop);
        double delta = Math.min(deltaR, deltaL);

        ArrayList<Point> s = new ArrayList<>();
        for (int i = half; i <= stop; i++) {
            if (Math.abs(pointsX.get(i).x() - pointsX.get(half).x()) < delta) {
                s.add(pointsX.get(i));
            } else {
                break;
            }
        }

        for (int i = half-1; i > start; i--) {
            if (Math.abs(pointsX.get(i).x() - pointsX.get(half).x()) < delta) {
                s.add(pointsX.get(i));
            } else {
                break;
            }
        }
        s.sort(Point::compareY);
        for (int i = 0; i < s.size(); i++) {
            for (int j = i + 1; (j < s.size()) && (s.get(j).y() - (s.get(i).y()) < delta); j++) {
                double distance = s.get(i).distanceTo(s.get(j));
                if (distance < delta) {
                    delta = distance;
                }
            }
        }
        return delta;
    }



    private void parseWord(String infile) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(infile));
            String s;
            while (!(s = br.readLine()).startsWith("DIMENSION")) {
            }
            dimension = Integer.parseInt(s.replaceAll(" ", "").substring(10)); //Dimension
            while (!br.readLine().startsWith("NODE")) {
            }
            for (int i = 0; i < dimension; i++) {
                s = br.readLine().replaceAll("\\s{2,}", " ").trim();
                String[] strings = s.split(" ");
                pointsX.add(new Point(Double.parseDouble(strings[1]), Double.parseDouble(strings[2])));
                }
            pointsX.sort(Point::compareX);
        } catch (IOException e) {
            System.out.println("No file found");
        }
    }

}

class Point {

    Double x,y;

    public Point(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo (Object o) {
        Point other = (Point) o;
        return Math.hypot(this.x - other.x, this.y - other.y);
    }

    public int compareX(Point p) {
        return this.x.compareTo(p.x);
    }

    public int compareY(Point p) {
        return this.y.compareTo(p.y);
    }

    public double x() {
        return x;
    }
    public double y() {
        return y;
    }

    @Override
    public String toString() {
        return x.toString() + ":" + y.toString();
    }
}
