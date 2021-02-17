package birdsVSPets;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Dachshund extends Pet {
	
	public static final int HEALTH = 150;
	public static final int COOLDOWN = 34;
	public static final int ATTACK_DAMAGE = 12;
	public static final BufferedImage IMAGE;
	
	static {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("src/a10/Animal-Icons/dachshund-icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		IMAGE = tempImage;
	}

	public Dachshund(Double startingPosition) {
		super(startingPosition, new Point2D.Double(IMAGE.getWidth(), IMAGE.getHeight()), IMAGE, HEALTH, COOLDOWN, ATTACK_DAMAGE);
	}

}
