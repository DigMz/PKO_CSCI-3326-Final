package org.digmz.components;

import java.util.ArrayList;
import java.util.List;

import org.digmz.components.Spritesheet;

public class Animation extends Component {

  private Spritesheet images;
  public int imageCount;

  public int frame = 0;
  public int fps = 8;
  public float intervalLength = 1.0f/fps;
  public float intervalTime = 0;
  public boolean loop = true;
  public boolean frameChanged = false;


  public Animation(Spritesheet images) {
    this.images = images;
    imageCount = images.size()-1;
  }


  public Sprite currentSprite() {return images.getSprite(frame);}

}
