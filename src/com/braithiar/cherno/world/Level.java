package com.braithiar.cherno.world;

import com.braithiar.cherno.Game;
import com.braithiar.cherno.graphics.Screen;
import com.braithiar.cherno.world.tile.GrassTile;
import com.braithiar.cherno.world.tile.Tile;

public abstract class Level {
  private final int MID_SCREEN_WIDTH, MID_SCREEN_HEIGHT, MAX_VIEW_WIDTH,
    MAX_VIEW_HEIGHT;
  protected int mapWidth, mapHeight;
  protected int[] tiles;
  private int cameraX = 0;
  private int cameraY = 0;
  private final int TILE_SIZE;
  private final int TILE_POWER;

  // Color constants for translating level map pixels to tiles
  private final static int GRASS_COLOR_ID = 0xFF00FF00;
  private final static int YELLOW_FLOWERS_COLOR_ID = 0xFFFFFF00;
  private final static int ROCK_COLOR_ID = 0xFF888888;
  private final static int WATER_COLOR_ID = 0xFF0000AA;
  private final static int ROUGH_WATER_COLOR_ID = 0xFF0000FF;
  private final static int DIRT_PATCH_COLOR_ID = 0xFF555555;

  public static Level sporg = new SporgLevel("/maps/test_map.png", Game.WIDTH,
                                             Game.HEIGHT);

  public Level(String path, int screenWidth, int screenHeight) {
    MID_SCREEN_WIDTH = screenWidth / 2;
    MID_SCREEN_HEIGHT = screenHeight / 2;
    TILE_SIZE = Screen.getTileSize();
    TILE_POWER = Screen.getTilePower();

    loadLevel(path);

    MAX_VIEW_WIDTH = TILE_SIZE * mapWidth;
    MAX_VIEW_HEIGHT = TILE_SIZE * mapHeight;
  }

  /**
   * Gets the map size (width * height).
   * 
   * @return the map size in units squared
   */
  public int getMapSize() {
    return mapWidth * mapHeight;
  }

  public void setCameraLocation(int x, int y) {
    cameraX = x;
    cameraY = y;
  }

  private void setCameraPlayerCenter(int playerX, int playerY) {
    cameraX = playerX - MID_SCREEN_WIDTH;
    cameraY = playerY - MID_SCREEN_HEIGHT;
  }

  public void setCamera(int playerX, int playerY) {
    int cameraX = playerX;
    int cameraY = playerY;

    cameraX = cameraX < 0 ? 0 : cameraX;
    cameraX = cameraX >= MAX_VIEW_WIDTH ? MAX_VIEW_WIDTH : cameraX;
    cameraY = cameraY < 0 ? 0 : cameraY;
    cameraY = cameraY >= MAX_VIEW_HEIGHT ? MAX_VIEW_HEIGHT : cameraY;

    if (cameraY < 0 || cameraY >= MAX_VIEW_HEIGHT) {
      cameraY = MAX_VIEW_HEIGHT;
    }

    setCameraPlayerCenter(cameraX, cameraY);
  }

  public int getCameraX() {
    return cameraX;
  }

  public int getCameraY() {
    return cameraY;
  }

  private boolean onMapEdge() {
    return false;
  }

  protected abstract void loadLevel(String path);

  abstract void worldTime();

  public abstract void update();

  public void render(Screen screen) {

    int x0 = getCameraX() >> TILE_POWER;
    // Add TILE_SIZE to mask tile loading at max mapWidth edge
    int x1 = (getCameraX() + screen.getWidth() + TILE_SIZE) >> TILE_POWER;
    int y0 = getCameraY() >> TILE_POWER;
    // Add TILE_SIZE to mask tile loading at max mapHeight edge
    int y1 = (getCameraY() + screen.getHeight() + TILE_SIZE) >> TILE_POWER;

    screen.setOffset(getCameraX(), getCameraY());
    for (int y = y0; y < y1; ++y) {
      for (int x = x0; x < x1; ++x) {
        getTile(x, y).render(x, y, screen);
      }
    }

  }

  /*
   * public void render(Screen screen) {
   * screen.setOffset(getCameraX(), getCameraY());
   * 
   * int x0 = getCameraX() >> TILE_POWER;
   * // Add TILE_SIZE to mask tile loading at max mapWidth edge
   * int x1 = (getCameraX() + screen.getWidth() + TILE_SIZE) >> TILE_POWER;
   * int y0 = getCameraY() >> TILE_POWER;
   * // Add TILE_SIZE to mask tile loading at max mapHeight edge
   * int y1 = (getCameraY() + screen.getHeight() + TILE_SIZE) >> TILE_POWER;
   * 
   * for (int y = y0; y < y1; ++y) {
   * for (int x = x0; x < x1; ++x) {
   * getTile(x, y).render(x, y, screen);
   * }
   * }
   * }
   */

  private Tile getTile(int x, int y) {
    int i = x + (y * mapWidth);

    if (i < 0 || i >= mapWidth * mapHeight) {
      return Tile.NULL_TILE;
    }

    switch (tiles[i]) {
      case GRASS_COLOR_ID:
        return GrassTile.getGrass(i);
      case YELLOW_FLOWERS_COLOR_ID:
        return Tile.YELLOW_FLOWERS_TILE;
      case ROCK_COLOR_ID:
        return Tile.ROCK_TILE;
      case WATER_COLOR_ID:
        return Tile.WATER_TILE;
      case ROUGH_WATER_COLOR_ID:
        return Tile.ROUGH_WATER_TILE;
      case DIRT_PATCH_COLOR_ID:
        return Tile.DIRT_PATCH_TILE;
      default:
        return Tile.NULL_TILE;
    }
  }
}
