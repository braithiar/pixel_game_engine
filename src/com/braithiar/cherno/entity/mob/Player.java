package com.braithiar.cherno.entity.mob;

import com.braithiar.cherno.graphics.Screen;
import com.braithiar.cherno.graphics.Screen.Mirror;
import com.braithiar.cherno.graphics.Sprite;
import com.braithiar.cherno.input.Keyboard;
import com.braithiar.cherno.world.tile.TileCoordinate;

public class Player extends Mob {

  public enum Leg {
    RIGHT(0), LEFT(1);

    private int legID;
    private static int MAX_TIME = 10;

    static int animationTimer = 0;

    private Leg(int legID) {
      this.legID = legID;
    }

    public int getLegID() {
      return legID;
    }

    public static Leg cycleLeg() {
      return animationTimer >= (MAX_TIME / 2) ? LEFT : RIGHT;
    }

    public static void tickAnimationTimer() {
      animationTimer = animationTimer >= MAX_TIME ? 0 : ++animationTimer;
    }
  }

  private Keyboard input;

  public Player(Keyboard input, Sprite sprite) {
    super(sprite);
    this.input = input;
  }

  public Player(int x, int y, Keyboard input, Sprite sprite) {
    super(sprite);

    this.x = x;
    this.y = y;
    this.input = input;
  }

  public Player(TileCoordinate coord, Keyboard input, Sprite sprite) {
    super(sprite);

    x = coord.getX();
    y = coord.getY();
    this.input = input;
  }

  @Override
  public void update() {
    int playerX = 0, playerY = 0;

    Leg.tickAnimationTimer();

    if (input.controlsMap.get(Keyboard.Control.UP)) {
      --playerY;
    }

    if (input.controlsMap.get(Keyboard.Control.DOWN)) {
      ++playerY;
    }

    if (input.controlsMap.get(Keyboard.Control.LEFT)) {
      --playerX;
    }

    if (input.controlsMap.get(Keyboard.Control.RIGHT)) {
      ++playerX;
    }

    if (playerX != 0 || playerY != 0) {
      move(playerX, playerY);
      setMoving(true);
    } else {
      setMoving(false);
    }
  }

  private void setSpriteForRender() {
    Sprite sprite = Sprite.PLAYER_DOWN;

    if (isMoving()) {
      switch (getDirection()) {
        case 0:
          sprite = Leg.cycleLeg() == Leg.RIGHT ? Sprite.PLAYER_DOWN_WALK_FRAME_1
                                               : Sprite.PLAYER_DOWN_WALK_FRAME_2;
          break;
        case 1:
          sprite = Leg.cycleLeg() == Leg.RIGHT ? Sprite.PLAYER_SIDE_WALK_FRAME_1
                                               : Sprite.PLAYER_SIDE_WALK_FRAME_2;
          break;
        case 2:
          sprite = Leg.cycleLeg() == Leg.RIGHT ? Sprite.PLAYER_UP_WALK_FRAME_1
                                               : Sprite.PLAYER_UP_WALK_FRAME_2;
          break;
        case 3:
          sprite = Leg.cycleLeg() == Leg.RIGHT ? Sprite.PLAYER_SIDE_WALK_FRAME_1
                                               : Sprite.PLAYER_SIDE_WALK_FRAME_2;
          break;
      }
    } else {
      switch (getDirection()) {
        case 0:
          sprite = Sprite.PLAYER_DOWN;
          break;
        case 1:
          sprite = Sprite.PLAYER_SIDE;
          break;
        case 2:
          sprite = Sprite.PLAYER_UP;
          break;
        case 3:
          sprite = Sprite.PLAYER_SIDE;
          break;
      }
    }

    setSprite(sprite);
  }

  private Mirror getFlipDirection() {
    switch (getDirection()) {
      case 3:
        return Mirror.FLIP_X_AXIS;
    }

    return Mirror.NO_FLIP;
  }

  @Override
  public void render(Screen screen) {
    Mirror flip = getFlipDirection();
    setSpriteForRender();

    //TODO replace magic numbers with calculated values from Level/Game classes.
    x = x < 0 ? 0 : x;
    x = x >= 1024 - Screen.getTileSize() ? 1024 - Screen.getTileSize() : x;
    y = y < 0 ? 0 : y;
    y = y >= 1024 - Screen.getTileSize() ? 1024 - Screen.getTileSize() : y;

    screen.renderPlayer(x, y, flip, getSprite());
  }

}
