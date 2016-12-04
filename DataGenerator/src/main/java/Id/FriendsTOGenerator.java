package Id;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FriendsTOGenerator {

    public FriendsTOGenerator() {

    }

    private List<Integer> ids;

    private List<PersonTO> generate(Integer count) {

        List<PersonTO> persons = new ArrayList<>();

        ids = new ArrayList<>();
        for(Integer i = 0; i < count; i++) {
            ids.add(i);
        }

        for (Integer i = 0; i < count; i++) {
            Integer id = ids.get(0);
            ids.remove(0);
            Random random = new Random();
            int x = random.nextInt(count > 1000 ? 1000 : count - 1);
            List<Integer> friends = getFriends(x);
            ids.add(id);
            persons.add(new PersonTO(id, friends));

        }

        return persons;
    }


    private List<Integer> getFriends(Integer count) {

        List<Integer> namesCopy = new ArrayList<>(ids);
        List<Integer> friends = new ArrayList<>();

        while (friends.size() != count) {
            Random random = new Random();
            int x = random.nextInt(namesCopy.size());
            Integer id = namesCopy.get(x);
            namesCopy.remove(x);
            friends.add(id);
        }

        return friends;
    }

    public static void main(String... args) {

        FriendsTOGenerator generator = new FriendsTOGenerator();

        for(int i = 100; i <= 5000; i += 100) {
            List<PersonTO> friends = generator.generate(i);
            try {
                PrintWriter out = new PrintWriter("Data\\I" + i +".txt");
                for(PersonTO person : friends) {
                    out.write(person.toString());
                }

                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
       /* List<PersonTO> friends = generator.generate(100);

        try {
            PrintWriter out = new PrintWriter("Data\\I200.txt");
            for(PersonTO person : friends) {
                out.write(person.toString());
            }

            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

    }
}
