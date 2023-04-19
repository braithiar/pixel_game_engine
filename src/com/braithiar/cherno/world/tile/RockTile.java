package com.braithiar.cherno.world.tile;

import com.braithiar.cherno.graphics.Sprite;

public class RockTile extends Tile {

  public RockTile(Sprite sprite) {
    super(sprite);
  }

  @Override
  public boolean hasCollision() {
    return true;
  }
}
