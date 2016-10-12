package de.dbo.samples.docker;

public class Main {

    public static void main(String[] args) throws Exception {
	
	 final Client client = new Client();
	 client.connectToDockerServer();
	 client.createImage();
	 client.close();

    }

}
