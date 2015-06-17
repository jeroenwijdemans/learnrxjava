package learnrxjava.exercises;

import learnrxjava.solutions.ComposableListSolutions;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Map;

import learnrxjava.types.BoxArt;
import learnrxjava.types.ComposableList;
import learnrxjava.types.JSON;
import learnrxjava.types.Video;

import org.junit.Test;

public class ComposableListExercisesTest {

    @Test
    public void testExercise01() {
        System.out.println("----------- testExercise1 ----------------");
        ComposableListExercises.exercise01();
    }

    @Test
    public void testExercise02() {
        System.out.println("----------- testExercise2 ----------------");
        ComposableListExercises.exercise02();
    }

    @Test
    public void testExercise03() {
        System.out.println("----------- testExercise3 ----------------");
        ComposableList<JSON> e = ComposableListExercises.exercise03();
        ComposableList<JSON> s = ComposableListSolutions.exercise3();
        assertMatch(e, s);
    }

    @Test
    public void testExercise04() {
        System.out.println("----------- testExercise4 ----------------");
        ComposableList<Integer> slr = ComposableListExercises.of(1, 2, 3).map(i -> i + 1);
        slr.forEach(System.out::println);
        assertEquals(Arrays.asList(2, 3, 4), slr);
    }

    @Test
    public void testExercise05() {
        System.out.println("----------- testExercise5 ----------------");
        ComposableList<JSON> e = ComposableListExercises.exercise05();
        ComposableList<JSON> s = ComposableListSolutions.exercise5();
        assertMatch(e, s);
    }

    @Test
    public void testExercise06() {
        System.out.println("----------- testExercise6 ----------------");
        ComposableList<Video> e = ComposableListExercises.exercise06();
        ComposableList<Video> s = ComposableListSolutions.exercise6();
        assertMatch(e, s);
    }

    @Test
    public void testExercise07() {
        System.out.println("----------- testExercise7 ----------------");
        ComposableList<Integer> slr = ComposableListExercises.of(1, 2, 3).filter(i -> i <= 2);
        slr.forEach(System.out::println);
        assertEquals(Arrays.asList(1, 2), slr);
    }

    @Test
    public void testExercise08() {
        System.out.println("----------- testExercise8 ----------------");
        ComposableList<Integer> e = ComposableListExercises.exercise08();
        ComposableList<Integer> s = ComposableListSolutions.exercise8();
        assertMatch(e, s);
    }

    @Test
    public void testExercise09() {
        System.out.println("----------- testExercise9 ----------------");
        ComposableList<Integer> e = ComposableListExercises.exercise09();
        ComposableList<Integer> s = ComposableListSolutions.exercise9();
        assertMatch(e, s);
    }

    @Test
    public void testExercise10() {
        System.out.println("----------- testExercise10 ----------------");
        ComposableList<Integer> slr = ComposableListExercises.of(1, 2, 3).concatMap((t) -> ComposableListExercises.of(t + 1, t + 2, t + 3));
        slr.forEach(System.out::println);
        assertEquals(Arrays.asList(2, 3, 4, 3, 4, 5, 4, 5, 6), slr);
    }

    @Test
    public void testExercise11() {
        System.out.println("----------- testExercise11 ----------------");
        ComposableList<Integer> e = ComposableListExercises.exercise11();
        ComposableList<Integer> s = ComposableListSolutions.exercise11();
        assertMatch(e, s);
    }

    @Test
    public void testExercise12() {
        System.out.println("----------- testExercise12 ----------------");
        ComposableList<JSON> e = ComposableListExercises.exercise12();
        ComposableList<JSON> s = ComposableListSolutions.exercise12();
        assertMatch(e, s);

        //   {"id": 675465,"title": "Fracture","boxart":"http://cdn-0.nflximg.com/images/2891/Fracture150.jpg" },
        //   {"id": 65432445,"title": "The Chamber","boxart":"http://cdn-0.nflximg.com/images/2891/TheChamber150.jpg" },
        //   {"id": 654356453,"title": "Bad Boys","boxart":"http://cdn-0.nflximg.com/images/2891/BadBoys150.jpg" },
        //   {"id": 70111470,"title": "Die Hard","boxart":"http://cdn-0.nflximg.com/images/2891/DieHard150.jpg" }

    }

