import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class MapEntity1 implements DrawableObject {

	HoleOfTheDamned hole; 
	PhysicsStaticBox platform; 
	Vector2 pos; 
	//to make the width of the platform random
	//int platformWidth;

	public MapEntity1(Vector2 position, int holeWidth) {
		this.pos = position; 
		hole = new HoleOfTheDamned("hole", pos, holeWidth); 
//		hole.setSensor(true); 
//		hole.enableCollisionListener(); 
		pos.x += 600; 
		//platformWidth = (int)(Math.random()*1000) + 200;
		platform = new PhysicsStaticBox("platform", pos, 1000, 100);
	}


	public void draw(GdxGraphics g) {
		Vector2 drawPos = new Vector2(platform.getBodyPosition());
		g.drawFilledRectangle(drawPos.x, drawPos.y, 1000, 100, 0, Color.LIGHT_GRAY); 
		hole.draw(g); 
	}
}
