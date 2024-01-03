package main;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "C:/Users/lucam/OneDrive/Desktop/GestoreFile/ThreadFile/file/esercizi.txt";

        int nThread = 5;

        FileReaderTask fileReaderTask = new FileReaderTask();
        int numberLines = fileReaderTask.getNumberLines(path);

        int rowsForThread = numberLines / nThread;

        ExecutorService executorService = Executors.newFixedThreadPool(nThread);

        List<Future<List<String>>> futures = new ArrayList<>();

        for (int i = 0; i < nThread; i++) {
            int startLine = i * rowsForThread + 1;
            int endLine = (i + 1) * rowsForThread;
            FileReaderTask fileReaderTask1 = new FileReaderTask(path, startLine, endLine);
            Future<List<String>> submit = executorService.submit(fileReaderTask1);
            futures.add(submit);

        }

        ArrayList<String> strings = new ArrayList<>();

        for (Future<List<String>> listFuture : futures) {
            List<String> list = null;
            try {
                list = listFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            for (String s : list) {
                strings.add(s);
                System.out.println(s);
            }
        }



        List<String> worldsToWrite = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            worldsToWrite.add("Task scritta numero" + i);
        }

        FileWriterTask fileWriterTask = new FileWriterTask(path, worldsToWrite);

        Future<Void> submit = executorService.submit(fileWriterTask);

        try {
            submit.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }




    }
}