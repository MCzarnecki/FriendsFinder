import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class MapperMeasurement {

    public static void main(String ...args) {
        System.setProperty("hadoop.home.dir", "C:\\Users\\mczarnecki\\Desktop\\Winutils\\");
        int testCount = 10;

        for(int i = 5000; i <= 5000; i += 100) {

            String path = "Data\\I" + i + ".txt";

            long startTime = System.nanoTime();

            for (int j = 0; j < testCount; j++) {

                Mapper mapper = new Mapper();
                mapper.mapAndSave(path);
            }

            long endTime = System.nanoTime() - startTime;

            long averageTime = endTime / testCount;
            long durationInMs = TimeUnit.MILLISECONDS.convert(averageTime, TimeUnit.NANOSECONDS);

            try {
                PrintWriter out = new PrintWriter("MapReduceResults\\result_I" + i + ".txt");
                out.write(((Long) durationInMs).toString());
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
