package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.Callable;

public class FileWriterTask implements Callable<Void> {

    private String path;
    private List<String> strings;

    public FileWriterTask(String path, List<String> strings) {
        this.path = path;
        this.strings = strings;
    }

    @Override
    public Void call() throws Exception {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true));
        for (String s : strings) {
            bufferedWriter.write(s);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        return null;
    }
}
