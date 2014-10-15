
package ffcm.antsim.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import ffcm.antsim.gui.MenuBar;

public class GuiInput extends InputAdapter
{
	private MenuBar menuBar;
	
	public GuiInput(MenuBar menuBar)
	{
		this.menuBar = menuBar; 
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		if(menuBar.IsPointInMenuBar(new Vector2(screenX, screenY)))
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) 
	{
		if(menuBar.IsPointInMenuBar(new Vector2(screenX, screenY)))
		{
			return true;
		}
		
		return false;
	}
}
