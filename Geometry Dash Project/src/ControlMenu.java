import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.ScreenManager;
import ch.hevs.gdx2d.lib.utils.Logger;


public class ControlMenu extends RenderingScreen{
	
	Skin skin;
	Stage stage;
	TextButton soundOnOffButton, backToMenuButton;
	Slider fxVolSlider, musicVolSlider; 
	TextField textArea;
	int stateCounter = 0; 
	String state; 
	SoundSample loop; 
	BitmapImage bckgrnd; 

	public static void main(String[] args) {
		new MenuWindow();
	}

	
	public void onInit(){
		
		bckgrnd = new BitmapImage("data/images/CrownNebula.bmp/");
		int buttonWidth = 540;
		int buttonHeight = 90;
		loop = new SoundSample("data/sounds/Space Cube.wav"); 

		stage = new Stage();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(Gdx.input.getInputProcessor());
        Gdx.input.setInputProcessor(multiplexer);

		// Load the default skin (which can be configured in the JSON file)
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));

		soundOnOffButton = new TextButton("Sound Off", skin); // Use the initialized skin
		soundOnOffButton.setWidth(buttonWidth);
		soundOnOffButton.setHeight(buttonHeight);

		backToMenuButton = new TextButton("Back to main menu", skin); // Use the initialized skin
		backToMenuButton.setWidth(buttonWidth);
		backToMenuButton.setHeight(buttonHeight);
		
		musicVolSlider = new Slider(0, 100, 1, false, skin); 
		musicVolSlider.setWidth(buttonWidth); 

		
		fxVolSlider = new Slider(0, 100, 1, false, skin); 
		fxVolSlider.setWidth(buttonWidth); 

		soundOnOffButton.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, (int) (Gdx.graphics.getHeight() * .6));
		backToMenuButton.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, (int) (Gdx.graphics.getHeight() * .7));
		musicVolSlider.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, (int)(Gdx.graphics.getHeight()/2));
		fxVolSlider.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, (int)(Gdx.graphics.getHeight()/2) - 50);

		textArea = new TextField("Enter your name...", skin);
		textArea.setWidth(buttonWidth);
		textArea.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, (int) (Gdx.graphics.getHeight() * .4));

		textArea.setTextFieldListener(new TextFieldListener() {
			public void keyTyped(TextField textField, char key) {
				textArea.setSelection(0, 0);

				// When you press 'enter', do something
				if (key == 13)
					Logger.log("You have typed " + textArea.getText());
			}
		});
		
		/**
		 * Adds the buttons to the stage
		 */
		stage.addActor(soundOnOffButton);
		stage.addActor(backToMenuButton);
		stage.addActor(fxVolSlider); 
		stage.addActor(musicVolSlider); 
		stage.addActor(textArea);

		/**
		 * Register listener
		 */
		soundOnOffButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
					
					if(ControlMenu.this.backToMenuButton.isChecked()){
						state = "On"; 
					}
					if(!ControlMenu.this.backToMenuButton.isChecked())
					{
						state = "Off"; 
					}
					stateCounter++; 
					ControlMenu.this.backToMenuButton.setText("Sound " + state);
			}
		});
		
		backToMenuButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				ScreenHub.s.transitionTo(0, ScreenManager.TransactionType.SMOOTH); 

			}
		});

		musicVolSlider.addListener(new ChangeListener() {
			
				@Override
				public void changed(ChangeEvent e, Actor arg1) {
				ParamSingleton.getinstance().musicLevel = musicVolSlider.getValue()/100;
			}
		}); 
		
		fxVolSlider.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent e, Actor fxVolSlider) {
				ParamSingleton.getinstance().fxLevel = musicVolSlider.getValue()/100; 
			}
		});
		
		loop.loop(); 
	}
	
	
	public void onGraphicRender(GdxGraphics g) {
		g.clear(); 
		g.drawBackground(bckgrnd, 0, 0); 
		
		// This is required for having the GUI work properly
		stage.act();
		stage.draw();

//		g.drawStringCentered(getWindowHeight() / 4, "Colin Cina & Martin Juon");
		g.drawSchoolLogo();
		g.drawFPS();
	}

	@Override
	public void dispose() {
		loop.stop(); 
		loop.dispose(); 
		bckgrnd.dispose(); 
		stage.dispose(); 
		super.dispose();
	}
}
