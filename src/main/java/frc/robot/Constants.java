package frc.robot;

import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.revrobotics.CANSparkMax;

public interface Constants {
    
    interface motorControllers {
  
        public CANSparkMax leftMotor1 = new CANSparkMax(3, MotorType.kBrushed);
                CANSparkMax leftMotor2 = new CANSparkMax(4, MotorType.kBrushed);  
                CANSparkMax rightMotor1 = new CANSparkMax(7, MotorType.kBrushed);
                CANSparkMax rightMotor2 = new CANSparkMax(2, MotorType.kBrushed);

        public static int GyroID = 0;
        public static int leftEncoderID = 0;
        public static int rightEncoderID = 0;

        // measure and change
        public static int MAXSPEED = 10;


        public static double trackWidthMeters = 0.5588;
        
        public CANSparkMax shooterMotor = new CANSparkMax(5, MotorType.kBrushed);

        public static XboxController controller = new XboxController(0);

        public static Encoder encoder = new Encoder(0, 1, true, EncodingType.k4X);

        

    }
}