    @Test
    public void testExercise13() {
        System.out.println("----------- testExercise13 ----------------");
        BoxArt e = ComposableListExercises.exercise13();
        BoxArt s = ComposableListSolutions.exercise13();
        assertEquals(e, s);
    }

    @Test
    public void testExercise14() {
        System.out.println("----------- testExercise14 ----------------");
        ComposableList<Integer> slr = ComposableListExercises.of(1, 2, 3).reduce((s, i) -> s + i);
        slr.forEach(System.out::println);
        assertEquals(Arrays.asList(6), slr);
    }

    @Test
    public void testExercise15() {
        System.out.println("----------- testExercise15 ----------------");
        ComposableList<Integer> slr = ComposableListExercises.of(1, 2, 3).reduce(1, (s, i) -> s + i);
        slr.forEach(System.out::println);
        assertEquals(Arrays.asList(7), slr);
    }

    @Test
    public void testExercise16() {
        System.out.println("----------- testExercise16 ----------------");
        ComposableList<Integer> e = ComposableListExercises.exercise16();
        ComposableList<Integer> s = ComposableListSolutions.exercise16();
        assertMatch(e, s);
    }

    @Test
    public void testExercise17() {
        System.out.println("----------- testExercise17 ----------------");
        ComposableList<String> e = ComposableListExercises.exercise17();
        ComposableList<String> s = ComposableListSolutions.exercise17();
        assertMatch(e, s);
    }

    @Test
    public void testExercise18() {
        System.out.println("----------- testExercise18 ----------------");
        ComposableList<Map<Integer, String>> e = ComposableListExercises.exercise18();
        ComposableList<Map<Integer, String>> s = ComposableListSolutions.exercise18();
        assertMatch(e, s);
    }

    @Test
    public void testExercise19() {
        System.out.println("----------- testExercise19 ----------------");
        ComposableList<JSON> e = ComposableListExercises.exercise19();
        ComposableList<JSON> s = ComposableListSolutions.exercise19();
        assertMatch(e, s);
    }

    @Test
    public void testExercise20() {
        System.out.println("----------- testExercise20 ----------------");
        ComposableList<JSON> e = ComposableListExercises.exercise20();
        ComposableList<JSON> s = ComposableListSolutions.exercise20();
        assertMatch(e, s);
    }

    @Test
    public void testExercise21() {
        System.out.println("----------- testExercise21 ----------------");
        ComposableList<Integer> slr = ComposableListExercises.zip(ComposableListExercises.of(1, 2, 3), ComposableListExercises.of(4, 5, 6), (l, r) -> l + r);
        slr.forEach(System.out::println);
        assertEquals(Arrays.asList(5, 7, 9), slr);
    }

    @Test
    public void testExercise22() {
        System.out.println("----------- testExercise22 ----------------");
        ComposableList<JSON> e = ComposableListExercises.exercise22();
        ComposableList<JSON> s = ComposableListSolutions.exercise22();
        assertMatch(e, s);
    }

    @Test
    public void testExercise23() {
        System.out.println("----------- testExercise23 ----------------");
        ComposableList<JSON> e = ComposableListExercises.exercise23();
        ComposableList<JSON> s = ComposableListSolutions.exercise23();
        assertMatch(e, s);
    }

    private void assertMatch(ComposableList<? extends Object> e, ComposableList<? extends Object> s) {
        if (e.size() != s.size()) {
            throw new RuntimeException("Count Doesn't Match");
        }
        for (int i = 0; i < e.size(); i++) {
            if (!e.get(i).equals(s.get(i))) {
                throw new RuntimeException("Item does not match at index: " + i);
            }
        }
    }
}
