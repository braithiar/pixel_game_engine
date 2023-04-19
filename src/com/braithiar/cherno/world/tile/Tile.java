package com.braithiar.cherno.world.tile;

import com.braithiar.cherno.graphics.Screen;
import com.braithiar.cherno.graphics.Sprite;

public abstract class Tile {
  // Static tile fields
  public final static Tile YELLOW_FLOWERS_TILE = new YellowFlowersTile(Sprite.YELLOW_FLOWERS);
  public final static Tile ROCK_TILE = new RockTile(Sprite.ROCK);
  public final static Tile GRASS_TILE = new GrassTile(Sprite.GRASS);
  public final static Tile ROUGH_WATER_TILE = new WaterTile(Sprite.WATER_ROUGH);
  public final static Tile WATER_TILE = new WaterTile(Sprite.WATER);
  public final static Tile DIRT_PATCH_TILE = new WaterTile(Sprite.DIRT_PATCH);
  public final static Tile NULL_TILE = new NullTile(Sprite.NULL_SPRITE);

  private int x, y;
  private final Sprite sprite;
  private int tilePower;

  public Tile(Sprite sprite) {
    this.sprite = sprite;
    tilePower = Screen.getTilePower();
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

  public Sprite getSprite() {
    return sprite;
  }

  /**
   * Returns a boolean that determines if the tile should have collision. The
   * default value returned is false. <strong>This method should be
   * overridden</strong> if true should be returned, or if any logic is needed
   * to determine true/false.
   * 
   * @return false if the tile has no collision
   */
  public boolean hasCollision() {
    return false;
  }

  public void render(int x, int y, Screen screen) {
    screen.renderTile(x << tilePower, y << tilePower, this);
  }
}
