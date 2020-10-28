package IA.Azamon;
import java.util.*;



public class AzamonState {
	
	// ATTRIBUTES DECLARATION
	static Paquetes packages;
	static Transporte offers;
	
	private Vector<Integer> packageAssignments;
	private Vector<Double> offersLoad;	
	
	private double price;
	private int happiness;
	
	
	// CONSTRUCTORS
	public AzamonState(int numPaq, int seedPaquetes, double proportion, int seedOfertas) {
		packages = new Paquetes(numPaq, seedPaquetes);
		offers = new Transporte(packages, proportion, seedOfertas);
		packageAssignments = new Vector<Integer>(numPaq);
		for (int i = 0; i < numPaq; i++) { packageAssignments.add(-1); }
		offersLoad = new Vector<Double>(offers.size());
		for (int i = 0; i < offers.size(); i++) { offersLoad.add(0.0); }		
	}
	
	public AzamonState(AzamonState state){
		packages = state.getPackages();
		offers = state.getOffers();
		price = state.price;
		happiness = state.happiness;
        	packageAssignments = new Vector<Integer> (state.packageAssignments);
        	offersLoad = new Vector<Double> (state.offersLoad); 
    	}
	
	
	// METHODS
	public Paquetes getPackages(){
		return packages;
	}
	
	public Transporte getOffers(){
		return offers;
	}
	
	public Vector<Integer> getPackageAssignments() {
		return packageAssignments;
	}
	
