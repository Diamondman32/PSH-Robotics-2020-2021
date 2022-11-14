package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.lang.Math;

@TeleOp(name="Freight Frenzy TeleOP",group=" ")
public class FreightTeleOP extends OpMode{
private robot teleRobot;
    public void init() {
        teleRobot = new robot (this);
    }

    public void loop(){
        //CONTROLLER A (Driving Controller)
        //Drive train and half speed
        float yPosLeft01 = gamepad1.left_stick_y;
        float yPosRight01 = gamepad1.right_stick_y;
        
        //Optional Half Speed Setting (was traded in for spinning the carousel)
        /*if (gamepad1.right_bumper){
            yPosLeft01 = yPosLeft01*0.5f;
            yPosRight01 = yPosRight01*0.5f;
        }*/;

        teleRobot.leftFrontMotor.setPower(yPosLeft01);
        teleRobot.leftBackMotor.setPower(yPosLeft01);
        teleRobot.rightFrontMotor.setPower(yPosRight01);
        teleRobot.rightBackMotor.setPower(yPosRight01);
        //Auto Lift
                if (gamepad1.a){
            teleRobot.liftToHeightEncoders(1, 0.4);
        }
        if (gamepad1.b){
            teleRobot.liftToHeightEncoders(2, 0.4);
        }
        if (gamepad1.y){
            teleRobot.liftToHeightEncoders(3, 0.4);
        }


        //CONTROLLER B (Mechanism Controller)
    
        //Capper
        if (gamepad2.a){
            teleRobot.capper.setPower(-0.2);
        }
        else if (gamepad2.b){
            teleRobot.capper.setPower(0.2);
        }
        else {
            teleRobot.capper.setPower(0);
        }
        
        //Manual Lift
        float liftPower = gamepad2.left_stick_y;
        teleRobot.liftMotor.setPower(liftPower);

        //Vomiter Controls
        if (gamepad2.right_bumper){
            //open
            teleRobot.vomiter.setPosition(0.38);
        }

        if (gamepad2.left_bumper){
            //closed
            teleRobot.vomiter.setPosition(0.05);
        }

        //Carousel Controls
        float carouselPowerRed = gamepad2.right_trigger;
        teleRobot.carousel.setPower(carouselPowerRed);

        float carouselPowerBlue = -gamepad2.left_trigger;
        teleRobot.carousel.setPower(carouselPowerBlue);
        
        if (gamepad1.right_bumper){
            teleRobot.carousel.setPower(0.75);
        }
        
        if (gamepad1.left_bumper){
            teleRobot.carousel.setPower(-0.75);
        }
        
        //Intake
        float intakePower = gamepad2.right_stick_y;
        teleRobot.intakeMotor.setPower(intakePower);
        teleRobot.intakeServo.setPower(intakePower);

        //flipper
        if (gamepad2.y) {
            teleRobot.flipUp();
        }

        if (gamepad2.x) {
            teleRobot.flipDown();
        }

        //TELEMETRY
        int leftMotorPosition = teleRobot.leftFrontMotor.getCurrentPosition();
        int rightMotorPosition = teleRobot.rightFrontMotor.getCurrentPosition();

        int liftEncoderValue = teleRobot.liftMotor.getCurrentPosition();
        double vomiterPosition = teleRobot.vomiter.getPosition();

        //teleRobot.sensorTelemetry();
        telemetry.addData("vomiter Position", vomiterPosition);
        telemetry.addData("Lift Position", (liftEncoderValue));
        //telemetry.addData("Carousel Speed", (carouselPower));
        //telemetry.addData("Left Front Position", (leftMotorPosition));
        //telemetry.addData("Right Front Position", (rightMotorPosition));
        //telemetry.addData("leftPower", yPosLeft01);
        //telemetry.addData("RightPower", yPosRight01);
        telemetry.addData("Element pos 1", teleRobot.sensor1.getDistance(DistanceUnit.INCH));
        telemetry.addData("Element pos 2", teleRobot.sensor2.getDistance(DistanceUnit.INCH));
        telemetry.addData("Element pos", teleRobot.detectElement());
        telemetry.addData("side distance", teleRobot.sideDistance.getDistance(DistanceUnit.INCH));
        telemetry.update();

    }

}
