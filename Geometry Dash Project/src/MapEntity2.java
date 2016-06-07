import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class MapEntity2 implements DrawableObject {

	Platform stepBox; 
	int height = Gsing.get().me2H; 
	int length; 
	public MapEntity2(int length, Vector2 position) {
		this.length = length; 
		stepBox = new Platform(position, length, height); 
	}
	public void draw(GdxGraphics g) {
		stepBox.draw(g); 
	}
}
