import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by ma5176sc-s on 09/05/18.
 */
public class gorilla {

    private HashMap<String, String> dnaOfSpecies = new HashMap<>();
    private static final char ALIGNMENT_CONSTANT = 65;
    private static final char GAP_CONSTANT = 26;
    private static final int[][] blosum = new int[][]
    {{4, -2, 0, -2, -1, -2, 0, -2, -1, 0, -1, -1, -1, -2, 0, -1, -1, -1, 1, 0, 0, 0, -3, 0, -2, -1, -4 }, //A
    {-2, 4, -3, 4, 1, -3, -1, 0, -3, 0, 0, -4, -3, 3, 0, -2, 0, -1, 0, -1, 0, -3, -4, -1, -3, 1, -4 }, //B
    {0, -3, 9, -3, -4, -2, -3, -3, -1, 0, -3, -1, -1, -3, 0, -3, -3, -3, -1, -1, 0, -1, -2, -2, -2, -3, -4 }, //C
    {-2, 4, -3, 6, 2, -3, -1, -1, -3, 0, -1, -4, -3, 1, 0, -1, 0, -2, 0, -1, 0, -3, -4, -1, -3, 1, -4}, //D
    {-1, 1, -4, 2, 5, -3, -2, 0, -3, 0, 1, -3, -2, 0, 0, -1, 2, 0, 0, -1, 0, -2, -3, -1, -2, 4, -4}, //E
    {-2, -3, -2, -3, -3, 6, -3, -1, 0, 0, -3, 0, 0, -3, 0, -4, -3, -3, -2, -2, 0, -1, 1, -1, 3, -3, -4}, //F
    {0, -1, -3, -1, -2, -3, 6, -2, -4, 0, -2, -4, -3, 0, 0, -2, -2, -2, 0, -2, 0, -3, -2, -1, -3, -2, -4 }, //G
    {-2, 0, -3, -1, 0, -1, -2, 8, -3, 0, -1, -3, -2, 1, 0, -2, 0, 0, -1, -2, 0, -3, -2, -1, 2, 0, -4 }, //H
    {-1, -3, -1, -3, -3, 0, -4, -3, 4, 0, -3, 2, 1, -3, 0, -3, -3, -3, -2, -1, 0, 3, -3, -1, -1, -3, -4 }, //I
    {-4, -4, -4, -4, -4, -4, -4, -4, -4, 0, -4, -4, -4, -4, 0, -4, -4, -4, -4, -4, 0, -4, -4, -4, -4, -4, 1 }, //* J
    {-1, 0, -3, -1, 1, -3, -2, -1, -3, 0, 5, -2, -1, 0, 0, -1, 1, 2, 0, -1, 0, -2, -3, -1, -2, 1, -4 }, //K
    {-1, -4, -1, -4, -3, 0, -4, -3, 2, 0, -2, 4, 2, -3, 0, -3, -2, -2, -2, -1, 0, 1, -2, -1, -1, -3, -4 }, //L
    {-1, -3, -1, -3, -2, 0, -3, -2, 1, 0, -1, 2, 5, -2, 0, -2, 0, -1, -1, -1, 0, 1, -1, -1, -1, -1, -4 }, //M
    {-2, 3, -3, 1, 0, -3, 0, 1, -3, 0, 0, -3, -2, 6, 0, -2, 0, 0, 1, 0, 0, -3, -4, -1, -2, 0, -4 }, //N
    {-4, -4, -4, -4, -4, -4, -4, -4, -4, 0, -4, -4, -4, -4, 0, -4, -4, -4, -4, -4, 0, -4, -4, -4, -4, -4, 1 }, //* O
    {-1, -2, -3, -1, -1, -4, -2, -2, -3, 0, -1, -3, -2, -2, 0, 7, -1, -2, -1, -1, 0, -2, -4, -2, -3, -1, -4 }, //P
    {-1, 0, -3, 0, 2, -3, -2, 0, -3, 0, 1, -2, 0, 0, 0, -1, 5, 1, 0, -1, 0, -2, -2, -1, -1, 3, -4 }, //Q
    {-1, -1, -3, -2, 0, -3, -2, 0, -3, 0, 2, -2, -1, 0, 0, -2, 1, 5, -1, -1, 0, -3, -3, -1, -2, 0, -4 }, //R
    {1, 0, -1, 0, 0, -2, 0, -1, -2, 0, 0, -2, -1, 1, 0, -1, 0, -1, 4, 1, 0, -2, -3, 0, -2, 0, -4 }, //S
    {0, -1, -1, -1, -1, -2, -2, -2, -1, 0, -1, -1, -1, 0, 0, -1, -1, -1, 1, 5, 0, 0, -2, 0, -2, -1, -4 }, //T
    {-4, -4, -4, -4, -4, -4, -4, -4, -4, 0, -4, -4, -4, -4, 0, -4, -4, -4, -4, -4, 0, -4, -4, -4, -4, -4, 1 }, //* U
    {0, -3, -1, -3, -2, -1, -3, -3, 3, 0, -2, 1, 1, -3, 0, -2, -2, -3, -2, 0, 0, 4, -3, -1, -1, -2, -4 }, //V
    {-3, -4, -2, -4, -3, 1, -2, -2, -3, 0, -3, -2, -1, -4, 0, -4, -2, -3, -3, -2, 0, -3, 11, -2, 2, -3, -4 }, //W
    {0, -1, -2, -1, -1, -1, -1, -1, -1, 0, -1, -1, -1, -1, 0, -2, -1, -1, 0, 0, 0, -1, -2, -1, -1, -1, -4 }, //X
    {-2, -3, -2, -3, -2, 3, -3, 2, -1, 0, -2, -1, -1, -2, 0, -3, -1, -2, -2, -2, 0, -1, 2, -1, 7, -2, -4 }, // Y
    {-1, 1, -3, 1, 4, -3, -2, 0, -3, 0, 1, -3, -1, 0, 0, -1, 3, 0, 0, -1, 0, -2, -3, -1, -2, 4, -4 }, //Z
    {-4, -4, -4, -4, -4, -4, -4, -4, -4, 0, -4, -4, -4, -4, 0, -4, -4, -4, -4, -4, 0, -4, -4, -4, -4, -4, -4} //*
};
    private int[][] m;
    public static void main(String[] args) {
     //   args = new String[1];
     //   args[0] = "/h/d5/l/ma5176sc-s/IdeaProjects/Lab5/src/input";
        new gorilla().run(args);
    }

