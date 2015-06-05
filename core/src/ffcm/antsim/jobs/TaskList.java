package ffcm.antsim.jobs;

import com.badlogic.gdx.utils.SortedIntList;
import com.badlogic.gdx.utils.SortedIntList.Node;

import java.util.Iterator;

public class TaskList
{
    private SortedIntList<Task> tasks;

    public TaskList()
    {
        tasks = new SortedIntList<>();
    }

    public void AddTask(Task task)
    {
        tasks.insert(task.priority, task);
    }

    public Iterator<Node<Task>> GetIterator()
    {
        return tasks.iterator();
    }
}
