import java.util.Vector;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.utils.Logger;
import com.badlogic.gdx.graphics.Color;


public class Window extends PortableApplication{

	Vector<DrawableObject> toDraw = new Vector<DrawableObject>();
	
	public Window(){
		super(2000,1000);	
	}
	
	public void onGraphicRender(GdxGraphics g) {
		g.clear(); 
		g.setBackgroundColor(Color.BLUE); 
		for (DrawableObject obj : toDraw) {
			obj.draw(g); 
		}
	}

	public void onInit() {
		Logger.log("Welcome to the jumping cube game!"); 
		
		//add the cube
		toDraw.add(new Cube(100, 120)); 
		Logger.log("Cube has been created");
		
		//add the enemies
		int totalEnnemies = 0; 
		for(int i = 0; i < 20; i++){			
			toDraw.add(new Ennemies(100 + (int)(Math.random()*200) * i, (int)(Math.random()*200 + 500)));
			totalEnnemies++; 
		}
		Logger.log(totalEnnemies + "Ennemies have been created"); 
		
		//add the floor blocks
		int totalBlocks = 0; 
		for(int i = 0; i < 100; i++){			
			toDraw.add(new Decor(50+i*100, 50));
			totalBlocks++; 
		}
		Logger.log(totalBlocks + "Floor blocks have been created"); 
	}
	
	public static void main(String args[]){
		new Window(); 
	}
}
