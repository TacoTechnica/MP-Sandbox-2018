package org.usfirst.frc.team694.trajectory.commands;

import org.usfirst.frc.team694.trajectory.DrivetrainData;
import org.usfirst.frc.team694.trajectory.Path;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class FollowPathCommand extends Command {

    private Path path;
    private DrivetrainData constants;

    public FollowPathCommand(Path path, DrivetrainData constants) {
        this.path = path;
        this.constants = constants;
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

        // The target speed for the left and right drivetrain
        double leftTargetSpeed  = vel + constants.getRobotWidth() / (2d * angularVel);
        double rightTargetSpeed = 2*vel - leftTargetSpeed;

        moveDrivetrain(leftTargetSpeed, rightTargetSpeed);
    }

    @Override
    protected boolean isFinished() {
        return getCurrentDistance() >= path.getTotalDistance();
    }

    protected abstract double getCurrentDistance();
    protected abstract void moveDrivetrain(double leftTargetSpeed, double rightTargetSpeed);
}