    private void run(String[] args) {
        readInputFromFile(args[0]);
    }

    private void printSpecie(String specie1, String specie2) {
        String si = dnaOfSpecies.get(specie1);
        String sj = dnaOfSpecies.get(specie2);
        m = new int[si.length()+1][sj.length()+1];
        make_table(si, sj);
        System.out.print(specie1 + "--" + specie2 + ": ");
        System.out.println(m[si.length()][sj.length()]);
     //   System.out.println(OPT(si, sj, si.length(), sj.length()));
    }


    private void make_table(String si, String sj) {
        for (int i = 1; i <= si.length(); i++)
            m[i][0] = m[i - 1][0] - 4;
        for (int j = 1; j <= sj.length(); j++)
            m[0][j] = m[0][j - 1] - 4;

        for (int i = 1; i <= si.length(); i++) {
            for (int j = 1; j <= sj.length(); j++) {
                int scoreDiag = m[i - 1][j - 1] + blosum[si.charAt(i-1)-ALIGNMENT_CONSTANT][sj.charAt(j-1)-ALIGNMENT_CONSTANT];
                int scoreLeft = m[i][j - 1] - 4;
                int scoreUp = m[i - 1][j] - 4;
                m[i][j] = Math.max(Math.max(scoreDiag, scoreLeft), scoreUp);
            }
        }
    }


