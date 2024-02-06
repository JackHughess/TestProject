package frc.robot.Subsystems;

import static frc.robot.Constants.motorControllers.controller;
import static frc.robot.Constants.motorControllers.leftMotor1;
import static frc.robot.Constants.motorControllers.rightMotor1;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class PID {

         public static final NetworkTableInstance inst = NetworkTableInstance.getDefault();
         public static final NetworkTable table = inst.getTable("limelight");
   
         public static final DoubleSubscriber tx = table.getDoubleTopic("tx").subscribe(0);
         public  static final DoubleSubscriber ty = table.getDoubleTopic("ty").subscribe(0);
         public static final DoubleSubscriber ta = table.getDoubleTopic("ta").subscribe(0);
         public static final DoubleSubscriber tz = table.getDoubleTopic("tz").subscribe(0);

    public static void limeLight() {

         double x = tx.get();
         double y = ty.get();
         double area = ta.get();
         double distance = tz.get();

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