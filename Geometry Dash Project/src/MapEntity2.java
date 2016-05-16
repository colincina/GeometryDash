import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class MapEntity2 implements DrawableObject {

	PhysicsStaticBox stepBox; 
	int height; 
	
	public MapEntity2(Vector2 position, int height) {
		this.height = height;
		stepBox = new PhysicsStaticBox("stepBox", position, 1200, height, 0); 
	}
	public void draw(GdxGraphics g) {
		Vector2 pos = new Vector2(stepBox.getBodyPosition());
		g.drawFilledRectangle(pos.x, pos.y, 1200, height, 0, Color.LIGHT_GRAY); 
	}
}
