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


@Autonomous(name = "Auto Red Left Gyro", group = "")
public class AutoRedLeftGyro extends LinearOpMode {
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
        autoRobot.turnDegGyro(-25, 0.5);
        autoRobot.driveDistance(autoRobot.getTowerDistanceLeft(elementPosition),0.4);
        autoRobot.liftToHeightEncoders(elementPosition, 0.7);
        autoRobot.openVomiter();
        sleep(2000);
        autoRobot.closeVomiter();
        sleep(500);
        autoRobot.liftToHeightEncoders(1, 0.7);

        //Drive Back toward the carousel
        autoRobot.driveDistance(-(autoRobot.getTowerDistanceLeft(elementPosition))-3 ,0.4);
        autoRobot.turnDegGyro(76,0.4);

        /*while (autoRobot.sideDistance.getDistance(DistanceUnit.INCH) > 8.25 ) {
            autoRobot.leftFrontMotor.setPower(0.3);
            autoRobot.leftBackMotor.setPower(0.3);
            autoRobot.rightFrontMotor.setPower(-0.3);
            autoRobot.rightBackMotor.setPower(-0.3);
        }

        autoRobot.leftFrontMotor.setPower(0);
        autoRobot.leftBackMotor.setPower(0);
        autoRobot.rightFrontMotor.setPower(0);
        autoRobot.rightBackMotor.setPower(0);
        */

        autoRobot.driveDistance(-12, 0.5);
        autoRobot.driveTrainPower(-0.1);
        Thread.sleep(1000);
        while (autoRobot.findPositionChange() > 55 ){
        telemetry.addData("Change in Position",autoRobot.findPositionChange());
        telemetry.update(); 
        }
        autoRobot.driveTrainPower(0);
        
        autoRobot.carousel.setPower(0.7);
        sleep(4000);
        autoRobot.carousel.setPower(0);

        //park
        autoRobot.turnDegGyro(16,0.5);
        autoRobot.driveDistance(-14,0.5);

    }
}



