package engine.entities.mobs;

import java.util.ArrayList;
import java.util.List;

import util.Environment;
import util.Keyboard;
import util.Mouse;
import util.Stats;
import engine.behaviors.Collideable;
import engine.behaviors.ItemObserver;
import engine.behaviors.Weapon;
import engine.behaviors.move.KeyboardControlled;
import engine.entities.items.Item;
import engine.entities.weapons.WatermelonLauncher;
import engine.graphics.Sprite;
import engine.management.Mediator;

public class Player extends Mob implements ItemObserver {
	
	private Keyboard keyboard;
	private Weapon weapon;
	private List<Item> inventory;
	
	public Player(Keyboard keyboard) {
		super(Environment.getInstance().getWidth()/2, Environment.getInstance().getHeight()/2, Sprite.PLAYER);
		initialize(keyboard);
	}
	
	public Player(double x, double y, Keyboard keyboard) {
		super(x, y, Sprite.PLAYER);
		initialize(keyboard);
	}
	
	public Player(double x, double y, Sprite sprite, Keyboard keyboard) {
		super(x, y, sprite);
		initialize(keyboard);
	}
	
	private void initialize(Keyboard keyboard) {
		this.keyboard = keyboard;
		baseSpeed = Stats.PLAYER_SPEED;
		this.setMoveBehavior(new KeyboardControlled(keyboard));
		this.weapon = new WatermelonLauncher(this);
		this.x -= this.getWidth()/2;
		this.y -= this.getHeight()/2;
		inventory = new ArrayList<Item>();
		Mediator.getInstance().addItemObserver(this);
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public void update() {
		keyboard.update();
		handleShooting();
		super.update();
		
	}
	
	private void handleShooting() {
		if(Mouse.getB() == 1) {
			double mx = Mouse.getXWithOffset();
			double my = Mouse.getYWithOffset();
			double angle = angleTo(mx, my);
			weapon.shoot(angle);
		}
	}

	public void notifyOfItems(List<Item> items) {
		for(int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if(item instanceof Collideable) {
				if(collides((Collideable)item)) {
					item.setOwner(this);
					this.inventory.add(item);
					Mediator.getInstance().remove(item);
				}
			}
		}
	}
}
