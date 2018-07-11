package org.usfirst.frc.team694.trajectory.commands;

import org.usfirst.frc.team694.trajectory.DrivetrainData;
import org.usfirst.frc.team694.trajectory.Path;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class FollowPathCommand extends Command {

    private Path path;
    private DrivetrainData constants;

    private PIDController anglePID; // Extra, to adjust the angle.
    private double anglePIDValue;

    public FollowPathCommand(Path path, DrivetrainData constants, double angleKP, double angleKI, double angleKD) {
        this.path = path;
        this.constants = constants;

        AngleSourceOutput sourceOutput = new AngleSourceOutput();
        anglePID = new PIDController(angleKP, angleKI, angleKD, sourceOutput, sourceOutput);
    }

    @Override
    protected void execute() {
        // Don't go over the limits
        if (isFinished()) {
            return;
        }


        double currentDistance = getCurrentDistance();
        double vel        = path.getSpeedAtDistance(currentDistance);
        double angularVel = path.getAngularVelocityAtDistance(currentDistance);

        anglePID.setSetpoint(path.getHeadingAtDistance(currentDistance));

        // The target speed for the left and right drivetrain
        double leftTargetSpeed  = vel + (angularVel * constants.getRobotWidth()) / 2d ;
        double rightTargetSpeed = 2*vel - leftTargetSpeed;

        leftTargetSpeed += anglePIDValue;
        rightTargetSpeed -= anglePIDValue;

        moveDrivetrain(leftTargetSpeed, rightTargetSpeed);
    }

    @Override
    protected boolean isFinished() {
        return getCurrentDistance() >= path.getTotalDistance();
    }

    private class AngleSourceOutput implements PIDSource, PIDOutput {
        @Override
        public void pidWrite(double output) {
            anglePIDValue = output;
        }
        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {}

        @Override
        public PIDSourceType getPIDSourceType() {return PIDSourceType.kDisplacement;}

        @Override
        public double pidGet() {
            return getCurrentAngle();
        }
    }
    
    protected abstract double getCurrentDistance();
    protected abstract void moveDrivetrain(double leftTargetSpeed, double rightTargetSpeed);
    protected abstract double getCurrentAngle();
}
