package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.robot.Robot;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "Auto Blue Left Gyro", group = "")
public class AutoBlueLeftGyro extends LinearOpMode {
    private robot autoRobot;
    public void runOpMode() throws InterruptedException {
        autoRobot = new robot (this);
        waitForStart();
        
        //ELement Detection
        int elementPosition = autoRobot.detectElement();
        telemetry.addData("Element pos", elementPosition);
        telemetry.update();
        
        //Drive to tower and place
        autoRobot.driveDistance(-3, 0.3);
        autoRobot.turnDegGyro(-27, 0.5);
        autoRobot.driveDistance(autoRobot.getTowerDistanceRight(elementPosition)-2,0.4);
        autoRobot.liftToHeightEncoders(elementPosition, 0.7);
        autoRobot.openVomiter();
        sleep(2000);
        autoRobot.closeVomiter();
        //sleep(500);
        autoRobot.liftToHeightEncoders(1, 0.7);
       
        //Park
        autoRobot.driveDistance(-(autoRobot.getTowerDistanceRight(elementPosition))-9,0.4);
        autoRobot.turnDegGyro(80, 0.5);
        autoRobot.driveDistance(53,-1);
        
        //Teleop Prep
        autoRobot.turnDeg(180, 0.8);
        autoRobot.flipDown();
        sleep(2000);

    }
}



