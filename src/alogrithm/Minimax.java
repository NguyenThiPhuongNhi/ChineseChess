package alogrithm;

import java.util.ArrayList;
import java.util.Map;

import chess.Board;
import chess.Piece;
import chess.Rules;
import control.GameController;


public class Minimax {
	// private static int DEPTH = 2;// độ sâu cần xét???????????
	private Board board;// bàn cờ
	private GameController controller = new GameController();// bản điều
																	// khiển

	public Node search(Board board, int depth) {// có 16quan cờ+16 quân cờ=32
		this.board = board;
		// if (board.pieces.size() < 28)// đếm số quân cờ còn có trên bàn cờ là bao
		// 	DEPTH = 3;
		// if (board.pieces.size() < 16)
		// 	DEPTH = 4;
		// if (board.pieces.size() < 6)
		// 	DEPTH = 5;
		// if (board.pieces.size() < 4)
		// 	DEPTH = 6;
		long startTime = System.currentTimeMillis();
		Node best = null;
		ArrayList<Node> moves = generateMovesForAll(true);

		for (Node n : moves) {
			Piece eaten = board.updatePiece(n.piece, n.to); // cập nhật lại bàn cờ, để xem vị trí mới của quân
			// n.value = minimax(DEPTH, n, board, false);
			n.value = minimax(depth, n, board, false);
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

	private ArrayList<Node> generateMovesForAll(boolean isMax) {
		ArrayList<Node> moves = new ArrayList<Node>();
		for (Map.Entry<String, Piece> stringPieceEntry : board.pieces.entrySet()) {
			Piece piece = stringPieceEntry.getValue();
			if (isMax && piece.color == 'r')
				continue;// đúng và đỏ thì tiếp tục
			if (!isMax && piece.color == 'b')
				continue;// sai và đen thì tiếp tục
			for (int[] nxt : Rules.getNextMove(piece.key, piece.position, board))
				// lấy [] lưu vi trí mới của quân cờ
				moves.add(new Node(piece.key, piece.position, nxt));
			// add từng quaan cờ đó vào, lưu tên, vị trí cũ, vị trí mới
		}
		// for (int i = 0; i < moves.size(); i++) {
		// System.out.println(moves.get(i).toString());
		//
		// }
		return moves;// tạo được mảng luu được vị trí các quân cờ có(vị trí cũ,
						// vị trí mới)
	}
	public int minimax(int depth, Node state, Board board, boolean isplayer) {
		int result = 0;
		ArrayList<Node> moves = generateMovesForAll(isplayer);

		if (depth == 0 || controller.hasWin(board) != 'x') // đỏ chưa thắng và game chưa hòa
			return new EvalModel().eval(board, 'b');

		if (depth == 2) {
			if (isplayer == true) { // max /r
				// ArrayList<Node> moves = generateMovesForAll();
				int temp = -99999999;
				for (Node n : moves) {
					int value = minimax(depth - 1, n, board, false);
					if (value > temp) {
						temp = value;
//						result = temp;
					}
					return temp;
				}
				return temp;
			} else if (isplayer == false) { // min /b
				// ArrayList<Node> moves = generateMovesForAll();
				int temp = 99999999;
				for (Node n : moves) {
					int value = minimax(depth - 1, n, board, true);
					if (value < temp) {
						temp = value;
//						result = temp;
					}
					return temp;
				}
				return temp;
			}
		} 
		else {
			for (Node n : moves) {
				 Piece eaten = board.updatePiece(n.piece, n.to);
			if (isplayer)
				result = Math.max(result, minimax(depth - 1, n, board, false));
			else
				result = Math.min(result, minimax(depth - 1, n, board, true));
		
			 board.updatePiece(n.piece, n.from);
             if (eaten != null) {
                 board.pieces.put(eaten.key, eaten);
                 board.backPiece(eaten.key);
             }	
			}
			// System.out.println(result +"oiug");
		}
		return result;
	}

//	public int minimax(int depth, Node state, Board board, boolean isMax) {
//		int result = 0;
//
//		if (depth == 0 || controller.hasWin(board) != 'x') // đỏ chưa thắng và game chưa hòa
//			return new EvalModel().eval(board, 'b');
//
//		// nếu chưa có người thắng thì
//		ArrayList<Node> moves = generateMovesForAll(isMax);
//		// tạo ra mảng lưu
//		// vị trí nước đi của từng quân cờ?????????????????????????????
//
//		synchronized (this) {
//			for (final Node n : moves) {
//				Piece eaten = board.updatePiece(n.piece, n.to);
//				// cập nhật lại bàn cờ,
//				// để xem được vị trí các quân cờ ở tọa độ mới (update vị trí)
//				/* Is maximizing player? */
//
//				// hoặc beta
//				if (depth == 2) {
//					// nếu độ sâu =2
//					// kiểm tra tiếp trường hợp isMAX
//					if (isMax == true) {
//						int temp = -99999999;
//						int value = minimax(depth - 1, n, board, false);
//						if (value > temp) {
//							temp = value;
//						}
//						return temp;
//
//					} else if (isMax == false) { // min /b
//						int temp = 99999999;
//						int value = minimax(depth - 1, n, board, true);
//						if (value < temp) {
//							temp = value;
//						}
//						return temp;
//
//					}
//				}
//				// nếu độ sâu khác 2
//				else {
//					if (isMax)
//						result = Math.max(result, minimax(depth - 1, state, board, false));
//					else
//						result = Math.min(result, minimax(depth - 1, n, board, true));
//				}
//
//				board.updatePiece(n.piece, n.from);
//				if (eaten != null) {// nếu điểm đến null chưa có ai thì có thể
//									// đi đến
//					board.pieces.put(eaten.key, eaten);// add quân cờ đó vào
//					board.backPiece(eaten.key);// ???????????????????????
//					// kiểm tra lại vị trí trên bàn cờ có phải là quân mới đến
//					// vị trí mới ko
//				}
//
//			}
//		}
//		return result;
//	}

}