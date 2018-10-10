package engine.management;

import implementation.Screen;

import java.util.ArrayList;
import java.util.List;

import util.Keyboard;
import util.LevelData;
import engine.level.Level;
import engine.level.LevelFactory;

public class LevelManager {
	
	private static LevelManager instance;
	private List<Level> levels;
	private Screen screen;
	private Keyboard keyboard;
	private Level currentLevel;
	
	public static LevelManager getInstance() {
		if(instance == null) {
			System.err.println("Level Manager has not been built yet...");
		}
		return instance;
	}
	
	public static void build(Screen screen, Keyboard keyboard) {
		if(instance == null) {
			instance = new LevelManager(keyboard, screen);
		}
	}
	
	private LevelManager(Keyboard keyboard, Screen screen) {
		this.keyboard = keyboard;
		this.screen = screen;
		levels = new ArrayList<Level>();
		loadLevels();
		currentLevel = levels.get(1);
		
	}
	
	private void loadLevels() {
		List<LevelData> leveldata = LevelData.getAllLevelData();
		for(int i = 0; i < leveldata.size(); i++) {
			levels.add(LevelFactory.createLevel(keyboard, screen, leveldata.get(i)));
		}
		System.out.println(levels.size() + " Levels were found and loaded.");
	}
	
	public void setCurrentLevel(Level level) {
		this.currentLevel = level;
	}
	
	public Level getCurrentLevel() {
		return currentLevel;
	}
	
	public Level getLevel(int index) {
		return levels.get(index);
	}
	
	public int getNumberOfLevels() {
		return levels.size();
	}
}
