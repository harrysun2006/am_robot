package com.amdocs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AllTest {

  private Robot robot;
  private StringWriter writer;

  @Before public void setUp() {
    robot = new Robot();
    writer = new StringWriter();
    robot.setWriter(writer);
  }

  @After public void tearDown() {
    try {
      if (writer != null) {
        writer.flush();
        writer.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test public void testPlace01() {
    assertTrue(robot.read("PLACE 0,0,EAST"));
    assertEquals(robot.getX(), 0);
    assertEquals(robot.getY(), 0);
    assertEquals(robot.getFacing(), Robot.Direction.EAST);
  }

  @Test public void testPlace02() {
    assertTrue(robot.read("PLACE 2,3,SOUTH"));
    assertEquals(robot.getX(), 2);
    assertEquals(robot.getY(), 3);
    assertEquals(robot.getFacing(), Robot.Direction.SOUTH);
  }

  @Test public void testPlace03() {
    assertFalse(robot.read("PLACE a,b,CENTER"));
  }

  @Test public void testPlace04() {
    assertFalse(robot.read("PLACE 3,5,SOUTH"));
  }

  @Test public void testPlace05() {
    assertFalse(robot.read("PLACE 3.2,2.5,EAST"));
  }

  @Test public void testPlace06() {
    assertFalse(robot.read("PLACE -3,-2,WEST"));
  }

  @Test public void testMove01() {
    assertTrue(robot.read("PLACE 0,0,NORTH"));
    assertTrue(robot.read("MOVE"));
    assertEquals(robot.getX(), 0);
    assertEquals(robot.getY(), 1);
    assertEquals(robot.getFacing(), Robot.Direction.NORTH);
  }

  @Test public void testMove02() {
    assertTrue(robot.read("PLACE 0,0,WEST"));
    assertFalse(robot.read("MOVE"));
  }
  
  @Test public void testMove03() {
    assertTrue(robot.read("PLACE 0,0,SOUTH"));
    assertFalse(robot.read("MOVE"));
  }

  @Test public void testMove04() {
    assertTrue(robot.read("PLACE 0,0,EAST"));
    assertTrue(robot.read("MOVE"));
    assertEquals(robot.getX(), 1);
    assertEquals(robot.getY(), 0);
    assertEquals(robot.getFacing(), Robot.Direction.EAST);
  }

  @Test public void testMove05() {
    assertTrue(robot.read("PLACE 4,4,NORTH"));
    assertFalse(robot.read("MOVE"));
  }

  @Test public void testMove06() {
    assertTrue(robot.read("PLACE 4,4,WEST"));
    assertTrue(robot.read("MOVE"));
    assertEquals(robot.getX(), 3);
    assertEquals(robot.getY(), 4);
    assertEquals(robot.getFacing(), Robot.Direction.WEST);
  }
  
  @Test public void testMove07() {
    assertTrue(robot.read("PLACE 4,4,SOUTH"));
    assertTrue(robot.read("MOVE"));
    assertEquals(robot.getX(), 4);
    assertEquals(robot.getY(), 3);
    assertEquals(robot.getFacing(), Robot.Direction.SOUTH);
  }

  @Test public void testMove08() {
    assertTrue(robot.read("PLACE 4,4,EAST"));
    assertFalse(robot.read("MOVE"));
  }

  @Test public void testLeft01() {
    assertTrue(robot.read("PLACE 1,1,NORTH"));
    assertTrue(robot.read("LEFT"));
    assertEquals(robot.getX(), 1);
    assertEquals(robot.getY(), 1);
    assertEquals(robot.getFacing(), Robot.Direction.WEST);
  }

  @Test public void testLeft02() {
    assertTrue(robot.read("PLACE 1,1,WEST"));
    assertTrue(robot.read("LEFT"));
    assertEquals(robot.getX(), 1);
    assertEquals(robot.getY(), 1);
    assertEquals(robot.getFacing(), Robot.Direction.SOUTH);
  }

  @Test public void testLeft03() {
    assertTrue(robot.read("PLACE 1,1,SOUTH"));
    assertTrue(robot.read("LEFT"));
    assertEquals(robot.getX(), 1);
    assertEquals(robot.getY(), 1);
    assertEquals(robot.getFacing(), Robot.Direction.EAST);
  }

  @Test public void testLeft04() {
    assertTrue(robot.read("PLACE 1,1,EAST"));
    assertTrue(robot.read("LEFT"));
    assertEquals(robot.getX(), 1);
    assertEquals(robot.getY(), 1);
    assertEquals(robot.getFacing(), Robot.Direction.NORTH);
  }

  @Test public void testRight01() {
    assertTrue(robot.read("PLACE 1,1,NORTH"));
    assertTrue(robot.read("RIGHT"));
    assertEquals(robot.getX(), 1);
    assertEquals(robot.getY(), 1);
    assertEquals(robot.getFacing(), Robot.Direction.EAST);
  }

  @Test public void testRight02() {
    assertTrue(robot.read("PLACE 1,1,EAST"));
    assertTrue(robot.read("RIGHT"));
    assertEquals(robot.getX(), 1);
    assertEquals(robot.getY(), 1);
    assertEquals(robot.getFacing(), Robot.Direction.SOUTH);
  }

  @Test public void testRight03() {
    assertTrue(robot.read("PLACE 1,1,SOUTH"));
    assertTrue(robot.read("RIGHT"));
    assertEquals(robot.getX(), 1);
    assertEquals(robot.getY(), 1);
    assertEquals(robot.getFacing(), Robot.Direction.WEST);
  }

  @Test public void testRight04() {
    assertTrue(robot.read("PLACE 1,1,WEST"));
    assertTrue(robot.read("RIGHT"));
    assertEquals(robot.getX(), 1);
    assertEquals(robot.getY(), 1);
    assertEquals(robot.getFacing(), Robot.Direction.NORTH);
  }

  @Test public void testReport01() {
    assertTrue(robot.read("PLACE 0,0,NORTH"));
    assertTrue(robot.read("MOVE"));
    assertTrue(robot.read("REPORT"));
    assertEquals(robot.getX(), 0);
    assertEquals(robot.getY(), 1);
    assertEquals(robot.getFacing(), Robot.Direction.NORTH);
    assertEquals(writer.getBuffer().toString(), "0,1,NORTH\n");
  }

  @Test public void testReport02() {
    assertTrue(robot.read("PLACE 0,0,NORTH"));
    assertTrue(robot.read("LEFT"));
    assertTrue(robot.read("REPORT"));
    assertEquals(robot.getX(), 0);
    assertEquals(robot.getY(), 0);
    assertEquals(robot.getFacing(), Robot.Direction.WEST);
    assertEquals(writer.getBuffer().toString(), "0,0,WEST\n");
  }

  @Test public void testReport03() {
    assertTrue(robot.read("PLACE 1,2,EAST"));
    assertTrue(robot.read("MOVE"));
    assertTrue(robot.read("MOVE"));
    assertTrue(robot.read("LEFT"));
    assertTrue(robot.read("MOVE"));
    assertTrue(robot.read("REPORT"));
    assertEquals(robot.getX(), 3);
    assertEquals(robot.getY(), 3);
    assertEquals(robot.getFacing(), Robot.Direction.NORTH);
    assertEquals(writer.getBuffer().toString(), "3,3,NORTH\n");
  }
}
