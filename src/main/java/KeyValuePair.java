import java.util.List;

public class KeyValuePair {
    private List<String> sortedKey;
    private List<String> value;

    public KeyValuePair(List<String> sortedKey, List<String> value ) {
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
