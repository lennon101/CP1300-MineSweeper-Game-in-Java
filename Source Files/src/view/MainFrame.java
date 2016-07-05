package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {
	private JLabel treasuresLabel;
	private JLabel falseFlagsLabel;
	private GridBagConstraints g;
	private JLabel emptyCellsLeftLabel;

	public MainFrame(String string) {
		super(string);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(400, 400));
		setMaximumSize(new Dimension(1000, 700));
		setPreferredSize(new Dimension(500, 500));


		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BorderLayout());
		
		// create the treasuresLeft label and add it too the bottom left of
		// frame
		JPanel treasuresPanel = new JPanel(new BorderLayout());
		treasuresLabel = new JLabel("Treasures Left: ", SwingConstants.LEFT);
		treasuresPanel.add(treasuresLabel, BorderLayout.EAST);
		labelPanel.add(treasuresPanel, BorderLayout.EAST);

		// create the falseflagsLabel label and add it too the bottom right of
		// frame
		JPanel flagsPanel = new JPanel(new BorderLayout());
		falseFlagsLabel = new JLabel("False flags Left: ");
		flagsPanel.add(falseFlagsLabel, BorderLayout.WEST);
		labelPanel.add(falseFlagsLabel, BorderLayout.WEST);
		
		//add the emptyCellsLeftLabel to the middel of the bottom of the frame
		JPanel emptyCellsPanel = new JPanel(new BorderLayout());
		emptyCellsLeftLabel = new JLabel("--Empty Cells Left: 00--");
		emptyCellsLeftLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emptyCellsPanel.add(emptyCellsLeftLabel, BorderLayout.CENTER);
		labelPanel.add(emptyCellsPanel, BorderLayout.CENTER);

		// add the labelPanel to the bottom of the MainFrame
		add(labelPanel, BorderLayout.SOUTH);

	}

	public void setTrueFlagLabel(int trueFlagsLeft) { //
		treasuresLabel.setText(String.format("--Treasures Left: %d--", trueFlagsLeft));
	}
	
	public void setFalseFlagsLabel(int totalFalseFlags) {
		falseFlagsLabel.setText(String.format("--False Flags on board: %d--", totalFalseFlags));
	}
	
	public void setEmptyCellsLeftLabel(int emptyCellsLeft){
		emptyCellsLeftLabel.setText(String.format("--Empty Cells Left: %d--", emptyCellsLeft));
	}
}
