package core.host.router;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import core.host.communication.CommunicatingHostInterface;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("javadoc")
/**
 * TODO
 * WILL BE REFACTORED
 */
public class NetworkGraph {

    private int[][] distances;
    private int INF = 0;

//    public static Graph createGraph(String filename) {
//    }

    public NetworkGraph(String filename) {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        distances = gson.fromJson(reader, int[][].class);
    }

    public void addRoutingToHosts(Collection<CommunicatingHostInterface> hosts) {
		Map<Integer, CommunicatingHostInterface> hostsMap = new HashMap<Integer, CommunicatingHostInterface>();
		for (CommunicatingHostInterface host : hosts) {
			hostsMap.put(host.getId(), host);
		}
        floydWarshall(distances, hostsMap);
        return;
    }

    // TODO: very inefficient alghorithm. !!! TO BE CHANGED !!!
    private void floydWarshall(int graph[][], Map<Integer, CommunicatingHostInterface> hostsMap) {
        int V = graph.length;
        int dist[][] = new int[V][V];
        int A[][] = new int[V][V];

        /* Initialize the solution matrix same as input graph matrix.
           Or we can say the initial values of shortest distances
           are based on shortest paths considering no intermediate
           vertex. */
        for (int i = 0; i < V; i++)
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];

                if (i == j) {
                    A[i][j] = i;
                } else if (graph[i][j] != 0 && graph[i][j] != INF) {
                    A[i][j] = j;
                } else {
                    A[i][j] = INF;
                }

            }

        /* Add all vertices one by one to the set of intermediate
           vertices.
          ---> Before start of an iteration, we have shortest
               distances between all pairs of vertices such that
               the shortest distances consider only the vertices in
               set {0, 1, 2, .. k-1} as intermediate vertices.
          ----> After the end of an iteration, vertex no. k is added
                to the set of intermediate vertices and the set
                becomes {0, 1, 2, .. k} */


        // Print the shortest distance matrix

        for (int k = 0; k < V; k++) {
            // Pick all vertices as source one by one
            for (int i = 0; i < V; i++) {
                // Pick all vertices as destination for the
                // above picked source
                for (int j = 0; j < V; j++) {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of dist[i][j]
                    if (dist[i][k] == INF || dist[k][j] == INF)
                        continue;
                    if (dist[i][k] + dist[k][j] < dist[i][j] || dist[i][j] == INF) {
                        A[i][j] = k;
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        for (int i = 0; i < V; i++) {
        	CommunicatingHostInterface router = hostsMap.get(i);
            for (int j = 0; j < V; j++) {
//                if (i == j)
//                    continue;
                CommunicatingHostInterface destinationRouter = hostsMap.get(j);
                List<Integer> path = new ArrayList<Integer>();
                get_path(A, i, j, path);
                path.add(j);
                CommunicatingHostInterface nextHopRouter = hostsMap.get(path.get(1));
                router.addRouteNextHop(destinationRouter.getId(), nextHopRouter.getId());
            }
        }
    }

    public void get_path(int[][] A, int s, int t, List<Integer> path) {
        if(path.size() >= 2) {
            return;
        }
        if (A[s][t] == s || A[s][t] == t) {
            path.add(s);
            return;
        }
        get_path(A, s, A[s][t], path);
        get_path(A, A[s][t], t, path);
    }

    void printSolution(int graph[][]) {
        int V = graph.length;
        System.out.println("The following matrix shows the shortest " +
                "distances between every pair of vertices");
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (graph[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.print(graph[i][j] + "   ");
            }
            System.out.println();
        }
    }

    public int[][] getDistances() {
        return distances;
    }

    public int getDistance(int previousHopId, int nextHopId) { 
    	return distances[previousHopId][nextHopId];
    }
}
