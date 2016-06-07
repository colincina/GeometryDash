import javax.lang.model.SourceVersion;

import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.ScreenManager;
import ch.hevs.gdx2d.lib.utils.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MenuWindow extends RenderingScreen{

	Skin skin;
	Stage stage;
	TextButton controlPanelButton, startGameButton;
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

		controlPanelButton = new TextButton("Sound Parameters", skin); // Use the initialized skin
		controlPanelButton.setWidth(buttonWidth);
		controlPanelButton.setHeight(buttonHeight);

		startGameButton = new TextButton("Start Game", skin); // Use the initialized skin
		startGameButton.setWidth(buttonWidth);
		startGameButton.setHeight(buttonHeight);

		controlPanelButton.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, (int) (Gdx.graphics.getHeight() * .6));
		startGameButton.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, (int) (Gdx.graphics.getHeight() * .7));

		textArea = new TextField("Enter seed here", skin);
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
		stage.addActor(controlPanelButton);
		stage.addActor(startGameButton);
		stage.addActor(textArea);

		/**
		 * Register listener
		 */
		controlPanelButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				ScreenHub.s.transitionTo(3, ScreenManager.TransactionType.SLIDE);
			}
		});
		
		startGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				ScreenHub.s.transitionTo(1, ScreenManager.TransactionType.SMOOTH); 
			}
		});
		loop.loop(); 
	}
	
	public void onGraphicRender(GdxGraphics g) {
		g.clear(Color.BLACK); 
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

