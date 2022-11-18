package controller;

import java.util.HashMap;
import java.util.Map;

import model.Board;
import model.Piece;

public class GameController {
	private Map<String, Piece> initPieces() {
		Map<String, Piece> pieces = new HashMap<String, Piece>();// lưu vị trí các con cờ lên bàn cờ
		// bàn cờ là ma trận lưu dạng MAP, position là tọa độ trên bàn cờ của quân cờ đó
		pieces.put("bj0", new Piece("bj0", new int[] { 0, 0 }));// xe thứ nhất màu đen
		pieces.put("bm0", new Piece("bm0", new int[] { 0, 1 }));
		pieces.put("bx0", new Piece("bx0", new int[] { 0, 2 }));
		pieces.put("bs0", new Piece("bs0", new int[] { 0, 3 }));
		pieces.put("bb0", new Piece("bb0", new int[] { 0, 4 }));// vua
		pieces.put("bs1", new Piece("bs1", new int[] { 0, 5 }));
		pieces.put("bx1", new Piece("bx1", new int[] { 0, 6 }));
		pieces.put("bm1", new Piece("bm1", new int[] { 0, 7 }));
		pieces.put("bj1", new Piece("bj1", new int[] { 0, 8 }));// xe thứ hai màu đen
		pieces.put("bp0", new Piece("bp0", new int[] { 2, 1 }));
		pieces.put("bp1", new Piece("bp1", new int[] { 2, 7 }));
		pieces.put("bz0", new Piece("bz0", new int[] { 3, 0 }));
		pieces.put("bz1", new Piece("bz1", new int[] { 3, 2 }));
		pieces.put("bz2", new Piece("bz2", new int[] { 3, 4 }));
		pieces.put("bz3", new Piece("bz3", new int[] { 3, 6 }));
		pieces.put("bz4", new Piece("bz4", new int[] { 3, 8 }));

		pieces.put("rj0", new Piece("rj0", new int[] { 9, 0 }));
		pieces.put("rm0", new Piece("rm0", new int[] { 9, 1 }));
		pieces.put("rx0", new Piece("rx0", new int[] { 9, 2 }));
		pieces.put("rs0", new Piece("rs0", new int[] { 9, 3 }));
		pieces.put("rb0", new Piece("rb0", new int[] { 9, 4 }));
		pieces.put("rs1", new Piece("rs1", new int[] { 9, 5 }));
		pieces.put("rx1", new Piece("rx1", new int[] { 9, 6 }));
		pieces.put("rm1", new Piece("rm1", new int[] { 9, 7 }));
		pieces.put("rj1", new Piece("rj1", new int[] { 9, 8 }));
		pieces.put("rp0", new Piece("rp0", new int[] { 7, 1 }));
		pieces.put("rp1", new Piece("rp1", new int[] { 7, 7 }));
		pieces.put("rz0", new Piece("rz0", new int[] { 6, 0 }));
		pieces.put("rz1", new Piece("rz1", new int[] { 6, 2 }));
		pieces.put("rz2", new Piece("rz2", new int[] { 6, 4 }));
		pieces.put("rz3", new Piece("rz3", new int[] { 6, 6 }));
		pieces.put("rz4", new Piece("rz4", new int[] { 6, 8 }));
		return pieces;
	}

	private Board initBoard() {
		Board board = new Board();// tạo bàn cờ
		board.pieces = initPieces();// add dữ liệu MAP của từng quân cờ vào BÀN cờ //lấy phương thức mới tạo ở trên
		for (Map.Entry<String, Piece> stringPieceEntry : initPieces().entrySet())
			board.update(stringPieceEntry.getValue());// lấy từng quân cờ bỏ GẮN vào bàn cờ
		// chuyển qua phuong thuc BOARD.update(vị trí từng quân cờ)
		return board;
	}

	public Board playChess() {
		initPieces();// tạo quân cờ
		return initBoard();// tạo bàn cờ, có quân cờ
	}

	public void moveChess(String key, int[] position, Board board) {
		board.updatePiece(key, position);// di chuyển quân cờ trên bàn cờ
		// là phương thúc cập nhật lại vị trí của quân cờ
	}

//	    public void responseMoveChess(Board board, GameView view) {
//	        a4SearchAlphaBeta searchModel = new a4SearchAlphaBeta();
//	        a5AlphaBetaNode result = searchModel.search(board);//? tìm kiếm để tìm ra được nước đi tiếp theo trên bàn cờ 
//	        //thuật toán xử lí??????????????????????????
//	        view.movePieceFromAI(result.piece, result.to);//nước di chuyen của tên quân cờ đó đến đâu
//	        System.out.println(result.toString()+"Quân Đen");
//	        board.updatePiece(result.piece, result.to);//cập nhật lại nước đi của quân cờ đó đến đâu
//	        //cập nhật lại vị trí quân cờ
//	    }

	public void printBoard(Board board) {
		Map<String, Piece> pieces = board.pieces;
		for (Map.Entry<String, Piece> stringPieceEntry : pieces.entrySet()) {
			Piece piece = stringPieceEntry.getValue();
			System.out.println(stringPieceEntry.getKey() + ":" + (char) (piece.position[1] + 'A') + piece.position[0]);
		}

		System.out.println();
	}

	public char hasWin(Board board) {
		boolean isRedWin = board.pieces.get("bb0") == null;// đỏ thắng khi đang nằm ở vị trí
		// của quân vua ĐEN thì =TRUE

		boolean isBlackWin = board.pieces.get("rb0") == null;
		if (isRedWin)
			return 'r';
		else if (isBlackWin)
			return 'b';
		else
			return 'x';// x là tiếp tục đánh
	}

}
