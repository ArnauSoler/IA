package IA.Azamon;

import java.util.*;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import IA.Azamon.AzamonState;


public class Create {
	private static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.println("Select the algorithm");
		System.out.println("1.-Hill Climbing");
		System.out.println("2.-Simulated Annealing");
		System.out.println("3.-Exit");
		switch(scan.nextInt()){
		    case 1:
		    HillClimbingAlgorithm();
			break;
		    case 2:
			SimulatedAnnealingAlgorithm();
			break;
		    case 3:
			scan.close();
			return;
		    default:
		    main(args);
		}
	}
	
	private static int SelectGenerator(){
		System.out.println("Select the generator");
		System.out.println("1.- Generator A");
		System.out.println("2.- Generator B");
		System.out.println("3.- Generator C");
		int response = scan.nextInt();
		return response;
	    }
	
	private static AzamonState CreateState(){
		System.out.println("Enter the number of packages:");
		int numPaq = scan.nextInt();
		System.out.println("Enter a seed to generate the packages:");
		int seedPaquetes = scan.nextInt();
		System.out.println("Enter a proportion for transports:");
		double proportion = scan.nextDouble();
		System.out.println("Enter a seed to generate the transports:");
		int seedOfertas = scan.nextInt();
		AzamonState azamonState = new AzamonState(numPaq, seedPaquetes, proportion, seedOfertas);
		int Generator = SelectGenerator();
		
		switch (Generator) {
		    case 1:
		    	azamonState.generateInitialStateA();
			break;
		    case 2:
		    	azamonState.generateInitialStateB();
			break;
		    case 3:
		    	azamonState.generateInitialStateC();
			break;
		}
		return azamonState;
	   }

	private static int selectHeuristic(){
		System.out.println("Heuristic Selection");
		System.out.println("1.- Minimize transportation and storage cost.");
		System.out.println("2.- Maximize the happiness of customers.");
		int response = scan.nextInt();
		return response;
	}

	private static void HillClimbingAlgorithm(){
		System.out.println("Azamon - Hill Climbing Algorithm Selected");
		AzamonState azamonState = CreateState();
		try{
			int heuristic = selectHeuristic();
			heuristic = (heuristic == 1)?1:2;
			azamonState.setSelectedHeuristic(heuristic);

			HeuristicFunction f_heuristic = (heuristic == 1)? new AzamonHeuristicCost(): new AzamonHeuristicHappiness();
			
			Problem problem = new Problem(azamonState, new AzamonSuccessorHillClimbing(), new AzamonGoalTest(), f_heuristic);
			
			HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
			
			execute(problem, hillClimbingSearch);
		
		} catch(Exception e){
		    	System.out.println(e.getMessage());
		}
	}

	private static void SimulatedAnnealingAlgorithm(){
		System.out.println("Azamon - Simulated Annealing Algorithm Selected");
		AzamonState azamonState = CreateState();
		try{
			int heuristic = selectHeuristic();
			heuristic = (heuristic == 1)?1:2;
			azamonState.setSelectedHeuristic(heuristic);

			HeuristicFunction f_heuristic = (heuristic == 1)? new AzamonHeuristicCost(): new AzamonHeuristicHappiness();
			
			Problem problem = new Problem(azamonState, new AzamonSuccessorSimulatedAnnealing(), new AzamonGoalTest(), f_heuristic);
			
			SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch();
			
			execute(problem, simulatedAnnealingSearch);
		
		} catch(Exception e){
		    	System.out.println(e.getMessage());
		}
	}
}