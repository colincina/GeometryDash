import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;


public class AirTimeObstacle extends PhysicsStaticBox implements DrawableObject {

	int width; 
	int height; 
	
	public AirTimeObstacle(Vector2 position, int width, int height) {
		super(null, position, width, height, 0);
		this.width = width; 
		this.height = height; 
	}

	public void draw(GdxGraphics g) {
		Vector2 pos = this.getBodyWorldCenter(); 
		g.drawFilledRectangle(pos.x, pos.y, width, height, this.getBodyAngleDeg(), Color.BLACK); 
	}
	
	
}
