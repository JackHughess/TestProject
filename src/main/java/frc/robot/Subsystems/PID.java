package frc.robot.Subsystems;

import static frc.robot.Constants.motorControllers.controller;
import static frc.robot.Constants.motorControllers.leftMotor1;
import static frc.robot.Constants.motorControllers.leftMotor2;
import static frc.robot.Constants.motorControllers.rightMotor1;
import static frc.robot.Constants.motorControllers.rightMotor2;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class PID {

    public static void limeLight() {
   
         DoublePublisher tx;
         DoublePublisher ty;
         DoublePublisher ta;
         DoublePublisher tz;
         
         NetworkTableInstance inst = NetworkTableInstance.getDefault();
         NetworkTable table = inst.getTable("limelight");

         tx = table.getDoubleTopic("tx").publish();
         ty = table.getDoubleTopic("ty").publish();
         ta = table.getDoubleTopic("ta").publish();
         tz = table.getDoubleTopic("tz").publish();

         double x = 0;
         double y = 0;
         double area = 0;
         double distance = 0;

         tx.set(x);
         ty.set(y);
         ta.set(area);
         tz.set(distance);

         System.out.println(x);

         double kP = 0.009;
         double kI = 0.0;
         double kD = 0;
         double error = x;
         boolean foundTag = false;

         PIDController centerRobot = new PIDController(kP, kI, kD);
         PIDController moveTowardTag = new PIDController(kP, kI, kD);
         System.out.println("center Robot Output =" + centerRobot.calculate(error, 0));
         System.out.println("Move Toward Tag Output =" + moveTowardTag.calculate(distance, 0.4));

        if (controller.getAButton() == true) {
         if (x == 0 && y == 0 && distance == 0) {
            leftMotor1.set(0.3);
            rightMotor1.set(-0.3);
        } else {
         foundTag = true;
        }
         
        if (foundTag == true) {
         if (Math.abs(error) < 5) {
         leftMotor1.set(moveTowardTag.calculate(distance, 0.4));
         rightMotor1.set(moveTowardTag.calculate(distance, 0.4));
         } else {
            if (error > 0 ) {
            leftMotor1.set(centerRobot.calculate(error, 0 ));
            rightMotor1.set(-centerRobot.calculate(error, 0 ));
         } else if (error < 0 ) {
            double error1 = Math.abs(error);
            leftMotor1.set(-centerRobot.calculate(error1, 0 ));
            rightMotor1.set(centerRobot.calculate(error1, 0 ));
         }
         }
        }
      }
      }
   }