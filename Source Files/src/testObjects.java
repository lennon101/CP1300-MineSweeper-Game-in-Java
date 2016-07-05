

import java.util.Scanner;

import model.Cell;
import model.Field;

public class testObjects {

	public static void main(String[] args) {
		
		//test Cell Class
		Cell emptyCell = new Cell();
		System.out.println("Empty Cell test (expected: \"[-]\"). Actual: " + emptyCell);
		Cell trueCell = new Cell(1);
		System.out.println("Empty Cell test (expected: \"1\"). Actual: " + trueCell);
		Cell flaseCell = new Cell(2);
		System.out.println("Empty Cell test (expected: \"2\"). Actual: " + flaseCell + "\n");
		
		//test Field Class
		System.out.println("Begin Field Class Test:");
		Field field1 = new Field(5,5,"Easy");
		System.out.println(field1);
		
		boolean finished = false;
		do{
			System.out.print("enter x coordinate: ");
			int x = getCoordinate();
			System.out.print("enter y coordinate: ");
			int y = getCoordinate();

			System.out.printf("The coordinates are: (%d,%d)\n",x,y);

			field1.unHide(x,y);
			System.out.println(field1);

		}while (finished  == false);
	}
	
	private static int getCoordinate() {
		Scanner scanner = new Scanner(System.in);
		int coordinate = scanner.nextInt();
		return coordinate;
	}
}




