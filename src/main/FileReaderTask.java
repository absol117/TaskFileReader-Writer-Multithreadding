package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class FileReaderTask implements Callable<List<String>> {

    private String path;
    private int start;
    private int end;


    public FileReaderTask(String path, int start, int end) {
        this.path = path;
        this.start = start;
        this.end = end;
    }

    public FileReaderTask() {
    }


    public int getNumberLines(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            int tot = 0;
            while (bufferedReader.readLine() != null) {
                tot++;
            }
            return tot;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<String> call() throws Exception {
        List<String> strings = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        for (int i = 1; i < end; i++) {
            String s = bufferedReader.readLine();
            if(i >= start) {
                strings.add(s);
            }
        }
        return strings;
    }

}
