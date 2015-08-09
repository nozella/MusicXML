package br.com.nozella.musicxml.main;

import br.com.nozella.musicxml.service.impl.FileProcessorServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;

public class App implements Runnable {
    private static Log log = LogFactory.getLog(App.class);
    private String directoryPath;
    private String path;

    public App(String directoryPath, String path) {
        this.directoryPath = directoryPath;
        this.path = path;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            log.error("waiting for exactly two arguments");
            return;
        }

        for (String arg : args) {
            if (!new File(arg).isDirectory()) {
                log.error("at least one of the arguments is an invalid directory");
                return;
            }
        }

        new App(args[0], args[1]).run();
    }

    public void run() {
        log.info("initializing...");
        try {
            new FileProcessorServiceImpl().processFile(directoryPath, path);
        } catch (Exception e) {
            log.error("error when processing file", e);
        }
    }
}
