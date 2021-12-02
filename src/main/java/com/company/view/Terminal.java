package com.company.view;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.Console;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Terminal implements Runnable{

    @Override
    public void run() {
        try{
            setup();
            loop();
        }
        finally{
            finish();
        }
    }

    @SneakyThrows
    private void setup(){
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c");
        processBuilder.start();
//        Process process = Runtime.getRuntime().exec("start cmd /K echo hello");
//        process.waitFor();
//        Runtime.getRuntime().exec("start cmd /C echo hello");
//        @Cleanup OutputStreamWriter outputStreamWriter = new OutputStreamWriter(process.getOutputStream());
//        @Cleanup BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
//        bufferedWriter.newLine();
//        bufferedWriter.write("echo hello");
//        bufferedWriter.flush();
    }

    private void loop(){}

    private void finish(){
    }
}
