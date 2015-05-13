
package ffcm.antsim.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ffcm.antsim.World;
import ffcm.antsim.gui.MenuBar;
import ffcm.antsim.input.AppInput;
import ffcm.antsim.resource.Resources;
import ffcm.ecs.ECSManager;

public class SimulationScreen implements Screen
{
    public World world;

    private AppInput appInput;

	private MenuBar menuBar;

	private SpriteBatch spriteBatch;
	private BitmapFont font;

    @Override
    public void show()
    {
        spriteBatch = Resources.instance.spriteBatch;
        font = Resources.instance.font;

        world = new World();

        menuBar = new MenuBar();
		menuBar.Init();

		appInput = new AppInput(world);

		Gdx.input.setInputProcessor(new InputMultiplexer(menuBar.stage, appInput));
    }

    @Override
    public void render(float delta)
    {
        Update();

		DrawGUI();
    }

    private void Update()
	{
		world.Update();
		world.Draw();

        ECSManager.instance.Update();
	}

	private void DrawGUI()
	{
		spriteBatch.setProjectionMatrix(Resources.instance.guiCamera.combined);
		spriteBatch.begin();
		{
			font.draw(spriteBatch, Gdx.graphics.getFramesPerSecond() + " | " + world.numAnts, 10.0f, 20.0f);
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
    }

    @Override
    public void dispose()
    {
    }
}
