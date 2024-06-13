package guru.qa.niffler.config;

public interface Config {

    static Config getInstance() {
        String env = System.getProperty("test.env", "local");

        if ("local".equals(env)) {
            return LocalConfig.instance;
        } else if ("docker".equals(env)) {
            return DockerConfig.instance;
        } else {
            throw new IllegalStateException("Can not find Config for given env");
        }
    }

    String frontUrl();

    String spendUrl();

    String dbHost();

    String gatewayUrl();

    default int dbPort(){
        return 5432;
    }
}
