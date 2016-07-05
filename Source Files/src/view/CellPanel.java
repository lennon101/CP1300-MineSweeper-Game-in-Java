package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CellPanel extends JPanel {
	private int row;
	private int col;
	private JLabel label;

	public CellPanel(int cellRow, int cellCol){
		row = cellRow;
		col = cellCol;
		
		label = new JLabel();//cellRow + "," + cellCol
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(50,50));
		setLayout(new BorderLayout());
		add(label, BorderLayout.CENTER);
		setBorder(BorderFactory.createRaisedBevelBorder());
		
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void unHideCell(String unHiddenText, int flagtype, boolean hasNearbyFlags) {
		/*unHiddenText may contain 1 of three strings:
		 * 	(0,0)
		 * 	(1,0) - flags nearby
		 * 	1/2   - flags found */
		label.setText(unHiddenText);
		setOpaque(true); //allows colors to be set
		if (flagtype == 0 && hasNearbyFlags == false){
			setBackground(Color.GREEN);
		}else if (flagtype ==0 && hasNearbyFlags == true){
			setBackground(Color.gray);
		}else if (flagtype == 1){
			setBackground(Color.BLUE);
		}else if (flagtype == 2){
			setBackground(Color.RED);
		}
		
	}
}
