package engine;

import java.io.Serializable;
import java.util.ArrayList;

public class NumberSquare extends SquareBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int number;
	
	private int original_number;
	
	private boolean editormode = false;
	
	private ArrayList<RaySquare> englighted_squares = new ArrayList<RaySquare>();
	

	public NumberSquare(int num, int posX, int posY) {
		super(posX,posY);
		this.number = num;
		this.original_number = num;
	}
	
	public NumberSquare(int posX, int posY) {
		super(posX,posY);
		this.number = -1;
		this.original_number = -1;
	}
	public int getNumber() {
		return this.number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getOriginalNumber(){
		return this.original_number;
	}
	
	/*
	 * Author: Andreas Soiron
	 * checks if the lightsource can enlight the given square
	 * Warning: doesen't check for ohther rays that might block the way
	 */
	public boolean canEnlight(SquareBase rs) {
		if(rs.getClass() == NumberSquare.class)
			return false; // number squares can never be enlighted
		
		//if(this.getPositionX() == rs.getPositionX() && this.getPositionY() == rs.getPositionY())
			//return false; // of course the number square its self can not be enlighted
		
		if(
				( // first part: check x
					// check the left hand side of the light source
					rs.getPositionX() <= this.getPositionX() && rs.getPositionX() >= this.getPositionX() - (this.getNumber() + this.getEnlightedSquares("left").size()) // range
					|| // or
					// check the right hand side...
					rs.getPositionX() >= this.getPositionX() && rs.getPositionX() <= this.getPositionX() + (this.getNumber() + this.getEnlightedSquares("right").size())
				)
				&& // and
				( // second part: check y
					// check the bottom side
					rs.getPositionY() >= this.getPositionY() && rs.getPositionY() <= this.getPositionY() + (this.getNumber() + this.getEnlightedSquares("underneath").size())
					|| // ...
					// check the upper side
					rs.getPositionY() <= this.getPositionY() && rs.getPositionY() >= this.getPositionY() - (this.getNumber() + this.getEnlightedSquares("above").size())
				)
				&& // ...
				(
					// make sure the coordinate lies somewhere on the x and y axis so we get a cross match insead of a square 
					rs.getPositionX() == this.getPositionX()
					||
					rs.getPositionY() == this.getPositionY()
				)
			) // end of the big if
			return true;
		
		// Otherwise the given square can't be enlighted by this numSquare instance
		return false;
	}

	/*
	 * Author: Yusuf Congar
	 * returns if the square is a number square
	 */
	public String getPrintableValue() {
		return Integer.toString(this.getNumber());
	}
	
	/*
	 * Author: Andreas Soiron
	 * returns if the square is a number square
	 */
	public boolean isNumberSquare(){
		return true;
	}
	
	/*
	 * Author: Andreas Soiron
	 * returns all squares that are englighted by this lightsource
	 */
	public ArrayList<RaySquare> getEnlightedSquares(){
		return this.englighted_squares;
	}
	
	/*
	 * Author: Andreas Soiron
	 * returns all squares that are enlighted by this lightsource on the given side
	 * use left,right,above and underneath as sides
	 */
	public ArrayList<RaySquare> getEnlightedSquares(String side){
		ArrayList<RaySquare> squares = new ArrayList<RaySquare>();
		for(RaySquare square : this.englighted_squares){
			if(side.equals("left") && square.getPositionY() == this.getPositionY() && square.getPositionX() < this.getPositionX()){
				squares.add(square);
			}else if(side.equals("right") && square.getPositionY() == this.getPositionY() && square.getPositionX() > this.getPositionX()){
				squares.add(square);
			}else if(side.equals("above") && square.getPositionX() == this.getPositionX() && square.getPositionY() < this.getPositionY()){
				squares.add(square);
			}else if(side.equals("underneath") && square.getPositionX() == this.getPositionX() && square.getPositionY() > this.getPositionY()){
				squares.add(square);
			}
		}
		return squares;
	}
	
	/*
	 * Author: Andreas Soiron, Michael Herpers
	 * Adds a square to the enlighted list, works for playmode and edit mode
	 */
	public void addEnlightedSquare(RaySquare square){
		this.englighted_squares.add(square);
		if(editormode==false){
			this.number = this.original_number - this.englighted_squares.size();
		}else{
			this.original_number = this.englighted_squares.size();
		}
	}
	
	/*
	 * Author: Andreas Soiron, Michael Herpers
	 * Removes a square to the enlighted list, works for playmode and edit mode
	 */
	public void removeEnlightedSquare(RaySquare square){
		this.englighted_squares.remove(square);
		if(editormode==false){
			this.number = this.original_number - this.englighted_squares.size();
		}else{
			this.original_number = this.englighted_squares.size();
		}
	}
	
	/*
	 * Author: Andreas Soiron, Michael Herpers
	 * Removes a square to the enlighted list, works for playmode and edit mode
	 */
	public void removeEnlightedSquare(int index){
		this.englighted_squares.remove(index);
		if(editormode==false){
			this.number = this.original_number - this.englighted_squares.size();
		}else{
			this.number = this.original_number + this.englighted_squares.size();
		}
	}
	
	/*
	 * Author: Andreas Soiron, Michael Herpers
	 * removes all enlightes squares from the enlighted list
	 */
	public void clearEnlightedSquares(){
		this.englighted_squares = new ArrayList<RaySquare>();
	}
	
	/*
	 * Author: Michael Herpers
	 * toggles the editor mode
	 */
	public void changeEditorMode(){
		if(editormode!=true){
			editormode = true;
		}else{
			editormode = false;
		}
			
	}

}
