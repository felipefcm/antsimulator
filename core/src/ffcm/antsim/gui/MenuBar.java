
package ffcm.antsim.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ffcm.antsim.AntSim;
import ffcm.antsim.AntWorld;
import ffcm.antsim.ai.SimulationState;
import ffcm.antsim.resource.Resources;
import ffcm.antsim.screen.SimulationScreen;

public class MenuBar
{
	public Stage stage;
	private Skin skin;

    private AntWorld world;
	
	public MenuBar()
	{
	}
	
	public void Init()
	{
		SpriteBatch spriteBatch = Resources.instance.spriteBatch;

		world = SimulationScreen.instance.antWorld;

		TextureAtlas atlas = Resources.instance.assetManager.get("ui/uiSprites.atlas", TextureAtlas.class);
		
		skin = new Skin(Gdx.files.internal("ui/ui_skin.json"), atlas);
		
		Table table = new Table(skin);
		//table.debug();

		table.setPosition(AntSim.V_WIDTH * 0.03f, AntSim.V_HEIGHT * 0.5f);

		stage = new Stage(Resources.instance.guiViewport, spriteBatch);
		stage.addActor(table);

		ImageButton createAntButton = new ImageButton(skin, "createAntButton");
		ImageButton getFoodButton = new ImageButton(skin, "getFoodButton");

		final float buttonScale = AntSim.V_HEIGHT * 0.1f;

		table.add(createAntButton).size(buttonScale).spaceBottom(5.0f).row();
		table.add(getFoodButton).size(buttonScale);//.row();

		createAntButton.addListener(
            new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    world.SpawnAntsFromNest(1);
                }
            }
        );

		getFoodButton.addListener(
            new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    MessageManager.getInstance().dispatchMessage(
                        0f, null, SimulationScreen.instance.stateMachine, SimulationState.SET_SELECT_ITEM_MSG
                    );
                }
            }
        );
    }

    public void Draw()
	{
		/*
		shapeRenderer.setProjectionMatrix(guiCamera.combined);
		shapeRenderer.setColor(Color.BLACK);
		
		shapeRenderer.begin(ShapeType.Line);
		{
			table.drawDebug(shapeRenderer);
		}
		shapeRenderer.end();
		*/

		stage.act();
		stage.draw();
	}
	
	public void Dispose()
	{
		if(stage != null)
			stage.dispose();
		
		if(skin != null)
			skin.dispose();
	}
}