   private int OPT(String si, String sj, int i, int j) {
        if (m[i][j] != Integer.MIN_VALUE) {
            return m[i][j];
        }
        if (j == 0 || i == 0) {
            int cost = blosum[si.charAt(i)-ALIGNMENT_CONSTANT][sj.charAt(j)-ALIGNMENT_CONSTANT] + (i+j)*(-4);
            m[i][j] = cost;
            return cost;
        } else {
            int a,b,c;
            a = blosum[si.charAt(i-1)-ALIGNMENT_CONSTANT][sj.charAt(j-1)-ALIGNMENT_CONSTANT] + OPT(si, sj, i-1, j-1); //Diagonalt
            b = OPT(si, sj, i, j-1) - 4; //Upp
            c = OPT(si, sj, i-1, j) - 4; //Höger
            int highest = Math.max(Math.max(a, b), c);
            m[i][j] = highest;
            return highest;
        }
    }

    private String getAlignment(String si, String sj) {
        String aligned1 = "";
        String aligned2 = "";

        int i = si.length()-1;
        int j = sj.length()-1;

        while (i > 0 && j > 0) {
            if (m[i][j] - blosum[si.charAt(i)-ALIGNMENT_CONSTANT][sj.charAt(j)-ALIGNMENT_CONSTANT] == m[i-1][j-1]) {
                aligned1 = si.charAt(i) + aligned1;
                aligned2 = sj.charAt(j) + aligned2;
                j--;
                i--;
            } else if (m[i][j] - blosum[GAP_CONSTANT][sj.charAt(j)-ALIGNMENT_CONSTANT] == m[i][j-1]) {
                aligned2 = sj.charAt(j) + aligned2;
                aligned1 = "-" + aligned1;
                j--;
            } else if (m[i][j] - blosum[GAP_CONSTANT][sj.charAt(j)-ALIGNMENT_CONSTANT] == m[i-1][j]) {
                aligned1 = si.charAt(i) + aligned1;
                aligned2 = "-" + aligned2;
                i--;
            }
        }
        while (i >= 0 && j >= 0) {
            aligned1 = si.charAt(i) + aligned1;
            aligned2 = sj.charAt(j) + aligned2;
            i--;
            j--;
        }
        while (i >= 0) {
            aligned1 = si.charAt(i) + aligned1;
            aligned2 = "-" + aligned2;
            i--;
        }
        while (j >= 0) {
            aligned2 = sj.charAt(j) + aligned2;
            aligned1 = "-" + aligned1;
            j--;
        }
        return aligned1 + "\n" + aligned2;
    }

/*
    private void readInputFromFile(String file) {
        try {
            String current = null;

            StringBuilder sb = null;
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
                String s = br.readLine();
                if (s.startsWith(">")) {
                    if (sb != null) {
                        dnaOfSpecies.put(current, sb.toString());
                        sb = null;
                    }
                    if (s.contains(" "))
                        current = s.substring(1, s.indexOf(" "));
                    else
                        current = s.substring(1);
                    species.add(current);
                } else {
                    if (sb == null) {
                        sb = new StringBuilder(s);
                    } else {
                        sb.append(s);
                    }
                }
            }
            dnaOfSpecies.put(current, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */

    private void readInputFromFile(String file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String[] first = br.readLine().split(" ");
            int amtOfSpecies = Integer.parseInt(first[0]);
            int amtOfMatches = Integer.parseInt(first[1]);
            for (int i = 0; i < amtOfSpecies; i++) {
                String specie = br.readLine();
                String dna = br.readLine();
                dnaOfSpecies.put(specie, dna);
            }
            for (int i = 0; i < amtOfMatches; i++) {
                String[] species = br.readLine().split(" ");
                printSpecie(species[0], species[1]);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
