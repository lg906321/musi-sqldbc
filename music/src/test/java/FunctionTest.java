import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2018/3/16.
 */
public class FunctionTest {

//    public static void main(String[] args) {
//
//
//        List<Integer> list1 = Arrays.asList(1, 3, 5, 7, 9, 11);
//
//        Stream<Integer> boxed = IntStream.rangeClosed(1, list1.size())
//                .boxed();
//
//        IntSummaryStatistics statistics = list1.stream()
//                .filter(integer -> integer > 2)
//                .mapToInt(i -> i * 2)
//                .skip(2)
//                .limit(2)
//                .summaryStatistics();
//        System.out.println(statistics.getMax());//18
//        System.out.println(statistics.getMin());//14
//        System.out.println(statistics.getAverage());//16
//        double average = statistics.getAverage();
//        int min = statistics.getMin();
//
//    }
//
//    public static void main1(String[] args) {
//        FunctionTest functionTest = new FunctionTest();
//        System.out.println(functionTest.compute1(5,i -> i * 2,i -> i * i));//50
//        System.out.println(functionTest.compute2(5,i -> i * 2,i -> i * i));//100
//        System.out.println(functionTest.test3(i -> i * 2,i -> i * i).apply(5));
//
//
//        Supplier<FunctionTest> supplier = FunctionTest::new;
//        FunctionTest functionTest1 = supplier.get();
////        functionTest1.compute1()
//
//
//
//
//        Stream<List<Integer>> listStream =
//                Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
//        List<Integer> collect1 = listStream.flatMap(theList -> theList.stream()).
//                map(integer -> integer * integer).collect(Collectors.toList());
//        System.out.println(collect1);
//
//        listStream.flatMap(theList -> theList.stream())
//                .mapToInt(i -> i)
//                .forEach(System.out::println);
//
//
//    }
//
//    public int compute1(int i, Function<Integer,Integer> after,Function<Integer,Integer> before){
//        return after.compose(before).apply(i);
//    }
//
//    public int compute2(int i, Function<Integer,Integer> before,Function<Integer,Integer> after){
//        return before.andThen(after).apply(i);
//    }
//
//    public Function<Integer, Integer> test3(Function<Integer,Integer> after,Function<Integer,Integer> before){
//        return after.compose(before);
//    }
}