import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper implements Serializable {

    /*
    JavaPairRDD<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
  public Tuple2<String, Integer> call(String s) { return new Tuple2<String, Integer>(s, 1); }
});
JavaPairRDD<String, Integer> counts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
  public Integer call(Integer a, Integer b) { return a + b; }
});
     */


    public void mapAndSave() {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Spark");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> textFile = sc.textFile("./src/main/resources/friends");

        JavaRDD<KeyValuePair> words = textFile.flatMap(x -> lineToKeyValue(x));
        JavaPairRDD<List<String>, List<String>> pairedFriends = words.mapToPair(x -> new Tuple2<>(x.getSortedKey(), x.getValue()));

        JavaPairRDD<List<String>, List<String>> friendship = pairedFriends.reduceByKey(new Function2<List<String>, List<String>, List<String>>() {
            public List<String> call(List<String> a, List<String> b) {
                return common(a, b);
            }
        });

        friendship.saveAsTextFile("output");
        sc.close();
    }

    private List<String> common(List<String> a, List<String> b) {
        List<String> c = new ArrayList<>();

        for(String s : a) {
            if(b.contains(s)) {
                c.add(s);
            }
        }

        return c;
    }


    private List<KeyValuePair> lineToKeyValue(String line) {

        List<KeyValuePair> keyValuePairs = new ArrayList<>();
        String[] splitedLine = line.split(" -> ");
        String person = splitedLine[0];
        List<String> friends = Arrays.asList(splitedLine[1].split(" "));
        friends = friends.stream().sorted((x, y) -> x.compareTo(y)).collect(Collectors.toList());

        for (String friend : friends) {
            List<String> key = new ArrayList<>();
            key.add(person);
            key.add(friend);
            key = key.stream().sorted((x, y) -> x.compareTo(y)).collect(Collectors.toList());

            KeyValuePair keyValuePair = new KeyValuePair(key, friends);
            keyValuePairs.add(keyValuePair);
        }
        return keyValuePairs;
    }
}
