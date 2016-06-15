import ch.hevs.gdx2d.components.bitmaps.Spritesheet;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

	
	public class HoleOfTheDamned extends PhysicsStaticBox implements DrawableObject {
		
		int width; 
		float dt; 
		float frameTime = 0.1f; 
		int currentFrame = 0; 
		int currentRow = 0; 
		int nFrames = 4; 
		boolean playerDiedInMe = false; 
		JumpDetector detector; 
		Spritesheet laser; 
		
		public HoleOfTheDamned(int width, Vector2 position) {
			super(null, position, width, Gsing.get().holeHeight, 0);
			detector = new JumpDetector(new Vector2(position.x - width/2 + Gsing.get().detectorW/2 + Gsing.get().cSize, position.y + Gsing.get().detectorH/2)); 
			this.width = width; 
			laser = new Spritesheet("data/Spritesheets/LaserWaveColorsSpritesheet.png", 465, 60); 
			currentRow = (int)(Math.random()*3); 
		}
	
		public void updateGraphics(GdxGraphics g){
			
			dt += Gdx.graphics.getDeltaTime();

			//Selects the right field of the SpriteSheet 
			
				if (dt > frameTime) {
					dt = 0;
					currentFrame = (currentFrame + 1) % nFrames;
					if(currentFrame == 4){
//						currentRow++; 
						currentFrame = 0; 
					}
					
//					if(currentRow == 3){
//						currentRow = 0; 
//					}
				}
		}
		
		public void draw(GdxGraphics g) {
			
			updateGraphics(g); 
			Vector2 pos = this.getBodyWorldCenter();
//			g.drawFilledRectangle(pos.x, pos.y, width, Gsing.get().holeHeight, 0, Color.RED);
			g.draw(laser.sprites[currentRow][currentFrame], pos.x - 232.5f , pos.y - 30);

		}
	}
