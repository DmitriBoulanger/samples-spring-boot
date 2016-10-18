package de.ityx.response.it.docker.network;

import static de.ityx.response.it.docker.network.NetworkIpamGeberator.*;
import static de.ityx.response.it.docker.util.DockerPrint.DONE;
import static de.ityx.response.it.docker.util.DockerPrint.printNetworks;
import static de.ityx.response.it.docker.util.DockerPrint.q;
import static de.ityx.response.it.docker.util.Utils.nn;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateNetworkCmd;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.command.ListNetworksCmd;
import com.github.dockerjava.api.command.RemoveNetworkCmd;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.api.model.Network.Ipam;

import de.ityx.response.it.docker.commander.Commander;

public class NetworkManager {
    private static final Logger LOG                            = LoggerFactory.getLogger(Commander.class);

    public static final String  DOCKER_NETWWORK_DEFAULT_BRIDGE = "bridge";
    public static final String  DOCKER_NETWWORK_DEFAULT_DRIVER = "bridge";

    private NetworkManager() {

    }

    public static List<Network> showAvaiableNetworks(final boolean all, final DockerClient dockerClient)
            throws Exception {
        final String msg = "Available networks ";
        LOG.info(msg + "....");
        final List<Network> networks = avialbleNetworks(null, null, dockerClient);
        final String info;
        if (networks.isEmpty()) {
            info = "No networks found";
        }
        else {
            info = "Found " + q(networks.size()) + " networks: " + printNetworks(networks);
        }
        LOG.info(msg + DONE + info);
        return networks;
    }

    public static List<Network> avialbleNetworks(final String networkId, final String networkName, final DockerClient dockerClient) {
        final ListNetworksCmd listNetworksCmd = dockerClient.listNetworksCmd();
        if (!nn(networkId)) {
            listNetworksCmd.withIdFilter(networkId);
        }
        if (!nn(networkId)) {
            listNetworksCmd.withNameFilter(networkName);
        }
        return listNetworksCmd.exec();
    }

    public static final void connectContainerToCustomNetwork(final String containerId, final String customNetworkName, final DockerClient dockerClient) {
        final String msg = "connecting container to custom network [" + customNetworkName + "]";
        LOG.info(msg + "....");

        // disconnect from default bridge-network
        final List<Network> networks = avialbleNetworks(null, DOCKER_NETWWORK_DEFAULT_BRIDGE, dockerClient);
        if (null != networks && !networks.isEmpty()) {
            for (final Network network : networks) {
                dockerClient.disconnectFromNetworkCmd()
                        .withContainerId(containerId)
                        .withForce(true)
                        .withNetworkId(network.getId())
                        .exec();
            }
        }

        final List<Network> responseNecustomeNetworks = NetworkManager.avialbleNetworks(null, customNetworkName, dockerClient);
        assertThat("Custom networks are not available (null)", responseNecustomeNetworks, notNullValue());
        assertThat("Custom network name [" + customNetworkName + "] cannot found or there more than one with the same name", responseNecustomeNetworks, hasSize(1));
        final Network customNetwork = responseNecustomeNetworks.get(0);
        dockerClient.connectToNetworkCmd()
                .withNetworkId(customNetwork.getId())
                .withContainerId(containerId).exec();

        LOG.info(msg + DONE);
    }
    
    public static final void removeCustomNetwork(final String networkId, final DockerClient dockerClient) {
        final RemoveNetworkCmd createNetworkCmd = dockerClient.removeNetworkCmd(networkId);
        createNetworkCmd.exec();
    }


    /**
     * creates a custom network
     * 
     * @param freeOffs
     *            used to have different gateway/subnet IP-addresses. Its value should be 1 .... 100
     * @param networkName unique network name
     * @param dockerClient
     * 
     * @return docker network ID
     */
    public static final String createCustomNetwork(final int freeOffs, final String networkName, final DockerClient dockerClient) {
        final CreateNetworkCmd createNetworkCmd = dockerClient.createNetworkCmd();
        createNetworkCmd.withName(networkName);
        createNetworkCmd.withDriver(DOCKER_NETWWORK_DEFAULT_DRIVER);

        final Ipam ipam = newImap(freeOffs);

        createNetworkCmd.withIpam(ipam);

        final CreateNetworkResponse createNetworkResponse;
        final String networkId;
        try {
            createNetworkResponse = createNetworkCmd.exec();
            networkId = createNetworkResponse.getId();
        }
        catch(Exception e) {
            assertThat("Failure creating custom network: \n" + e.getMessage(), e, nullValue());
            return null;
        }

        return networkId;
    }
  
}
