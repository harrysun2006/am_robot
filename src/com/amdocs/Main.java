package com.amdocs;

import java.io.InputStreamReader;
import java.io.Reader;

public class Main {

  protected static void help() {
    System.out.println("Please input commands for robot, each one in a line. The commands end with an empty line.");
    System.out.println();
    System.out.println("Valid commands:");
    System.out.println("PLACE X,Y,F");
    System.out.println("  place the robot at (X,Y) and facing to F");
    System.out.println("    (0,0) is the SOUTH WEST most cornor");
    System.out.println("    F should be one of following: NORTH,WEST,SOUTH,EAST");
    System.out.println("MOVE");
    System.out.println("  move the toy robot one unit forward in its current facing direction");
    System.out.println("LEFT");
    System.out.println("  rotate the toy robot 90 degrees in counter clock-wise");
    System.out.println("RIGHT");
    System.out.println("  rotate the toy robot 90 degrees in clock-wise");
    System.out.println("REPORT");
    System.out.println("  announce the X,Y and F of the robot");
    System.out.println();
    System.out.println("Example:");
    System.out.println("PLACE 0,0,NORTH");
    System.out.println("MOVE");
    System.out.println("REPORT");
    System.out.println();
  }

  public static void main(String[] argv) throws Exception {
    try {
      help();
      Robot robot = new Robot();
      Reader reader = new InputStreamReader(System.in);
      robot.walk(reader);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
