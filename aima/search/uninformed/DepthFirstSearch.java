package aima.search.uninformed;

import java.util.List;

import aima.search.framework.Metrics;
import aima.search.framework.Problem;
import aima.search.framework.QueueSearch;
import aima.search.framework.Search;
import aima.search.nodestore.LIFONodeStore;

public class DepthFirstSearch implements Search {

	QueueSearch search;

	public DepthFirstSearch(QueueSearch search) {

		this.search = search;

	}

	public List search(Problem p) {

		return search.search(p, new LIFONodeStore());
	}

	public Metrics getMetrics() {
		return search.getMetrics();
	}
        public Object getGoalState(){
            return(null);
        };

    public List getPathStates() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}