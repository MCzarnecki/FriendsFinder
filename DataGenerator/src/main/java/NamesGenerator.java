import org.apache.commons.lang.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class NamesGenerator {

    private int numberOfNamesToGenerate;

    private List<String> names = new ArrayList<>();

    public NamesGenerator(int numberOfNamesToGenerate) {
        this.numberOfNamesToGenerate = numberOfNamesToGenerate;
    }

    public List<String> generate() {
        while(names.size() != numberOfNamesToGenerate) {
            String name = RandomStringUtils.random(10, true, false);
            if(!names.contains(name)) {
                names.add(name);
            }
        }

        return names;
    }

    public int getNumberOfNamesToGenerate() {
        return numberOfNamesToGenerate;
    }

    public void setNumberOfNamesToGenerate(int numberOfNamesToGenerate) {
        this.numberOfNamesToGenerate = numberOfNamesToGenerate;
    }
}
