package de.ityx.response.it.docker.network;

import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.ityx.response.it.docker.commander.Commander;
import de.ityx.response.it.docker.testimpl.TestAbstraction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NetworkTest extends TestAbstraction {

    private static Commander COMMANDER;

    @BeforeClass
    public static void initCommander() {
        COMMANDER = new Commander();
        COMMANDER.init();
    }

    @AfterClass
    public static void closeCommander() throws Exception {
        COMMANDER.close();
    }

    /**
     * creating and removing custom networks.
     * 
     * @throws Exception
     */
    @Test
    public void t00_createCustomNetwork() throws Exception {
        final String networkName = "reponse-it-" + UUID.randomUUID().toString().replaceAll("-", "");
        final String networkId  = NetworkManager.createCustomNetwork(77, networkName, COMMANDER.dockerClient());
        NetworkManager.removeCustomNetwork(networkId,COMMANDER.dockerClient());
    }

}
