package com.howardpchen.aries.network;

import java.util.Map;

public abstract class NetworkWrapper {
	public Map<String,Double> getDiagnosisProbs() {
		return getNodeProbs("Diseases");
	}
	public abstract Map<String, Double> getNodeProbs(String nodeName);
	
}