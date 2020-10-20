package IA.Azamon;

import aima.search.framework.HeuristicFunction;

public class AzamonHeuristicCost implements HeuristicFunction {	
	
	public double getHeuristicValue(Object aState) {
		AzamonState state = (AzamonState) aState;
		return state.getPrice();
	}
}
