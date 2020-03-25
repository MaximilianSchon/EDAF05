import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class spanning {

    ArrayList<Edge> edges = new ArrayList<>();
    ArrayList<ArrayList<Edge>> G = new ArrayList<>();
    HashMap<String, Integer> citiesToint = new HashMap<>();
    private int currentCity = 0;
    public static void main(String[] args) {
        args = new String[1];
        args[0] = "/h/d5/l/ma5176sc-s/IdeaProjects/Lab3/src/infile";
        new spanning().run(args);
    }

    private void run(String[] args) {
        parseWords(args[0]);
        prims(createGraph());
    }


    private void parseWords(String file) {
        try {
            Files.lines(Paths.get(file)).forEach(s -> handleWord(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleWord(String word) {
        if (word.charAt(word.length() - 1) == ']') {
            String[] cities = word.split("--");
            int distance = Integer.parseInt(cities[1].substring(cities[1].indexOf("[") + 1, cities[1].length() - 1));
            cities[1] = cities[1].substring(0, cities[1].indexOf("[")-1);
            edges.add(new Edge(citiesToint.get(cities[0]), citiesToint.get(cities[1]), distance));
            G.add(new ArrayList<>());
            G.add(new ArrayList<>());
        } else {
            citiesToint.put(word.trim(), currentCity++);
        }
    }

    private ArrayList<ArrayList<Edge>> createGraph(){
        for (Edge e : edges){
            Edge other = new Edge(e.getTo(), e.getFrom(), e.getWeight());
            G.get(e.getFrom()).add(e);
            G.get(e.getTo()).add(other);
        }

        return G;
    }



    private void prims(ArrayList<ArrayList<Edge>> G) {
        int distance = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>((Object o1, Object o2) -> {
            Edge first = (Edge) o1;
            Edge second = (Edge) o2;
            return first.compareTo(second);
        });

        pq.addAll(G.get(0));
        boolean[] visited = new boolean[G.size()];
        visited[0] = true;
        while(!pq.isEmpty()){
            Edge e = pq.poll();
            if (visited[e.getFrom()] && visited[e.getTo()]) {
                continue;
            }

            visited[e.getFrom()] = true;

            for(Edge edge : G.get(e.getTo())){
                if(!visited[edge.getTo()]){
                    pq.add(edge);
                }
            }
            visited[e.getTo()] = true;
            distance += e.getWeight();
        }
        System.out.println(distance);
    }


}

class Edge implements Comparable{
    private int from, to, weight;
    public Edge(int from, int to, int weight)  {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(weight,((Edge) o).weight);
    }
}