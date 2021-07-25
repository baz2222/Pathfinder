package com.baz2222.pathfinder.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.baz2222.pathfinder.Pathfinder;

public class TaskManager {
    public Array<Task> tasks;
    public Task tenLevelsComplete;
    public Task twentyLevelsComplete;
    public Task thirtyLevelsComplete;
    public Task fiveEnemiesKilled;
    public Task tenEnemiesKilled;
    public Task twentyEnemiesKilled;
    public Task wrappedThirtyTimes;
    public Task wrappedSixtyTimes;
    public Task wrapped120Times;
    public Task diedFiveTimes;
    public Task diedTenTimes;
    public Task diedTwentyTimes;
    public int taskCount;

    private Pathfinder game;

    public TaskManager(Pathfinder game) {
        this.game = game;
        taskCount = 12;
        tasks = new Array<>();
        tenLevelsComplete = new Task("Ten levels complete task completed!", "tasks/task-strip1.png");
        twentyLevelsComplete = new Task("Twenty levels complete task completed!", "tasks/task-strip2.png");
        thirtyLevelsComplete = new Task("Thirty levels complete task completed!", "tasks/task-strip3.png");
        fiveEnemiesKilled = new Task("Five enemies killed task completed!", "tasks/task-strip4.png");
        tenEnemiesKilled = new Task("Ten enemies killed task completed!", "tasks/task-strip5.png");
        twentyEnemiesKilled = new Task("Twenty enemies killed task completed!", "tasks/task-strip6.png");
        wrappedThirtyTimes = new Task("Wrapped thirty times task completed!", "tasks/task-strip7.png");
        wrappedSixtyTimes = new Task("Wrapped sixty times task completed!", "tasks/task-strip8.png");
        wrapped120Times = new Task("Wrapped 120 times task completed!", "tasks/task-strip9.png");
        diedFiveTimes = new Task("Died five times task completed!", "tasks/task-strip10.png");
        diedTenTimes = new Task("Died ten times task completed!", "tasks/task-strip11.png");
        diedTwentyTimes = new Task("Died twenty times task completed!", "tasks/task-strip12.png");
        tasks.add(tenLevelsComplete);
        tasks.add(twentyLevelsComplete);
        tasks.add(thirtyLevelsComplete);
        tasks.add(fiveEnemiesKilled);
        tasks.add(tenEnemiesKilled);
        tasks.add(twentyEnemiesKilled);
        tasks.add(wrappedThirtyTimes);
        tasks.add(wrappedSixtyTimes);
        tasks.add(wrapped120Times);
        tasks.add(diedFiveTimes);
        tasks.add(diedTenTimes);
        tasks.add(diedTwentyTimes);

    }

    public void checkForCompleted() {
        if (!tenLevelsComplete.completed && game.stateManager.completed >= 10) {
            tenLevelsComplete.completed = true;
            game.uiManager.showMessage(tenLevelsComplete.message);
        }
        if (!twentyLevelsComplete.completed && game.stateManager.completed >= 20) {
            twentyLevelsComplete.completed = true;
            game.uiManager.showMessage(twentyLevelsComplete.message);
        }
        if (!thirtyLevelsComplete.completed && game.stateManager.completed >= 30) {
            thirtyLevelsComplete.completed = true;
            game.uiManager.showMessage(thirtyLevelsComplete.message);
        }
        if (!fiveEnemiesKilled.completed && game.stateManager.killed >= 5) {
            fiveEnemiesKilled.completed = true;
            game.uiManager.showMessage(fiveEnemiesKilled.message);
        }
        if (!tenEnemiesKilled.completed && game.stateManager.killed >= 10) {
            tenEnemiesKilled.completed = true;
            game.uiManager.showMessage(tenEnemiesKilled.message);
        }
        if (!twentyEnemiesKilled.completed && game.stateManager.killed >= 20) {
            twentyEnemiesKilled.completed = true;
            game.uiManager.showMessage(twentyEnemiesKilled.message);
        }
        if (!wrappedThirtyTimes.completed && game.stateManager.wrapped >= 30) {
            wrappedThirtyTimes.completed = true;
            game.uiManager.showMessage(wrappedThirtyTimes.message);
        }
        if (!wrappedSixtyTimes.completed && game.stateManager.wrapped >= 60) {
            wrappedSixtyTimes.completed = true;
            game.uiManager.showMessage(wrappedSixtyTimes.message);
        }
        if (!wrapped120Times.completed && game.stateManager.wrapped >= 120) {
            wrapped120Times.completed = true;
            game.uiManager.showMessage(wrapped120Times.message);
        }
    }//check for completed

    public void resetProgress() {
        for (Task task : tasks) {
            task.completed = false;
        }
    }
}//task manager

class Task {
    public String message;
    public String taskIconURL;
    public boolean completed;
    public AssetManager manager;
    public Texture icon;

    public Task(String message, String taskIconURL) {
        this.message = message;
        this.taskIconURL = taskIconURL;
        this.completed = false;
        manager = new AssetManager();
        manager.load(taskIconURL, Texture.class);
        manager.finishLoading();
        icon = manager.get(taskIconURL, Texture.class);
    }
}//task
