import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class MapEntity1 implements DrawableObject {

	HoleOfTheDamned hole; 
	PhysicsStaticBox platform; 
	Vector2 pos = new Vector2(0, 50); 
	int platformLength; 

	public MapEntity1(int platformLength, int holeWidth, Vector2 holePosition) {
		
		this.platformLength = platformLength; 
		hole = new HoleOfTheDamned("hole", holePosition, holeWidth, 80); 
		hole.setSensor(true); 
		hole.enableCollisionListener();
		pos.x += (holePosition.x + holeWidth/2 + platformLength/2); 
		platform = new PhysicsStaticBox("platform", pos, platformLength, 100);
	}


	public void draw(GdxGraphics g) {
		Vector2 drawPos = new Vector2(platform.getBodyWorldCenter());
		g.drawFilledRectangle(drawPos.x, drawPos.y, platformLength, 100, 0, Color.LIGHT_GRAY); 
		hole.draw(g); 
	}
}
