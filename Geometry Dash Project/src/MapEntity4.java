import java.util.Vector;

import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;


public class MapEntity4 implements DrawableObject {

	HoleOfTheDamned hole; 
	Vector<DoubleJumpBox> boxes = new Vector<DoubleJumpBox>(); 
	Vector2 pos; 
	int highYPoint = 800; 
	int middleYPoint = 600; 
	int lowYPoint = 400;
	
	public MapEntity4(String name, Vector2 position, int length, int boxDim) {
		this.pos = position;
		hole = new HoleOfTheDamned("Hole", pos, length, 25); 
		hole.enableCollisionListener(); 
		hole.setSensor(true); 
		pos.x -= length/2 - 200; 
			
				for(int i = 0; i < 10; i++){
				pos.y = highYPoint; 
				DoubleJumpBox box1 = new DoubleJumpBox("Box group" + i, position, boxDim, boxDim, 0);
				boxes.add(box1); 
				pos.y = lowYPoint;
				DoubleJumpBox box2 = new DoubleJumpBox("Box group" + i, position, boxDim, boxDim, 0);
				boxes.add(box2); 
				pos.x += boxDim*4; 
				pos.y = middleYPoint; 
				DoubleJumpBox box3 = new DoubleJumpBox("Box group" + i, position, boxDim, boxDim, 0);
				boxes.add(box3); 
				pos.x += boxDim*4; 
			}
	}

	public void draw(GdxGraphics g) {
	
		for (DoubleJumpBox box : boxes) {
			box.draw(g); 
		}
		hole.draw(g); 
		
	}
}
