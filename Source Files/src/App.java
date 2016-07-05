import model.Field; //import from model package

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.CellPanel;
import view.MainFrame;
import view.GameDisplay;
import view.SizeDialog;

public class App {

	private static Field field;

	// the main objects
	private static MainFrame gameFrame;
	private static GameDisplay mainPanel;

	private static final String NEWGAME = "New Game";
	private static final String QUIT = "Quit";
	private static final String EASY = "Easy";
	private static final String NORMAL = "Normal";
	private static final String HARD = "Hard";

	protected static boolean mouseEventEnabled;

	protected static int rows;
	protected static int cols;
	protected static String level;

	private static SizeDialog sizeDialog;

	public static void main(String[] args) {

		// set default values
		rows = 10;
		cols = 10;
		level = EASY;

		// setup the main objects
		gameFrame = new MainFrame("True/False Game");

		// setup the menu
		JMenuBar menubar = setupMenu(menuListener);
		gameFrame.setJMenuBar(menubar);
		sizeDialog = new SizeDialog();

		// setup field with array of cells and
		// populate the board with panels and labels to act as cells
		setUpGame(level);

		// add listeners to sizeDialog
		addSizeDialogListeners();
	}

	public static void setLevel(String level) {
		App.level = level;
	}

	public static void setUpGame(String level) {
		// allow the board to be clicked
		mouseEventEnabled = true;

		// make a new instance of a field
		field = new Field(rows, cols, level);

		// if the game has already been in progress and the panel exists then
		// remove it from the frame and make a new one
		if (mainPanel != null) {
			gameFrame.remove(mainPanel);
		}

		// create new instance of GameDisplay Panel
		mainPanel = new GameDisplay(rows, cols);

		// add the main panel (now built with all the sub panels inside it) to
		// the frame
		gameFrame.add(mainPanel, BorderLayout.CENTER);

		// the proceeding display code needs to occur in the App Class because
		// App knows
		// about field but Mainframe does not
		gameFrame.setTrueFlagLabel(field.getTrueFlagsLeft());
		gameFrame.setFalseFlagsLabel(field.getTotalFalseFlags());
		gameFrame.setEmptyCellsLeftLabel(field.getEmptyCellsLeft());
		gameFrame.pack();
		gameFrame.setVisible(true);
		// center the main frame relative to the screen
		gameFrame.setLocationRelativeTo(null);

		// add a mouseListener to each cellPanel
		addPanelMouseListeners();
		
	}

	private static JMenuBar setupMenu(ActionListener listener) {

		// create the necessary menu items and menus
		JMenuBar menubar = new JMenuBar();

		JMenu file = new JMenu("File");
		JMenuItem newGameItem = new JMenuItem(NEWGAME);
		JMenuItem quitItem = new JMenuItem(QUIT);

		file.add(newGameItem);
		file.add(quitItem);

		JMenu settingsMenu = new JMenu("Settings");
		JMenuItem sizeMenu = new JMenuItem("Size");

		JMenu difficultyMenu = new JMenu("Difficulty");
		ButtonGroup levelGroup = new ButtonGroup();
		JCheckBoxMenuItem easyItem = new JCheckBoxMenuItem(EASY);
		JCheckBoxMenuItem normalItem = new JCheckBoxMenuItem(NORMAL);
		JCheckBoxMenuItem hardItem = new JCheckBoxMenuItem(HARD);

		easyItem.setSelected(true); // the default setting

		// this button group ensures that only one check box is selected
		levelGroup.add(easyItem);
		levelGroup.add(normalItem);
		levelGroup.add(hardItem);

		difficultyMenu.add(easyItem);
		difficultyMenu.add(normalItem);
		difficultyMenu.add(hardItem);

		settingsMenu.add(difficultyMenu);
		settingsMenu.add(sizeMenu);

		menubar.add(file);
		menubar.add(settingsMenu);

		// use a single listener to handle all menu item selections
		for (int i = 0; i < menubar.getMenuCount(); ++i) {
			for (JMenuItem item : getMenuItems(menubar.getMenu(i))) {
				item.addActionListener(listener);
			}
		}

		return menubar;
	}

