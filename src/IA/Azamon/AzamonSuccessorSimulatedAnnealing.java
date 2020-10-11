
package IA.Azamon;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class AzamonSuccessorSimulatedAnnealing implements SuccessorFunction {  
	
	public List<Successor> getSuccessors(Object aState) {
		ArrayList<Successor> retVal = new ArrayList<>();
		AzamonState estat = (AzamonState) aState;

		// something

		return retVal;

	}

}
