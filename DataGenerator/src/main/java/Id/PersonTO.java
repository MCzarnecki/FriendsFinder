package Id;

import java.util.List;

public class PersonTO {
    private Integer id;

    private List<Integer> friendsIds;

    public PersonTO(Integer id, List<Integer> friendsIds) {
        this.id = id;
        this.friendsIds = friendsIds;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setFriendsIds(List<Integer> friendsIds) {
        this.friendsIds = friendsIds;
    }

    public List<Integer> getFriendsIds() {
        return friendsIds;
    }

    @Override
    public String toString() {
        String result = id.toString() + " -> ";

        for (Integer friendId : friendsIds) {
            result += friendId.toString() + " ";
        }

        result += "\n";
        return result;
    }
}
