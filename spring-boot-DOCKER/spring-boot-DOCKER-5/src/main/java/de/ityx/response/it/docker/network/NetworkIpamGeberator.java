package de.ityx.response.it.docker.network;

import static org.apache.commons.lang.StringUtils.indexOf;
import static org.apache.commons.lang.StringUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.startsWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.api.model.Network.Ipam;
import com.github.dockerjava.api.model.Network.Ipam.Config;

/*
 * EXAMPLE ddev_net
 * 
 * networks:
 * net:
 * driver: bridge
 * ipam:
 * driver: default
 * config:
 * - subnet: 10.254.99.0/24
 * gateway: 10.254.99.1
 */

public class NetworkIpamGeberator {
    private static final Logger LOG = LoggerFactory.getLogger(NetworkIpamGeberator.class);
    
    private static final String PREFIX = "10.254.";
    private static final int BASE = 100;
    private static final int MAX_NETWORK_CNT = 100;

    /**
     * generates IMap for a free Gateway offset
     * @param freeOffs
     * 
     * @return Imap instance
     */
    public static final Ipam newImap(final int freeOffs) {
	final Ipam ipam = new Ipam();
	final Config ipamConfig = new Config();
	ipamConfig.withGateway(gateway(freeOffs));
	ipamConfig.withSubnet(subnet(freeOffs));
	ipam.withConfig(ipamConfig);
	return ipam;
    }
    
    /**
     * searches free offset for a gateway IP-address
     * @param dockerClient
     * 
     * @return non-null only if a free offset found
     */
    public static Integer freeOffs(final DockerClient dockerClient) {
	final List<Integer> addrs = busyAddrs(dockerClient);
	if (addrs.isEmpty()) {
	    return 1;
	}
	
        for (int i = BASE + 1; i < BASE + MAX_NETWORK_CNT; i++) {
            final Integer x = new Integer(i);
            if ( addrs.contains(x)) {
        	continue;
            }
            return new Integer(x - BASE);
        }
 	return null;
     }
    
    public static Ipam freeImap(final DockerClient dockerClient) {
	final int offs = freeOffs(dockerClient);
 	final Ipam ipam = new Ipam();
 	final Config ipamConfig = new Config();
 	ipamConfig.withGateway(gateway(offs));
 	ipamConfig.withSubnet(subnet(offs));
 	ipam.withConfig(ipamConfig);
 	return ipam;
     }
    
    /**
     * collects gateway addresses that are already used in some custom network.
     * 
     * @param dockerClient
     * @return list with busy gateway addresses
     */
    private static List<Integer> busyAddrs(final DockerClient dockerClient)  {
	final List<Network> networks = dockerClient.listNetworksCmd().exec();
	final List<Integer> addrs = new ArrayList<Integer>();
	for (final Network network : networks) {
	    final Iterator<Config> iteraor = network.getIpam().getConfig().iterator();
	    while (iteraor.hasNext()) {
		final String gateway =  iteraor.next().getGateway();
		LOG.debug("Gateway  = " + gateway);
		if (isNotEmpty(gateway) && startsWith(gateway,PREFIX)) {
		   final String gatewaySuffix = gateway.replaceAll(PREFIX,"");
		   final int lastDot = indexOf(gatewaySuffix,".");
		   final Integer gatewayAddr = Integer.parseInt(gatewaySuffix.substring(0,lastDot));
		   addrs.add(gatewayAddr);
		}
	    }
	}
	return addrs;
    }

    // ======================
    // IP-Address generation
    // ======================

    private static String gateway(final int offs) {
	return prefix(offs) + "1";
    }

    private static String subnet(final int offs) {
	return prefix(offs) + "0" + "/24";
    }
    
    private static final String prefix(final int offs) {
	return PREFIX + (BASE + offs) + ".";
    }

}
