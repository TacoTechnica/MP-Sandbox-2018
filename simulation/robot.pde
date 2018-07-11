/**
 *  This simulates a drivetrain
 */
public class SimulatedDrivetrain {

  private static final double WHEEL_DIAMETER = 8;
  public static final double MAX_SPEED = 17*12;
  public static final double BOT_WIDTH = 32;

  // Physics variables
  private double posX, posY;
  private double velX, velY;

  // Orientation of the drivetrain (0 is right, positive is clockwise)
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

    leftMotor = new Motor(MAX_SPEED, WHEEL_DIAMETER);
    rightMotor = new Motor(MAX_SPEED, WHEEL_DIAMETER);
  }

  public void update() {

    leftMotor.update();
    rightMotor.update();
    
    double leftSpeed = leftMotor.getSpeed();
    double rightSpeed = rightMotor.getSpeed();

    double vel = (leftSpeed + rightSpeed) / 2.0;
    double angularSpeed = (leftSpeed - rightSpeed) / BOT_WIDTH;
    
    heading += angularSpeed * deltaTime();

    velX = vel * Math.cos(heading);
    velY = vel * Math.sin(heading);
    //println(posX + ", " + velX + ", " + deltaTime());

    posX += velX * deltaTime();
    posY += velY * deltaTime(); 
  }

  public void draw() {
    stroke(0, 0, 0);
    fill(255, 0, 0);
    
    pushMatrix();
      translate((float)posX, (float)posY);
      rotate((float)heading + PI/2);
      rect(-(float)BOT_WIDTH/2, -(float)BOT_WIDTH*1.5f/2, (float)BOT_WIDTH, (float)BOT_WIDTH*1.5f);
    popMatrix();
  }

  public void tankDrive(double left, double right) {
    leftMotor.setPWM(left);
    rightMotor.setPWM(right);
  }
  
  // Tank drive but in real units, not PWM.
  public void tankDriveSpeed(double leftSpeed, double rightSpeed) {
    tankDrive(leftSpeed / MAX_SPEED, rightSpeed / MAX_SPEED);
  }

  public double getAvgDistance() {
    return (leftMotor.getEncoderPosition() + rightMotor.getEncoderPosition()) / 2;
  }
  public double getHeading() {
    return heading; 
  }
}

// Simulated motor
public class Motor {
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
    encoderPosition += getSpeed() * deltaTime();
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
