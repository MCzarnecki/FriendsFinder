import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mapper implements Serializable {

    public void mapAndSave() {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Spark");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> textFile = sc.textFile("./src/main/resources/friends");

        JavaRDD<MyKeyValuePair> words = textFile.flatMap(new FlatMapFunction<String, MyKeyValuePair>() {
            public Iterable<MyKeyValuePair> call(String s) { return lineToKeyValue(s); }
        });
        words.saveAsTextFile("output");
        sc.close();
    }

    public List<MyKeyValuePair> lineToKeyValue(String line) {

        List<MyKeyValuePair> keyValuePairs = new ArrayList<>();
        String[] splitedLine = line.split(" -> ");
        String person = splitedLine[0];
        List<String> friends = Arrays.asList(splitedLine[1].split(" "));
        friends.stream().sorted((x, y) -> x.compareTo(y));

        for(String friend : friends) {
            List<String> key = new ArrayList<>();
            key.add(person);
            key.add(friend);
            key.stream().sorted((x,y) -> x.compareTo(y));

            MyKeyValuePair keyValuePair = new MyKeyValuePair(key, friends);
            keyValuePairs.add(keyValuePair);
        }
        return keyValuePairs;
    }

    private class MyKeyValuePair implements Serializable {

        private List<String> sortedKey;
        private List<String> value;

        public MyKeyValuePair(List<String> sortedKey, List<String> value ) {
            this.sortedKey = sortedKey;
            this.value = value;
        }

        public List<String> getSortedKey() {
            return sortedKey;
        }

        public void setSortedKey(List<String> sortedKey) {
            this.sortedKey = sortedKey;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }

        @Override
        public String toString() {
            String result = "(" + sortedKey.get(0) + "," + sortedKey.get(1) + ") - > (";

            for(int i = 0; i < value.size() - 1; i++) {
                result += value.get(i) + ", ";
            }

            result += value.get(value.size() - 1) + ")";

            return result;
        }
    }

 }
