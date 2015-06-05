package ffcm.antsim.jobs;

import com.badlogic.gdx.utils.BinaryHeap;

public class TaskList
{
    private BinaryHeap<Task> jobs;

    public TaskList()
    {
        jobs = new BinaryHeap<>();
    }
}
