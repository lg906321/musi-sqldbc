import io.vavr.API;
import io.vavr.Lazy;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

public class TestVavr {
//
//    @Getter
//    @Setter
//    class Domain{
//        private Integer port;
//        private String ip;
//    }
//
//    private static final Tuple2<String, Integer> java8 = Tuple.of("Java", 8);
//
//    public static void main(String[] args) {
//        TestVavr testVavr = new TestVavr();
//        testVavr.testTry();
//
//    }
//
//    @Test
//    public void testMatch(){
//        val i = 2;
//        Option<String> s = Match(i).option(
//                Case($(0), "zero"),
//                Case($(1), "one"),
//                Case($(2),x -> {return "two";}),
//                Case($(), "x")
//        );
//        System.out.println(s.get());
//
//    }
//
//    @Test
//    public void testList(){
//        Optional<Integer> reduce = Arrays.asList(1, 2, 3).stream().reduce((i, j) -> i + j);
//        System.out.println(reduce.get());
//        int sum = IntStream.of(1, 2, 4).sum();
//        System.out.println(sum);
//        int i = List.of(1, 2, 5).sum().intValue();
//        System.out.println(i);
////        List.of(1, 2, 5).
//    }
//
//    @Test
//    public void testLazy(){
//        Lazy<Double> lazy = Lazy.of(Math::random);
//        System.out.println(lazy.isEvaluated()); // = true
//        System.out.println(lazy.get());         // = 0.123 (random generated)
//        System.out.println(lazy.isEvaluated());; // = true
//        System.out.println(lazy.get());         // = 0.123 (memoized)
//
//        CharSequence chars = Lazy.val(() -> "Yay!", CharSequence.class);
//        Lazy.val(() -> Integer.valueOf(222),Integer.class);
//    }
//
//
//
//    public void testTry(){
//        Integer orElse = Try.of(this::testTry11)
//                .recover(x -> API.Match(x).of(
//                        Case($(instanceOf(RuntimeException.class)), t -> Integer.valueOf(333))
//                )).getOrElse(Integer.valueOf(222));
//        System.out.println(orElse);
//    }
//
//    public Integer testTry11() {
//        int div = 100;
//        int div1 = 0;
//        return div / div1;
//    }
//
//    @Test
//    public void testOption(){
//        Option<String> maybeFoo = Option.of("foo");
//        System.out.println(maybeFoo.get());
//        Option<String> maybeFooBar = maybeFoo.flatMap(s -> Option.of((String)null))
//                .map(s -> s.toUpperCase() + "bar");
//        System.out.println(maybeFooBar.isEmpty());
//        System.out.println(maybeFooBar.getOrElse("xx"));
//        System.out.println(maybeFooBar.getOrNull());
//    }
//
//    @Test
//    public void testOptionl(){
//        Optional<String> maybeFoo = Optional.of("foo");
//        System.out.println("maybeFoo.get():" + maybeFoo.get());
//        Optional<String> maybeFooBar = maybeFoo.map(s -> (String)null)
//                .map(s -> s.toUpperCase() + "bar");
//
//        System.out.println(maybeFooBar.get());
//    }
//
//    @Test
//    public void testToString(){
//        String apply = java8.apply((k, v) -> k + v.toString());
//        System.out.println(apply);
//        System.out.println("12 * 1781 = " + 12 * 1781);
//    }
//
//    @Test
//    public void test2(){
//        java8.map((k,v) -> Tuple.of(k.substring(2) + "vr", v / 8));
//        java8.map((k,v) -> Tuple.of(k, v));
//    }
//
//    @Test
//    public void test1(){
//        System.out.println(java8._1);
//        System.out.println(java8._2);
//
//        Tuple2<String, Integer> tup2 = java8.map(
//                k -> k.substring(2),
//                v -> v + 1
//        );
//        System.out.println(tup2);
//    }

}