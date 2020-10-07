package aima.basic.vaccum;

import java.util.Hashtable;
import java.util.Random;

import aima.basic.Agent;
import aima.basic.Environment;
import aima.basic.Percept;

public class TrivialVaccumEnvironment extends Environment {

	String location1, location2;

	String defaultLocation;

	Hashtable status;

	public TrivialVaccumEnvironment() {
		super();
		location1 = "A";
		location2 = "B";
		defaultLocation = location1;
		status = new Hashtable();
		Random r = new Random();
		int i = r.nextInt(2);
		int j = r.nextInt(2);
		if (i == 0) {
			status.put(location1, "Clean");

		} else {
			status.put(location1, "Dirty");
		}

		if (j == 0) {
			status.put(location2, "Clean");

		} else {
			status.put(location2, "Dirty");
		}
	}

	public TrivialVaccumEnvironment(String loc1Status, String loc2Status) {
		super();
		location1 = "A";
		location2 = "B";
		defaultLocation = location1;
		status = new Hashtable();
		status.put(location1, loc1Status);
		status.put(location2, loc2Status);

	}

	public void executeAction(Agent a, String agentAction) {

		if (agentAction.equals("Right")) {
			setAgentLocation(a, location2);
			setAgentPerformance(a, getAgentperformance(a) - 1);
		} else if (agentAction.equals("Left")) {
			setAgentLocation(a, location1);
			setAgentPerformance(a, getAgentperformance(a) - 1);
		} else if (agentAction.equals("Suck")) {
			if (getLocationStatus(getAgentLocation(a)).equals("Dirty")) {
				setLocationStatus(getAgentLocation(a), "Clean");
				setAgentPerformance(a, getAgentperformance(a) + 10);
			}

		} else if (agentAction.equals("NoOP")) {
			a.die();

		}
	}

	public Percept getPerceptSeenBy(Agent anAgent) {
		Percept retval = new Percept();
		retval.setAttribute("location", anAgent.getAttribute("location"));
		retval.setAttribute("status", status.get(anAgent
				.getAttribute("location")));
		return retval;

	}

	public void addAgent(Agent a) {
		addAgent(a, defaultLocation);

	}

	public void addAgent(Agent a, String location) {
		setAgentLocation(a, location);
		// agents.add(a);
		super.addAgent(a);
		setAgentPerformance(a, 0);
	}

	public String getLocation1Status() {
		return (String) status.get(location1);
	}

	public String getLocation2Status() {
		return (String) status.get(location2);
	}

	public String getLocationStatus(String location) {
		return (String) status.get(location);
	}

	public Hashtable getStatus() {
		return status;
	}

	private void setAgentLocation(Agent a, String location) {
		a.setAttribute("location", location);
	}

	public String getAgentLocation(Agent a) {
		return (String) a.getAttribute("location");
	}

	private void setAgentPerformance(Agent a, int i) {
		a.setAttribute("performance", new Integer(i));
	}

	public int getAgentperformance(Agent a) {
		Integer i = (Integer) a.getAttribute("performance");
		return i.intValue();
	}

	private void setLocationStatus(String aLocation, String aStatus) {
		status.put(aLocation, aStatus);
	}
}