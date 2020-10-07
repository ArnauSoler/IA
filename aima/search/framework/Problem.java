package aima.search.framework;

public class Problem {

	Object initialState;

	SuccessorFunction successorFunction;

	GoalTest goalTest;

	StepCostFunction stepCostFunction;

	HeuristicFunction heuristicFunction;

	public Problem(Object initialState, SuccessorFunction successorFunction,
			GoalTest goalTest) {

		this.initialState = initialState;
		this.successorFunction = successorFunction;
		this.goalTest = goalTest;
		this.stepCostFunction = new DefaultStepCostFunction();
		this.heuristicFunction = new DefaultHeuristicFunction();

	}

	public Problem(Object initialState, SuccessorFunction successorFunction,
			GoalTest goalTest, StepCostFunction stepCostFunction) {
		this(initialState, successorFunction, goalTest);
		this.stepCostFunction = stepCostFunction;
	}

	public Problem(Object initialState, SuccessorFunction successorFunction,
			GoalTest goalTest, HeuristicFunction heuristicFunction) {
		this(initialState, successorFunction, goalTest);
		this.heuristicFunction = heuristicFunction;
	}

	public Problem(Object initialState, SuccessorFunction successorFunction,
			GoalTest goalTest, StepCostFunction stepCostFunction,
			HeuristicFunction heuristicFunction) {
		this(initialState, successorFunction, goalTest, stepCostFunction);
		this.heuristicFunction = heuristicFunction;
	}

	public Object getInitialState() {

		return initialState;
	}

	public boolean isGoalState(Object state) {

		return goalTest.isGoalState(state);
	}

	public StepCostFunction getStepCostFunction() {
		return stepCostFunction;
	}

	public SuccessorFunction getSuccessorFunction() {
		return successorFunction;
	}

	public HeuristicFunction getHeuristicFunction() {
		// TODO Auto-generated method stub
		return heuristicFunction;
	}

}