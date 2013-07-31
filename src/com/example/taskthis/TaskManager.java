package com.example.taskthis;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

	private Task selectedTask;
	private List<Task> tasks;
	private long id;

	private static TaskManager instance = new TaskManager();

	private TaskManager() {
		this.tasks = new ArrayList<Task>();
	}

	public Task getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(Task selectedTask) {
		this.selectedTask = selectedTask;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public static TaskManager getInstance() {
		if (instance == null) {
			return new TaskManager();
		}
		return instance;
	}

	public long getId() {
		return id;
	}

	public void addTask(Task task) {
		if (task != null && !tasks.contains(task)) {
			tasks.add(task);
		}
	}

	public void removeTask(Task task) {
		if (task != null && tasks.contains(task)) {
			tasks.remove(task);
		}
	}

	public long increaseId() {
		return ++id;
	}

	public void refreshSelectedTask() {
		if (tasks.contains(this.selectedTask)) {
			tasks.remove(this.selectedTask);
			tasks.add(this.selectedTask);
		}
	}

	public void removeSelectedTask() {
		removeTask(this.selectedTask);
	}

	public int amountDoing() {
		int result = 0;
		for (Task t : tasks) {
			if (t.getStatus().equals(Status.DOING)) {
				result++;
			}
		}
		return result;
	}
}
