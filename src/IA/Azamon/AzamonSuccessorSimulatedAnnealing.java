
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
		for(int i = 0; i < estat.packages.size(); ++i) {
			// ens movem per tots els packages
			for(int j = 0; j < estat.offers.size(); ++j) {
				// ens movem per totes les ofertes
				// aqui hem de mirar si es pot moure el paquet i a la oferta j
				AzamonState newState = new AzamonState((AzamonState) aState);
				if(newState.canMove(i, j)) {
					newState.movePackage(i, j);
				}
			}
			for(int j = 0; j < estat.packages.size(); ++j) {
				// ens movem per tots els packages
				// aqui hem de mirar si es pot cambiar el paguet i amb el paquet j
				AzamonState newState = new AzamonState((AzamonState) aState);
				// if(newState.canSwap(i, j)){
				// 	newState.swapPackage(i, j);
				// }
			}
		}

		return retVal;

	}

}
