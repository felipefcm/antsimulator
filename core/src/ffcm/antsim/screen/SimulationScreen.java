
package ffcm.antsim.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ffcm.antsim.AntWorld;
import ffcm.antsim.ai.SimulationState;
import ffcm.antsim.gui.MenuBar;
import ffcm.antsim.input.AppInput;
import ffcm.antsim.resource.Resources;
import ffcm.ecs.ECSManager;

public class SimulationScreen implements Screen
{
    public static SimulationScreen instance;

    public AntWorld antWorld;

	private MenuBar menuBar;

	private SpriteBatch spriteBatch;
	private BitmapFont font;

	public StateMachine<SimulationScreen> stateMachine;

    @Override
    public void show()
    {
        instance = this;

        spriteBatch = Resources.instance.spriteBatch;
        font = Resources.instance.font;

        antWorld = new AntWorld();

        menuBar = new MenuBar();
		menuBar.Init();

		stateMachine = new DefaultStateMachine<>(this, SimulationState.IDLE);
		stateMachine.setGlobalState(SimulationState.GLOBAL);

        OrthographicCamera mainCamera = Resources.instance.mainCamera;

        mainCamera.zoom = 2.0f;
		mainCamera.translate(antWorld.terrain.mapSizePixels.x * 0.195f, antWorld.terrain.mapSizePixels.y * 0.2f);
		mainCamera.update();

        Gdx.input.setInputProcessor(new InputMultiplexer(menuBar.stage, new AppInput(antWorld)));
    }

    @Override
    public void render(float delta)
    {
        Update();

		DrawGUI();
    }

    private void Update()
	{
        stateMachine.update();

		antWorld.Update();
		antWorld.Draw();

        ECSManager.instance.Update();
	}

	private void DrawGUI()
	{
		spriteBatch.setProjectionMatrix(Resources.instance.guiCamera.combined);
		spriteBatch.begin();
		{
			font.draw(spriteBatch, Gdx.graphics.getFramesPerSecond() + " | " + antWorld.numAnts, 10.0f, 20.0f);
		}
		spriteBatch.end();

		menuBar.Draw();
	}

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void hide()
    {
        dispose();
    }

    @Override
    public void dispose()
    {
        if(menuBar != null)
            menuBar.Dispose();

        if(antWorld != null)
            antWorld.Dispose();
    }
}
