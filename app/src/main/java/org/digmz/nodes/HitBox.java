package nodes;

public class HitBox extends Node {
  public String id = "Player";
  public int[] extent = {-1,-1};

  public HitBox(int[] extent) {
    this.id = "Hitbox";
    this.extent = extent;
  }

  protected void _init() {
    System.out.println(this.id);
    System.out.println(this.extent[0] + this.extent[1]);
  }

  protected void _run() {
    System.out.println(this.extent[0] + " " + this.extent[1]);
  }

}
