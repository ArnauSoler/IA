package IA.Azamon;

import java.text.DecimalFormat;
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
		System.out.println("### 5.	Experiment Number 6 		###");
		System.out.println("### 6.	Experiment Number 7.1 		###");
		System.out.println("### 7.	Experiment Number 7.2 		###");
		System.out.println("### 8.	Experiment Number 7.4 		###");
		System.out.println("### 9.	Experiment Number 7.6 		###");
		
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
		case 5:
			experiment6();
			break;
		case 6:
			experiment1_annealing();
			break;
		case 7:
			experiment2_annealing();
			break;
		case 8:
			experiment4_annealing();
			break;
		case 9:
			experiment6_annealing();
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

		System.out.println("Initial price: " + azamonState.getPrice());
		System.out.println("Initial happiness: " + azamonState.getHappiness() + "\n");

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
		azamonState.generateInitialStateA();

		System.out.println("Initial price: " + azamonState.getPrice());
		System.out.println("Initial happiness: " + azamonState.getHappiness() + "\n");
		
		HeuristicFunction f_heuristic = new AzamonHeuristicCost();
		String[] operators = {"Move","Swap","Move & Swap"};
		
		for(int j = 0; j < 3; ++j){
			
			Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(j+1), new AzamonGoalTest(), f_heuristic);
			
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
					// List actions = searchAgent.getActions();
					// showActions(actions);
				
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
			System.out.println("Operator: " + operators[j]);
			System.out.println("Mean price: " + meanPrice/iterations);
			System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
			System.out.println("");
		}
	}

	private static void experiment2(){
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		String[] states = {"A","B","C"};
		
		for(int j = 0; j < 3; ++j){
			AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
			
			if(j == 0) azamonState.generateInitialStateA();
			else if(j == 1) azamonState.generateInitialStateB();
			else azamonState.generateInitialStateC();
			
			System.out.println("Initial State: " + states[j]);
			System.out.println("Initial price: " + azamonState.getPrice());
			System.out.println("Initial happiness: " + azamonState.getHappiness());

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
			System.out.println("Mean price: " + meanPrice/10);
			System.out.println("Mean time: " + Math.round(meanTime/1000000)/10 + "\n");
		}
	}

	private static void experiment3(){
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;

		DecimalFormat numberFormat = new DecimalFormat("#0.0000");
		
		for(int j = 1; j <= 5; ++j){
			for(int w = 1; w <= 5; ++w){
				for(int k = 1; k <= 5; ++k){
					for(int l = 1; l <= 5; ++l){
						AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
						azamonState.generateInitialStateC();
						
						HeuristicFunction f_heuristic = new AzamonHeuristicCost();
						
						Problem problem = new Problem(azamonState, new AzamonSuccessorSimulatedAnnealing(3), new AzamonGoalTest(), f_heuristic);
						
						int steps = 1000;
						int stiter = 1;
						double lamb = Math.pow(0.5, l);
						SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(steps * j, stiter * j * w, k, lamb);
					
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
						System.out.print("Steps: " + steps * j + " \t|\tStiter: " + stiter * j * w + " \t|\tK: " + k + " \t|\tLamb: " + numberFormat.format(lamb));
						System.out.print("\t|\tMean price: " + numberFormat.format(meanPrice / 10));
						System.out.println("\t|\tMean time: " + Math.round(meanTime/1000000) + "\n");
					}
				}
			}
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
			System.out.println("Proportion: " + (proportion + 0.2*j));
			System.out.println("Initial price: " + azamonState.getPrice());
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
			System.out.println("Initial price: " + azamonState.getPrice());
			System.out.println("Mean price: " + meanPrice/iterations);
			System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
			System.out.println("");
		}
	}

	private static void experiment6(){
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
		azamonState.generateInitialStateA();

		System.out.println("Initial price: " + azamonState.getPrice());
		System.out.println("Initial happiness: " + azamonState.getHappiness() + "\n");
		
		for(int k = 0; k < 15; ++k){
			System.out.println("###########################################");
			System.out.println("Epsilon happines: " + k);
			HeuristicFunction f_heuristic = new AzamonHeuristicHappiness(k);
			String[] operators = {"Move","Swap","Move & Swap"};
			
			for(int j = 0; j < 3; ++j){
				
				Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(j+1), new AzamonGoalTest(), f_heuristic);
				
				HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
			
				double meanPrice = 0;
				double price = 0;
				double meanHappiness = 0;
				double happiness = 0;
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
						happiness = state.getHappiness();
						meanHappiness += happiness;
						// List actions = searchAgent.getActions();
						// showActions(actions);
					
					} catch (Exception e) {
						e.printStackTrace();
					}		
				}
				System.out.println("Operator: " + operators[j]);
				System.out.println("Mean price: " + meanPrice/iterations);
				System.out.println("Mean happiness: " + meanHappiness/iterations);
				System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
				System.out.println("");
			}
		}

	}

	private static void experiment1_annealing(){
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
		azamonState.generateInitialStateC();

		System.out.println("Initial price: " + azamonState.getPrice());
		System.out.println("Initial happiness: " + azamonState.getHappiness() + "\n");
		
		HeuristicFunction f_heuristic = new AzamonHeuristicCost();
		String[] operators = {"Move","Swap","Move & Swap"};
		
		for(int j = 0; j < 3; ++j){
			
			Problem problem = new Problem(azamonState, new AzamonSuccessorSimulatedAnnealing(j+1), new AzamonGoalTest(), f_heuristic);
			
			int steps = 5000;
			int stiter = 100;
			int k = 5;
			double lamb = 0.01;
			SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(steps, stiter, k, lamb);
		
			double meanPrice = 0;
			double price = 0;
			long meanTime = 0;
			int iterations = 10;
			
			for (int i = 0; i < iterations; ++i) {
				try {
					long time = System.nanoTime();
					SearchAgent searchAgent = new SearchAgent(problem, simulatedAnnealingSearch);
					AzamonState state = (AzamonState) simulatedAnnealingSearch.getGoalState();		
					meanTime += (System.nanoTime() - time);
					price = state.getPrice();	
					meanPrice += price;
					// List actions = searchAgent.getActions();
					// showActions(actions);
				
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
			System.out.println("Operator: " + operators[j]);
			System.out.println("Mean price: " + meanPrice/iterations);
			System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
			System.out.println("");
		}
	}

	private static void experiment2_annealing(){
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		String[] states = {"A","B","C"};
		
		for(int j = 0; j < 3; ++j){
			AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
			
			if(j == 0) azamonState.generateInitialStateA();
			else if(j == 1) azamonState.generateInitialStateB();
			else azamonState.generateInitialStateC();
			
			System.out.println("Initial State: " + states[j]);
			System.out.println("Initial price: " + azamonState.getPrice());
			System.out.println("Initial happiness: " + azamonState.getHappiness());

			HeuristicFunction f_heuristic = new AzamonHeuristicCost();
			
			Problem problem = new Problem(azamonState, new AzamonSuccessorSimulatedAnnealing(), new AzamonGoalTest(), f_heuristic);
			
			int steps = 5000;
			int stiter = 100;
			int k = 1;
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
			System.out.println("Mean price: " + meanPrice/10);
			System.out.println("Mean time: " + Math.round(meanTime/1000000)/10 + "\n");
		}
	}

	private static void experiment4_annealing(){
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		for(int j = 0; j < 10; ++j){
			AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion + 0.2*j, seedOfertas);
			
			azamonState.generateInitialStateC();
			
			HeuristicFunction f_heuristic = new AzamonHeuristicCost();
			
			Problem problem = new Problem(azamonState, new AzamonSuccessorSimulatedAnnealing(), new AzamonGoalTest(), f_heuristic);
			
			int steps = 5000;
			int stiter = 100;
			int k = 1;
			double lamb = 0.01;
			SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(steps, stiter, k, lamb);
		
			double meanPrice = 0;
			double price = 0;
			long meanTime = 0;
			int iterations = 3;
			
			for (int i = 0; i < iterations; ++i) {
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
			System.out.println("Proportion: " + (proportion + 0.2*j));
			System.out.println("Initial price: " + azamonState.getPrice());
			System.out.println("Mean price: " + meanPrice/iterations);
			System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
			System.out.println("");
		}

		for(int j = 0; j < 10; ++j){
			AzamonState azamonState = new AzamonState(numPaq + 50*j, seedPaquetes, proportion, seedOfertas);
			
			azamonState.generateInitialStateC();
			
			HeuristicFunction f_heuristic = new AzamonHeuristicCost();
			
			Problem problem = new Problem(azamonState, new AzamonSuccessorSimulatedAnnealing(), new AzamonGoalTest(), f_heuristic);
			
			int steps = 5000;
			int stiter = 100;
			int k = 1;
			double lamb = 0.01;
			SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(steps * (j+1), stiter, k, lamb);
		
			double meanPrice = 0;
			double price = 0;
			long meanTime = 0;
			int iterations = 1;
			
			for (int i = 0; i < iterations; ++i) {
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

			System.out.println("Packages: " + (numPaq + 50*j));
			System.out.println("Initial price: " + azamonState.getPrice());
			System.out.println("Mean price: " + meanPrice/iterations);
			System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
			System.out.println("");
		}
	}

	private static void experiment6_annealing(){
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
		azamonState.generateInitialStateA();

		System.out.println("Initial price: " + azamonState.getPrice());
		System.out.println("Initial happiness: " + azamonState.getHappiness() + "\n");
		
		for(int l = 0; l < 15; ++l){
			System.out.println("###########################################");
			System.out.println("Epsilon happines: " + l);
			HeuristicFunction f_heuristic = new AzamonHeuristicHappiness(l);
			String[] operators = {"Move","Swap","Move & Swap"};
			
			for(int j = 0; j < 3; ++j){
				
				Problem problem = new Problem(azamonState, new AzamonSuccessorSimulatedAnnealing(), new AzamonGoalTest(), f_heuristic);
			
				int steps = 5000;
				int stiter = 100;
				int k = 1;
				double lamb = 0.01;
				SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(steps * (j+1), stiter, k, lamb);
			
				double meanPrice = 0;
				double price = 0;
				double meanHappiness = 0;
				double happiness = 0;
				long meanTime = 0;
				int iterations = 1;
				
				for (int i = 0; i < iterations; ++i) {
					try {
						long time = System.nanoTime();
						SearchAgent searchAgent = new SearchAgent(problem, simulatedAnnealingSearch);
						AzamonState state = (AzamonState) simulatedAnnealingSearch.getGoalState();		
						meanTime += (System.nanoTime() - time);
						price = state.getPrice();	
						meanPrice += price;
						happiness = state.getHappiness();
						meanHappiness += happiness;
						// List actions = searchAgent.getActions();
						// showActions(actions);
					
					} catch (Exception e) {
						e.printStackTrace();
					}		
				}
				System.out.println("Operator: " + operators[j]);
				System.out.println("Mean price: " + meanPrice/iterations);
				System.out.println("Mean happiness: " + meanHappiness/iterations);
				System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
				System.out.println("");
			}
		}
	}

	// auxiliars
	private static void showActions(List actions){
		for(int i = 0; i < actions.size(); ++i){
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}
}