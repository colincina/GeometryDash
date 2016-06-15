import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.math.Vector2;


public class MapEntity2 implements DrawableObject {

	Platform stepBox; 
	HorizontalCollisionSensor hzSensor; 
	int height = Gsing.get().me2H; 
	Vector2 tempPos; 
	
	public MapEntity2(int length, Vector2 position) {
		tempPos = position; 
		stepBox = new Platform(tempPos, length, height); 
		tempPos.x -= length/2; 
		hzSensor = new HorizontalCollisionSensor(tempPos, 10, height - 10); 
		tempPos.x += length/2; 
	}
	
	public void draw(GdxGraphics g) {
		stepBox.draw(g); 
		hzSensor.draw(g); 
	}
}
