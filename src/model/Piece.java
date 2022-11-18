package model;

public class Piece implements Cloneable {
	public String key; // tên của từng quân cờ
	public char color; // màu của cờ (black(b)/ red(r:người chơi))
	public char character;	//rank(tướng, sĩ, tượng,...)
	public char index;	//số thứ tự theo số quân vd chốt đỏ rz0,..rz4
	public int[] position = new int[2];// vị trí hiện tại quân cờ [x,y]

	public Piece(String name, int[] position) {
		this.key = name;
		this.color = name.charAt(0);
		this.character = name.charAt(1);
		this.index = name.charAt(2);
		this.position = position;
	}
}
