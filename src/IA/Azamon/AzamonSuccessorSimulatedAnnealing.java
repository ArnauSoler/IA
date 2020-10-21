
package IA.Azamon;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class AzamonSuccessorSimulatedAnnealing implements SuccessorFunction {  
	
	public List<Successor> getSuccessors(Object aState) {
		ArrayList<Successor> successors = new ArrayList<>();

		for(int i = 0; i < AzamonState.packages.size(); ++i) {
			// ens movem per tots els packages
			for(int j = 0; j < AzamonState.offers.size(); ++j) {
				// ens movem per totes les ofertes
				// aqui hem de mirar si es pot moure el paquet i a la oferta j
				AzamonState newState = new AzamonState((AzamonState) aState);
				if(newState.movePackage(i, j)) {
					StringBuffer S = new StringBuffer();
					S.append("moving package " + i + " to offer " + j + "\n");

					successors.add(new Successor(S.toString(), newState));
				}
			}
			for(int j = 0; j < AzamonState.packages.size(); ++j) {
				// ens movem per tots els packages
				// aqui hem de mirar si es pot cambiar el paguet i amb el paquet j
				AzamonState newState = new AzamonState((AzamonState) aState);
				if(i != j && newState.swapPackage(i, j)){
					StringBuffer S = new StringBuffer();
					S.append("swapping package " + i + " with package " + j + "\n");

					successors.add(new Successor(S.toString(), newState));
				}
			}
		}

		return successors;

	}

}
