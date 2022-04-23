package hw4.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    private MinPQ<SearchNode> pq = new MinPQ<>();
    private int moves;
    private List<WorldState> states;

    public Solver(WorldState initial) {
        
        pq.insert(new SearchNode(initial, 0, null));
        while (!pq.isEmpty()) {
            SearchNode node = pq.delMin();
            if (node.state.isGoal()) {
                fill(node);
                break;
            }
            else {
                int nextMoves = node.moves + 1;
                for (WorldState s : node.state.neighbors()) {
                    if (node.pre != null && s.equals(node.pre.state)) {continue;}
                    pq.insert(new SearchNode(s, nextMoves, node));
                }
            }
        }
    }

    private void fill(SearchNode node) {
        states = new ArrayList<>();
        moves = node.moves;
        while (node != null) {
            states.add(node.state);
            node = node.pre;
        }
        Collections.reverse(states);
    }

    public int moves() {
        return moves;
    }
    public Iterable<WorldState> solution() {
        return states;
    }

    private class SearchNode implements Comparable<SearchNode>{
        WorldState state;
        int moves;
        SearchNode pre;
        public SearchNode(WorldState state, int moves, SearchNode pre) {
            this.state = state;
            this.moves = moves;
            this.pre = pre;
        }
        @Override
        public int compareTo(SearchNode o) {
            return moves + state.estimatedDistanceToGoal() - o.moves - o.state.estimatedDistanceToGoal();
        }

    }
}
