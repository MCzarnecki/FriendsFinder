import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import scala.Tuple2;

import java.io.StringReader;
import java.util.Arrays;

public class App {

    private static  JavaSparkContext sc;

    public static void main(String... args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Spark");
        sc = new JavaSparkContext(conf);
        wordCount();
        sc.close();
    }

    public static void wordCount() {

        JavaRDD<String> lines = sc.textFile("./src/main/resources/input");
        JavaRDD<String> words = lines.flatMap(x -> Arrays.asList(x.split(" ")));
        JavaPairRDD<String, Integer> counts = words.mapToPair(x -> new Tuple2<String, Integer>(x, 1)).reduceByKey((x , y) -> x + y);
        counts.saveAsTextFile("output");
    }


    public static void filter() {
        JavaRDD<String> inputRDD = sc.textFile("input");
        JavaRDD<String> matowy = inputRDD.filter(x -> x.contains("matowy"));
        matowy.collect().forEach(x -> System.out.println(x));
    }

    public static void square() {
        JavaRDD<Integer> inputRDD = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5));
        JavaRDD<Integer> squares = inputRDD.map(x -> x * x);
        System.out.println(StringUtils.join(squares.collect(), ", "));
    }

    public static void linesToWords() {
        JavaRDD<String> lines = sc.textFile("input");
        JavaRDD<String> words = lines.flatMap(x -> Arrays.asList(x.split(" ")));
        System.out.println(words.take(10));
    }

    public static void operations() {
        JavaRDD<String> RDD1 = sc.parallelize(Arrays.asList("coffe", "coffe panda", "monkey", "tea"));
        JavaRDD<String> RDD2 = sc.parallelize(Arrays.asList("coffe", "monkey", "kitty"));
        System.out.println(StringUtils.join(RDD1.distinct().collect(), ", "));
        System.out.println(StringUtils.join(RDD1.union(RDD2).collect(), ", "));
        System.out.println(StringUtils.join(RDD1.intersection(RDD2).collect(), ", "));
        System.out.println(StringUtils.join(RDD1.subtract(RDD2).collect(), ", "));
    }

    public static void squaresSum() {
        JavaRDD<Integer> inputRDD = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5));
        JavaRDD<Integer> squares = inputRDD.map(x -> x * x);
        Integer sum = squares.reduce((x, y) -> x + y);
        System.out.println(sum);
    }

    public static void createPairByFirstWord() {
        JavaRDD<String> lines = sc.textFile("input");
        JavaPairRDD<String, String> pairs = lines.mapToPair((String x) -> new Tuple2<String, String>(x.split(" ")[0], x));
        pairs.saveAsTextFile("pairs");
    }

    public static void filterPair() {
        JavaRDD<String> lines = sc.textFile("input");
        JavaPairRDD<String, String> pairs = lines.mapToPair((String x) -> new Tuple2<String, String>(x.split(" ")[0], x));
        JavaPairRDD<String, String> filtered = pairs.filter((Tuple2<String, String> x) -> x._2().length() < 20);
        filtered.saveAsTextFile("filteredPairs");
    }

    public static void sortWordsCount() {
        JavaRDD<String> lines = sc.textFile("input");
        JavaRDD<String> words = lines.flatMap(x -> Arrays.asList(x.split(" ")));
        JavaPairRDD<String, Integer> counts = words.mapToPair(x -> new Tuple2<String, Integer>(x, 1)).reduceByKey((x , y) -> x + y);
        JavaPairRDD<String, Integer> sortedAsc = counts.sortByKey((a, b) -> a.compareTo(b), true);
        //sortedAsc.saveAsTextFile("sortedWordsCount");

        JavaPairRDD<String, Integer> sorted = counts.sortByKey((a, b) -> a.compareTo(b), false);
        //sorted.saveAsTextFile("sortedWordsCountDescending");
    }

    public static void readMultiple() {
        JavaPairRDD<String, String> files = sc.wholeTextFiles("sales");
        files.saveAsTextFile("SalesPair");
    }

    public static class ParseLine implements FlatMapFunction<Tuple2<String, String>, String[]> {

        @Override
        public Iterable<String[]> call(Tuple2<String, String> file) throws Exception {
            CSVReader reader = new CSVReader(new StringReader(file._2));
            return reader.readAll();
        }
    }

    public static void readCSV() {
        JavaPairRDD<String, String> csvData = sc.wholeTextFiles("sample.csv");
        JavaRDD<String[]> keyedRDD = csvData.flatMap(new ParseLine());
        keyedRDD.saveAsTextFile("csv");
    }
}