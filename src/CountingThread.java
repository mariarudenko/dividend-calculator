import java.nio.file.Path;

/**
 * Created by Maria Rudenko on 21.04.2015.
 */
public class CountingThread implements Runnable {

    private Path in, out;
    private String filename;

    CountingThread(Path in, Path out, String filename) {
        this.in = in;
        this.out = out;
        this.filename = filename;
    }

    public void run() {
        CSVHandler csvHandler = new CSVHandler(in, out, filename);
        csvHandler.recast();
        Main.LOGGER.info(csvHandler.getDivFile().getFileName().toString(), Thread.currentThread().getName());
        Main.LOGGER.info(csvHandler.getClaimFile().getFileName().toString(), Thread.currentThread().getName());
    }
}
