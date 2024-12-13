package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;

import com.qualcomm.hardware.lynx.LynxI2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.util.TypeConversion;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
//import org.firstinspires.ftc.teamcode.Pinpoint_Setup;


@Autonomous
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

   // Pinpoint_Setup odo;


    boolean WristIsOpen = true;
    boolean ClawIsOpen = false;

    boolean lastYState = false;
    boolean currentYState = false;

    boolean lastXState = false;
    boolean currentXState = false;

    double YPos;


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

     //   odo = hardwareMap.get(Pinpoint_Setup.class,"odo");




        //Encoder
        LBMotor.setDirection(DcMotor.Direction.FORWARD);
        LFMotor.setDirection(DcMotor.Direction.REVERSE);
        Wrist.setDirection(Servo.Direction.FORWARD);

         //  RArm.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Running");
        telemetry.update();

        LArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Claw.setPosition(0.9);
//Arm Encoders
        int AxelUpPos = 1000; // changeable
        int AxelMidPos = 200;
        int AxelDownPos = 0;

        LArm.setTargetPosition(-1237);
        RArm.setTargetPosition(1277);

        LArm.setPower(0.4);
        RArm.setPower(0.4);

        LArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while ((LArm.getCurrentPosition() <= -1237) && (RArm.getCurrentPosition() >= 1277)) {
            LArm.setPower(0.04);
            RArm.setPower(0.04);
        }
    /*

//Slide Encoders
        LSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

      /*  int slideUpPos = 1000; // changeable
        int slideDownPos = 0;

        LSlide.setTargetPosition(slideDownPos);
        RSlide.setTargetPosition(slideDownPos);

        LSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //Wheels Forward


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
        LSlide.setTargetPosition(0);
        RSlide.setTargetPosition(0);

        LSlide.setPower(-0.6);
        RSlide.setPower(-0.6);

        LSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LSlide.setPower(0);
        RSlide.setPower(0);
//Move Back
        LFMotor.setTargetPosition(-500);
        RFMotor.setTargetPosition(-500);
        LBMotor.setTargetPosition(-500);
        RBMotor.setTargetPosition(-500);

        LFMotor.setPower(0.6);
        LBMotor.setPower(0.6);
        RFMotor.setPower(0.6);
        RBMotor.setPower(0.6);

        LFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LFMotor.setPower(0);
        LBMotor.setPower(0);
        RFMotor.setPower(0);
        RBMotor.setPower(0);

//Strafe Left
        LFMotor.setTargetPosition(500);
        RFMotor.setTargetPosition(-500);
        LBMotor.setTargetPosition(-500);
        RBMotor.setTargetPosition(500);

        LFMotor.setPower(0.6);
        LBMotor.setPower(0.6);
        RFMotor.setPower(0.6);
        RBMotor.setPower(0.6);

        LFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LFMotor.setPower(0);
        LBMotor.setPower(0);
        RFMotor.setPower(0);
        RBMotor.setPower(0);*/

//Park



        waitForStart();

        LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LFMotor.setTargetPosition(500);
        RFMotor.setTargetPosition(500);
        LBMotor.setTargetPosition(500);
        RBMotor.setTargetPosition(500);

        LFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LFMotor.setPower(0.6);
        LBMotor.setPower(0.6);
        RFMotor.setPower(0.6);
        RBMotor.setPower(0.6);

        LArm.setTargetPosition(-1500);
        RArm.setTargetPosition(1500);

        LArm.setPower(0.4);
        RArm.setPower(0.4);

        LArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Wrist.setPosition(0.7);

      //Wrist.setPosition(0.2);

        while (opModeIsActive() && (LBMotor.isBusy())) {

            int  LArmPos = LArm.getCurrentPosition();
            int  RArmPos = RArm.getCurrentPosition();
            int  LSlidePos = LSlide.getCurrentPosition();
            int  RSlidePos = RSlide.getCurrentPosition();
            int  LFMotorPos = LFMotor.getCurrentPosition();
            int  LBMotorPos = LBMotor.getCurrentPosition();
            int  RFMotorPos = RFMotor.getCurrentPosition();
            int  RBMotorPos = RBMotor.getCurrentPosition();

        //  XPos  = (Pinpoint_Setup.getPosX());
        //  YPos  = (Pinpoint_Setup.getPosY());

            telemetry.addData("RArmPos", RArmPos);
            telemetry.addData("LArmPos", LArmPos);
            telemetry.addData("LSlidePos", LSlidePos);
            telemetry.addData("RSlidePos", RSlidePos);
            telemetry.addData("LFMotorPos", LFMotorPos);
            telemetry.addData("LBMotorPos", LBMotorPos);
            telemetry.addData("RFMotorPos", RFMotorPos);
            telemetry.addData("RBMotorPos", RBMotorPos);
            // telemetry.addData("XPos", XPos);
            // telemetry.addData("YPos", YPos);
            telemetry.update();

            LArm.setPower(0.04);
            RArm.setPower(0.04);


        }
        LFMotor.setPower(0);
        LBMotor.setPower(0);
        RFMotor.setPower(0);
        RBMotor.setPower(0);
        LSlide.setPower(0);
        RSlide.setPower(0);
        RArm.setPower(0);
        LArm.setPower(0);


    }
}