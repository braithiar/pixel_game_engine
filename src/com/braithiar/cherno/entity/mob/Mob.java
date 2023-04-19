package com.braithiar.cherno.entity.mob;

import com.braithiar.cherno.entity.Entity;
import com.braithiar.cherno.graphics.Sprite;

public abstract class Mob extends Entity {
  private Sprite sprite;
  private int direction;
  private boolean moving;

  public Mob(Sprite sprite) {
	this.sprite = sprite;
	direction = 0;
	moving = false;
  }

  public void setSprite(Sprite sprite) {
	this.sprite = sprite;
  }

  public Sprite getSprite() {
	return sprite;
  }

  public int getDirection() {
	return direction;
  }

  public void setMoving(boolean moving) {
	this.moving = moving;
  }

  public boolean isMoving() {
	return moving;
  }

  public void move(int moveX, int moveY) {
	if (moveX > 0) {
	  direction = 1;
	}
	if (moveX < 0) {
	  direction = 3;
	}
	if (moveY > 0) {
	  direction = 0;
	}
	if (moveY < 0) {
	  direction = 2;
	}

	if (!isColliding()) {
	  x += moveX;
	  y += moveY;
	}
  }

  /**
   * Returns a boolean that determines if the mob is colliding with another
   * object. The default value returned is false. <strong>This method should be
   * overridden</strong> if true should be returned, or if any logic is needed
   * to determine true/false.
   * 
   * @return false if the mob is not colliding with another object
   */
  public boolean isColliding() {
	return false;
  }
}
