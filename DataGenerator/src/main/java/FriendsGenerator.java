import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FriendsGenerator {

    private FriendsGenerator() {

    }

    public static List<Person> generate(int count) {
        List<String> names = NamesGenerator.generate(count);
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String name = names.get(0);
            names.remove(0);
            Random random = new Random();
            int x = random.nextInt(100);
            List<String> friends = getFriends(x, names);
            names.add(name);
            persons.add(new Person(name, friends));

        }

        return persons;

    }

    private static List<String> getFriends(int number, List<String> names) {

        List<String> namesCopy = new ArrayList<>(names);
        List<String> friends = new ArrayList<>();

        while (friends.size() != number) {
            Random random = new Random();
            int x = random.nextInt(namesCopy.size());
            String name = namesCopy.get(x);
            namesCopy.remove(x);
            friends.add(name);
        }

        return friends;
    }

    public static void test(List<String> list) {
        list.remove(1);
    }

    public static void main(String... args) throws FileNotFoundException {
        List<Person> persons = generate(100);

        try {
            PrintWriter out = new PrintWriter("filename.txt");
            out.write(persons.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
