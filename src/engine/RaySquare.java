package engine;

import java.io.Serializable;

// TODO: Javadoc kontrollieren
/**
 * The Class RaySquare.
 */
public class RaySquare extends SquareBase implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The direction. */
	Direction direction;
	
	private NumberSquare lightSource;
	private int lightsourcePositionX;
	private int lightsourcePositionY;
	
	/**
	 * Instantiates a new ray square.
	 *
	 * @param posx the posx
	 * @param posy the posy
	 */
	public RaySquare(int posx, int posy) {
		super(posx,posy);
		this.direction = Direction.UNSET; // default direction
	}
	
	public RaySquare(int posx, int posy, NumberSquare lightSource){
		super(posx,posy);
		assignLightSource(lightSource);
	}
	
	/**
	 * Instantiates a new ray square.
	 *
	 * @param d the d
	 * @param posx the posx
	 * @param posy the posy
	 */
	public RaySquare(Direction d, int posx, int posy) {
		super(posx,posy);
		this.direction = d;
	}
	
	public void assignLightSource(NumberSquare lightSource){
		this.lightSource = lightSource;
		if(this.lightSource.getPositionX()==this.getPositionX()){
			direction = Direction.HORIZONTAL;
		}else if(this.lightSource.getPositionY()==this.getPositionY()){
			direction = Direction.VERTICAL;
		}else{
			direction = Direction.UNSET;
		}
		lightSource.addEnlightedSquare(this);	
		this.lightsourcePositionX = lightSource.getPositionX();
		this.lightsourcePositionY = lightSource.getPositionY();
	}
	
	public int getLightsourcePositionX(){
		return this.lightsourcePositionX;
	}
	
	public int getLightsourcePositionY(){
		return this.lightsourcePositionY;
	}
	

	/**
	 * Gets the direction.
	 *
	 * @return the direction
	 */
	public Direction getDirection() {
		return this.direction;
	}
	
	/**
	 * Sets the direction.
	 *
	 * @param d the new direction
	 */
	public void setDirection(Direction d) {
		this.direction = d;
	}
	
	/**
	 * Not yet tested!.
	 *
	 * @param ns the ns
	 * @return Direction
	 */
	public Direction getRayDirectionForLightSource(NumberSquare ns) {
		if(this.getPositionX() == ns.getPositionX() )
			return Direction.HORIZONTAL;
		else if(this.getPositionY() == ns.getPositionY() )
			return Direction.VERTICAL;
		else
			return null;
	}

	/**
	 * @see engine.SquareBase#getPrintableValue()
	 */
	@Override
	public String getPrintableValue() {
		return this.direction.equals(Direction.HORIZONTAL) ? "-" : (this.direction.equals(Direction.VERTICAL) ? "|" : " ");
	}
	
	public NumberSquare getLightSource(){
		return this.lightSource;
	}
	
	//returns if the raysquare has is associated to a lightsource
	//this could be false if it is loaded from a file
	public boolean hasLightsource(){
		if(this.lightSource != null){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * @see engine.SquareBase#isRaySquare()
	 */
	public boolean isRaySquare(){
		return true;
	}
	
	public boolean isInSameRay(SquareBase square){
		boolean isInSameRay = false;
		try{
			if(((RaySquare) square).getLightSource() == this.lightSource){
				isInSameRay = true;
			}
		}catch(ClassCastException e){
			
		}catch(Exception e){
			System.out.println(e);
		}
		return isInSameRay;
	}
	
	public boolean isLastInRay(GameGrid gameGrid){
		int sameLineAround = 0;
		SquareBase tmpSquare = null;
		//check above
		try{
			tmpSquare = gameGrid.getSquare(this.getPositionX(),this.getPositionY()-1);
			if(this.isInSameRay(tmpSquare) || (tmpSquare.isNumberSquare() && tmpSquare==this.lightSource))
				sameLineAround ++;
		}catch(Exception e){
			System.out.println(e);
		}
		//check right
		try{
		tmpSquare = gameGrid.getSquare(this.getPositionX()+1,this.getPositionY());
		if(this.isInSameRay(tmpSquare) || (tmpSquare.isNumberSquare() && tmpSquare==this.lightSource))
			sameLineAround ++;
		}catch(Exception e){
			System.out.println(e);
		}
		//check underneath
		try{
		tmpSquare = gameGrid.getSquare(this.getPositionX(),this.getPositionY()+1);
		if(this.isInSameRay(tmpSquare) || (tmpSquare.isNumberSquare() && tmpSquare==this.lightSource))
			sameLineAround ++;
		}catch(Exception e){
			System.out.println(e);
		}
		//check left
		try{
		tmpSquare = gameGrid.getSquare(this.getPositionX()-1,this.getPositionY());
		if(this.isInSameRay(tmpSquare) || (tmpSquare.isNumberSquare() && tmpSquare==this.lightSource))
			sameLineAround ++;
		}catch(Exception e){
			System.out.println(e);
		}
		
		if(sameLineAround<=1){
			return true;
		}else{
			return false;
		}
				
	}
	
	public String getPositionToLightsource(){
		String positionToLightsource = "unset";
		if(this.getPositionX() == this.lightSource.getPositionX()){
			if(this.getPositionY() < this.lightSource.getPositionY()){
				positionToLightsource = "above";
			}else if(this.getPositionY() > this.lightSource.getPositionY()){
				positionToLightsource = "underneath";
			}
		}else if(this.getPositionY() == this.lightSource.getPositionY()){
			if(this.getPositionX() < this.lightSource.getPositionX()){
				positionToLightsource = "left";
			}else if(this.getPositionX() > this.lightSource.getPositionX()){
				positionToLightsource = "right";
			}
		}
		return positionToLightsource;
	}

}
