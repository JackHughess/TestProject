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
         public static final DoubleSubscriber ty = table.getDoubleTopic("ty").subscribe(0);
         public static final DoubleSubscriber ta = table.getDoubleTopic("ta").subscribe(0);
         public static final DoubleSubscriber tz = table.getDoubleTopic("tz").subscribe(0);
         public static double lastx = 0;
    public static void limeLight() {

         double x = tx.get();
         double y = ty.get();
         double area = ta.get();
         double distance = tz.get();

         System.out.println(x);

         double ckP = 0.009;
         double ckI = 0.00;
         double ckD = 0;

         double mkP = 0.1;
         double mkI = 0;
         double mkD = 0;

         double error = x;
         boolean foundTag = false;

         PIDController centerRobot = new PIDController(ckP, ckI, ckD);
         PIDController moveTowardTag = new PIDController(mkP, mkI, mkD);
         System.out.println("center Robot Output =" + centerRobot.calculate(error, 0));
         System.out.println("Move Toward Tag Output =" + moveTowardTag.calculate(distance, 1.5));
         if(x != 0){
            lastx = x;
         }
        if (controller.getAButton() == true) {
         if (x == 0 && y == 0 && distance == 0) {
            if (lastx > 0) {
            leftMotor1.set(0.15);
            rightMotor1.set(-0.15);
            } else if (lastx < 0) {
               leftMotor1.set(-0.15);
            rightMotor1.set(0.15);
            } else {
            leftMotor1.set(0.15);
            rightMotor1.set(-0.15);
            }
        } else {
         foundTag = true;
      }
         
        if (foundTag == true) {
            if (Math.abs(x) < 10) {
               leftMotor1.set(-0.2);
               rightMotor1.set(-0.2);
       
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
        System.out.println("lastx" +lastx);
      }
   }
      
   