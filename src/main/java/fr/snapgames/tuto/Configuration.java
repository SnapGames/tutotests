package fr.snapgames.tuto;

import java.util.Locale;
import java.util.ResourceBundle;

public class Configuration {

    // default values from configruation file
    private ResourceBundle attributes;

    // values from configuration file that can be overloaded by arguments.
    private int threadMin = 2;
    private int threadMax = 4;
    private int keepAliveTime = 3000;

    private int executionNumber = 10;
    private long maxWaitTime = 10;

    /**
     * initialize configuration object with values from configuration file name.
     *
     * @param configurationFileName the name of the configuration bundle (properties
     *                              file).
     */
    private Configuration(String configurationFileName) {
        this.attributes = ResourceBundle.getBundle(configurationFileName, Locale.ROOT);
        preloadValues();
    }

    private void preloadValues() {
        this.threadMin = Integer.parseInt(attributes.getString("system.thread.min"));
        this.threadMax = Integer.parseInt(attributes.getString("system.thread.max"));
        this.keepAliveTime = Integer.parseInt(attributes.getString("system.keep.alive.time"));
        this.executionNumber = Integer.parseInt(attributes.getString("system.execution.number"));
        this.maxWaitTime = Long.parseLong(attributes.getString("system.max.wait.time"));
    }

    private static Configuration parseArgs(Configuration cfg, String[] args) {
        for (String arg : args) {
            switch (arg.toLowerCase()) {
                case "system.thread.min":
                    cfg.threadMin = Integer.parseInt(arg);
                    break;
                case "system.thread.max":
                    cfg.threadMax = Integer.parseInt(arg);
                    break;
                case "system.execution.number":
                    cfg.executionNumber = Integer.parseInt(arg);
                    break;
                case "system.keep.alive.time":
                    cfg.keepAliveTime = Integer.parseInt(arg);
                    break;
                case "system.max.wait.time":
                    cfg.maxWaitTime = Long.parseLong(arg);
                    break;
                default:
                    break;
            }
        }

        return cfg;
    }

    public static Configuration get(String configPath, String[] args) {
        Configuration cfg = new Configuration(configPath);
        return parseArgs(cfg, args);
    }

    public int getKeepAliveTime() {
        return this.keepAliveTime;
    }

    public int getThreadMin() {
        return this.threadMin;
    }

    public int getThreadMax() {
        return this.threadMax;
    }

    public int getExecutionNumber() {
        return this.executionNumber;
    }

    public long getMaxWaitTime() {
        return this.maxWaitTime;
    }
}
