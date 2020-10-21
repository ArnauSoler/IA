
package IA.Azamon;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.Random;

public class AzamonSuccessorSimulatedAnnealing implements SuccessorFunction { 
	
	private static Random r;
	
	public AzamonSuccessorSimulatedAnnealing() {
        r = new Random();
    }

	public List<Successor> getSuccessors(Object aState) {
		ArrayList<Successor> successors = new ArrayList<>();
		boolean done = false;
		while(!done){
			int packageIndex = r.nextInt(AzamonState.packages.size());
			if(r.nextInt(2) == 0){
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
