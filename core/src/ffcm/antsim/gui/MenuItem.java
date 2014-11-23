
package ffcm.antsim.gui;

import java.util.LinkedList;

import ffcm.antsim.event.IEventListener;
import ffcm.antsim.event.IEventSender;

public class MenuItem implements IEventSender
{
	public LinkedList<IEventListener> listeners;
}
