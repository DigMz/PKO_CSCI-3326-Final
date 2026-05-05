package nodes;

public class Player extends Node {
  public String id = "Player";
  public String name;
  private HitBox hitbox;

  public Player(String name, HitBox hitbox) {
    this.name = name;
    this.hitbox = hitbox;
  }

  protected void _init() {
    System.out.println(id);
    System.out.println(name);
  }

  protected void _run() {
    System.out.println(frame);
  }
}
