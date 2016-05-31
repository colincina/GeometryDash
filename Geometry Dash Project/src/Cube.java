import java.util.LinkedList;
import java.util.Random;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import com.badlogic.gdx.math.Vector2;

 
public class Cube extends PhysicsBox implements DrawableObject {
	
	Vector2 pos;
	
	boolean jump = false; 
	boolean grounded = true; 
	boolean isHurt = false; 
	boolean cubeDead = false; 
	
	int size; 
	int particleamount = 50;
	
	float speed = 40f;
	float impulse; 
	
	long creationTime; 
	
	BitmapImage cubeSkin; 
	LinkedList<Particle> particles;
	
	public Cube(Vector2 position, int size, BitmapImage skin){
		
		super("Cube", position, size, size, 15f, 0, 0);
		
		this.size = size;
		this.cubeSkin = skin; 
		this.getBody().setFixedRotation(true); 
		this.setCollisionGroup(-1); 
		this.getBody().getFixtureList().get(0).setRestitution(0);
		impulse = this.getBodyMass()*15; 
		particles = new LinkedList<Particle>(); 
	}
	
	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		
		super.collision(theOtherObject, energy);
		
		if(theOtherObject.getClass() == PhysicsStaticBox.class){
			grounded = true; 
		}
		if(theOtherObject.getClass() == Obstacle.class){
			isHurt = true; 
			cubeDead = true; 
		}
		
		System.out.println("Collided with " + theOtherObject);
	}
	
	public void update(){
		
		if(!cubeDead){
			//linear x movement of the cube 
			this.applyBodyForceToCenter(speed, 0, true); 
			this.setBodyLinearDamping(0.4f); 
		}
		pos = this.getBodyWorldCenter(); 
		
		if(jump && grounded){
			jump = false; 
			grounded = false; 
			this.applyBodyLinearImpulse(new Vector2(0, impulse), pos, true); 
		}
	
		if(System.nanoTime() - creationTime > 1000000000){
			
			while(!particles.isEmpty()){
				Particle p = particles.poll();
				p.destroy(); 
			}
		}
	}
	
	public void draw(GdxGraphics g) {
		
		update(); 
		Vector2 centerPos = this.getBodyWorldCenter(); 
			g.drawTransformedPicture(centerPos.x, centerPos.y, 0, size/2, size/2, cubeSkin);
		
		for (Particle particle : particles) {
			particle.draw(g); 
		}
	}

	public void emitParticle(){
		
			
			int impulsIntensity = 10;  
			int angle = 0; 
			long seed = (long)(Math.random() * 10000);
			
			Vector2 impulse = new Vector2(0, 0); 
			Random r = new Random(seed); 
			
			isHurt = false; 
			creationTime = System.nanoTime();
			
			for(int i = 0; i < particleamount; i++){
			int size = (int)(r.nextDouble()*20) + 5; 
			Particle aParticle = new Particle("Particle" , Cube.this.getBodyWorldCenter(), size, (long)(Math.random()*10000));
			aParticle.setCollisionGroup(-1);
			particles.add(aParticle); 
			Vector2 pos = this.getBodyWorldCenter();
			impulse.x = (float) Math.sin(angle)*impulsIntensity; 
			impulse.y = (float) Math.cos(angle)*impulsIntensity; 
			aParticle.applyBodyLinearImpulse(impulse, pos, true); 
			angle += 180/particleamount;
		}
}
}