	// this recursion works because JMenu is a subclass of JMenuItem!
	private static List<JMenuItem> getMenuItems(JMenuItem item) {
		List<JMenuItem> items = new ArrayList<>();

		if (item instanceof JMenu) {
			JMenu menu = (JMenu) item;
			for (int i = 0; i < menu.getItemCount(); ++i) {
				items.addAll(getMenuItems(menu.getItem(i)));
			}
		} else {
			items.add(item);
		}
		return items;
	}

	// listener for the menu items
	private static ActionListener menuListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case NEWGAME:
				mainPanel.removeAll();
				setUpGame(level);
				break;

			case QUIT:
				System.exit(0);
				break;

			case EASY:
				mainPanel.removeAll();
				level = EASY;
				setUpGame(EASY);
				break;

			case NORMAL:
				mainPanel.removeAll();
				level = NORMAL;
				setUpGame(NORMAL);
				break;

			case HARD:
				mainPanel.removeAll();
				level = HARD;
				setUpGame(HARD);
				break;
			case "Size":
				sizeDialog.setVisible(true);
			}
		}
	};

	public static void addPanelMouseListeners() {
		// add a mouseListener to each cellPanel
		mainPanel.addPanelMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (mouseEventEnabled == true) {
					// (CellPanel) e.getSource()) is the object returned by
					// getSource
					int row = ((CellPanel) e.getSource()).getRow();
					int col = ((CellPanel) e.getSource()).getCol();

					// check for flag type and update game data and gameFrame
					// labels
					checkFlagType(row, col);

					// set relevant cell panel to unhidden state in View
					unHideCellPanel(row, col);

					// if all cells have been revealed,
					// notify of win,
					// reveal all false flags
					// and wait for "New Game" to be actioned
					if (field.getTrueFlagsLeft() == 0 && field.getEmptyCellsLeft() == 0) {
						JOptionPane.showMessageDialog(gameFrame, "Congratulations! You have won!!!");
						mouseEventEnabled = false;
					}
				} else {
					// display pop up message to inform player the game is
					// over
					JOptionPane.showMessageDialog(gameFrame, "The game is over. Please select File/New Game.");
				}
			}
		});
	}
	
	protected static void unHideCellPanel(int row, int col) {
		mainPanel.unHide(row, col, field.getUnHiddenText(row, col),
				field.getCellFlag(row, col), field.hasNearbyFlags(row, col));
		// check for other panels that need to be unhid after the
		// recursion of field.unHide has occurred
		for (int i = 0; i < field.getWidth(); ++i) {
			for (int j = 0; j < field.getHeight(); ++j) {
				if (field.isHidden(i, j) == false) {
					mainPanel
							.unHide(i, j, field.getUnHiddenText(i, j),
									field.getCellFlag(i, j),
									field.hasNearbyFlags(i, j));
				}
			}
		}
	}

	protected static void checkFlagType(int row, int col) {
		// if true flag is clicked then update the true flags left
		// label
		if (field.getCellFlag(row, col) == 1) {
			field.decrementTrueFlagsLeft();
			gameFrame.setTrueFlagLabel(field.getTrueFlagsLeft());
		}
		// if false flag is clicked on, quit and restart the game
		else if (field.getCellFlag(row, col) == 2) {
			// disable the board to the user
			mouseEventEnabled = false;
			// display popup message to user
			JOptionPane.showMessageDialog(gameFrame, "Game Over");

			// reveal the rest of the board
			field.unHideAll();
			// wait for "New Game" to be actioned
		} else if (field.isHidden(row, col) == true) {
			// only do this if its still hidden
			// set relevant empty cell to unhidden state in Model
			// and decrement the number of empty cells left by 1
			field.unHide(row, col);
			int emptyCellsLeft = field.getEmptyCellsLeft();
			field.setEmptyCellsLeft(--emptyCellsLeft);
			gameFrame.setEmptyCellsLeftLabel(field.getEmptyCellsLeft());
		}
	}

	public static void addSizeDialogListeners() {
		sizeDialog.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				System.out.println(slider.getValue());
				if (slider.getName() == "rowSlider") {
					sizeDialog.setRowsText("Rows: " + slider.getValue());

				} else if (slider.getName() == "colsSlider") {
					sizeDialog.setColsText("columns: " + slider.getValue());
				}
			}
		});

		sizeDialog.addOkMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rows = sizeDialog.getRowValue();
				cols = sizeDialog.getColValue();
				sizeDialog.setVisible(false);
				setUpGame(level);
			}
		});
	}
}
