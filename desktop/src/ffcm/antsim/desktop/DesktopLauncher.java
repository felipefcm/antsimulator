
package ffcm.antsim.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ffcm.antsim.AntSim;

public class DesktopLauncher 
{
	public static void main(String[] arg) 
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Ant Simulator";
		config.width = (int)(AntSim.V_WIDTH * AntSim.DESKTOP_SCALE);
		config.height = (int)(AntSim.V_HEIGHT * AntSim.DESKTOP_SCALE);
		config.vSyncEnabled = false;
		
		config.x = -1;
		config.y = -1;
		
		new LwjglApplication(new AntSim(), config);
	}
}
