package frc.robot.Subsystems;

import static frc.robot.Constants.motorControllers.*;

import java.util.ArrayList;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelPositions;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Drivetrain extends SubsystemBase{ 

    private ChassisSpeeds chassisSpeed;
    private DifferentialDriveWheelPositions differentialDriveWheelPositions;
    private DifferentialDriveOdometry differentialDriveOdometry;
    private Pose2d pose;
    private BuiltInAccelerometer accelerometer;
    private Pigeon2 gyro;
    private CANcoder leftEncoder;
    private CANcoder rightEncoder;
    private DifferentialDriveKinematics differentialDriveKinematics;
    private DifferentialDriveWheelSpeeds differentialDriveWheelSpeeds;


    public Drivetrain () {
        chassisSpeed = new ChassisSpeeds();
        gyro = new Pigeon2(GyroID);
        leftEncoder = new CANcoder(leftEncoderID);
        rightEncoder = new CANcoder(rightEncoderID);
        differentialDriveWheelSpeeds = new DifferentialDriveWheelSpeeds(0, 0);

        differentialDriveWheelPositions = new DifferentialDriveWheelPositions(0, 0);

        // Odometry needs: angle(Rotation2D), initial left encoder reading, initial right encoder reading, optional pose2D
        differentialDriveOdometry = new DifferentialDriveOdometry(gyro.getRotation2d(), leftEncoder.getPosition().getValueAsDouble(), rightEncoder.getPosition().getValueAsDouble());
        differentialDriveKinematics = new DifferentialDriveKinematics(trackWidthMeters);

        accelerometer = new BuiltInAccelerometer();

        // change ID in constants
        
        pose = new Pose2d();
    }
        
    public static void tankDrive() {
        
        double left = controller.getLeftY()*0.4;
        double right = controller.getRightY()*-0.4; 
        boolean speedBoost = controller.getRightBumper();
        
        if (speedBoost == true) {
        left = left * 1.3;
        right = right * 1.3;
        } 
        leftMotor1.set(left);
        rightMotor1.set(-right);

    }
    public static void arcadeDrive() {
        double speed = controller.getRightY() * 0.5;
        double turn = controller.getRightX() * -0.3;
    
        double left = speed + turn;
        double right = speed - turn;
        boolean speedBoostPressed = controller.getRightBumper();
        
        if (speedBoostPressed == true) {
            left = left * 1.3;
            right = right * 1.3;
        } 
            leftMotor1.set(left);
            rightMotor1.set(right);
    }

    public void drivetrainPeriodic() {

        Rotation2d rotation2d = gyro.getRotation2d();

        this.pose = differentialDriveOdometry.update(rotation2d, differentialDriveWheelPositions);
    }

    public Pose2d getPose() {
        return pose;
    }

    public void resetPose(double x, double y, Rotation2d rotation) {
        pose = new Pose2d(x, y, rotation);
    }

    public ChassisSpeeds getCurrentSpeeds() {
        return chassisSpeed;
    }

    public void kinematicDrive(ChassisSpeeds cSpeeds) {
        chassisSpeed = cSpeeds;
        differentialDriveWheelSpeeds = differentialDriveKinematics.toWheelSpeeds(chassisSpeed);
        

        // convert m/s to a -1 to 1 scale to input into motors
        double leftMotorSpeed = differentialDriveWheelSpeeds.leftMetersPerSecond / MAXSPEED;
        double rightMotorSpeed = differentialDriveWheelSpeeds.rightMetersPerSecond / MAXSPEED;
        
        System.out.println("LeftSpeed: " + differentialDriveWheelSpeeds.leftMetersPerSecond);
        System.out.println("RightSpeed: " + differentialDriveWheelSpeeds.rightMetersPerSecond);

        SmartDashboard.putNumber("LeftSpeed", differentialDriveWheelSpeeds.leftMetersPerSecond);
        SmartDashboard.putNumber("RightSpeed", differentialDriveWheelSpeeds.rightMetersPerSecond);
        
        leftMotor1.set(leftMotorSpeed);
        rightMotor1.set(rightMotorSpeed);
    }
}

