package org.digmz.game;

import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.digmz.components.Animation;
import org.digmz.components.AnimationManager;
import org.digmz.components.SpriteRenderer;

public class CharacterObject extends GameObject {

  private SpriteRenderer sprRend;
  private AnimationManager animan;
  private List<boolean[]> hitFramesList;

  private boolean hitting = false;
  private boolean prevHit = false;
  private boolean justHit = false;

  public CharacterObject(
      String name, Transform transform, int zIndex, SpriteRenderer sprRend,
      AnimationManager animan, List<boolean[]> hitFramesList) {
    super(name, transform, zIndex);
    this.animan = animan;
    this.hitFramesList = hitFramesList;
    this.sprRend = sprRend;
    this.addComponent(animan);
    this.addComponent(sprRend);

    if (this.hitFramesList.size() < animan.size()) {
      for (int i = this.hitFramesList.size(); i < animan.size(); i++) {
        boolean[] data = new boolean[animan.getAnimation(i).getImageCount()];
        Arrays.fill(data, false);
        hitFramesList.add(data);
      }
    }
    for (int i = 0 ; i < animan.size(); i++) {
      boolean[] values = hitFramesList.get(i);
      Animation anim = animan.getAnimation(i);
      if (values.length <= anim.imageCount) {
        assert false : "Not enough hitframes on index " + i + " for CharacterObject " + name;
      }
    }

    sprRend.setSprite(animan.currentSprite());
  }

  public void update(float dt) {
    super.update(dt);

    if (animan.frameChanged()) {
      sprRend.setSprite(animan.currentSprite());
    };

    hitting = hitFramesList.get(animan.getCurrAnimIndex())[animan.currentFrame()];

    if (prevHit == false && hitting == true) {
      justHit = true;
    }

    if (justHit) {
      System.out.println("Hit Registered");

      justHit = false;
    }

    prevHit = hitting;
  }

  public void start() {
    super.start();
  }

  public void setAnim(int index) {
    animan.setAnimation(index);
  }

  public void playAnim(int index) {
    this.setAnim(index);
    animan.play();
  }

  public void resetAnim() {
    this.animan.resetCurrAnim();
  }

  public boolean isHitting() {
    return hitting;
  }
 
}
