package com.amdocs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Robot {

  private final static int DEFAULT_MAXX = 5;
  private final static int DEFAULT_MAXY = 5;
  private final static Pattern PLACE_COMMAND = Pattern.compile("PLACE (\\d+),(\\d+),(NORTH|WEST|SOUTH|EAST)");

  public static enum Direction {
    NORTH,
    WEST,
    SOUTH,
    EAST;
  };

  // table size, default is 5x5 square
  private int maxx;
  private int maxy;

  // position of robot
  private int x = -1; // position is (0,0)+, -1 means the robot has not been placed yet
  private int y = -1;
  // (0,0) is the SOUTH WEST most corner.
  private Direction facing = null;
  private Writer writer = new PrintWriter(System.out);

  public Robot() {
    this(DEFAULT_MAXX, DEFAULT_MAXY);
  }

  public Robot(int maxx, int maxy) {
    if (maxx <= 0 || maxy <= 0) throw new RuntimeException("Invalid parameters!");
    this.maxx = maxx;
    this.maxy = maxy;
  }

  public boolean place(int x, int y, Direction f) {
    if (x < 0 || x >= maxx || y < 0 || y >= maxy || f == null) return false;
    this.x = x;
    this.y = y;
    this.facing = f;
    return true;
  }

  /**
   * command: MOVE
   * MOVE will move the toy robot one unit forward in the direction it is currently facing.
   */
  public boolean move() {
    if (!isValid()) return false;
    boolean r = false;
    if (facing == Direction.NORTH) {
      if (y < maxy-1) {
        y++;
        r = true; 
      }
    } else if (facing == Direction.SOUTH) {
      if (y > 0) {
        y--;
        r = true;
      }
    } else if (facing == Direction.EAST) {
      if (x < maxx-1) {
        x++;
        r = true;
      }
    } else if (facing == Direction.WEST) {
      if (x > 0) {
        x--;
        r = true;
      }
    } 
    return r;
  }

  /**
   * command: LEFT
   * LEFT will rotate the robot 90 degrees in the specified direction without changing the position of the robot.
   */
  public boolean left() {
    if (!isValid()) return false;
    if (facing == Direction.NORTH) facing = Direction.WEST;
    else if (facing == Direction.WEST) facing = Direction.SOUTH;
    else if (facing == Direction.SOUTH) facing = Direction.EAST;
    else if (facing == Direction.EAST) facing = Direction.NORTH;
    else return false;
    return true;
  }

  /**
   * command: RIGHT
   * RIGHT will rotate the robot 90 degrees in the specified direction without changing the position of the robot.
   */
  public boolean right() {
    if (!isValid()) return false;
    if (facing == Direction.NORTH) facing = Direction.EAST;
    else if (facing == Direction.EAST) facing = Direction.SOUTH;
    else if (facing == Direction.SOUTH) facing = Direction.WEST;
    else if (facing == Direction.WEST) facing = Direction.NORTH;
    else return false;
    return true;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public Direction getFacing() {
    return this.facing;
  }

  public void setWriter(Writer writer) {
    this.writer = writer;
  }

  public boolean report() {
    if (!isValid()) return false;
    try {
      writer.write(String.format("%d,%d,%s\n", x, y, facing));
      writer.flush();
    } catch (Throwable t) {
      t.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean read(String command) {
    if (command == null) return false;
    else if ("MOVE".equals(command)) return move();
    else if ("LEFT".equals(command)) return left();
    else if ("RIGHT".equals(command)) return right();
    else if ("REPORT".equals(command)) return report();
    else if (command.startsWith("PLACE")) {
      try {
        Matcher m = PLACE_COMMAND.matcher(command);
        if (m.matches()) {
          int x = Integer.parseInt(m.group(1));
          int y = Integer.parseInt(m.group(2));
          Direction f = Direction.valueOf(m.group(3));
          return place(x, y, f);
        } else {
          return false;
        }
      } catch (Throwable t) {
        t.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
  }

  public void walk(Reader reader) throws IOException {
    BufferedReader br = new BufferedReader(reader);
    String line;
    while ((line = br.readLine()) != null) {
      if ("".equals(line)) break;
      read(line);
    }
  }

  protected boolean isValid() {
    return (facing != null && x >= 0 && x < maxx && y >= 0 && y < maxy);
  }
}
