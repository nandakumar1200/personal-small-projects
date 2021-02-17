package birdsVSPets;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Cardinal extends Bird {

	public static final int HEALTH = 75;
	public static final int COOLDOWN = 43;
	public static final double SPEED = -2;
	public static final int ATTACK_DAMAGE = 7;
	public static final BufferedImage IMAGE;

	static {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("src/a10/Animal-Icons/cardinal-icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		IMAGE = tempImage;
	}

	public Cardinal(Double startingPosition) {
		super(startingPosition, new Point2D.Double(IMAGE.getWidth(), IMAGE.getHeight()), IMAGE, HEALTH, COOLDOWN, SPEED, ATTACK_DAMAGE);
	}
	
	/**
	 * Increases speed of all other birds on the field upon death
	 */
	@Override 
	public void removeAction(ArrayList<Actor> others) {
		if (!this.isAlive()) {
			for (Actor actor: others) {
				if (actor instanceof Bird)
					actor.changeSpeed(-.5);
			}
		}
	}

}