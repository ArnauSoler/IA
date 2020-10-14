package IA.Azamon;
import java.util.*;
import IA.Azamon.Paquete;
import IA.Azamon.Paquetes;
import IA.Azamon.Oferta;
import IA.Azamon.Transporte;


public class AzamonState {
	
	
	// ATTRIBUTES DECLARATION
	private Paquetes packages;
	private Transporte offers;
	private Vector<Integer> packageAssignments;
	private Vector<Double> offersLoad;	
	
	
	// CONSTRUCTORS
	public AzamonState(int numPaq, int seedPaquetes, double proportion, int seedOfertas) {
		packages = new Paquetes(numPaq, seedPaquetes);
		offers = new Transporte(packages, proportion, seedOfertas);
		packageAssignments = new Vector<Integer>(numPaq);
		offersLoad = new Vector<Double>(offers.size());
		for (int i = 0; i < numPaq; i++) { packageAssignments.set(i, -1); }
		for (int i = 0; i < offers.size(); i++) { offersLoad.set(i, 0.0); }		
	}
	
	
	// METHODS
	public double computePrice() {
		double price = 0.0;
		for (int i = 0; i < packageAssignments.size(); i++) {
			double weight = packages.get(i).getPeso();
			double offerPrice = offers.get(packageAssignments.get(i)).getPrecio();
			int offerDays = offers.get(packageAssignments.get(i)).getDias();
			// Add storage cost
			if (offerDays > 3) price += 2*0.2*weight;
			else if (offerDays > 1) price += 0.25*weight;
			// Add transport cost
			price += weight*offerPrice;
		}
		return price;
	}
	
	public int computeHappiness() {
		int happiness = 0;
		for (int i = 0; i < packageAssignments.size(); i++) {
			double priority = packages.get(i).getPrioridad();
			int offerDays = offers.get(packageAssignments.get(i)).getDias();
			if (priority == 1 && offerDays == 1) happiness += 1;
			else if (priority == 2 && offerDays < 4) happiness += (4-offerDays);
		}
		return happiness;
	}
	
	public int daysToPriority(int days) {
		if (days == 1) return 0;
		if (days <= 3) return 1;
		return 2;
	}
	
	public int priorityToDays(int priority) {
		if (priority == 0) return 1;
		if (priority == 1) return 2;
		return 4;
	}
	
	
	// INITIAL STATE GENERATORS
	public void generateInitialStateA() {
		// Sort packages by priority (lowest to highest)
		Collections.sort(packages, new Comparator<Paquete>() {
            @Override
            public int compare(Paquete p1, Paquete p2) {
                return ((Integer) p2.getPrioridad()).compareTo((Integer) p1.getPrioridad());
            }
        });
        // Sort offers by price (lowest to highest)
        Collections.sort(offers, new Comparator<Oferta>() {
            @Override
            public int compare(Oferta o1, Oferta o2) {
                return ((Double) o2.getPrecio()).compareTo((Double) o1.getPrecio());
            }
        });
        // Assign packages to offers
        for (int i = 0; i < packages.size(); i++) {
        	Paquete p = packages.get(i);
        	for (int j = 0; j < offers.size(); j++) {
        		Oferta offer = offers.get(j);
        		if ( (offer.getPesomax() - offersLoad.get(j)) >= p.getPeso() &&
        			  p.getPrioridad() <= daysToPriority(offer.getDias()) ) {
        			packageAssignments.set(i, j);
        			offersLoad.set(j, offersLoad.get(j)+packages.get(i).getPeso());
        		}
        	}
        }
	}

	public void generateInitialStateB() {
		// Sort packages by priority (lowest to highest)
		Collections.sort(packages, new Comparator<Paquete>() {
            @Override
            public int compare(Paquete p1, Paquete p2) {
                return ((Integer) p2.getPrioridad()).compareTo((Integer) p1.getPrioridad());
            }
        });
		// Sort offers by date (lowest to highest)
        Collections.sort(offers, new Comparator<Oferta>() {
            @Override
            public int compare(Oferta o1, Oferta o2) {
                return ((Integer) o2.getDias()).compareTo((Integer) o1.getDias());
            }
        });
		
	}

	public void generateInitialStateC() {
		// TODO Auto-generated method stub
		
	}

	public void setSelectedHeuristic(int heuristic) {
		// TODO Auto-generated method stub
		
	}
	
	// setSelectedHeuristic

}

