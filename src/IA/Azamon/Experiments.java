package IA.Azamon;

import java.util.List;
import java.util.Scanner;

import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class Experiments {
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("###########################################");
		System.out.println("###	   Select an Experiment	        ###");
		System.out.println("###########################################");
		System.out.println("### 0.	Special Experiment 			###");
		
		switch(scan.nextInt()){
	    case 0:
	    	SpecialExperiment();
		}
	}
	
	private static void SpecialExperiment(){
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
		
		HeuristicFunction f_heuristic = new AzamonHeuristicCost();
		
		Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(), new AzamonGoalTest(), f_heuristic);
		
		HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
		
		double meanPrice = 0;
		double price = 0;
		long meanTime = 0;
		
		for (int i = 0; i < 10; ++i) {
			try {
				long time = System.nanoTime();
				AzamonState state = (AzamonState) hillClimbingSearch.getGoalState();					
				SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
				price = state.getPrice();	
				System.out.println("Price: " + price);
				System.out.println("Time: " + Math.round(time/1000000));	
				meanPrice += price;
				meanTime += time;
			
			} catch (Exception e) {
                e.printStackTrace();
            }		
		}
		System.out.println("Mean price: " + meanPrice);
		System.out.println("Mean time: " + Math.round(meanTime/1000000));
	}
}
