import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;


public class StartPlatform implements DrawableObject{

	int width;
	PhysicsStaticBox startPlatform; 
	
	public StartPlatform(int width) {
		this.width = width;
		startPlatform = new PhysicsStaticBox("Start!", new Vector2(300, 50), width, 100, 0); 
	}
	public void draw(GdxGraphics g) {
		
		Vector2 pos = new Vector2(startPlatform.getBodyPosition()); 
		g.drawFilledRectangle(pos.x, pos.y, width, 100, 0, Color.GREEN); 	
	}

}