	public Vector<Double> getOffersLoad() {
		return offersLoad;
	}
	
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
			if (offerDays == 5) price += 2*0.25*weight;
			else if (offerDays >= 3) price += 0.25*weight;
			// Add transport cost
			price += weight*offerPrice;
		}
		return price;
	}
	
	public int deliveryHappiness(int priority, int days) {
		if (priority == 1 && days == 1) return 1;
		else if (priority == 2 && days < 4) return (4-days);
		return 0;
	}
	
	public int computeHappiness() {
		int happiness = 0;
		for (int i = 0; i < packageAssignments.size(); i++) {
			int priority = packages.get(i).getPrioridad();
			int offerDays = offers.get(packageAssignments.get(i)).getDias();
			happiness += deliveryHappiness(priority, offerDays);
		}
		return happiness;
	}
	
	public boolean checkPriority(int priority, int offerDays){
        	if(priority == 0 & offerDays == 1) return true;
        	if(priority == 1 & offerDays <= 3) return true;
        	if(priority == 2 & offerDays <= 5) return true;
        	return false;
    	}
	
	
	// INITIAL STATE GENERATORS
	// This generator is made for price optimization
	public void generateInitialStateA() {
		// Sort packages by priority (lowest to highest)
		Collections.sort(packages, new Comparator<Paquete>() {
            @Override
            public int compare(Paquete p1, Paquete p2) {
                return ((Integer) p1.getPrioridad()).compareTo((Integer) p2.getPrioridad());
            }
        });
        // Sort offers by price (lowest to highest)
        Collections.sort(offers, new Comparator<Oferta>() {
            @Override
            public int compare(Oferta o1, Oferta o2) {
                return ((Double) o1.getPrecio()).compareTo((Double) o2.getPrecio());
            }
        });
        // Assign packages to offers
        for (int i = 0; i < packages.size(); i++) {
        	Paquete p = packages.get(i);
        	for (int j = 0; j < offers.size(); j++) {
        		Oferta offer = offers.get(j);
        		if ( (offer.getPesomax() - offersLoad.get(j)) >= p.getPeso() &&
        			  checkPriority(p.getPrioridad(), offer.getDias()) ) {
        			packageAssignments.set(i, j);
        			offersLoad.set(j, offersLoad.get(j)+p.getPeso());
        			break;
        		}
        	}
        }
        price = computePrice();
        happiness = computeHappiness();
	}

	// This generator is made for happiness optimization
	public void generateInitialStateB() {
		// Sort packages by priority (lowest to highest)
		Collections.sort(packages, new Comparator<Paquete>() {
            @Override
            public int compare(Paquete p1, Paquete p2) {
                return ((Integer) p1.getPrioridad()).compareTo((Integer) p2.getPrioridad());
            }
        });
		// Sort offers by date (lowest to highest)
        Collections.sort(offers, new Comparator<Oferta>() {
            @Override
            public int compare(Oferta o1, Oferta o2) {
                return ((Integer) o1.getDias()).compareTo((Integer) o2.getDias());
            }
        });
        // Assign packages to offers
        for (int i = 0; i < packages.size(); i++) {
        	Paquete p = packages.get(i);
        	for (int j = 0; j < offers.size(); j++) {
        		Oferta offer = offers.get(j);
        		if ( (offer.getPesomax() - offersLoad.get(j)) >= p.getPeso() &&
          			  checkPriority(p.getPrioridad(), offer.getDias()) ) {
          			packageAssignments.set(i, j);
          			offersLoad.set(j, offersLoad.get(j)+p.getPeso());
          			break;
          		}
        	}
        }
        price = computePrice();
        happiness = computeHappiness();
	}

	// Purposely bad initialization for comparison purposes
	public void generateInitialStateC() {
		// Sort packages by weight (highest to lowest)
		Collections.sort(packages, new Comparator<Paquete>() {
            @Override
            public int compare(Paquete p1, Paquete p2) {
                return ((Double) p2.getPeso()).compareTo((Double) p1.getPeso());
            }
        });
		// Sort offers by date (highest to lowest)
        Collections.sort(offers, new Comparator<Oferta>() {
            @Override
            public int compare(Oferta o1, Oferta o2) {
                return ((Integer) o2.getDias()).compareTo((Integer) o1.getDias());
            }
        });
		for (int i = 0; i < packages.size(); i++) {
			Paquete p = packages.get(i);
			for (int j = 0; j < offers.size(); j++) {
				Oferta offer = offers.get(j);
				if ( (offer.getPesomax() - offersLoad.get(j)) >= p.getPeso() &&
          			  checkPriority(p.getPrioridad(), offer.getDias()) ) {
          			packageAssignments.set(i, j);
          			offersLoad.set(j, offersLoad.get(j)+p.getPeso());
          			break;
				}
			}
		}
		price = computePrice();
        happiness = computeHappiness();
	}

	// SUCCESORS METHODS
	public double updatePrice(Paquete p, Oferta oldOffer, Oferta newOffer) {
		double priceDelta = p.getPeso() * (newOffer.getPrecio()-oldOffer.getPrecio());
		double priceStorageOld = 0;
		if (oldOffer.getDias() == 5) priceStorageOld = 2*0.25*p.getPeso();
		else if (oldOffer.getDias() >= 3) priceStorageOld += 0.25*p.getPeso();

		double priceStorageNew = 0;
		if (newOffer.getDias() == 5) priceStorageNew = 2*0.25*p.getPeso();
		else if (newOffer.getDias() >= 3) priceStorageNew += 0.25*p.getPeso();
		
		priceDelta += (priceStorageNew - priceStorageOld);
		
		return priceDelta;
	}
	
	public int updateHappiness(Paquete p, Oferta oldOffer, Oferta newOffer) {
		int oldHappiness = deliveryHappiness(p.getPrioridad(), oldOffer.getDias());
		int newHappiness = deliveryHappiness(p.getPrioridad(), newOffer.getDias());
		int happinessDelta = newHappiness - oldHappiness;
		happiness += happinessDelta;
		return happinessDelta;		
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
			Paquete p = packages.get(p_idx);
			Oferta o1 = offers.get(packageAssignments.get(p_idx));
			Oferta o2 = offers.get(o_idx);
			// Update package assignment
			packageAssignments.set(p_idx, o_idx);
			offersLoad.set(packageAssignments.get(p_idx), offersLoad.get(packageAssignments.get(p_idx)) - p.getPeso());
			offersLoad.set(o_idx, offersLoad.get(o_idx) + p.getPeso());
			// Update price and happiness
			price += updatePrice(p, o1, o2);
			updateHappiness(p, o1, o2);
			return true;
		}
		return false;
	}
	
	
	public boolean canSwap(Paquete p1, Paquete p2, int o1_idx, int o2_idx) {
		Oferta o1 = offers.get(o1_idx);
		Oferta o2 = offers.get(o2_idx);
		double w1 = offersLoad.get(o1_idx) + p2.getPeso() - p1.getPeso();
		double w2 = offersLoad.get(o2_idx) - p2.getPeso() + p1.getPeso();
		if (w1 <= o1.getPesomax() && w2 <= o2.getPesomax()) {
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
			// Update packages assignments
			packageAssignments.set(p1_idx, o2_idx);
			packageAssignments.set(p2_idx, o1_idx);
			offersLoad.set(o1_idx, offersLoad.get(o1_idx) - p1.getPeso() + p2.getPeso());
			offersLoad.set(o2_idx, offersLoad.get(o2_idx) + p1.getPeso() - p2.getPeso());
			// Update price and happiness (for both packages)
			// price += (p2.getPeso()-p1.getPeso()) * (offers.get(o2_idx).getPrecio()-offers.get(o1_idx).getPrecio());
			price += updatePrice(p1, offers.get(o1_idx), offers.get(o2_idx));
			price += updatePrice(p2, offers.get(o2_idx), offers.get(o1_idx));
			updateHappiness(p1, offers.get(o1_idx), offers.get(o2_idx));
			updateHappiness(p2, offers.get(o2_idx), offers.get(o1_idx));
			return true;
		}
		return false;
	}

	
	// PRINTS
	public void showPackages() {
		for (int i = 0; i < packages.size(); i++) {
			Paquete p = packages.get(i);
			System.out.println(i + ". " + p.toString());
		}
	}
	
	public void showOffers() {
		for (int j = 0; j < offers.size(); j++) {
			Oferta o = offers.get(j);
			System.out.println(j + ". " + o.toString());
		}
	}

	public void showPackageAssignments() {
		System.out.println(packageAssignments);
	}
	
	public void showOffersLoad() {
		System.out.println(offersLoad);
	}
	
	public void showPrice() {
		System.out.println(price);
	}
	
	public void showHappiness() {
		System.out.println(happiness);
	}
	
	public void show() {
		// System.out.println("# PACKAGES:");
		// showPackages();
		// System.out.println("# OFFERS:");
		// showOffers();
		System.out.println("# PACKAGE ASSIGNMENTS:");
		showPackageAssignments();
		System.out.println("# OFFERS LOAD:");
		showOffersLoad();
		System.out.println("# PRICE:");
		showPrice();
		System.out.println("# HAPPINESS:");
		showHappiness();
	}
}

