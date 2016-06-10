import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;


public class Platform extends PhysicsStaticBox implements DrawableObject{

	int width; 
	int height; 
	int lHeight = Gsing.get().lightHeight; 
	boolean drawSuperFancyLine = false; 
	
	public Platform(Vector2 position, int width, int height) {
		super(null, position, width, height, 0);
		this.width = width; 
		this.height = height; 
	}

	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		super.collision(theOtherObject, energy);
		if (theOtherObject.getClass() == Cube.class) {
			drawSuperFancyLine = true; 
		}
	}
	
	public void draw(GdxGraphics g) {
		
		Vector2 pos = this.getBodyWorldCenter(); 
		g.drawFilledRectangle(pos.x, pos.y, width, height, 0, Color.BLACK); 
		
		if(drawSuperFancyLine){
			g.drawFilledRectangle(pos.x, pos.y + height/2 - 20, this.width, this.lHeight, 0, Color.PURPLE); 
		}
		
	}
}
