
package IA.Azamon;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class AzamonSuccessorHillClimbing implements SuccessorFunction {  

	private static int operator = 3;

	public AzamonSuccessorHillClimbing(){
		operator = 3;
	}

	public AzamonSuccessorHillClimbing(int op){
		operator = op; // 1 = move, 2 = swap, 3 = both operators
	}
	
	public List<Successor> getSuccessors(Object aState) {
		List<Successor> successors = new ArrayList<>();
		AzamonState parent_state = (AzamonState) aState;
		
		for (int i = 0; i < AzamonState.packages.size(); ++i) {
			if(operator == 3 || operator == 1){
				for (int j = 0; j < AzamonState.offers.size(); ++j) {
					AzamonState child_state = new AzamonState(parent_state);
					// només afegim els estats possibles i que milloren la solucio
					if(child_state.movePackage(i, j)){
						StringBuffer S = new StringBuffer();
						S.append("moving package " + i + " to offer " + j + "\n");
						successors.add(new Successor (S.toString(), child_state));
						
					}
				}
			}
			if(operator == 3 || operator == 2){	
				// fem i+1 perque no te sentit fer swap amb ell mateix
				for(int j = i+1; j < AzamonState.packages.size(); ++j){
					AzamonState child_state = new AzamonState(parent_state);
					if(child_state.swapPackage(i, j)){
						StringBuffer S = new StringBuffer();
						S.append("swapping package " + i + " with package " + j + "\n");
						successors.add(new Successor (S.toString(), child_state));
						
					}
				}
			}
		}
		return successors;
	}

}
