package model;

import java.util.Map;

//Bàn cờ, di chuyển

public class Board {
	public final int BOARD_WIDTH = 9, BOARD_HEIGHT = 10;//ma trận bàn cờ
	public Map<String, Piece> pieces;//lưu trữ quân cờ
    public char player = 'r';// kí hiệu người chơi
    private Piece[][] cells = new Piece[BOARD_HEIGHT][BOARD_WIDTH];//ma trận 2 chiều lưu vị trí từng quân cờ
    
    public boolean isInside(int[] position) {	//kiểm tra vị trí có trong bàn cờ hay không
    	return isInside(position[0], position[1]);
    }

	public boolean isInside(int x, int y) {	//phải nằm trong giới hạn chiều dài, chiều rộng
        return !(x < 0 || x >= BOARD_HEIGHT
                || y < 0 || y >= BOARD_WIDTH);
    }

    public boolean isEmpty(int[] position) {	//kiểm tra vị trí đó có quân cờ nào không
        return isEmpty(position[0], position[1]);
    }

    public boolean isEmpty(int x, int y) {	//vị trí đó phải thuộc bàn cờ và trong ma trận đó chưa có quân cờ nào
        return isInside(x, y) && cells[x][y] == null;
    }
    public boolean update(Piece piece) {
        int[] pos = piece.position;	//lấy vị trí ra
        cells[pos[0]][pos[1]] = piece;	//từ dữ liệu vị trí mảng 1 chiều chuyển sang mảng 2 chiều==lưu vị trí
        return true;
    }
    public Piece updatePiece(String key, int[] newPos) {
    	Piece orig = pieces.get(key);	//lấy ra tên của quân cờ =(key) mới đánh của quân cờ
    	Piece inNewPos = getPiece(newPos);	//tạo ra quân cờ mới có vị trí mới đánh
        if (inNewPos != null)
            pieces.remove(inNewPos.key);	//nếu chỗ mới có quân cờ nào thì xóa nó đi,
        									//ăn quân cờ đó để đánh quân cờ của mình
        int[] origPos = orig.position;
        cells[origPos[0]][origPos[1]] = null;	//xóa toa độ của quan cờ ở vị trí cũ
        cells[newPos[0]][newPos[1]] = orig;	//cập nhật lại vị trí mới trên bàn cờ [][]
        orig.position = newPos;	//gán giá trị vị trí mới cho quân cờ
        						//thay đổi lại người đánh
        player = (player == 'r') ? 'b' : 'r';
        return inNewPos;
    }
    public Piece getPiece(int[] pos) {
        return getPiece(pos[0], pos[1]);//lấy ra Quân cờ trên mảng 2 chiều dựa vào tọa độ vị trí mảng 1 chiều
    }

    public Piece getPiece(int x, int y) {
    	//lấy ra Quân cờ trên mảng 2 chiều
    	//dựa vào tọa độ vị trí x,y trên mảng 2 chiều(là bàn cờ)
        return cells[x][y];
    }
    //màu đen do máy tính tự động đánh
    public boolean backPiece(String key) {
        int[] origPos = pieces.get(key).position;	//lấy ra toa độ vị trí của quân cờ
        cells[origPos[0]][origPos[1]] = pieces.get(key);	
        //xem quân cờ đang có trên mảng 2 chiều 
        //(mà lấy vị trí quân cờ đen đang đúng) có giống =  với quân cờ đen hay không
        return true;
    }

}
