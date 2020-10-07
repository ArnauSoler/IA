package aima.datastructures;

import java.util.ArrayList;
import java.util.List;

import aima.util.AbstractQueue;

public class FIFOQueue extends AbstractQueue {
	public void add(Object anItem) {
		super.addToFront(anItem);
	}

	public void add(List items) {
		List reversed = new ArrayList();
		for (int i = items.size() - 1; i > -1; i--) {
			reversed.add(items.get(i));
		}
		super.addToFront(reversed);
	}

	public Object remove() {
		return super.removeLast();
	}

	public Object get() {
		return super.getLast();
	}

}