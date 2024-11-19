package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Pinpoint_Setup;


@TeleOp
public class Auto_Specimen extends LinearOpMode {


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




        //Encoders
       /* LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);*/

        LBMotor.setDirection(DcMotor.Direction.REVERSE);
        LFMotor.setDirection(DcMotor.Direction.REVERSE);

        //   LArm.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Running");
        telemetry.update();

        LArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Wrist.setPosition(1);
//Arm Encoders
        LArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int AxelUpPos = 1000; // changeable
        int AxelMidPos = 200;
        int AxelDownPos = 0;

        LArm.setTargetPosition(AxelMidPos);
        RArm.setTargetPosition(AxelMidPos);

        LArm.setPower(0.4);
        RArm.setPower(0.4);

        LArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LArm.setPower(0);
        RArm.setPower(0);
//Slide Encoders
        LSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int slideUpPos = 1000; // changeable
        int slideDownPos = 0;

        LSlide.setTargetPosition(slideDownPos);
        RSlide.setTargetPosition(slideDownPos);

        LSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();
        while (opModeIsActive()) {

            int  LArmPos = LArm.getCurrentPosition();
            int  RArmPos = RArm.getCurrentPosition();
            int  LSlidePos = LSlide.getCurrentPosition();
            int  RSlidePos = RSlide.getCurrentPosition();

           double XPos  = (Pinpoint_Setup.getPosX());
           double YPos  = (Pinpoint_Setup.getPosY());

            telemetry.addData("RArmPos", RArmPos);
            telemetry.addData("LArmPos", LArmPos);
            telemetry.addData("LSlidePos", LSlidePos);
            telemetry.addData("RSlidePos", RSlidePos);
            telemetry.update();

            //Wheels Forward
            Pose2D setPosition = new Pose2D(DistanceUnit.MM, XPos + 15, YPos, AngleUnit.RADIANS,0);

            //Slide Up
            LSlide.setTargetPosition(300);
            RSlide.setTargetPosition(300);

            LSlide.setPower(0.6);
            RSlide.setPower(0.6);

            LSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            LSlide.setPower(0.04);
            RSlide.setPower(0.04);
//Wrist Down

            Wrist.setPosition(0.4);
//Claw Open
            Claw.setPosition(0.4);
//Slide Down
            LSlide.setTargetPosition(slideDownPos);
            RSlide.setTargetPosition(slideDownPos);

            LSlide.setPower(-0.6);
            RSlide.setPower(-0.6);

            LSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            LSlide.setPower(0);
            RSlide.setPower(0);
//Move Back
            setPosition = new Pose2D(DistanceUnit.MM, XPos - 5, YPos, AngleUnit.RADIANS,0);

//Strafe Left
            setPosition = new Pose2D(DistanceUnit.MM, XPos, YPos - 30, AngleUnit.RADIANS,0);

//Park

        }
    }
}