package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int source;
    private int target;
    private Queue<Integer> q;
    private int[] edgeDmp;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        source = m.xyTo1D(sourceX, sourceY);
        target = m.xyTo1D(targetX, targetY);
        distTo[source] = 0;
        edgeTo[source] = source;
        q = new LinkedBlockingDeque<>();
        edgeDmp = new int[m.N()*m.N()];
        edgeDmp[source] = source;
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {

        q.add(source);
        marked[source] = true;
        announce();
        while (!q.isEmpty()) {
            Integer cur = q.poll();
            for (Integer v : maze.adj(cur)) {
                if (!marked[v]) {
                    marked[v] = true;
                    distTo[v] = distTo[cur] + 1;
                    edgeDmp[v] = cur;
                    announce();
                    if (v == target) {
                        for (int i = v; i != source; i = edgeDmp[i]) {
                            edgeTo[i] = edgeDmp[i];
                            announce();
                        }
                        return;
                    }
                    q.add(v);
                }
            }
        }
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
    }


    @Override
    public void solve() {
         bfs();
    }
}

