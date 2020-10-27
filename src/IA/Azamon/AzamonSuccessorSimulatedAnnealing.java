
package IA.Azamon;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.Random;

public class AzamonSuccessorSimulatedAnnealing implements SuccessorFunction { 
	
	private static Random r;
	private static int operator;
	
	public AzamonSuccessorSimulatedAnnealing(int op) {
		r = new Random();
		operator = op; // 1 = move, 2 = swap, 3 = both operators
    }

	public AzamonSuccessorSimulatedAnnealing() {
		r = new Random();
		operator = 3;
	}

	public List<Successor> getSuccessors(Object aState) {
		List<Successor> successors = new ArrayList<>();
		boolean done = false;
		while(!done){
			int packageIndex = r.nextInt(AzamonState.packages.size());
			int nextOp = operator - 1;
			if(nextOp == 2) nextOp = r.nextInt(2);
			if(nextOp == 0){
				int offerIndex = r.nextInt(AzamonState.offers.size());
				
				AzamonState newState = new AzamonState((AzamonState) aState);
				if(newState.movePackage(packageIndex, offerIndex)) {
					StringBuffer S = new StringBuffer();
					S.append("moving package " + packageIndex + " to offer " + offerIndex + "\n");
					successors.add(new Successor(S.toString(), newState));
					done = true;
				}
			}
			else{
				int packageIndex2 = r.nextInt(AzamonState.packages.size());

				AzamonState newState = new AzamonState((AzamonState) aState);
				if(packageIndex != packageIndex2 && newState.swapPackage(packageIndex, packageIndex2)){
					StringBuffer S = new StringBuffer();
					S.append("swapping package " + packageIndex + " with package " + packageIndex2 + "\n");
					successors.add(new Successor(S.toString(), newState));
					done = true;
				}
			}
		}
		return successors;
	}

}
