package IA.Azamon;
import java.util.*;
import IA.Azamon.Paquete;
import IA.Azamon.Paquetes;
import IA.Azamon.Oferta;
import IA.Azamon.Transporte;


public class AzamonState {
	
	
	// ATTRIBUTES DECLARATION
	static Paquetes packages;
	static Transporte offers;
	
	private Vector<Integer> packageAssignments;
	private Vector<Double> offersLoad;	
	
	private int happiness;
	private double price;
	
	// CONSTRUCTORS
	public AzamonState(int numPaq, int seedPaquetes, double proportion, int seedOfertas) {
		packages = new Paquetes(numPaq, seedPaquetes);
		offers = new Transporte(packages, proportion, seedOfertas);
		
		/// He afegit aixo, nose si ta be
		happiness = computeHappiness();
		price = computePrice();
		
		packageAssignments = new Vector<Integer>(numPaq);
		for (int i = 0; i < numPaq; i++) { packageAssignments.set(i, -1); }
		
		offersLoad = new Vector<Double>(offers.size());
		for (int i = 0; i < offers.size(); i++) { offersLoad.set(i, 0.0); }		
	}
	
	public AzamonState(AzamonState state){
		packages = state.packages;
		offers = state.offers;
		price = state.price;
		
        packageAssignments = new Vector<Integer> (state.packageAssignments);
        offersLoad = new Vector<Double> (state.offersLoad); 
    }
	
	
	// METHODS
	
	public int getHappiness() {
		return happiness;
	}
	
	public double getPrice() {
		return price;
	}
	
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
	
	///////// Successors Methods
	
	public boolean checkPriority(int priority, int offerDays){
        if(priority == 0 & offerDays == 1) return true;
        if(priority == 1 & offerDays <= 3) return true;
        if(priority == 2 & offerDays <= 5) return true;
        return false;
    }
	
	public boolean canMove(int p_idx, int o_idx) {
		
		Paquete p = packages.get(p_idx);
		Oferta o = offers.get(o_idx);
		
		double w = offersLoad.get(o_idx) + p.getPeso();
	
		if (w <= o.getPesomax()) {
			
			if(checkPriority(p.getPrioridad(), o.getDias())) {
				
				return true;
			}
		}
		
		return false;
	}

	public boolean movePackage(int p_idx, int o_idx) {
		if (canMove(p_idx, o_idx)) {
			
			packageAssignments.set(p_idx, o_idx);
			
			offersLoad.set(packageAssignments.get(p_idx), offersLoad.get(packageAssignments.get(p_idx)) - packages.get(p_idx).getPeso());
			offersLoad.set(o_idx, offersLoad.get(o_idx) + packages.get(p_idx).getPeso());
			return true;
		}
		
		return false;
	}
	
	
	public boolean canSwap(Paquete p1, Paquete p2, int o1_idx, int o2_idx) {
		
		Oferta o1 = offers.get(o1_idx);
		Oferta o2 = offers.get(o2_idx);
		
		double w1 = offersLoad.get(o1_idx) + p2.getPeso() - p1.getPeso();
		double w2 = offersLoad.get(o2_idx) - p2.getPeso() + p1.getPeso();
		
		if (w1<= o1.getPesomax() & w2 <= o2.getPesomax()) {
			
			if (checkPriority(p1.getPrioridad(), o2.getDias()) &
				checkPriority(p2.getPrioridad(), o1.getDias())) {
				
				return true;
			}
			
		}
		return false;
	}
	
	public boolean swapPackage(int p1_idx, int p2_idx) {
	
		int o1_idx = packageAssignments.get(p1_idx);
		int o2_idx = packageAssignments.get(p2_idx);
		
		Paquete p1 = packages.get(p1_idx);
		Paquete p2 = packages.get(p2_idx);
		
		if (canSwap(p1, p2, o1_idx, o2_idx)) {
			
			packageAssignments.set(p1_idx, o2_idx);
			packageAssignments.set(p2_idx, o1_idx);
			
			offersLoad.set(o1_idx, offersLoad.get(o1_idx) - p1.getPeso() + p2.getPeso());
			offersLoad.set(o2_idx, offersLoad.get(o2_idx) + p1.getPeso() - p2.getPeso());
			
			return true;
		}
		
		return false;
	}

	
	// setSelectedHeuristic !!!!!!!!

}

