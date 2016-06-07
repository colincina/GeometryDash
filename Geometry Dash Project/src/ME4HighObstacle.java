import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;


public class ME4HighObstacle extends ME4Obstacle implements DrawableObject {

	ME4Sensor sensor; 
	Platform platform; 
    	
	public ME4HighObstacle(Vector2 position){
		super(position); 
		platform = new Platform(position, height, width); 
		pos.y -= height; 
		sensor  = new ME4Sensor(position);
	}
	
	public void draw(GdxGraphics g) {
		sensor.draw(g); 
		platform.draw(g); 
	}

}
