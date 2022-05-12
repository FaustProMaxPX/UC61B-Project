package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] parent;
    private boolean isCycle;

    public MazeCycles(Maze m) {
        super(m);
        parent = new int[m.N() * m.N()];
        isCycle = false;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(0);
    }

    // Helper methods go here
    private void dfs(int cur) {

        marked[cur] = true;
        announce();
        for (int v : maze.adj(cur)) {
            if (!marked[v]) {
                parent[v] = cur;
                dfs(v);
                if (isCycle) {
                    return;
                }
            }
            else if (parent[cur] != v) {
                isCycle = true;
                parent[v] = cur;
                edgeTo[v] = cur;
                announce();
                for (int i = cur; parent[i] != cur; i = parent[i]) {
                    edgeTo[i] = parent[i];
                    announce();
                }
                return;
            }
        }
    }
}