package com.braithiar.cherno.world;

import com.braithiar.cherno.Game;
import com.braithiar.cherno.graphics.Camera;
import com.braithiar.cherno.graphics.Screen;
import com.braithiar.cherno.world.tile.GrassTile;
import com.braithiar.cherno.world.tile.Tile;

public abstract class Level {
  private Camera cam;
  protected int mapWidth, mapHeight;
  protected int[] tiles;

  // Color constants for translating level map pixels to tiles
  private final static int GRASS_COLOR_ID = 0xFF00FF00;
  private final static int YELLOW_FLOWERS_COLOR_ID = 0xFFFFFF00;
  private final static int ROCK_COLOR_ID = 0xFF888888;
  private final static int WATER_COLOR_ID = 0xFF0000AA;
  private final static int ROUGH_WATER_COLOR_ID = 0xFF0000FF;
  private final static int DIRT_PATCH_COLOR_ID = 0xFF555555;

  public final static Level SPORG_LEVEL = new SporgLevel("/maps/test_map.png",
                                                         Game.WIDTH,
                                             Game.HEIGHT);

  public Level(String path) {
    loadLevel(path);

    cam = new Camera(Game.WIDTH, Game.HEIGHT, Screen.getTileSize(), mapWidth,
                     mapHeight);
  }

  protected abstract void loadLevel(String path);

  abstract void worldTime();

  public abstract void update();

  /**
   * Gets the map size (width * height).
   * 
   * @return the map size in units squared
   */
  public int getMapSize() {
    return mapWidth * mapHeight;
  }

  public void render(Screen screen) {
    int tileSize = Screen.getTileSize();
    int tilePower = Screen.getTilePower();
    int camX = cam.getCameraX();
    int camY = cam.getCameraY();

    screen.setOffset(camX, camY);

    int x0 = camX >> tilePower;
    // Add TILE_SIZE to mask tile loading at max mapWidth edge
    int x1 = (camX + screen.getWidth() + tileSize) >> tilePower;
    int y0 = camY >> tilePower;
    // Add TILE_SIZE to mask tile loading at max mapHeight edge
    int y1 = (camY + screen.getHeight() + tileSize) >> tilePower;

    for (int y = y0; y < y1; ++y) {
      for (int x = x0; x < x1; ++x) {
        getTile(x, y).render(x, y, screen);
      }
    }

  }

  public Camera getCamera() {
    return cam;
  }

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
