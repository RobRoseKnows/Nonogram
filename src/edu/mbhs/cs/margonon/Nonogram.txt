import java.awt.Color;
import java.awt.event.*;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Nonogram extends dodgeGame {

	// variables

	BufferedImage instructions;
	BufferedImage cat;
	BufferedImage cat2;
	Cells grid;
	ArrayList<int[][]> puzzles;
	int screen = 0; // 0 is main menu
					// 1 is level select
					// 2 is level play
	int level = -1;
	int checking = 0; // 0 is not checking
						// 1 is checking false
						// 2 is checking true
	int counter = 0;
	int[] isDone; // has the level been completed in this run?

	/**
	 * The constructor Contains the key listener and mouse listener
	 */

	public Nonogram() {
		super(); // call super constructor
		addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {

				// quits
				if (e.getKeyCode() == KeyEvent.VK_Q)
					System.exit(0);
				// clears grid
				if (e.getKeyCode() == KeyEvent.VK_C) {
					grid.clear();
				}

			}

			public void keyReleased(KeyEvent arg0) {

			}

			public void keyTyped(KeyEvent arg0) {

			}
		});

		addMouseListener(new MouseAdapter() {

			/**
			 * Does the following actions when the mouse is pressed.
			 */
			public void mousePressed(MouseEvent e) {

				// When at the menu screen, a click will lead to the level
				// select screen
				if (screen == 0)
					screen = 1;
				

				// When at the level select screen, it reads what level to go to
				// based on what "button" is clicked
				if (screen == 1) {
					if (e.getX() >= 100 && e.getX() <= 220) {
						for (int i = 0; i < puzzles.size(); i++) {
							if (e.getY() >= 150 + (i * 60)
									&& e.getY() <= 190 + (i * 60)) {
								newLevel(i); // change the level
								screen = 2;
							}
						}
					}
				}

				// When at the level play screen, it either detects a click on
				// the grid or a click on the "buttons"
				if (screen == 2) {
					int x = (e.getX() - (int) grid.p.x);
					int y = (e.getY() - (int) grid.p.y);
					if (x >= 0 && y >= 0) {
						x = x / grid.csize;
						y = y / grid.csize;
						if (x < grid.cells.length && y < grid.cells[0].length) {
							grid.cells[x][y] = grid.cells[x][y] + 1;
							if (grid.cells[x][y] >= 3)
								grid.cells[x][y] = 0;
						}// end

					}

					// Level check button
					if (e.getX() >= 220 && e.getX() <= 340
							&& e.getY() >= grid.csize * grid.cy + grid.p.y + 20
							&& e.getY() <= grid.csize * grid.cy + grid.p.y + 60) {
						if (check(level)) {
							isDone[level] = 1;// indicates a level is complete,
												// to be displayed in level
												// select screen
							checking = 2;
						} else
							checking = 1;
					}
					// Level select button
					if (e.getX() >= 220
							&& e.getX() <= 340
							&& e.getY() >= grid.csize * grid.cy + grid.p.y + 80
							&& e.getY() <= grid.csize * grid.cy + grid.p.y
									+ 120) {
						screen = 1; // goes back to level select screen
					}
					// Clear button
					if (e.getX() >= 220
							&& e.getX() <= 340
							&& e.getY() >= grid.csize * grid.cy + grid.p.y
									+ 140
							&& e.getY() <= grid.csize * grid.cy + grid.p.y
									+ 180) {
						grid.clear();
					}
				}

			}// end mouseClicked

		});
	}

	/**
	 * Called when the game is created, initializes objects
	 */
	public void init() {
		try{
			instructions=ImageIO.read(new File("instructions.png"));
			cat=ImageIO.read(new File("cat.gif"));
			cat2=ImageIO.read(new File("cat 2.png"));
		}catch(IOException e){
			System.out.println("File not found");
		}
		puzzles = new ArrayList<int[][]>();
		readPuzzle();
		grid = null;

		start(); // start a game
	}

	/**
	 * Called when the game starts
	 */
	public void start() {

	}

	/**
	 * Call when the screen needs to be redrawn
	 */
	public void updateFrame(Graphics2D g) {
		drawBoard(g);
		drawSprites(g);
	}

	/**
	 * Draw background Contains a header with the title of game
	 * 
	 * @param g, the graphics2D object
	 */
	public void drawBoard(Graphics2D g) {
		g.setFont(new Font("Ariel", Font.PLAIN, 15));
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(53, 70, 92));
		g.fillRect(0, 0, getWidth(), 30);
		g.setColor(Color.white);
		g.drawString("MARGONON", 10, 20);
	}

	/**
	 * Called when the level has changed Initializes grid based on level
	 * 
	 * @param l, the level
	 */
	public void newLevel(int l) {
		level = l;
		grid = new Cells(
				puzzles.get(level)[0].length,
				puzzles.get(level).length,
				30, // size of cell on screen
				new Point2D.Double(220, 150),
				new int[puzzles.get(level)[0].length][puzzles.get(level).length]);
						//creates an array based on the size of the puzzle
	}

	/**
	 * Draws objects based on the screen
	 * 
	 * @param g, the graphics2D object
	 */
	public void drawSprites(Graphics2D g) {
		g.setFont(new Font("Ariel", Font.PLAIN, 15));
		// at the main menu, instructions are displayed
		if (screen == 0) {
			g.drawImage(instructions, 150, 50, null);
		}
		// draws the level select menu
		if (screen == 1) {
			boolean a=true;
			for(int i: isDone) if(i==0) a=false;
			drawLevelSelect(g);
			if(!a)g.drawImage(cat,260,120,null);
			else g.drawImage(cat2, 260, 120, null);
		}
		// draws the level play screen
		if (screen == 2) {
			drawGame(g);
		}
	}

	/**
	 * Called when the screen is at 1 Displays a button for each level/puzzle
	 * Also displays is level/puzzle has already been completed
	 * 
	 * @param g
	 */
	public void drawLevelSelect(Graphics2D g) {
		for (int i = 0; i < puzzles.size(); i++) {
			g.setFont(new Font("Ariel", Font.PLAIN, 15));
			g.setColor(new Color(53, 70, 92));
			g.fillRect(100, 150 + (i * 60), 120, 40);
			g.setColor(Color.white);
			g.drawString("Level " + i, 135, 175 + (i * 60));
			// displays check mark if level was already completed
			if (isDone[i] == 1) {
				g.setFont(new Font("Ariel", Font.PLAIN, 35));
				g.setColor(new Color(40, 170, 0));
				g.drawString("\u2713", 64, 180 + (i * 60));
			}
		}
	}

	/**
	 * Is called when screen is 2, or is at game play screen Draws the grid, the
	 * puzzle numbers, and buttons
	 * 
	 * @param g, the Graphics2D object
	 */
	public void drawGame(Graphics2D g) {
		if (grid != null) {
			grid.draw(g);
			g.setColor(new Color(53, 70, 92)); // Tumblr blue

			if (checking != 0) {	//if the check button had just been pressed
				g.setFont(new Font("Ariel", Font.PLAIN, 30));
				if (checking == 1)	//if solution is incorrect
					g.drawString("Incorrect", 400, (int) (grid.csize * grid.cy
							+ grid.p.y + 50));
				else				//if solution is correct
					g.drawString("Correct", 400, (int) (grid.csize * grid.cy
							+ grid.p.y + 50));
				counter++;
			}
			if (counter > 50) {		//displays message for 50 counts
				checking = 0;
				counter = 0;
			}

			g.setFont(new Font("Ariel", Font.PLAIN, 15));

			//check answer button
			g.fillRect(220, (int) (grid.csize * grid.cy + grid.p.y + 20), 120,
					40);
			//level select button
			g.fillRect(220, (int) (grid.csize * grid.cy + grid.p.y + 80), 120,
					40);
			//clear button
			g.fillRect(220, (int) (grid.csize * grid.cy + grid.p.y + 140), 120,
					40);

			if (level >= 0) {
				int[][] row = rowNums(level); //the numbers displayed on the left
				int[][] col = colNums(level); //the numbers displayed on top
				String s = "";
				for (int i = 0; i < row.length; i++) {
					s = "";
					for (int j : row[i]) {
						s += j + "   ";
					}
					FontMetrics metrics = getFontMetrics(new Font("Ariel",
							Font.PLAIN, 15));
					int width = metrics.stringWidth(s);
					g.drawString(s, 215 - width, (int) (grid.p.y + (grid.csize)
							* i) + 20);
				}

				for (int i = 0; i < col.length; i++) {
					s = "";
					for (int j = 0; j < col[i].length; j++) {
						s = col[i][j] + "";
						g.drawString(s,
								(int) (grid.p.x + (grid.csize) * i) + 10,
								160 - (col[i].length - j) * 20);
					}
				}
			}
			g.setColor(Color.white);
			g.drawString("Check answer", 232, (int) (grid.csize * grid.cy
					+ grid.p.y + 45));
			g.drawString("Level Select", 240, (int) (grid.csize * grid.cy
					+ grid.p.y + 105));
			g.drawString("Clear", 260,
					(int) (grid.csize * grid.cy + grid.p.y + 165));
		}
	}

	/**
	 * Reads the puzzles from a text document
	 * They are stored in an ArrayList
	 */
	public void readPuzzle() {
		File file = new File("puzzles.txt");
		int rows = 0;
		int columns = 0;
		int[][] puzzle;
		try {
			Scanner s = new Scanner(file);
			while (s.hasNext()) {
				rows = s.nextInt();
				columns = s.nextInt();
				puzzle = new int[rows][columns];
				for (int r = 0; r < rows; r++) {
					for (int c = 0; c < columns; c++) {
						puzzle[r][c] = s.nextInt();
					}
				}
				puzzles.add(puzzle);
			}
			isDone = new int[puzzles.size()];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Generates the numbers on the left of the puzzle
	 * It is calculated based on the puzzle itself
	 * @param level, the level to be calculated
	 * @return a double array of numbers
	 */

	public int[][] rowNums(int level) {

		int[][] ans = puzzles.get(level); //the puzzle
		int[][] nums = new int[ans.length][]; //will store the numbers
		int a; //a counter
		ArrayList<Integer> aa = new ArrayList<Integer>();

		for (int i = 0; i < nums.length; i++) {
			a = 0;
			aa.clear();
			for (int j = 0; j < ans[i].length; j++) {
				if (ans[i][j] == 1)
					a++;
				else {
					if (a != 0) {
						int b = a;
						aa.add(b);
						a = 0;
					}
				}
				if (j == ans[i].length - 1 && a != 0) {
					int b = a;
					aa.add(b);
				}

			}
			int[] n = new int[aa.size()];
			for (int h = 0; h < n.length; h++) {
				n[h] = aa.get(h);
			}
			nums[i] = n;
		}
		return nums;
	}
	
	/**
	 * Generates the numbers on the top of the puzzle
	 * It is calculated based on the puzzle itself
	 * @param level, the level to be calculated
	 * @return a double array of numbers
	 */

	public int[][] colNums(int level) {

		int[][] ans = puzzles.get(level);
		int[][] nums = new int[ans[0].length][];
		int a;
		ArrayList<Integer> aa = new ArrayList<Integer>();

		for (int i = 0; i < ans[0].length; i++) { // column
			a = 0;
			aa.clear();
			for (int j = 0; j < ans.length; j++) { // row
				if (ans[j][i] == 1)
					a++;
				else {
					if (a != 0) {
						int b = a;
						aa.add(b);
						a = 0;
					}
				}
				if (j == ans.length - 1 && a != 0) {
					int b = a;
					aa.add(b);
				}

			}
			int[] n = new int[aa.size()];
			for (int h = 0; h < n.length; h++) {
				n[h] = aa.get(h);
			}
			nums[i] = n;
		}
		return nums;
	}

	
	/**
	 * Checks if the user's solution is correct
	 * @param level, the level to be checked
	 * @return true if correct, false if not
	 */
	public boolean check(int level) {
		int[][] an = puzzles.get(level);
		int[][] sol = grid.cells;
		boolean ans = true;
		for (int i = 0; i < sol.length; i++) {
			for (int j = 0; j < sol[i].length; j++) {
				if (sol[i][j] == 2)
					sol[i][j] = 0;
			}
		}
		for (int i = 0; i < an.length; i++) {
			for (int j = 0; j < an[i].length; j++)
				if (an[i][j] != sol[j][i])
					ans = false;
		}

		return ans;

	}

	
	/**
	 * Inverses the puzzle's picture
	 * @param a, a puzzle
	 * @return the puzzle's inverse
	 */
	public int[][] inverse(int[][] a) {
		int[][] b = new int[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				if (a[i][j] == 0)
					b[i][j] = 1;
			}
		}
		return b;
	}

	public static void main(String[] args) {
		Nonogram myGame = new Nonogram();
		createGameFrame(myGame, 800, 900);
		myGame.init();
	}
	
	/**
	 * Sets up the game frame
	 * @param game 
	 * @param width
	 * @param height
	 */

	public static void createGameFrame(dodgeGame game, int width, int height) {
		Frame myFrame = new Frame();

		myFrame.setSize(width, height); // frame size
		myFrame.setBackground(Color.white);

		myFrame.add(game);
		game.addKeyListener(game);
		// Make sure program ends when window is closed
		WindowAdapter d = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		myFrame.addWindowListener(d);
		myFrame.setVisible(true); // see frame
		game.requestFocus(); // make sure the game is selected

	}

}