import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by Maria Rudenko on 19.04.2015.
 */
public class Logger {
    private Path path;

    public void setPath(String logPath){
        path = Paths.get(logPath + "logs.txt");

    }

    public void info(String str){
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date + " " + str);
    }

    public void info(String str, String thread){
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date + " " + str + " was created by thread " + thread);

        try(FileWriter out = new FileWriter(path.toString())) {

            BufferedWriter bw = new BufferedWriter(out);
            bw.write(date + " " + str + " was created by thread " + thread);
            bw.newLine();
            bw.flush();
            bw.close();

        } catch (IOException e) {

        }

    }
}
