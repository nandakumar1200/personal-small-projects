package birdsVSPets;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;




public class PetFood extends Actor {
	
	public static final int HEALTH = 50;
	public static final int COOLDOWN = 0;
	public static final double SPEED = -3;
	public static final int ATTACK_DAMAGE = 12;
	public static final BufferedImage IMAGE;
	
	static {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("src/a10/Animal-Icons/PetFood-icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		IMAGE = tempImage;
	}

	public PetFood(Double startingPosition) {
		super(startingPosition, new Point2D.Double(IMAGE.getWidth(), IMAGE.getHeight()), IMAGE, HEALTH, COOLDOWN, SPEED, ATTACK_DAMAGE);
	}
	
	public int getHealth() {
		return HEALTH;
	}
	
	public void attack(Actor other) {
	}
}
