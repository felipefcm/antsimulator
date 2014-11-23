
package ffcm.antsim.event;

import java.util.HashMap;
import java.util.LinkedList;

public class EventManager
{
	public static EventManager _instance = new EventManager();
	
	private HashMap<String, LinkedList<IEventListener>> listenersMap;
	
	public EventManager()
	{
	}
	
	public boolean Subscribe(String eventName, final IEventListener listener)
	{
		
		
		return true;
	}
	
	public boolean Unsubscribe(String eventName, final IEventListener listener)
	{
		return true;
	}
}
