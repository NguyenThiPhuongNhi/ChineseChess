package alogrithm;

import java.util.ArrayList;
import java.util.Map;

import chess.Board;
import chess.Piece;
import chess.Rules;
import control.GameController;

public class Minimax {
	private static int DEPTH = 2;
	private Board board;
	private GameController controller = new GameController();

	public Node search(Board board) {
		this.board = board;
		if (board.pieces.size() < 28)
			DEPTH = 3;
		if (board.pieces.size() < 16)
			DEPTH = 4;
		if (board.pieces.size() < 6)
			DEPTH = 5;
		if (board.pieces.size() < 4)
			DEPTH = 6;

		long startTime = System.currentTimeMillis();
		Node best = null;
		ArrayList<Node> moves = generateMovesForAll(true);

		for (Node n : moves) {
			Piece eaten = board.updatePiece(n.piece, n.to); // cập nhật lại bàn cờ, để xem vị trí mới của quân
			n.value = minimax(DEPTH, n, board, false);
			if (best == null || n.value >= best.value)
				best = n;
			/* Back move */
			board.updatePiece(n.piece, n.from);
			if (eaten != null) {
				board.pieces.put(eaten.key, eaten);
				board.backPiece(eaten.key);
			}
		}
		long finishTime = System.currentTimeMillis();
		System.out.println(finishTime - startTime);
		return best;

	}

	private ArrayList<Node> generateMovesForAll(boolean isplayer) {
		ArrayList<Node> moves = new ArrayList<Node>();
		for (Map.Entry<String, Piece> stringPieceEntry : board.pieces.entrySet()) {
			Piece piece = stringPieceEntry.getValue();
			if (isplayer && piece.color == 'r') continue;
            if (!isplayer && piece.color == 'b') continue;
			for (int[] nxt : Rules.getNextMove(piece.key, piece.position, board))
				moves.add(new Node(piece.key, piece.position, nxt));
		}
		return moves;
	}

	public int minimax(int depth, Node state, Board board, boolean isplayer) {
		int result = 0;
		ArrayList<Node> moves = generateMovesForAll(isplayer);
		if (depth == 0 || controller.hasWin(board) != 'x') { // đỏ chưa thắng và game chưa hòa
			return new EvalModel().eval(board, 'b');
		}
		else if (isplayer == true) { // max /r
//			ArrayList<Node> moves = generateMovesForAll();
			int temp = -99999999;
			for (Node n : moves) {
				int value = minimax(depth - 1, n, board, false);
				if (value > temp) {
					temp = value;
					result = temp;
				}
			}
			return temp;
		}
		else if (isplayer == false) { // min /b
//			ArrayList<Node> moves = generateMovesForAll();
			int temp = 99999999;
			for (Node n : moves) {
				int value = minimax(depth - 1, n, board, true);
				if (value < temp) {
					temp = value;
					result = temp;
				}
			}
			return temp;
		}
		return result;
	}
}
