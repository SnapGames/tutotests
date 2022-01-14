package fr.snapgames.tuto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private Configuration config;
    private ThreadPoolExecutor executionService;

    public App() {

    }

    public void run(String[] args) {
        initialize(args);
        loop();
        try {
            dispose();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    private void dispose() throws InterruptedException {
        this.executionService.shutdown();
        this.executionService.awaitTermination(300, TimeUnit.SECONDS);
    }

    private void loop() {
        this.executionService = new ThreadPoolExecutor(
                config.getThreadMin(),
                config.getThreadMax(),
                config.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        for (int i = 0; i < config.getExecutionNumber(); i++) {
            this.executionService.submit(new Runnable() {
                @Override
                public void run() {
                    logger.info("start Thread [{}]", Thread.currentThread().getName());
                    try {
                        Thread.sleep(config.getMaxWaitTime());
                    } catch (InterruptedException ie) {
                        logger.error(ie.getMessage());

                    }
                    logger.info("end Thread [{}]", Thread.currentThread().getName());

                }
            });
        }
    }

    public void initialize(String[] args) {
        this.config = parseArgs("config.setup", args);
    }

    private Configuration parseArgs(String configPath, String[] args) {
        return Configuration.get(configPath, args);
    }

    public static void main(String[] args) {
        App app = new App();
        app.run(args);
    }
}
