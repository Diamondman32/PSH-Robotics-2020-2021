package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "Freight Frenzy Auto", group = " ")
public class RedFreightFrenzyAuto extends LinearOpMode {
    private DcMotor leftFrontMotor;
    private DcMotor rightFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightBackMotor;
    private DcMotor liftMotor;
    private Servo vomiter;
    private CRServo carousel;


    /**
     * Turns a specified distance
     * @param degrees specified degrees expressed as integer
     * @param power double that corresponds to motor speed
     */
    public void turnDeg(int degrees, double power){
        double ticksPerRev = 560;
        //1120 for 40:1 (28 counts per revolution)
        //https://docs.revrobotics.com/15mm/actuators/motors/hd-hex-motor
        double inPerRev = Math.PI * 3.5;
        //3.5 is wheel diameter in inches
        double ticksPerInch = ticksPerRev/inPerRev;
        double diameter = 16.1;
        //TODO:Distance between the center of the front left and front right wheels
        double radians = degrees*Math.PI/180;
        double distanceIN = radians*diameter;
        double ticksDistance = ticksPerInch * distanceIN;
        double startPosition = leftFrontMotor.getCurrentPosition();
        if (degrees < 0) power = -power;

        while (Math.abs(leftFrontMotor.getCurrentPosition()) - startPosition < Math.abs(ticksDistance)){
            leftFrontMotor.setPower(power);
            leftBackMotor.setPower(power);
            rightFrontMotor.setPower(-power);
            rightBackMotor.setPower(-power);
        }

        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);
    }

    /**
     * Drives a specified distance at a specified speed in a specified direction
     * @param distanceIN specified distance expressed as integer
     * @param power double that corresponds to motor speed
     */
    public void driveDistance(int distanceIN, double power){
        double ticksPerRev = 560;
        //1120 for 40:1 (28 counts per revolution)
        //https://docs.revrobotics.com/15mm/actuators/motors/hd-hex-motor
        double inPerRev = Math.PI * 3.5;
        //3.5 is wheel diameter in inches
        double ticksPerInch = ticksPerRev/inPerRev;
        double ticksDistance = ticksPerInch * distanceIN;
        double startPosition = leftFrontMotor.getCurrentPosition();
        if (distanceIN < 0) power = -power;

        while (Math.abs(leftFrontMotor.getCurrentPosition()) - startPosition < Math.abs(ticksDistance)){
            leftFrontMotor.setPower(power);
            leftBackMotor.setPower(power);
            rightFrontMotor.setPower(power);
            rightBackMotor.setPower(power);
        }

        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);

    }

    public void liftToHeight(int level){
        int liftValue = 0;
        //TODO:Find the encoder values for corresponding heights
        if (level == 1){
            liftValue = -31;
        }
        else if(level == 2){
            liftValue = 1217;
        }
        else if (level ==3){
            liftValue = 3291;
        }
        if (liftMotor.getCurrentPosition() < liftValue) {
            while (liftMotor.getCurrentPosition() != liftValue) {
                liftMotor.setPower(-0.5);
            }
        }
        else if (liftMotor.getCurrentPosition() > liftValue){
            while (liftMotor.getCurrentPosition() != liftValue) {
                liftMotor.setPower(0.5);
            }
        }
    }
    public void detectedDistance() {
        if(elementPos == 1)
        {
            driveDistance(8,0.5);
        } else if (elementPos == 2) {
            driveDistance(8,0.5);
        }
        else
        {
            driveDistance(8,0.5);
        }
    }
    public void runOpMode() {
        //Drive train motors
        leftFrontMotor = hardwareMap.dcMotor.get("front_left_motor");
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftBackMotor = hardwareMap.dcMotor.get("back_left_motor");
        leftBackMotor.setDirection(DcMotor.Direction.FORWARD);
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightFrontMotor = hardwareMap.dcMotor.get("front_right_motor");
        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightBackMotor = hardwareMap.dcMotor.get("back_right_motor");
        rightBackMotor.setDirection(DcMotor.Direction.REVERSE);
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Lift Motor
        liftMotor = hardwareMap.dcMotor.get("lift");
        liftMotor.setDirection(DcMotor.Direction.FORWARD);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Servos
        vomiter =  hardwareMap.servo.get("vomiter");

        carousel =  hardwareMap.crservo.get("carousel");
        carousel.setDirection(DcMotorSimple.Direction.REVERSE);



        //carousel.setPower(-1 to 1);

        waitForStart();
        //To Do:
        //Detect position of the scoring element
        //drive close to tower ?distance depending on level?
        driveDistance(18,0.5);
        //angle towards tower
        turnDeg(45,0.5);
        //final approach to tower
        detectedDistance();
        //Put cube into desired level
        liftToHeight(1);
        //open then close vomiter
        vomiter.setPosition(0.38);
        sleep();
        vomiter.setPosition(0.05);
        //lower the lift
        //liftToHeight(0)
        //Rotate 90 degrees (face wall)
        turnDeg(135,0.5);
        //Drive to wall ?need space to turn/not against the wall?
        driveDistance(35,0.5);
        //Rotate 90 degrees (face carousel)
        turnDeg(90,0.5);
        //drive in range of carousel
        driveDistance(28,0.5);
        //Spin carousel
        carousel.setPower(1);
        sleep();
        //Rotate 90 (face warehouse)?Does it need more space for it not to the turn/rub into carousel
        turnDeg(90,0.5);
        //drive completely in warehouse
        driveDistance(96,0.5);

    }
}