package com.braithiar.cherno.entity;

import java.util.Random;

import com.braithiar.cherno.graphics.Screen;

public abstract class Entity {
  protected int x, y;
  private boolean removed;
  private final Random random;

  public Entity() {
    x = 0;
    y = 0;
    removed = false;
    random = new Random();
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public abstract void update();

  public abstract void render(Screen screen);

  public boolean isRemoved() {
    return removed;
  }

  public void remove() {
    removed = true;
  }
}
