package hw3.hash;

import org.junit.Test;

import edu.princeton.cs.algs4.StdRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */
    private ArrayList<Integer> randomList() {
        int N = StdRandom.uniform(1, 10);
        ArrayList<Integer> params = new ArrayList<>(N);
        for (int i = 0; i < N; i += 1) {
            params.add(StdRandom.uniform(0, 255));
        }
        return params;
    }
    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();
        Integer[] param = {255};
        // Your code here.
        int N = 100;
        ArrayList<Integer> suffix = new ArrayList<>();

        // because int can only store 32 bits, so we just need to add four suffix,
        // deeffect effects of elements before. so the hashcode will always be
        // (6*(suffix[0] + suffix[1] + suffix[2]) + suffix[3]) % 10

        suffix.add(5);
        suffix.add(2);
        suffix.add(3);
        suffix.add(4);
        for (int i = 0; i < N; i++) {
            ArrayList<Integer> params = randomList();
            params.addAll(suffix);
            deadlyList.add(new ComplexOomage(params));
        }
        // ArrayList<Integer> params = new ArrayList<>();
        // params.add(1);
        // params.add(2);
        // params.addAll(suffix);
        // deadlyList.add(new ComplexOomage(params));
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));

    } 

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
