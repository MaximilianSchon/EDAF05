

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class wordladders {

    private Map<String, List<String>> candidates = new HashMap<>();

    public static void main(String[] args) {
        new wordladders().run(args);
    }

    public void run(String[] args) {
        loadWords(args[0]);
        loadPairs(args[1]);
    }

    private void loadPairs(String pairFile) {
        try {
            Files.lines(Paths.get(pairFile)).forEach(s -> {
                int indexOfSpace = s.indexOf(" ");
                System.out.println(bfs(s.substring(0, indexOfSpace), s.substring(indexOfSpace+1)));
            });
        } catch (IOException e) {
            System.out.println("Something went wrong when loading the file");
        }
    }

     private int bfs(String s, String t) {
        HashMap<String, Boolean> visited = new HashMap<>();
        HashMap<String, String> pred = new HashMap<>();
        Queue<String> q = new LinkedList<>();
        q.add(s);
        visited.put(s, true);
        if (s.equals(t))
            return 0;
        while (!q.isEmpty()) {
            String v = q.poll();
            for (String w : getAdjList(v)) {
                if (visited.get(w) == null) {
                    visited.put(w, true);
                    q.add(w);
                    pred.put(w, v);
                    if (w.equals(t))
                        return getSize(pred, t);
                }
            }
        }
        return -1;
    }

    private int getSize(HashMap<String, String> pred, String t) {
        int counter = -1;
        if (pred.get(t) != null)
            for (String str = t; str != null; str = pred.get(str)) {
                //System.out.print(str + "->");
                counter++;
            }
        return counter;
    }

    private void loadWords(String wordFile) {
        try {
            Files.lines(Paths.get(wordFile)).forEach(s ->  {
                char[] ar = s.toCharArray();
                Arrays.sort(ar);
                String s1 = String.valueOf(ar);
                for (int i = 0; i < 5; i++) {
                    String rep = s1.substring(0, i) + s1.substring(i + 1);
                    candidates.computeIfAbsent(rep, k -> new LinkedList<>());
                    List<String> neighbours = candidates.get(rep);
                    if (neighbours != null) {
                        neighbours.add(s);
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("Something went wrong when loading the file");
        }
    }

    private List<String> getAdjList(String s) {
        char[] ar = s.substring(1).toCharArray();
        Arrays.sort(ar);
        return candidates.get(String.valueOf(ar));
    }
}