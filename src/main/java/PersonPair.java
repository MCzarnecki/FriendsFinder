import java.util.List;

public class PersonPair {

    private String firstPerson;

    private String secondPerson;

    private String pair;

    private List<String> firstPersonFriends;

    public String getFirstPerson() {
        return firstPerson;
    }

    public void setFirstPerson(String firstPerson) {
        this.firstPerson = firstPerson;
    }

    public String getSecondPerson() {
        return secondPerson;
    }

    public void setSecondPerson(String secondPerson) {
        this.secondPerson = secondPerson;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public List<String> getFirstPersonFriends() {
        return firstPersonFriends;
    }

    public void setFirstPersonFriends(List<String> firstPersonFriends) {
        this.firstPersonFriends = firstPersonFriends;
    }
}
