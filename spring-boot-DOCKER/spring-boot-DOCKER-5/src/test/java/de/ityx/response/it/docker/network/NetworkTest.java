package de.ityx.response.it.docker.network;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ityx.response.it.docker.commander.Commander;
import de.ityx.response.it.docker.testimpl.TestAbstraction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NetworkTest extends TestAbstraction {
    private static final Logger LOG = LoggerFactory.getLogger(NetworkTest.class);

    private static Commander COMMANDER;
    private static final List<String> NETWORK_IDS = new ArrayList<>();

    @BeforeClass
    public static void initCommander() {
	COMMANDER = new Commander();
	COMMANDER.init();
    }

    @AfterClass
    public static void closeCommanderAndCleanUp() throws Exception {
	COMMANDER.close();
	NETWORK_IDS.clear();
    }

    /**
     * creating and removing custom networks.
     * 
     * @throws Exception
     */
    @Test
    public void t00_createCustomNetworks() throws Exception {
	int n = 100;
	while (n-- > 0) {
	    final String networkName = "reponse-it-" + UUID.randomUUID().toString().replaceAll("-", "");
	    final Integer offs = NetworkIpamGeberator.freeOffs(COMMANDER.dockerClient());
	    LOG.info("Network = ["+networkName+"] Gateway address offset=" + offs);
	    if (null == offs) {
		LOG.warn("No free address found");
	    } else {
		final String networkId = NetworkManager.createCustomNetwork(offs, networkName,
			COMMANDER.dockerClient());
		NETWORK_IDS.add(networkId);
	    }
	}
    }

    @Test
    public void t01_removeCustomNetworks() throws Exception {

	for (final String networkId : NETWORK_IDS) {
	    LOG.info("Removing network with ID = [" + networkId +"] ...");
	    NetworkManager.removeCustomNetwork(networkId, COMMANDER.dockerClient());
	}

    }

}
