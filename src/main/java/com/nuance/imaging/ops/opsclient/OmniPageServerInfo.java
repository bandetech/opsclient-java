package com.nuance.imaging.ops.opsclient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OmniPageServerInfo {
	private String omnipageServer;
	private String omnipageServiceUrl;
	private static String jobUri = "/api/job";
	private static String storeUri = "/api/store";

	
	Logger logger = LogManager.getLogger(OmniPageServerInfo.class);
	
	public OmniPageServerInfo(){
		logger.debug("Initialize OmniPageServerInfo ...");
	}

	public String getOmnipageServer() {
		return omnipageServer;
	}

	public void setOmnipageServer(String omnipageServer) {
		this.omnipageServer = omnipageServer;
	}

	public String getOmnipageServiceUrl() {
		return omnipageServiceUrl;
	}

	public void setOmnipageServiceUrl(String omnipageServiceUrl) {
		this.omnipageServiceUrl = omnipageServiceUrl;
	}

	
	public String getJobUrlBase(){
		return "http://"+ omnipageServer + omnipageServiceUrl + jobUri;
	}

}
