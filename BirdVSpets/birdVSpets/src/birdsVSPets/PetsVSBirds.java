package birdsVSPets;

/**
 * Pets vs Birds Game
 * 
 * @author Nandakumar Jonnalagadda & Dylan Fawson
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PetsVSBirds extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private Timer timer;
	private ArrayList<Actor> actors; // Pets and cardinals all go in here
	private int numRows;
	private int numCols;
	private int cellSize;
	private Random rand;
	JLabel scoreLabel;
	JLabel resourceLabel;
	JButton dachshundButton;
	JButton catButton;
	private int score;
	private int selectedType = 1;
	int resources;
	private static BufferedImage img;
	Home home;

	static {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("src/a10/Animal-Icons/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		img = tempImage;
	}

	/**
	 * Setup the basic info for the example
	 */
	public PetsVSBirds() {

		super();
		// setBackground(Color.YELLOW);

		rand = new Random();

		// add the score and resource labels so the user can keep track of them
		resources = 10;
		scoreLabel = new JLabel("Score: " + score);
		this.add(scoreLabel);
		resourceLabel = new JLabel("- Resources: " + resources);
		this.add(resourceLabel);

		// adds buttons for selecting pets
		dachshundButton = new JButton("Dachshund");
		catButton = new JButton("Cat");

		this.add(catButton);
		this.add(dachshundButton);

		// actionListeners added to allow for placing of pets when certain areas are
		// clicked
		dachshundButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedType = 0;
			}
		});

		catButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedType = 1;
			}
		});

		this.addMouseListener(this);

		// Define some quantities of the scene
		numRows = 5;
		numCols = 7;
		cellSize = 65;
		setPreferredSize(new Dimension(65 + numCols * cellSize, 200 + numRows * cellSize));

		// Store all the pets and cardinals in here.
		actors = new ArrayList<>();

		// Make home base
		home = new Home(new Point2D.Double(0, 45));
		actors.add(home);

		// Make a bird
		Bird firstBird = new BlueBird(new Point2D.Double(500, 200 + 50));

		// Add them to the list of actors
		// actors.add(firstPet);
		actors.add(firstBird);

		// The timer updates the game each time it goes.
		// Get the javax.swing Timer, not from util.
		timer = new Timer(30, this);
		timer.start();

	}

	/***
	 * Implement the paint method to draw the pets
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(img, 0, 0, null);
		for (Actor actor : actors) {
			actor.draw(g, 0);
			actor.drawHealthBar(g);
		}

	}

	/**
	 * 
	 * This is triggered by the timer. It is the game loop of this test.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// This method is getting a little long, but it is mostly loop code
		// Increment their cooldowns and reset collision status
		for (Actor actor : actors) {
			actor.update();
		}

		int col = rand.nextInt(6);
		int row = rand.nextInt(5);

		int doesAddBird = rand.nextInt(1000);

		if (score < 20) { // level 1
			if (doesAddBird > 996) {
				if (doesAddBird <= 998) {
					Bird bird = new Cardinal(new Point2D.Double(500, (row * 100 + 50)));
					actors.add(bird);
				} else {
					Bird bird = new BlueBird(new Point2D.Double(500, (row * 100 + 50)));
					actors.add(bird);
				}
			}
		}

		if (score >= 40) { // level 2
			if (doesAddBird > 993) {
				if (doesAddBird <= 997) {
					Bird bird = new Cardinal(new Point2D.Double(500, (row * 100 + 50)));
					actors.add(bird);
				} else {
					Bird bird = new BlueBird(new Point2D.Double(500, (row * 100 + 50)));
					actors.add(bird);
				}
			}
		}

		if (score >= 60) { // level 3
			if (doesAddBird > 990) {
				if (doesAddBird >= 997) {
					Bird bird = new Cardinal(new Point2D.Double(500, (row * 100 + 50)));
					actors.add(bird);
				} else {
					Bird bird = new Chicken(new Point2D.Double(500, (row * 100 + 50)));
					actors.add(bird);
				}
			}
		}

		int doesAddPetFood = rand.nextInt(1000);

		if (doesAddPetFood > 994) {
			PetFood petFood = new PetFood(new Point2D.Double(600, (row * 100 + 50)));
			actors.add(petFood);
		}

		ArrayList<Actor> nextTurnActors = new ArrayList<>();
		for (Actor actor : actors) {
			if (actor.isAlive())
				nextTurnActors.add(actor);
			else {
				if (actor instanceof Bird) {
					score += 5;

					scoreLabel.setText("Score : " + score);
				}
				actor.removeAction(actors);

			} // any special effect or whatever on removal
		}
		actors = nextTurnActors;

		// Check for collisions between cardinals and pets and set collision status
		for (Actor actorX : actors) {
			for (Actor other : actors) {
				actorX.setCollisionStatus(other);
			}
		}

		// Try to attack
		for (Actor actorX : actors) {
			for (Actor other : actors) {
				actorX.attack(other);
			}
		}
		// Move the actors.
		for (Actor actor : actors) {
			actor.move(); // for Bird, only moves if not colliding.
		}

		// terminates the game if the home base is reached
		for (Actor actor : actors) {
			if (actor instanceof Home)
				if (!actor.isAlive()) {
					System.exit(0);
				}
		}

		// Redraw the new scene
		repaint();
	}

	/**
	 * Make the game and run it
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame app = new JFrame("Pets vs Birds Test");
				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				PetsVSBirds panel = new PetsVSBirds();
				app.setContentPane(panel);
				app.pack();
				app.setVisible(true);
			}

		});

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int clickX = e.getX();
		int clickY = e.getY();

		// can only place a pet if enough resources, resources then decreased
		if (resources >= 10) {
			Pet pet;

			// to find the center of the cell, divide by 100 and then multiply by 100 to get
			// a multiple of 100 (int rounds down) and then add 50 to find the middle
			if (selectedType == 0) {
				pet = new Dachshund(new Point2D.Double((clickX / 100) * 100 + 50, (clickY / 100) * 100 + 50));
				resources -= 10;
				resourceLabel.setText("- Resources " + resources);
			} else {
				pet = new Cat(new Point2D.Double((clickX / 100) * 100 + 50, (clickY / 100) * 100 + 50));
				resources -= 10;
				resourceLabel.setText("- Resources " + resources);
			}

			// check to make sure the cell is empty before placing the pet
			boolean isColliding = false;
			for (Actor actor : actors) {

				if (actor.isCollidingOther(pet)) {
					isColliding = true;
				}
			}

			if (!isColliding) {
				actors.add(pet);
			}

		}

		// adds resources when you click on the pet food and makes them disappear
		for (Actor actor : actors) {
			if (actor instanceof PetFood) {
				if (actor.isCollidingPoint(new Point2D.Double(clickX, clickY))) {
					actor.changeHealth(-50);
					resources += 7;
					resourceLabel.setText("- Resources " + resources);
				}
			}

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}