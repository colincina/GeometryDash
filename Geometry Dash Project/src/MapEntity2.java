import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class MapEntity2 implements DrawableObject {

	PhysicsStaticBox stepBox; 
	int height; 
	int length; 
	public MapEntity2(int length, int height, Vector2 position) {
		this.height = height;
		this.length = length; 
		stepBox = new PhysicsStaticBox("stepBox", position, length, height, 0); 
	}
	public void draw(GdxGraphics g) {
		Vector2 pos = new Vector2(stepBox.getBodyWorldCenter());
		g.drawFilledRectangle(pos.x, pos.y, length, height, 0, Color.BLACK); 
	}
}
