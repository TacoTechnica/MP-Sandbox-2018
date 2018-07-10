/**
 *  This simulates a drivetrain
 */
public class SimulatedDrivetrain {
  
  private static final double WHEEL_DIAMETER = 8;
  private static final double MAX_SPEED = 17*12;

  // Physics variables
  private double posX, posY;
  private double velX, velY;

  // Orientation of the drivetrain (0 is to the right, positive is clockwise)
  private double heading;
  // Angular velocity
  private double headingVel;

  private Motor leftMotor, rightMotor;

  public SimulatedDrivetrain(double posX, double posY, double heading) {
    this.posX = posX;
    this.posY = posY;
    this.heading = heading;
    velX = 0;
    velY = 0;
    headingVel = 0;
  }

  public void update() {
    
    
  }
}

// Simulated motor
class Motor {
  private double pwm; // -1 to 1
  private double wheelDiameter;
  private double maxRPS; // rotations per second

  private double encoderPosition; // inches
  private double realRPS; // real rotation speed of the wheels

  public Motor(double maxSpeed, double wheelDiameter) {
    this.maxRPS = maxSpeed / (wheelDiameter * PI);
    this.wheelDiameter = wheelDiameter;

    encoderPosition = 0 ;
  }

  public void setPWM(double pwm) {
    if      (pwm < -1) pwm = -1;
    else if (pwm > 1) pwm = 1;
    this.pwm = pwm;
  }

  // TODO: simulate inertia / torque 
  public void update() {
    encoderPosition += getSpeed();
    realRPS = pwm * maxRPS;
  }

  // The real speed of this wheel, simulated.
  // TODO: Simulate inetria / torque 
  public double getSpeed() {
    return realRPS * wheelDiameter * PI;
  }

  // Encoder position in inches.
  public double getEncoderPosition() {
    return encoderPosition;
  }
}
