import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;

public class AlgorithmMesurement {

    public static void main(String... args) {


       /* if(args.length == 0) {
            System.err.println("Podaj nazwę pliku z rozszerzeniem i ilość testów algorytmu");
            exit(1);
        } else if(args.length > 2) {
            System.err.println("Podaj tylko jedną nazwę pliku z rozszerzeniem");
            exit(1);
        }*/
        int testCount = 10; //Integer.parseInt(args[1]);
        for (int i = 4000; i <= 4000; i += 1000) {

            String path = "Data\\I" + i + ".txt";

            long startTime = System.nanoTime();

            for (int j = 0; j < testCount; j++) {
                Algorithm algorithm = new Algorithm();
                algorithm.start(path);
            }

            long endTime = System.nanoTime() - startTime;

            long averageTime = endTime / testCount;
            long durationInMs = TimeUnit.MILLISECONDS.convert(averageTime, TimeUnit.NANOSECONDS);

            try {
                PrintWriter out = new PrintWriter("Results\\result_I" + i + ".txt");
                out.write(((Long) durationInMs).toString());
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }


    }
}
