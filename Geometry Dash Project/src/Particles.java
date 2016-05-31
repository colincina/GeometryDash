import java.util.Vector;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

import com.badlogic.gdx.math.Vector2;


public class Particles implements DrawableObject{

	int amount; 
	int size; 
	
	long seed; 
	
	Vector2 intPos;
	Vector<Particle> particleCollection = new Vector<Particle>(); 
	
	public Particles(String name, Vector2 position, int size, int amount, long seed) {
		this.amount = amount; 
		this.size = size; 
		this.intPos = position; 
		this.seed = seed; 
	}

	public void buildParticles(int n){
			if(n == 0){
				particleCollection.add(new Particle("Particle", intPos, size, seed)); 
			}
			else{
				modifyIntPos(intPos);
				buildParticles(n-1); 
			}
	}

	public Vector2 modifyIntPos(Vector2 pos){
		
		int modifier = (int)(Math.random()*10); 
		
		switch (modifier) {
		case 1:
			pos.x += size;
			break;
		case 2:
			pos.x -= size;
			break;
		case 3:
			pos.y += size;
			break;
		case 4:
			pos.y -= size;
			break;
		case 5:
			pos.x += size;
			pos.y += size;
			break;
		case 6:
			pos.x += size;
			pos.y -= size;
			break;
		case 7:
			pos.x -= size;
			pos.y += size;
			break;
		case 8:
			pos.x -= size;
			pos.y -= size;
			break;
		
		default:
			break;
		}
	return pos; 
	}

	public void draw(GdxGraphics g) {
		for (Particle p : particleCollection) {
			p.draw(g); 
		}
	}

}