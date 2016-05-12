import com.badlogic.gdx.graphics.Color;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

public class Ennemies implements DrawableObject {

	int xPos;
	int yPos;

	public Ennemies(int x, int y) {

		xPos = x;
		yPos = y;
	}

	public void draw(GdxGraphics g) {
		g.drawFilledCircle((float) xPos, (float) yPos,(float) g.getScreenHeight() / 50, Color.RED);
	}
}
