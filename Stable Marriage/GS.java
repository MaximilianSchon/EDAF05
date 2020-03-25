import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class GS {

    private Man[] men;
    private Woman[] women;

    public static void main(String[] args ) {
        //String path = System.getProperty("user.dir") + File.separatorChar + "sm-illiad-in.txt";
        //new GS().run(new String[]{path});
        new GS().run(args);
    }

    private void run(String[] args) {
        //loadFile(args);
        loadStdIn();
        gsAlgorithm();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < men.length; i++) {
            sb.append(men[i] + " -- " + women[men[i].getWife()] + "\n");
        }
        System.out.print(sb.toString());
    }

    private void gsAlgorithm() {
        List<Man> freeMen = new ArrayList<>();
        freeMen.addAll(Arrays.asList(men));
        while (!freeMen.isEmpty()) {
            Man m = freeMen.remove(0);
            Woman w = women[m.getNextWife()];
            if (w.isSingle()) {
                m.setWife(w.getNumber());
                w.setHusband(m.getNumber());
            } else if (w.prefers(m.getNumber())) {
                freeMen.add(men[w.setHusband(m.getNumber())]);
                m.setWife(w.getNumber());
            } else {
                freeMen.add(m);
            }
        }
    }


    private void loadStdIn() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            boolean active = true;
            while (active) {
                String l = br.readLine();
                if (l.equals("q"))
                    active = false;
                parseLine(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
            }
    }

    private void loadFile(String[] args) {
        try {
            Files.lines(Paths.get(args[0])).forEach(s -> {
                parseLine(s);
            });
        } catch (IOException e) {
            System.out.println("Something went wrong when loading the file");
        }
    }

    private void parseLine(String s) {
        if (s.startsWith("#") || s.length() <= 2)
            return; //Mer läsbart att lägga allting i denna if-satsen och negera den?
        else if (s.startsWith("n=")) {
            int amountOfCouples = Integer.parseInt(s.replace("n=", "").trim());
            men = new Man[amountOfCouples];
            women = new Woman[amountOfCouples];
        }
        else if (s.contains(":"))
            addPriorityOrder(s);
        else
            addPersonToList(s);
    }

    private void addPriorityOrder(String p) {
        int num = getNum(p);
        int[] priority = Pattern.compile(" ")
                .splitAsStream(p.substring(p.indexOf(":")+2)).mapToInt(s -> Integer.parseInt(s)/2-1).toArray();
        if (isMan(Integer.parseInt(p.substring(0,p.indexOf(":")))))
            men[num].loadPriority(priority);
        else
            women[num].loadPriority(priority);
    }

    private void addPersonToList(String s) {
        int num = getNum(s);
        if (isMan(Integer.parseInt(s.substring(0,s.indexOf(" ")))))
            men[num] = new Man(getName(s), num);
         else
             women[num] = new Woman(getName(s), num);
    }

    private String getName(String s) {
        return s.substring(s.indexOf(" ")+1);
    }

    private int getNum(String s) {
        if (s.contains(":"))
            return (Integer.parseInt(s.substring(0, s.indexOf(":")))-1) / 2;
        return (Integer.parseInt(s.substring(0,s.indexOf(" ")))-1) / 2;
    }

    private boolean isMan(int num) {
        return num % 2 == 1;
    }
}
