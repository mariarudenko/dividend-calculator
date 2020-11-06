import java.io.*;
import java.nio.file.*;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created by Maria Rudenko on 17.04.2015.
 */
public class Main {
    static final Logger LOGGER = new Logger();

    public static void main(String[] args) {

        Properties prop = new Properties();

        try (FileInputStream fis = new FileInputStream("config/properties.xml")) {

            prop.loadFromXML(fis);

        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

        Path inputDir = Paths.get(prop.getProperty("input_folder"));
        Path outputDir = Paths.get(prop.getProperty("output_folder"));
        LOGGER.setPath(prop.getProperty("log_folder"));


        ExecutorService exec = Executors.newFixedThreadPool(10);
        LOGGER.info("Start of monitoring ...");

        try {

            WatchService watcher = FileSystems.getDefault().newWatchService();
            inputDir.register(watcher, ENTRY_CREATE);


            while (true) {


                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException ex) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (kind == OVERFLOW) continue;

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    exec.execute(new CountingThread(inputDir, outputDir, filename.toString()));

                }

            }

        } catch (IOException e) {
            System.out.println(e.toString());
        }

        exec.shutdown();

    }
}