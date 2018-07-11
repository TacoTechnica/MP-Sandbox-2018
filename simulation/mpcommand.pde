
public static class MPCommand extends CommandWrapper {

  private SimulatedDrivetrain drivetrain;
  
  private static final double ANGLE_KP = 0;
  private static final double ANGLE_KI = 0;
  private static final double ANGLE_KD = 0; 

  public MPCommand(Path path, SimulatedDrivetrain drivetrain) {
    super(path, new DrivetrainData(SimulatedDrivetrain.BOT_WIDTH, SimulatedDrivetrain.MAX_SPEED, 99), ANGLE_KP, ANGLE_KI, ANGLE_KD);
    this.drivetrain = drivetrain;
  }

  @Override
  protected double getCurrentDistance() {
    return drivetrain.getAvgDistance();
  }
  @Override
  protected double getCurrentAngle() {
    return drivetrain.getHeading();
  }
  @Override
  protected void moveDrivetrain(double leftTargetSpeed, double rightTargetSpeed) {
    println(leftTargetSpeed + ", " + rightTargetSpeed);
    drivetrain.tankDriveSpeed(leftTargetSpeed, rightTargetSpeed);
  }

  @Override
  protected void end() {
    super.end();
    drivetrain.tankDrive(0, 0);
  }
  
}
