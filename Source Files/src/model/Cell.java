package model;

public class Cell {
	private int flag;
	private int nearbyTrueFlags;
	private int nearbyFalseFlags;
	private boolean nearbyFlags;
	private boolean hidden;

	//default constructor 
	public Cell(){
		flag = 0; //empty
		nearbyTrueFlags = 0;
		nearbyFalseFlags = 0;
		nearbyFlags = false;
		hidden = true;
	}

	//initial value constructor
	public Cell(int flag){
		this.flag = flag;
	}

	public void setFlag(int flag){
		this.flag = flag;
	}

	public int getFlag(){
		return this.flag;
	}

	public void setNearbyFlags(int flagType){
		if (flagType == 1){
			++nearbyTrueFlags; //adds to the value of the nearby true flags
		}else if (flagType == 2){
			++nearbyFalseFlags;//adds to the value of the nearby false flags
		}
		nearbyFlags = true;
	}
	
	public void setHiddenState(boolean b){
		this.hidden = b;
	}
	
	public String getUnHiddenString(){
		//similar to toString method however this is required for Label display
		if (flag == 0){
			return String.format("(%d,%d)", nearbyFalseFlags, nearbyTrueFlags);
		}else if (flag == 1){
			return "T";
		}else{
			return "F";
		}
	}
	
	@Override
	public String toString() {
		//(false, true)
		if (hidden == false && flag == 0){
			return String.format("(%d,%d)", nearbyFalseFlags, nearbyTrueFlags);
		}else if (hidden == false && flag > 0){
			return String.format("%d", flag);
		}else
			return "[-]";
	}

	public boolean hasNearbyFlags(){
		return nearbyFlags;
	}

	public boolean isHidden() {
		return hidden;
	}
}
