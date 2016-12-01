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

        String filename = "I1000.txt";
        int testCount = 1; //Integer.parseInt(args[1]);

        String path = "D:\\BigData\\" + filename;

        long startTime = System.nanoTime();
        for(int i = 0; i < testCount; i++) {
            Algorithm algorithm = new Algorithm();
            algorithm.start(path);
        }

        long endTime = System.nanoTime() - startTime;

        long averageTime = endTime / testCount;
        long durationInMs = TimeUnit.MILLISECONDS.convert(averageTime, TimeUnit.NANOSECONDS);

        try {
            PrintWriter out = new PrintWriter("D:\\BigData\\result_" + filename);
            out.write(((Long)durationInMs).toString());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
