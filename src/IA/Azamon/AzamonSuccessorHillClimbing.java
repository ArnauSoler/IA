
package IA.Azamon;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class AzamonSuccessorHillClimbing implements SuccessorFunction {  
	
	public List<Successor> getSuccessors(Object aState) {
		Vector<Successor> successors = new Vector<>();
		AzamonState parent_state = (AzamonState) aState;
		
		for (int i = 0; i < parent_state.packages.size(); ++i) {	
			for (int j = 0; j < parent_state.offers.size(); ++j) {
				AzamonState child_state = new AzamonState(parent_state);
				if(child_state.movePackage(i, j)){
					// No acabo de tenir clar que s'ha de passar com a primer arg.
					successors.add(new Successor (null, child_state));
					
				}
			}
			// fem i+1 perque no te sentit fer swap amb ell mateix
			for(int j = i+1; j < parent_state.packages.size(); ++j){
				AzamonState child_state = new AzamonState(parent_state);
				if(child_state.swapPackage(i, j)){
					successors.add(new Successor (null, child_state));
					
				}
			}
		}
		return successors;
	}

}
