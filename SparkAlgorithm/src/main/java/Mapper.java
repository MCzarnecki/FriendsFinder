import org.apache.avro.generic.GenericData;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Mapper implements Serializable {

    private List<String> ids = new ArrayList<>();

    public void mapAndSave(String path) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Spark");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> file = sc.textFile(path);
        JavaRDD<String> idsLoaded = file.map(x -> x.split(" -> ")[0]);
        ids = idsLoaded.collect();

        JavaRDD<String> textFile = sc.textFile(path);
        JavaRDD<KeyValuePair> words = textFile.flatMap(x -> lineToKeyValue(x));
        JavaPairRDD<List<String>, List<String>> pairedFriends = words.mapToPair(x -> new Tuple2<>(x.getSortedKey(), x.getValue()));
        JavaPairRDD<List<String>, List<String>> friendship = pairedFriends.reduceByKey((List<String> a, List<String> b) -> common(a, b));


        friendship.saveAsTextFile("output");
        sc.close();
    }

    private List<String> common(List<String> a, List<String> b) {
        List<String> c = new ArrayList<>();

        for (String s : a) {
            if (b.contains(s)) {
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

        for (String friend : ids) {
            List<String> key = new ArrayList<>();

            if (person.equals(friend)) {
                continue;
            }
            key.add(person);
            key.add(friend);
            key = key.stream().sorted((x, y) -> x.compareTo(y)).collect(Collectors.toList());

            KeyValuePair keyValuePair = new KeyValuePair(key, friends);
            keyValuePairs.add(keyValuePair);
        }
        return keyValuePairs;
    }
}
