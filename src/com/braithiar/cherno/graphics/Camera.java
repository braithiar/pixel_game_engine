package com.braithiar.cherno.graphics;

/**
 * A <code>Camera</code> class, intended for use with {@link Level}.
 * Creates a <code>Camera</code> object that can be moved about the rendered
 * map. There are three methods that allow for the setting of the camera's
 * position:
 * 
 * {@link #setCameraLocation(int, int)}
 * {@link #setCameraPlayerCenter(int, int)}
 * {@link #setCamera(int, int)}
 * 
 * The coordinates provided to position the camera are ultimately used to set
 * the offsets for the rendering of map tiles and sprites.
 * 
 * @author JT Paxton
 * @see Screen#setOffset(int, int)
 * @see Screen#renderTile(int, int, com.braithiar.cherno.world.tile.Tile)
 * @see Screen#renderPlayer(int, int, com.braithiar.cherno.graphics.Screen.Mirror, Sprite)
 */
public class Camera {
  private final int MID_SCREEN_WIDTH, MID_SCREEN_HEIGHT, MAX_VIEW_WIDTH,
    MAX_VIEW_HEIGHT, MAX_CAMERA_X, MAX_CAMERA_Y;
  private int cameraX = 0;
  private int cameraY = 0;

  /**
   * Creates a <code>Camera</code> object.
   * 
   * @param screenWidth  the width of the screen used for rendering
   * @param screenHeight the height of the screen used for rendering
   * @param tileSize     the length, in pixels, of one edge of a tile square
   * @param mapWidth     the width of the loaded map
   * @param mapHeight    the height of the loaded map
   */
  public Camera(int screenWidth, int screenHeight, int tileSize, int mapWidth,
                int mapHeight) {
    MID_SCREEN_WIDTH = screenWidth / 2;
    MID_SCREEN_HEIGHT = screenHeight / 2;
    MAX_VIEW_WIDTH = tileSize * mapWidth;
    MAX_VIEW_HEIGHT = tileSize * mapHeight;
    MAX_CAMERA_X = MAX_VIEW_WIDTH - MID_SCREEN_WIDTH;
    MAX_CAMERA_Y = MAX_VIEW_HEIGHT - MID_SCREEN_HEIGHT;
  }

  /**
   * Moves the camera to the provided (x, y) coordinates. This method does not
   * check if the coordinates are within the bounds of the screen or map.
   * 
   * @param x the x coordinate where the camera should be placed
   * @param y the y coordinate where the camera should be placed
   */
  public void setCameraLocation(int x, int y) {
    cameraX = x;
    cameraY = y;
  }

  /**
   * Centers the camera around the player sprite at the provided coordinates.
   * Does not guarantee that the player sprite is at the coordinates provided,
   * nor does it check if the coordinates are within the bounds of the screen or
   * loaded map.
   * 
   * @param playerX the x coordinate where the player sprite is located
   * @param playerY the y coordinate where the player sprite is located
   */
  public void setCameraPlayerCenter(int playerX, int playerY) {
    cameraX = playerX - MID_SCREEN_WIDTH;
    cameraY = playerY - MID_SCREEN_HEIGHT;
  }

  /**
   * Ultimately calls {@link #setCameraPlayerCenter(int, int)}. In addition
   * to centering the camera around the player sprite, this method binds the
   * camera to the screen and loaded map. <strong>Intended as the primary
   * camera</strong> for {@link Level}.
   * 
   * 
   * @param playerX the x coordinate where the player sprite is located
   * @param playerY the y coordinate where the player sprite is located
   */
  public void setCamera(int playerX, int playerY) {
    int cameraX = playerX;
    int cameraY = playerY;

    cameraX = cameraX < MID_SCREEN_WIDTH ? MID_SCREEN_WIDTH : cameraX;
    cameraX = cameraX >= MAX_CAMERA_X ? MAX_CAMERA_X : cameraX;
    cameraY = cameraY < MID_SCREEN_HEIGHT ? MID_SCREEN_HEIGHT : cameraY;
    cameraY = cameraY >= MAX_CAMERA_Y ? MAX_CAMERA_Y : cameraY;

    setCameraPlayerCenter(cameraX, cameraY);
  }

  public int getCameraX() {
    return cameraX;
  }

  public int getCameraY() {
    return cameraY;
  }
}
