package birdsVSPets;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Chicken extends Bird {
	public static final int HEALTH = 200;
	public static final int COOLDOWN = 75;
	public static final double SPEED = -1;
	public static final int ATTACK_DAMAGE = 8;
	public static final BufferedImage IMAGE;

	static {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("src/a10/Animal-Icons/chicken-icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		IMAGE = tempImage;
	}

	public Chicken(Double startingPosition) {
		super(startingPosition, new Point2D.Double(IMAGE.getWidth(), IMAGE.getHeight()), IMAGE, HEALTH, COOLDOWN, SPEED, ATTACK_DAMAGE);
	}
}
