import org.usfirst.frc.team694.trajectory.*;
import org.usfirst.frc.team694.trajectory.Vector2d;
import org.usfirst.frc.team694.trajectory.commands.FollowPathCommand;

Simulation s;

Waypoint[] waypoints;
Path path;

void setup() {
  size(1080, 640);

  SimulatedDrivetrain drivetrain = new SimulatedDrivetrain(100, 100, 0);
  waypoints = new Waypoint[] {
     new Waypoint(100, 100, 170),
     new Waypoint(300, 300, 170),
     new Waypoint(300, 400, 170),
     //new Waypoint(200, 200, 170)
  };
  path = new Path(0, 0, 0, waypoints);

  s = new MPSimulation(path, drivetrain);
  s.setup();
}

void draw() {
  translate(200, 200);
  s.update();
  
  
  // Draw waypoints
  stroke(255, 255, 255);
  fill(255, 0, 0);
  for(int i = 0; i < waypoints.length; i++) {
    ellipse((float)waypoints[i].position.x, (float)waypoints[i].position.y, 10, 10);
  }
  // Draw line
  stroke(0, 0, 0);
  double total = path.getTotalDistance();
  Vector2d lastPos = path.getPositionAtDistance(0);
  for(double d = 0; d < total; d += total / 100.0) {
    Vector2d pos = path.getPositionAtDistance(d);
    line((float)lastPos.x, (float)lastPos.y, (float)pos.x, (float)pos.y);
    lastPos = pos;
  }

  // Angle thing
  stroke(255, 255, 0);
  double distance = total * ((float)mouseY / height);
  double heading = path.getHeadingAtDistance(distance);
  Vector2d pos = path.getPositionAtDistance(distance);
  float dx = 30*cos((float)heading);
  float dy = 30*sin((float)heading);
  line((float)pos.x - dx, (float)pos.y - dy, (float)pos.x + dx,(float)pos.y + dy);
}

double deltaTime() {
  return s.deltaTime(); 
}




/** 
 *  Our main MP Simulation
 */
public class MPSimulation extends CommandSimulation {

  private SimulatedDrivetrain drivetrain;

  public MPSimulation(Path path, SimulatedDrivetrain drivetrain) {
    super(new MPCommand(path, drivetrain));
    this.drivetrain = drivetrain;
  }

  @Override
  public void draw() {
    super.draw();
    background(127, 127, 127);
    drivetrain.update();
    drivetrain.draw();
  }
}


/**
 * Runs an MP command
 */
public abstract class CommandSimulation extends Simulation {

  private CommandWrapper command;
  private boolean done;

  public CommandSimulation(CommandWrapper command) {
    this.command = command;
  }

  @Override
  public void setup() {
    done = false;
    command.initializePublic();    
  }

  @Override
  public void draw() {
    if (!done) {
      if (!command.isFinishedPublic()) {
        command.executePublic();
      } else {
        command.endPublic();
        done = true;
      }
    }
  }
  
}

/**
 * Basically just makes all protected commands public for the purpose of this simulation.
 */
public abstract static class CommandWrapper extends FollowPathCommand {
 
  public CommandWrapper(Path path, DrivetrainData constants, double angleKP, double angleKI, double angleKD) {
    super(path, constants, angleKP, angleKI, angleKD);
  }

  public void initializePublic() {
    initialize();
  }
  public void executePublic() {
    execute(); 
  }
  public boolean isFinishedPublic() {
    return isFinished();
  }
  public void interruptedPublic() {
    interrupted();
  }
  public void endPublic() {
    end();
  }
}

public class RobotSimulation extends Simulation {

  SimulatedDrivetrain drivetrain;

  @Override
  public void setup() {
    drivetrain = new SimulatedDrivetrain(200, 200, 0);
  }

  @Override
  public void draw() {

    background(100, 100, 100);
    
    drivetrain.tankDrive(1.0, 1.0);
    
    drivetrain.update();
    drivetrain.draw();
  }
}

/**
 * Central simulation class, with a setup and loop update method.
 */
public abstract class Simulation {
  
  private double prevTime;
  private double dtime; // How many seconds elapsed between the last frame and this one

  public Simulation() {
    prevTime = System.nanoTime()/10E8;    
  }

  public void update() {
    double nowTime = (double)System.nanoTime()/10E8;
    dtime = nowTime - prevTime;

    draw();

    prevTime = nowTime;
  }

  public double deltaTime() {
    return dtime; 
  }

  public abstract void setup();
  public abstract void draw();
  
}
