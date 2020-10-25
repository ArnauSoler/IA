package IA.Azamon;

import java.util.List;
import java.util.Scanner;
import java.util.Vector;

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
		System.out.println("### 0.	Special Experiment 		###");
		System.out.println("### 1.	Experiment Number 1 		###");
		System.out.println("### 2.	Experiment Number 2 		###");
		System.out.println("### 3.	Experiment Number 3 		###");
		System.out.println("### 4.	Experiment Number 4 		###");
		
		switch(scan.nextInt()){
	    case 0:
			SpecialExperiment();
			break;
		case 1:
			experiment1();
			break;
		case 2:
			experiment2();
			break;
		case 3:
			experiment3();
			break;
		case 4:
			experiment4();
			break;
		}
	}
	
	private static void SpecialExperiment(){
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;

		int operators = 3;
		
		AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
		azamonState.generateInitialStateC();

		HeuristicFunction f_heuristic = new AzamonHeuristicCost();
		
		Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(operators), new AzamonGoalTest(), f_heuristic);
		
		HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
		
		double meanPrice = 0;
		double price = 0;
		long meanTime = 0;
		
		for (int i = 0; i < 10; ++i) {
			try {
				long time = System.nanoTime();
				SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
				AzamonState state = (AzamonState) hillClimbingSearch.getGoalState();					
				meanTime += (System.nanoTime() - time);
				price = state.getPrice();	
				// System.out.println("Price: " + price);
				// System.out.println("Time: " + Math.round(time/1000000));	
				meanPrice += price;
			
			} catch (Exception e) {
                e.printStackTrace();
            }		
		}
		System.out.println("Price: " + (double) Math.round(meanPrice) / 10);
		System.out.println("Mean time: " + Math.round(meanTime/1000000)/10 + " miliseconds");
	}

	private static void experiment1(){
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
		azamonState.generateInitialStateC();
		
		HeuristicFunction f_heuristic = new AzamonHeuristicCost();
		String[] operators = {"Move","Swap","Move & Swap"};
		
		for(int j = 0; j < 3; ++j){
			
			Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(j), new AzamonGoalTest(), f_heuristic);
			
			HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
		
			double meanPrice = 0;
			double price = 0;
			long meanTime = 0;
			
			for (int i = 0; i < 10; ++i) {
				try {
					long time = System.nanoTime();
					SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
					AzamonState state = (AzamonState) hillClimbingSearch.getGoalState();					
					meanTime += (System.nanoTime() - time);
					price = state.getPrice();	
					meanPrice += price;
				
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
			System.out.println("Operator: " + operators[j]);
			System.out.println("Mean price: " + meanPrice);
			System.out.println("Mean time: " + Math.round(meanTime/1000000));
			System.out.println("");
		}
	}

	private static void experiment2(){
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		for(int j = 0; j < 3; ++j){
			AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
			
			if(j == 0) azamonState.generateInitialStateA();
			else if(j == 1) azamonState.generateInitialStateB();
			else azamonState.generateInitialStateC();
			
			HeuristicFunction f_heuristic = new AzamonHeuristicCost();
			
			Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(3), new AzamonGoalTest(), f_heuristic);
			
			HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
		
			double meanPrice = 0;
			double price = 0;
			long meanTime = 0;
			
			for (int i = 0; i < 10; ++i) {
				try {
					long time = System.nanoTime();
					SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
					AzamonState state = (AzamonState) hillClimbingSearch.getGoalState();					
					meanTime += (System.nanoTime() - time);
					price = state.getPrice();	
					meanPrice += price;
				
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
			System.out.println("Initial State: " + j);
			System.out.println("Mean price: " + meanPrice/10);
			System.out.println("Mean time: " + Math.round(meanTime/1000000)/10);
			System.out.println("");
		}
	}

	private static void experiment3(){
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		for(int j = 1; j < 10; ++j){	
			
			AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
			azamonState.generateInitialStateC();
			
			HeuristicFunction f_heuristic = new AzamonHeuristicCost();
			
			Problem problem = new Problem(azamonState, new AzamonSuccessorSimulatedAnnealing(3), new AzamonGoalTest(), f_heuristic);
			
			int steps = 200 * j;
			int stiter = 20;
			int k = 5;
			double lamb = 0.01;
			SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(steps, stiter, k, lamb);
		
			double meanPrice = 0;
			double price = 0;
			long meanTime = 0;
			
			for (int i = 0; i < 10; ++i) {
				try {
					long time = System.nanoTime();
					SearchAgent searchAgent = new SearchAgent(problem, simulatedAnnealingSearch);
					AzamonState state = (AzamonState) simulatedAnnealingSearch.getGoalState();					
					meanTime += (System.nanoTime() - time);
					price = state.getPrice();	
					meanPrice += price;
				
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
			System.out.println("Steps: " + 200 * j);
			System.out.println("Mean price: " + meanPrice / 10);
			System.out.println("Mean time: " + Math.round(meanTime/1000000));
			System.out.println("");
		}
	}

	private static void experiment4(){
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		for(int j = 0; j < 10; ++j){
			AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion + 0.2*j, seedOfertas);
			
			azamonState.generateInitialStateC();
			
			HeuristicFunction f_heuristic = new AzamonHeuristicCost();
			
			Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(3), new AzamonGoalTest(), f_heuristic);
			
			HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
		
			double meanPrice = 0;
			double price = 0;
			long meanTime = 0;
			int iterations = 3;
			
			for (int i = 0; i < 10; ++i) {
				try {
					long time = System.nanoTime();
					SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
					AzamonState state = (AzamonState) hillClimbingSearch.getGoalState();					
					meanTime += (System.nanoTime() - time);
					price = state.getPrice();	
					meanPrice += price;
				
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
			System.out.println("Proportion: " + (proportion + 0.2*j));
			System.out.println("Mean price: " + meanPrice/iterations);
			System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
			System.out.println("");
		}

		for(int j = 0; j < 10; ++j){
			AzamonState azamonState = new AzamonState(numPaq + 50*j, seedPaquetes, proportion, seedOfertas);
			
			azamonState.generateInitialStateC();
			
			HeuristicFunction f_heuristic = new AzamonHeuristicCost();
			
			Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(3), new AzamonGoalTest(), f_heuristic);
			
			HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
		
			double meanPrice = 0;
			double price = 0;
			long meanTime = 0;
			int iterations = 1;
			
			for (int i = 0; i < iterations; ++i) {
				try {
					long time = System.nanoTime();
					SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
					AzamonState state = (AzamonState) hillClimbingSearch.getGoalState();					
					meanTime += (System.nanoTime() - time);
					price = state.getPrice();	
					meanPrice += price;
				
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}

			System.out.println("Packages: " + (numPaq + 50*j));
			System.out.println("Mean price: " + meanPrice/iterations);
			System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
			System.out.println("");
		}
	}
}
