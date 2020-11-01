package IA.Azamon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
	private static int seeds[] = {1234, 2345, 3456, 4567, 5678, 6789, 7890, 8901, 9012, 123};
	private static Scanner scan = new Scanner(System.in);
	private static BufferedWriter buffer;
	
	private static void Output(String file) {
        try {
        	buffer = new BufferedWriter(new FileWriter(new File("outputs/"+ file)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static boolean buffer_functions(String s, String action) {
		try {
			if (action == "write") {
				buffer.write(s);
				return true;
			}
			else if (action == "close") {
				buffer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println("###########################################");
		System.out.println("###	   Select an Experiment	        ###");
		System.out.println("###########################################");
		System.out.println("### 0.	Special Experiment 		###");
		System.out.println("### 1.	Experiment Number 1 		###");
		System.out.println("### 2.	Experiment Number 2 		###");
		System.out.println("### 3.	Experiment Number 3 		###");
		System.out.println("### 4.	Experiment Number 4 		###");
		System.out.println("### 6.	Experiment Number 6 		###");
		System.out.println("### 71.	Experiment Number 7.1 		###");
		System.out.println("### 72.	Experiment Number 7.2 		###");
		System.out.println("### 74.	Experiment Number 7.4 		###");
		System.out.println("### 76.	Experiment Number 7.6 		###");
		System.out.println("###########################################");

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
		case 6:
			experiment6();
			break;
		case 71:
			experiment1_annealing();
			break;
		case 72:
			experiment2_annealing();
			break;
		case 74:
			experiment4_annealing();
			break;
		case 76:
			experiment6_annealing();
			break;
		}
	}
	
	private static void SpecialExperiment(){
		
		Output("SpecialExperiment.txt");
		
		DecimalFormat numberFormat = new DecimalFormat("#0.0000");
		
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;

		int operators = 3;
		
		buffer_functions("\n······ Experiment Configuration ······ \n", "write");
		buffer_functions("- # Packages: " + numPaq + ", seed: " + seedPaquetes + "\n", "write");
		buffer_functions("- Proportion: " + proportion + ", seed: " + seedOfertas + "\n", "write");
		buffer_functions("- Operators: Move & Swap \n", "write");
		buffer_functions("······································" +"\n", "write");
		
		AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
		azamonState.generateInitialStateC();
		
		buffer_functions("\nInitial price: " + numberFormat.format(azamonState.getPrice()) + "\n", "write");
		buffer_functions("Initial happiness: " + azamonState.getHappiness() + "\n", "write");
		buffer_functions("\n", "write");

		System.out.println("Initial price: " + azamonState.getPrice());
		System.out.println("Initial happiness: " + azamonState.getHappiness() + "\n");

		HeuristicFunction f_heuristic = new AzamonHeuristicCost();
		
		Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(operators), new AzamonGoalTest(), f_heuristic);
		
		HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
		
		double meanPrice = 0;
		double price = 0;
		long meanTime = 0;
		int iterations = 10;
		
		buffer_functions("******* Results *********\n", "write");

		for (int i = 0; i < iterations; ++i) {
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

		buffer_functions("Mean Price: " + (double) Math.round(meanPrice) / iterations + "\n", "write");
		buffer_functions("Mean time: " + Math.round(meanTime/1000000)/iterations + " miliseconds", "write");
		
		System.out.println("Mean Price: " + (double) Math.round(meanPrice) / iterations);
		System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations + " miliseconds");
		
		buffer_functions(null, "close");
	}

	private static void experiment1(){
		
		Output("Experiment1_HC.txt");
		
		DecimalFormat numberFormat = new DecimalFormat("#0.0000");

		int numPaq = 100;
		double proportion = 1.2;
		double meanInitialPrice = 0;
		double meanInitialHappiness = 0;
		double totalMeanPrice[] = {0, 0, 0};
		long totalMeanTime[] = {0, 0, 0};
		String[] operators = {"Move","Swap","Move & Swap"};
		
		for(int i = 0; i < seeds.length; ++i){
			buffer_functions("\n······ Experiment Configuration ······ \n", "write");
			buffer_functions("- # Packages: " + numPaq + ", seed: " + seeds[i] + "\n", "write");
			buffer_functions("- Proportion: " + proportion + ", seed: " + seeds[i] + "\n", "write");
			buffer_functions("······································" +"\n", "write");
			

			AzamonState azamonState = new AzamonState(numPaq, seeds[i], proportion, seeds[i]);
			azamonState.generateInitialStateA();
			
			buffer_functions("\nInitial price: " + numberFormat.format(azamonState.getPrice()) + "\n", "write");
			meanInitialPrice += azamonState.getPrice();
			buffer_functions("Initial happiness: " + azamonState.getHappiness() + "\n", "write");
			meanInitialHappiness += azamonState.getHappiness();
			buffer_functions("\n", "write");
			
			// System.out.println("Initial price: " + azamonState.getPrice());
			// System.out.println("Initial happiness: " + azamonState.getHappiness() + "\n");
			
			HeuristicFunction f_heuristic = new AzamonHeuristicCost();
			
			for(int j = 0; j < 3; ++j){
				
				Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(j+1), new AzamonGoalTest(), f_heuristic);
				
				HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
			
				double meanPrice = 0;
				double price = 0;
				long meanTime = 0;
				int iterations = 10;
				
				for (int k = 0; k < iterations; ++k) {
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
				buffer_functions("\nOperator: " + operators[j] + "\n", "write");
				buffer_functions("******* Results *********\n", "write");
				buffer_functions("Mean Price: " + meanPrice/iterations + "\n", "write");
				totalMeanPrice[j] += meanPrice/iterations;
				buffer_functions("Mean time: " + Math.round(meanTime/1000000)/iterations + " miliseconds\n\n", "write");
				totalMeanTime[j] += Math.round(meanTime/1000000)/iterations;
				
				// System.out.println("Operator: " + operators[j]);
				// System.out.println("Mean price: " + meanPrice/iterations);
				// System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
				// System.out.println("");
			}
		}
		buffer_functions(null, "close");

		Output("Experiment1_HC_mean.txt");
		buffer_functions("\n······································" +"\n", "write");
		buffer_functions("······ Experiment Configuration ······ \n", "write");
		buffer_functions("- # Packages: " + numPaq + ", seeds: " + getSeeds() + "\n", "write");
		buffer_functions("- Proportion: " + proportion + ", seeds: " + getSeeds() + "\n", "write");
		buffer_functions("······································" +"\n", "write");
		buffer_functions("\nMean initial price: " + numberFormat.format(meanInitialPrice/seeds.length) + "\n", "write");
		buffer_functions("Mean initial happiness: " + meanInitialHappiness/seeds.length + "\n", "write");

		for(int i = 0; i < 3; ++i){
			buffer_functions("\nOperator: " + operators[i] + "\n", "write");
			buffer_functions("******* Results *********\n", "write");
			buffer_functions("Mean Price: " + totalMeanPrice[i]/seeds.length + "\n", "write");
			buffer_functions("Mean time: " + totalMeanTime[i]/seeds.length + " miliseconds\n\n", "write");
		}
		buffer_functions(null, "close");
	}

	private static void experiment2(){
		
		Output("Experiment2_HC.txt");
		
		DecimalFormat numberFormat = new DecimalFormat("#0.0000");
		
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		double meanInitialPrice = 0;
		double meanInitialHappiness = 0;
		double totalMeanPrice[] = {0, 0, 0};
		long totalMeanTime[] = {0, 0, 0};
		String[] states = {"A","B","C"};
		
		for(int i = 0; i < seeds.length; ++i){
			buffer_functions("\n······ Experiment Configuration ······ \n", "write");
			buffer_functions("- # Packages: " + numPaq + ", seed: " + seeds[i] + "\n", "write");
			buffer_functions("- Proportion: " + proportion + ", seed: " + seeds[i] + "\n", "write");
			buffer_functions("······································" +"\n", "write");
			
			for(int j = 0; j < 3; ++j){
				AzamonState azamonState = new AzamonState(numPaq, seeds[i], proportion, seeds[i]);
				
				if(j == 0) azamonState.generateInitialStateA();
				else if(j == 1) azamonState.generateInitialStateB();
				else azamonState.generateInitialStateC();
				
				buffer_functions("\nInitial State: " + states[j] + "\n", "write");
				buffer_functions("Initial price: " + numberFormat.format(azamonState.getPrice()) + "\n", "write");
				meanInitialPrice += azamonState.getPrice();
				buffer_functions("Initial happiness: " + azamonState.getHappiness() + "\n", "write");	
				meanInitialHappiness += azamonState.getHappiness();

				// System.out.println("Initial State: " + states[j]);
				// System.out.println("Initial price: " + azamonState.getPrice());
				// System.out.println("Initial happiness: " + azamonState.getHappiness());

				HeuristicFunction f_heuristic = new AzamonHeuristicCost();
				
				Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(3), new AzamonGoalTest(), f_heuristic);
				
				HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
			
				double meanPrice = 0;
				double price = 0;
				long meanTime = 0;
				int iterations = 10;
				
				buffer_functions("\n******* Results *********\n", "write");
				
				for (int k = 0; k < iterations; ++k) {
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
				buffer_functions("Mean Price: " + meanPrice/iterations + "\n", "write");
				totalMeanPrice[j] += meanPrice/iterations;
				buffer_functions("Mean time: " + Math.round(meanTime/1000000)/iterations + " miliseconds\n", "write");
				totalMeanTime[j] += Math.round(meanTime/1000000)/iterations;
				// System.out.println("Mean price: " + meanPrice/iterations);
				// System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations + "\n");
			}
		}
		buffer_functions(null, "close");

		Output("Experiment2_HC_mean.txt");
		buffer_functions("\n······································", "write");
		buffer_functions("\n······ Experiment Configuration ······ \n", "write");
		buffer_functions("- # Packages: " + numPaq + ", seed: " + getSeeds() + "\n", "write");
		buffer_functions("- Proportion: " + proportion + ", seed: " + getSeeds() + "\n", "write");
		buffer_functions("······································" +"\n", "write");
		
		for(int i = 0; i < 3; ++i){
			buffer_functions("\nInitial States: " + states[i] + "\n", "write");
			buffer_functions("******* Results *********\n", "write");
			buffer_functions("Mean Price: " + totalMeanPrice[i]/seeds.length + "\n", "write");
			buffer_functions("Mean time: " + totalMeanTime[i]/seeds.length + " miliseconds\n\n", "write");
		}
		buffer_functions(null, "close");
	}

	private static void experiment3(){
		
		Output("Experiment3_SA.txt");
		
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		buffer_functions("\n······ Experiment Configuration ······ \n", "write");
		buffer_functions("- # Packages: " + numPaq + ", seed: " + seedPaquetes + "\n", "write");
		buffer_functions("- Proportion: " + proportion + ", seed: " + seedOfertas + "\n", "write");
		buffer_functions("······································\n" +"\n", "write");

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
						buffer_functions("Steps: " + steps * j + " \t|\tStiter: " + stiter * j * w + " \t|\tK: " + k + " \t|\tLamb: " + numberFormat.format(lamb), "write");
						buffer_functions("\t|\tMean price: " + numberFormat.format(meanPrice / 10), "write");
						buffer_functions("\t|\tMean time: " + Math.round(meanTime/1000000) + " miliseconds\n", "write");
						
						System.out.print("Steps: " + steps * j + " \t|\tStiter: " + stiter * j * w + " \t|\tK: " + k + " \t|\tLamb: " + numberFormat.format(lamb));
						System.out.print("\t|\tMean price: " + numberFormat.format(meanPrice / 10));
						System.out.println("\t|\tMean time: " + Math.round(meanTime/1000000) + "\n");
					}
				}
			}
		}
		buffer_functions(null, "close");
	}

	private static void experiment4(){
		
		Output("Experiment4_HC.txt");
		
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		buffer_functions("\n······ Experiment Configuration ······ \n", "write");
		buffer_functions("- # Packages: " + numPaq + ", seeds: " + getSeeds() + "\n", "write");
		buffer_functions("- Proportion: " + proportion + ", seeds: " + getSeeds() + "\n", "write");
		buffer_functions("······································\n" +"\n", "write");
		
		for(int j = 0; j < 10; ++j){
			double meanInitialPrice = 0;
			double meanPrice = 0;
			long meanTime = 0;

			for (int i = 0; i < seeds.length; ++i) {
				AzamonState azamonState = new AzamonState(numPaq, seeds[i], proportion + 0.2*j, seeds[i]);
				azamonState.generateInitialStateC();
				meanInitialPrice += azamonState.getPrice();

				HeuristicFunction f_heuristic = new AzamonHeuristicCost();
				
				Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(3), new AzamonGoalTest(), f_heuristic);
				
				HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
			
				double price = 0;

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
			buffer_functions("\n******* Results *********\n", "write");
			buffer_functions("Proportion: " + (proportion + 0.2*j) + "\n", "write");
			buffer_functions("Mean initial price: " + meanInitialPrice/seeds.length + "\n", "write");
			buffer_functions("Mean price: " + meanPrice/seeds.length + "\n", "write");
			buffer_functions("Mean time: " + Math.round(meanTime/1000000)/seeds.length + " miliseconds\n", "write");
		}

		for(int j = 0; j < 5; ++j){
			double meanInitialPrice = 0;
			double meanPrice = 0;
			long meanTime = 0;

			int iterations = seeds.length;
			for (int i = 0; i < seeds.length; ++i) {
				AzamonState azamonState = new AzamonState(numPaq + 50*j, seeds[i], proportion, seeds[i]);
				azamonState.generateInitialStateC();
				meanInitialPrice += azamonState.getPrice();
				
				HeuristicFunction f_heuristic = new AzamonHeuristicCost();
				
				Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(3), new AzamonGoalTest(), f_heuristic);
				
				HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
			
				double price = 0;
				
				
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
			
			buffer_functions("\n******* Results *********\n", "write");
			buffer_functions("Packages: " + (numPaq + 50*j) + "\n", "write");
			buffer_functions("Initial price: " + meanInitialPrice/iterations + "\n", "write");
			buffer_functions("Mean price: " + meanPrice/iterations + "\n", "write");
			buffer_functions("Mean time: " + Math.round(meanTime/1000000)/iterations + " miliseconds\n", "write");

			System.out.println("Packages: " + (numPaq + 50*j));
			// System.out.println("Initial price: " + azamonState.getPrice());
			// System.out.println("Mean price: " + meanPrice/iterations);
			// System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
			// System.out.println("");
		}
		
		buffer_functions(null, "close");
	}

	private static void experiment6(){
		
		Output("Experiment6_HC.txt");
		
		DecimalFormat numberFormat = new DecimalFormat("#0.0000");
		
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		buffer_functions("\n······ Experiment Configuration ······ \n", "write");
		buffer_functions("- # Packages: " + numPaq + ", seed: " + seedPaquetes + "\n", "write");
		buffer_functions("- Proportion: " + proportion + ", seed: " + seedOfertas + "\n", "write");
		buffer_functions("······································\n" +"\n", "write");
		
		AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
		azamonState.generateInitialStateA();
		
		buffer_functions("Initial price: " + numberFormat.format(azamonState.getPrice()) + "\n", "write");
		buffer_functions("Initial happiness: " + azamonState.getHappiness() + "\n", "write");	
		
		System.out.println("Initial price: " + azamonState.getPrice());
		System.out.println("Initial happiness: " + azamonState.getHappiness() + "\n");
		
		for(int k = 0; k < 15; ++k){
			System.out.println("###########################################");
			System.out.println("Epsilon happiness: " + k);
			buffer_functions("\n----------------------\n", "write");
			buffer_functions("Epsilon happiness: " + k + "\n", "write");
			buffer_functions("----------------------\n", "write");
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
				
				buffer_functions("\nOperator: " + operators[j] + "\n", "write");
				
				buffer_functions("\n******* Results *********\n", "write");

				buffer_functions("Mean Price: " + meanPrice/iterations + "\n", "write");
				buffer_functions("Mean happiness: " + meanHappiness/iterations + "\n", "write");
				buffer_functions("Mean time: " + Math.round(meanTime/1000000)/iterations + " miliseconds\n", "write");
				
				System.out.println("Operator: " + operators[j]);
				System.out.println("Mean price: " + meanPrice/iterations);
				System.out.println("Mean happiness: " + meanHappiness/iterations);
				System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
				System.out.println("");
			}
		}
		buffer_functions(null, "close");

	}

	private static void experiment1_annealing(){
		
		Output("Experiment1_SA.txt");
		
		
		DecimalFormat numberFormat = new DecimalFormat("#0.0000");

		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		buffer_functions("\n······ Experiment Configuration ······ \n", "write");
		buffer_functions("- # Packages: " + numPaq + ", seed: " + seedPaquetes + "\n", "write");
		buffer_functions("- Proportion: " + proportion + ", seed: " + seedOfertas + "\n", "write");
		buffer_functions("······································" +"\n", "write");
		
		AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
		azamonState.generateInitialStateA();
		
		buffer_functions("\nInitial price: " + numberFormat.format(azamonState.getPrice()) + "\n", "write");
		buffer_functions("Initial happiness: " + azamonState.getHappiness() + "\n", "write");
		
		System.out.println("Initial price: " + azamonState.getPrice());
		System.out.println("Initial happiness: " + azamonState.getHappiness());
		
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
			buffer_functions("\nOperator: " + operators[j] + "\n", "write");
			
			buffer_functions("\n******* Results *********\n", "write");
			
			buffer_functions("Mean Price: " + meanPrice/iterations + "\n", "write");
			buffer_functions("Mean time: " + Math.round(meanTime/1000000)/iterations + " miliseconds\n", "write");
			
			System.out.println("Operator: " + operators[j]);
			System.out.println("Mean price: " + meanPrice/iterations);
			System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
			System.out.println("");
		}
		
		buffer_functions(null, "close");
	}

	private static void experiment2_annealing(){
		
		Output("Experiment2_SA.txt");
		
		DecimalFormat numberFormat = new DecimalFormat("#0.0000");
		
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		String[] states = {"A","B","C"};
		
		buffer_functions("\n······ Experiment Configuration ······ \n", "write");
		buffer_functions("- # Packages: " + numPaq + ", seed: " + seedPaquetes + "\n", "write");
		buffer_functions("- Proportion: " + proportion + ", seed: " + seedOfertas + "\n", "write");
		buffer_functions("······································" +"\n", "write");
		
		for(int j = 0; j < 3; ++j){
			AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
			
			if(j == 0) azamonState.generateInitialStateA();
			else if(j == 1) azamonState.generateInitialStateB();
			else azamonState.generateInitialStateC();
			
			buffer_functions("\nInitial State: " + states[j] + "\n", "write");
			buffer_functions("Initial price: " + numberFormat.format(azamonState.getPrice()) + "\n", "write");
			buffer_functions("Initial happiness: " + azamonState.getHappiness() + "\n", "write");	
			
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
			int iterations = 10;
			
			buffer_functions("\n******* Results *********\n", "write");
			
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
			buffer_functions("Mean Price: " + meanPrice/iterations + "\n", "write");
			buffer_functions("Mean time: " + Math.round(meanTime/1000000)/iterations + " miliseconds\n", "write");
			
			System.out.println("Mean price: " + meanPrice/10);
			System.out.println("Mean time: " + Math.round(meanTime/1000000)/10 + "\n");
		}
		
		buffer_functions(null, "close");
	}

	private static void experiment4_annealing(){
		
		Output("Experiment4_SA.txt");
		
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		buffer_functions("\n······ Experiment Configuration ······ \n", "write");
		buffer_functions("- # Packages: " + numPaq + ", seed: " + seedPaquetes + "\n", "write");
		buffer_functions("- Proportion: " + proportion + ", seed: " + seedOfertas + "\n", "write");
		buffer_functions("······································\n" +"\n", "write");
		
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
			
			buffer_functions("\n******* Results *********\n", "write");
			
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
			buffer_functions("Proportion: " + (proportion + 0.2*j) + "\n", "write");
			buffer_functions("Initial price: " + azamonState.getPrice() + "\n", "write");
			buffer_functions("Mean price: " + meanPrice/iterations + "\n", "write");
			buffer_functions("Mean time: " + Math.round(meanTime/1000000)/iterations + " miliseconds\n", "write");
			
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
			
			buffer_functions("\n******* Results *********\n", "write");
			
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
			
			buffer_functions("Packages: " + (numPaq + 50*j) + "\n", "write");
			buffer_functions("Initial price: " + azamonState.getPrice() + "\n", "write");
			buffer_functions("Mean price: " + meanPrice/iterations + "\n", "write");
			buffer_functions("Mean time: " + Math.round(meanTime/1000000)/iterations + " miliseconds\n", "write");
			
			System.out.println("Packages: " + (numPaq + 50*j));
			System.out.println("Initial price: " + azamonState.getPrice());
			System.out.println("Mean price: " + meanPrice/iterations);
			System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
			System.out.println("");
		}
		
		buffer_functions(null, "close");
	}

	private static void experiment6_annealing(){
		
		Output("Experiment6_SA.txt");
		
		DecimalFormat numberFormat = new DecimalFormat("#0.0000");
		
		int numPaq = 100;
		int seedPaquetes = 1234;
		double proportion = 1.2;
		int seedOfertas = 1234;
		
		buffer_functions("\n······ Experiment Configuration ······ \n", "write");
		buffer_functions("- # Packages: " + numPaq + ", seed: " + seedPaquetes + "\n", "write");
		buffer_functions("- Proportion: " + proportion + ", seed: " + seedOfertas + "\n", "write");
		buffer_functions("······································\n" +"\n", "write");
		
		AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
		azamonState.generateInitialStateA();

		buffer_functions("Initial price: " + numberFormat.format(azamonState.getPrice()) + "\n", "write");
		buffer_functions("Initial happiness: " + azamonState.getHappiness() + "\n", "write");	
		
		System.out.println("Initial price: " + azamonState.getPrice());
		System.out.println("Initial happiness: " + azamonState.getHappiness() + "\n");
		
		for(int l = 0; l < 15; ++l){
			System.out.println("###########################################");
			System.out.println("Epsilon happines: " + l);
			buffer_functions("\n----------------------\n", "write");
			buffer_functions("Epsilon happiness: " + l + "\n", "write");
			buffer_functions("----------------------\n", "write");
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
				
				buffer_functions("\nOperator: " + operators[j] + "\n", "write");
				
				buffer_functions("\n******* Results *********\n", "write");

				buffer_functions("Mean Price: " + meanPrice/iterations + "\n", "write");
				buffer_functions("Mean happiness: " + meanHappiness/iterations + "\n", "write");
				buffer_functions("Mean time: " + Math.round(meanTime/1000000)/iterations + " miliseconds\n", "write");
				
				System.out.println("Operator: " + operators[j]);
				System.out.println("Mean price: " + meanPrice/iterations);
				System.out.println("Mean happiness: " + meanHappiness/iterations);
				System.out.println("Mean time: " + Math.round(meanTime/1000000)/iterations);
				System.out.println("");
			}
		}
		
		buffer_functions(null, "close");
	}

	// auxiliars
	private static void showActions(List actions){
		for(int i = 0; i < actions.size(); ++i){
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}

	private static String getSeeds(){
		StringBuffer S = new StringBuffer();
		for(int i = 0; i < seeds.length; ++i){
			S.append(seeds[i] + ", ");
		}
		return S.toString();
	}
}