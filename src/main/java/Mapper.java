import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper implements Serializable {

    public void mapAndSave() {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Spark");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> textFile = sc.textFile("./src/main/resources/friends");

        JavaRDD<KeyValuePair> words = textFile.flatMap(x -> lineToKeyValue(x));
        words.saveAsTextFile("output");
        sc.close();
    }

    public List<KeyValuePair> lineToKeyValue(String line) {

        List<KeyValuePair> keyValuePairs = new ArrayList<>();
        String[] splitedLine = line.split(" -> ");
        String person = splitedLine[0];
        List<String> friends = Arrays.asList(splitedLine[1].split(" "));
        friends = friends.stream().sorted((x, y) -> x.compareTo(y)).collect(Collectors.toList());

        for(String friend : friends) {
            List<String> key = new ArrayList<>();
            key.add(person);
            key.add(friend);
            key = key.stream().sorted((x,y) -> x.compareTo(y)).collect(Collectors.toList());

            KeyValuePair keyValuePair = new KeyValuePair(key, friends);
            keyValuePairs.add(keyValuePair);
        }
        return keyValuePairs;
    }
 }
