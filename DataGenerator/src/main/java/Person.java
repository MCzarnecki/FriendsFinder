import java.util.List;

public class Person {

    private String name;

    private List<String> friends;

    public Person(String name, List<String> friends) {
        this.name = name;
        this.friends = friends;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getFriends() {
        return friends;
    }

    @Override
    public String toString() {
        String result =  name + " -> ";

        for(String friend : friends) {
            result += friend + " ";
        }

        result += "\n";
        return result;
    }

}