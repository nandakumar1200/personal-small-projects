package birdsVSPets;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Home extends Pet {
	public static final BufferedImage IMAGE;
	public static final int HEALTH = 1;
	public static final int COOLDOWN = 1000;
	public static final int ATTACK_DAMAGE = 0;
	
	static {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("src/a10/Animal-Icons/mansion.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		IMAGE = tempImage;
	}
	
	public int getHealth() {
		return HEALTH;
	}

	public Home(Double startingPosition) {
		super(startingPosition, new Point2D.Double(IMAGE.getWidth(), IMAGE.getHeight()), IMAGE, HEALTH, COOLDOWN, ATTACK_DAMAGE);
	}

}
