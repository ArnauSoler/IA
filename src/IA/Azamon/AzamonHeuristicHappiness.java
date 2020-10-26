package IA.Azamon;

import aima.search.framework.HeuristicFunction;

public class AzamonHeuristicHappiness implements HeuristicFunction {
	static int epsilon = 1;

	public AzamonHeuristicHappiness(){}

	public AzamonHeuristicHappiness(int e){
		epsilon = e;
	}

	public double getHeuristicValue(Object aState) {
		AzamonState state = (AzamonState) aState;
		return state.getPrice()-epsilon *state.getHappiness();
	}
}
