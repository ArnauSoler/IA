package IA.Azamon;

import aima.search.framework.HeuristicFunction;

public class AzamonHeuristicHappiness implements HeuristicFunction {

	public double getHeuristicValue(Object aState) {
		AzamonState state = (AzamonState) aState;
		int epsilon = 5;
		return state.getPrice()-epsilon *state.getHappiness();
	}
}
