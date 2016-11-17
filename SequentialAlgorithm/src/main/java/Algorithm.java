import StringData.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Algorithm {

    public static void main(String ...args) {

        Algorithm algorithm = new Algorithm();
        algorithm.start("filename.txt");
    }

    private void start(String filePath) {
        List<Person> persons = loadPersons(filePath);
        Map<List<String>, List<String>> mapped = new HashMap<>();

        for(int i = 0; i < persons.size() - 1; i++) {
            for(int j = i + 1; j < persons.size(); j++) {
                List<String> key = new ArrayList<>();
                key.add(persons.get(i).getName());
                key.add(persons.get(j).getName());
                key = key.stream().sorted().collect(Collectors.toList());
                mapped.put(key, commonFriends(persons.get(i), persons.get(j)));
            }
        }

        System.out.println(mapped.toString());


    }

    private List<Person> loadPersons(String filePath) {

        List<Person> persons = new ArrayList<>();

        try {
            BufferedReader file = new BufferedReader(new FileReader(filePath));


            String line;
            while ((line = file.readLine()) != null) {
                String lines[] = line.split(" -> ");
                String[] friends = lines[1].split(" ");
                Person person = new Person(lines[0], Arrays.asList(friends));
                persons.add(person);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return persons;
    }

    private List<String> commonFriends(Person a, Person b) {
        return a.getFriends().stream().filter(friend -> b.getFriends().contains(friend)).collect(Collectors.toList());
    }


}
