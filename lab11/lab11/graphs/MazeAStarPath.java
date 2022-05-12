package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private MinPQ<Node> pq;
    private int[] edgeDmp;
    

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        pq = new MinPQ<>();
        edgeDmp = new int[m.N()*m.N()];
        edgeDmp[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(t) - maze.toX(v)) + Math.abs(maze.toY(t) - maze.toY(v));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return pq.delMin().v;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        pq.insert(new Node(s, 0, s));
        while (!pq.isEmpty()) {
            Node cur = pq.delMin();
            marked[cur.v] = true;
            distTo[cur.v] = cur.dist;
            edgeDmp[cur.v] = cur.pre;
            announce();
            if (cur.isGoal()) {
                targetFound = true;
                for (int i = cur.v; i != s; i = edgeDmp[i]) {
                    edgeTo[i] = edgeDmp[i];
                    announce();
                }
                return;
            }
            for (int ve : maze.adj(cur.v)) {
                if (marked[ve]) continue;
                pq.insert(new Node(ve, cur.dist+1, cur.v));
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

    private class Node implements Comparable<Node>{
        
        int v;
        int dist;
        int pre;
        
        public Node(int v, int dist, int pre) {
            this.v = v;
            this.dist = dist;
            this.pre = pre;
        }

        @Override
        public int compareTo(Node n) {
            return (dist + h(v)) - (n.dist + h(n.v));
        }
        
        public boolean isGoal() {
            return v == t;
        }
        
    }

}

