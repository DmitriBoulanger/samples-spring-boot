package de.ityx.response.it.docker.commander;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;

public final class CommanderConfig {
    
    public static final String REGISTRY_URL_DEFAULT  = "https://index.docker.io/v1/";
    public static final String REGISTRY_URL_IYX_DREG = "https://dreg.ityx.de:5000/";
    
    private CommanderConfig() {
        
    }
    
    /**
     * Default registry configuration
     *
     * @return default docker-client configuration
     */
    public static DockerClientConfig config() {
        return config(null,null);
    }

    /**
     * Registry configuration
     * 
     * @param password
     * @param registryUrl
     * 
     * @return docker-client configuration
     */
    public static DockerClientConfig config(final String password, final String registryUrl) {
        final DefaultDockerClientConfig.Builder builder = DefaultDockerClientConfig.createDefaultConfigBuilder();
        if (null!=registryUrl) {
            builder.withRegistryUrl(registryUrl);
        } else {
            builder.withRegistryUrl(REGISTRY_URL_IYX_DREG);
        }
        if (password != null) {
            builder.withRegistryPassword(password);
        }

        return builder.build();
    }

}
