package aima.search.nqueens;

import java.util.ArrayList;
import java.util.List;

import aima.basic.XYLocation;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class NQueensSuccessorFunction implements SuccessorFunction {

	public List getSuccessors(Object state) {

		List successors = new ArrayList();
		NQueensBoard board = (NQueensBoard) state;
		int numQueens = board.getNumberOfQueensOnBoard();
		int boardSize = board.getSize();
		for (int i = 0; i < boardSize; i++) {
			if (!(board.isSquareUnderAttack(new XYLocation(numQueens, i)))) {
				NQueensBoard child = placeQueenAt(numQueens, i, board);

				successors.add(new Successor("placeQueenAt " + numQueens + "  "
						+ i, child));

			}

		}

		return successors;
	}

	private NQueensBoard placeQueenAt(int row, int column,
			NQueensBoard parentBoard) {

		NQueensBoard newBoard = new NQueensBoard(parentBoard.getSize());
		ArrayList queenPositionsOnParentBoard = parentBoard.getQueenPositions();
		queenPositionsOnParentBoard.add(new XYLocation(row, column));
		newBoard.setBoard(queenPositionsOnParentBoard);
		return newBoard;
	}

}