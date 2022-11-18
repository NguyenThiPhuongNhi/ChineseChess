package view;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import controller.GameController;
import model.Board;
import model.Piece;
import model.PieceRules;

public class GameView {
	private static final int VIEW_WIDTH = 700, VIEW_HEIGHT = 712;
	private static final int PIECE_WIDTH = 67, PIECE_HEIGHT = 67;
//	private static final int SY_COE = 68, SX_COE = 68;
//	private static final int SX_OFFSET = 50, SY_OFFSET = 15;
	private static final int SY_COE = 70, SX_COE = 76;
	private static final int SX_OFFSET = 14, SY_OFFSET = 12;	
	private Map<String, JLabel> pieceObjects = new HashMap<String, JLabel>();
	private Board board;
	private String selectedPieceKey;
	private JFrame frame;
	private JLayeredPane pane;
	private GameController controller;
	private JLabel lblPlayer;

	public GameView(GameController gameController) {
		this.controller = gameController;
	}

	public void init(final Board board) {
		this.board = board;
		frame = new JFrame("CỜ TƯỚNG (JIANG)");
//		frame.setIconImage(new ImageIcon("res/img/icon.png").getImage());
		frame.setSize(VIEW_WIDTH, VIEW_HEIGHT + 40);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pane = new JLayeredPane();
		frame.add(pane);

//		JLabel bgBoard = new JLabel(new ImageIcon("res/img/board2.png"));
		JLabel bgBoard = new JLabel(setImageIconButton("board2.png"));
//				new ImageIcon("res/img/board2.png"));
		
		bgBoard.setLocation(0, 0);
		bgBoard.setSize(VIEW_WIDTH, VIEW_HEIGHT);
		bgBoard.addMouseListener(new BoardClickListener());
		pane.add(bgBoard, 1);

		lblPlayer = new JLabel(setImageIconButton("r.png"));
				//new ImageIcon("res/img/r.png"));
		lblPlayer.setLocation(10, 323);
		lblPlayer.setSize(PIECE_WIDTH, PIECE_HEIGHT);
		pane.add(lblPlayer, 0);

		Map<String, Piece> pieces = board.pieces;
		for (Map.Entry<String, Piece> stringPieceEntry : pieces.entrySet()) {
			String key = stringPieceEntry.getKey();
			int[] pos = stringPieceEntry.getValue().position;
			int[] sPos = modelToViewConverter(pos);
			String nameIMG=key.substring(0,2)+".png";
			JLabel lblPiece = new JLabel(setImageIconButton(nameIMG));
					///new ImageIcon("res/img/"
				//	+ key.substring(0, 2) + ".png"));

			lblPiece.setLocation(sPos[0], sPos[1]);
			lblPiece.setSize(PIECE_WIDTH, PIECE_HEIGHT);
			lblPiece.addMouseListener(new PieceOnClickListener(key));
			pieceObjects.put(stringPieceEntry.getKey(), lblPiece);
			pane.add(lblPiece, 0);
		}
		frame.setVisible(true);
	}

	public void movePieceFromModel(String pieceKey, int[] to) {
		JLabel pieceObject = pieceObjects.get(pieceKey);
		int[] sPos = modelToViewConverter(to);
		pieceObject.setLocation(sPos[0], sPos[1]);

		selectedPieceKey = null;
	}

	public void movePieceFromAI(String pieceKey, int[] to) {
		Piece inNewPos = board.getPiece(to);
		if (inNewPos != null) {
			pane.remove(pieceObjects.get(inNewPos.key));
			pieceObjects.remove(inNewPos.key);
		}

		JLabel pieceObject = pieceObjects.get(pieceKey);
		int[] sPos = modelToViewConverter(to);
		pieceObject.setLocation(sPos[0], sPos[1]);

		selectedPieceKey = null;
	}

	private int[] modelToViewConverter(int pos[]) {
		int sx = pos[1] * SX_COE + SX_OFFSET, sy = pos[0] * SY_COE + SY_OFFSET;
		return new int[] { sx, sy };
	}

	private int[] viewToModelConverter(int sPos[]) {
		int ADDITIONAL_SY_OFFSET = 25;
		int y = (sPos[0] - SX_OFFSET) / SX_COE, x = (sPos[1] - SY_OFFSET - ADDITIONAL_SY_OFFSET)
				/ SY_COE;
		return new int[] { x, y };
	}

	public void showPlayer(char player) {
		String nameImg=player+".png";
		lblPlayer.setIcon(setImageIconButton(nameImg));
				//new ImageIcon("res/img/" + player + ".png"));
		frame.setVisible(true);
	}

	public void showWinner(char player) {
		JOptionPane.showMessageDialog(null,
				(player == 'r') ? "Chúc mừng Bạn Đã THẮNG!"
						: "Thật tiếc Bạn Đã THUA!", "Cờ Tướng (JIANG)",
				JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	class PieceOnClickListener extends MouseAdapter {
		private String key;

		PieceOnClickListener(String key) {
			this.key = key;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (selectedPieceKey != null && key.charAt(0) != board.player) {
				int[] pos = board.pieces.get(key).position;

				int[] selectedPiecePos = board.pieces.get(selectedPieceKey).position;
				for (int[] each : PieceRules.getNextMove(selectedPieceKey,
						selectedPiecePos, board)) {
					if (Arrays.equals(each, pos)) {
						pane.remove(pieceObjects.get(key));
						pieceObjects.remove(key);
						controller.moveChess(selectedPieceKey, pos, board);
						movePieceFromModel(selectedPieceKey, pos);
						break;
					}
				}
			} else if (key.charAt(0) == board.player) {
				selectedPieceKey = key;
			}
		}
	}

	class BoardClickListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (selectedPieceKey != null) {
				int[] sPos = new int[] { e.getXOnScreen() - frame.getX(),
						e.getYOnScreen() - frame.getY() };
				int[] pos = viewToModelConverter(sPos);
				int[] selectedPiecePos = board.pieces.get(selectedPieceKey).position;
				for (int[] each : PieceRules.getNextMove(selectedPieceKey,
						selectedPiecePos, board)) {
					if (Arrays.equals(each, pos)) {
						controller.moveChess(selectedPieceKey, pos, board);
						movePieceFromModel(selectedPieceKey, pos);
						break;
					}
				}
			}
		}
	}
	public ImageIcon setImageIconButton(String name_img) {
	;
		Image name_image = new ImageIcon(getClass().getResource(
				"/images/" + name_img + "")).getImage();

		ImageIcon name_imgicon = new ImageIcon(name_image);
		return name_imgicon;}
}
