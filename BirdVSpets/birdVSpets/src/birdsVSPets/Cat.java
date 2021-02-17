package birdsVSPets;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Cat extends Pet {

	public static final int HEALTH = 75;
	public static final int COOLDOWN = 40;
	public static final int ATTACK_DAMAGE = 10;
	public static final BufferedImage IMAGE;
	
	static {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("src/a10/Animal-Icons/cat-icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		IMAGE = tempImage;
	}

	public Cat(Double startingPosition) {
		super(startingPosition, new Point2D.Double(IMAGE.getWidth(), IMAGE.getHeight()), IMAGE, HEALTH, COOLDOWN, ATTACK_DAMAGE);
	}
	
	@Override
	public void removeAction(ArrayList<Actor> others) {
		if (!this.isAlive()) {
			for (Actor actor: others) {
				if (actor instanceof Pet)
					actor.changeHealth(7);
			}
		}
	}

}
