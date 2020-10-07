/*
 * Created on Sep 12, 2004
 *
 */
package aima.search.informed;

import java.util.Comparator;

import aima.search.framework.Node;
import aima.search.framework.PrioritySearch;
import aima.search.framework.Problem;
import aima.search.framework.QueueSearch;
import java.util.List;

/**
 * @author Ravi Mohan
 *  
 */
public class GreedyBestFirstSearch extends PrioritySearch {

	public GreedyBestFirstSearch(QueueSearch search) {
		this.search = search;
	}

	protected Comparator getComparator(Problem p) {
		return new NodeComparator(p);
	}

    public List getPathStates() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	class NodeComparator implements Comparator {
		private Problem problem;

		NodeComparator(Problem problem) {
			this.problem = problem;
		}

		public int compare(Object aNode, Object anotherNode) {
			Node one = (Node) aNode;
			Node two = (Node) anotherNode;

			double h1 = problem.getHeuristicFunction().getHeuristicValue(
					one.getState());
			double h2 = problem.getHeuristicFunction().getHeuristicValue(
					two.getState());
			if (h1 == h2) {
				return 0;
			} else if (h1 < h2) {
				return -1;
			} else {
				return +1;
			}
		}

	}
                public Object getGoalState(){
            return(null);
        };

}