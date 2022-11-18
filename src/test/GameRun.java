package test;

import controller.GameController;
import model.Board;
import view.GameView;

public class GameRun {
	 private Board board;

	    private GameController controller;
	    private GameView view;

	    public static void main(String[] args) throws InterruptedException {
	        GameRun game = new GameRun();
	        game.init();
	        game.run();
	    }

	    public void init() {
	        controller = new GameController();
	        board = controller.playChess();
	        view = new GameView(controller);
	        view.init(board);
	    }

	    public void run() throws InterruptedException {
	        while (controller.hasWin(board) == 'x') {//đang là tiếp tục 
	            view.showPlayer('r');//thì showw hình đỏ : người chơi đánh
	            while (board.player == 'r')
	                Thread.sleep(1000);

	            if (controller.hasWin(board) != 'x')
	                view.showWinner('r');//show RED người chơi thắng
	            view.showPlayer('b');//ngược lại ĐEN cho Máy đánh
//	            controller.responseMoveChess(board, view);//load lại bàn cờ, cập nhật vị trí quân cờ
	        }
	        
	        view.showWinner('b');////show ĐEN MÁYTINHS thắng
	    }
	}