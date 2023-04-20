package com.braithiar.cherno.graphics;

public class Camera {
  private final int MID_SCREEN_WIDTH, MID_SCREEN_HEIGHT, MAX_VIEW_WIDTH,
    MAX_VIEW_HEIGHT, MAX_CAMERA_X, MAX_CAMERA_Y;
  private int cameraX = 0;
  private int cameraY = 0;

  public Camera(int screenWidth, int screenHeight, int tileSize, int mapWidth,
                int mapHeight) {
    MID_SCREEN_WIDTH = screenWidth / 2;
    MID_SCREEN_HEIGHT = screenHeight / 2;
    MAX_VIEW_WIDTH = tileSize * mapWidth;
    MAX_VIEW_HEIGHT = tileSize * mapHeight;
    MAX_CAMERA_X = MAX_VIEW_WIDTH - MID_SCREEN_WIDTH;
    MAX_CAMERA_Y = MAX_VIEW_HEIGHT - MID_SCREEN_HEIGHT;
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
