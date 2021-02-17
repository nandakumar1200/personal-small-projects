package birdsVSPets;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

public class Pet extends Actor {

	public Pet(Point2D.Double startingPosition, Point2D.Double initHitbox, BufferedImage img, int health, int coolDown,
			int attackDamage) {
		super(startingPosition, initHitbox, img, health, coolDown, 0, attackDamage);
	}

	/**
	 * An attack means the two hitboxes are overlapping and the Actor is ready to
	 * attack again (based on its cooldown).
	 * 
	 * Pets only attack Cardinals.
	 * 
	 * @param other
	 */
	@Override
	public void attack(Actor other) {
		if (other instanceof Bird)
			super.attack(other);
	}
}
