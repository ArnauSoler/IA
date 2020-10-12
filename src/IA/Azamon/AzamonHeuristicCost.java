package IA.Azamon;

import aima.search.framework.HeuristicFunction;

public class AzamonHeuristicCost implements HeuristicFunction {	
	
	public double getHeuristicValue(Object state) {
		AzamonState estat = (AzamonState) state;
			return 0.0;// something
	}
}
