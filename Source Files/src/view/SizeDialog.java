package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class SizeDialog extends JDialog {
	private JLabel rowsLabel;
	private JLabel colsLabel;
	private static JSlider rowsSlider;
	private static JSlider colsSlider;
	private JButton okButton;

	public SizeDialog() {
		super();
		rowsLabel = new JLabel("Rows: 10");// initial values of 10
		colsLabel = new JLabel("Columns: 10");
		rowsSlider = new JSlider(4, 10, 10);
		rowsSlider.setName("rowSlider");
		colsSlider = new JSlider(4, 10, 10);
		colsSlider.setName("colsSlider");
		okButton = new JButton("Okay");
		okButton.setPreferredSize(new Dimension(300, 50));

		JPanel labelsPanel = new JPanel();

		labelsPanel.setLayout(new BorderLayout());
		labelsPanel.add(rowsLabel, BorderLayout.NORTH);
		labelsPanel.add(colsLabel, BorderLayout.SOUTH);

		JPanel slidersPanel = new JPanel();

		slidersPanel.setLayout(new BorderLayout());
		slidersPanel.add(rowsSlider, BorderLayout.NORTH);
		slidersPanel.add(colsSlider, BorderLayout.SOUTH);

		add(labelsPanel, BorderLayout.WEST);
		add(slidersPanel, BorderLayout.EAST);
		add(okButton, BorderLayout.SOUTH);

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
	}
	
	public void addChangeListener(ChangeListener changeListener){
		// add changelisteners to sliders
		rowsSlider.addChangeListener(changeListener);
		colsSlider.addChangeListener(changeListener);
	}

	public void setRowsText(String sliderText) {
		rowsLabel.setText(sliderText);
	}

	public void setColsText(String sliderText) {
		colsLabel.setText(sliderText);
	}

	public void addOkMouseListener(MouseAdapter mouseAdapter) {
		okButton.addMouseListener(mouseAdapter);
	}

	public int getRowValue() {
		return rowsSlider.getValue();
	}

	public int getColValue() {
		return colsSlider.getValue();
	}
}
