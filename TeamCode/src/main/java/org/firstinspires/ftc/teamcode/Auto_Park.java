package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Pinpoint_Setup;


@Autonomous
public class Auto_Park extends LinearOpMode {


    private DcMotor LFMotor;
    private DcMotor RFMotor;
    private DcMotor LBMotor;
    private DcMotor RBMotor;
    private DcMotor LSlide;
    private DcMotor RSlide;
    private DcMotor LArm;
    private DcMotor RArm;
    private Servo Wrist;
    private Servo Claw;

    private ElapsedTime     runtime = new ElapsedTime();


    boolean WristIsOpen = true;
    boolean ClawIsOpen = false;

    boolean lastYState = false;
    boolean currentYState = false;

    boolean lastXState = false;
    boolean currentXState = false;


    public void runOpMode() {
        LFMotor = hardwareMap.get(DcMotor.class, "LFMotor");
        RFMotor = hardwareMap.get(DcMotor.class, "RFMotor");
        LBMotor = hardwareMap.get(DcMotor.class, "LBMotor");
        RBMotor = hardwareMap.get(DcMotor.class, "RBMotor");
        LSlide = hardwareMap.get(DcMotor.class, "LSlide");
        RSlide = hardwareMap.get(DcMotor.class, "RSlide");
        LArm = hardwareMap.get(DcMotor.class, "LArm");
        RArm = hardwareMap.get(DcMotor.class, "RArm");

        Wrist = hardwareMap.get(Servo.class, "Wrist");
        Claw = hardwareMap.get(Servo.class, "Claw");


        LBMotor.setDirection(DcMotor.Direction.REVERSE);
        LFMotor.setDirection(DcMotor.Direction.REVERSE);

        LArm.setPower(0.04);
        RArm.setPower(-0.04);
        Wrist.setPosition(0);

        telemetry.addData("Status", "Running");
        telemetry.update();

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {

            int  LArmPos = LArm.getCurrentPosition();
            int  RArmPos = RArm.getCurrentPosition();
            int  LSlidePos = LSlide.getCurrentPosition();
            int  RSlidePos = RSlide.getCurrentPosition();

            telemetry.addData("RArmPos", RArmPos);
            telemetry.addData("LArmPos", LArmPos);
            telemetry.addData("LSlidePos", LSlidePos);
            telemetry.addData("RSlidePos", RSlidePos);
            telemetry.update();
            while (opModeIsActive()) {
              //  double XPos  = (Pinpoint_Setup.getPosX());
             //   double YPos  = (Pinpoint_Setup.getPosY());

                LFMotor.setPower(0.6);
                LBMotor.setPower(0.6);
                RFMotor.setPower(0.6);
                RBMotor.setPower(0.6);
              //  Pose2D setPosition = new Pose2D(DistanceUnit.MM, XPos, YPos + 30, AngleUnit.RADIANS,0);

                LFMotor.setPower(0);
                LBMotor.setPower(0);
                RFMotor.setPower(0);
                RBMotor.setPower(0);

            }
        }
    }
}