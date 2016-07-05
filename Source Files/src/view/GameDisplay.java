package view;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;

public class GameDisplay extends JPanel {
	CellPanel[][] panels;

	public GameDisplay(int numberOfRows, int numberOfColumns) {

		panels = new CellPanel[numberOfRows][numberOfColumns];

		// set the layout of the gameDisplay (main panel) to gridLayout
		setLayout(new GridLayout(numberOfRows, numberOfColumns));

		// add panels to the main panel
		for (int i = 0; i < numberOfRows; ++i) {
			for (int j = 0; j < numberOfColumns; ++j) {
				// create the panel that will act as one of the cells
				CellPanel panel = new CellPanel(i, j);
				// add the panel to mainPanel
				panels[i][j] = panel;

				add(panels[i][j]);
			}
		}
	}

	public void addPanelMouseListener(MouseAdapter mouseAdapter) {
		for (JPanel[] row : panels) {
			for (JPanel panel : row) {
				panel.addMouseListener(mouseAdapter); 
			}
		}
	}

	public void unHide(int row, int col, String unHiddenText, int flagtype,
			boolean hasNearbyFlags) {
		panels[row][col].unHideCell(unHiddenText, flagtype, hasNearbyFlags);
	}
}
