package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


@TeleOp
public class MarkFieldCentric extends LinearOpMode {


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

    int x = 0;


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

        LArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        RArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        LSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        RSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);

        LArm.setPower(0.04);
        RArm.setPower(-0.04);

        Wrist.setDirection(Servo.Direction.REVERSE);

        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        waitForStart();

        imu.resetYaw();

        while (opModeIsActive()) {


            currentYState = gamepad2.y;
            currentXState = gamepad2.x;

            int  LArmPos = LArm.getCurrentPosition();
            int  RArmPos = RArm.getCurrentPosition();
            int  LSlidePos = LSlide.getCurrentPosition();
            int  RSlidePos = RSlide.getCurrentPosition();

            telemetry.addData("RArmPos", RArmPos);
            telemetry.addData("LArmPos", LArmPos);
            telemetry.addData("LSlidePos", LSlidePos);
            telemetry.addData("RSlidePos", RSlidePos);
            telemetry.update();

            if (RArmPos <= -1900 && LArmPos >= 1900 && x< 1){
                LArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                LArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                RArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                x += 1;
            }


            double py = -gamepad1.left_stick_y;
            double px = gamepad1.left_stick_x;
            double pa = gamepad1.right_stick_x;

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            botHeading -= Math.toRadians(90);

            if (gamepad1.start) {
                imu.resetYaw();
            }

            // Rotate the movement direction counter to the bot's rotation
            double rotX = px * Math.cos(-botHeading) - py * Math.sin(-botHeading);
            double rotY = px * Math.sin(-botHeading) + py * Math.cos(-botHeading);

            rotX = rotX * 1.5;  // Counteract imperfect strafing

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(pa), 1);

            double LFtgtPower = (rotY + rotX + pa)/denominator;
            double LBtgtPower = (-rotY + rotX - pa) /denominator;
            double RFtgtPower = (rotY - rotX - pa)/denominator;
            double RBtgtPower = (rotY + rotX - pa)/denominator;

            RFMotor.setPower(RFtgtPower);

            LFMotor.setPower(LFtgtPower);

            RBMotor.setPower(RBtgtPower);

            LBMotor.setPower(LBtgtPower);

//Wrist toggle
            if (currentYState && !lastYState) {
                WristIsOpen = !WristIsOpen;
            }

            lastYState = currentYState;

            if (WristIsOpen) {
                Wrist.setPosition(0.72);
            }
            else {
                Wrist.setPosition(0);
            }



//Claw toggle
            if (currentXState && !lastXState) {
                ClawIsOpen = !ClawIsOpen;
            }

            lastXState = currentXState;

            if (ClawIsOpen) {
                Claw.setPosition(0.2);

            } else {
                Claw.setPosition(0.40);
            }

//Arm control
            if (gamepad2.right_bumper) {
                LArm.setPower(-0.6);
                RArm.setPower(0.6);
            }
            else if(gamepad2.left_bumper){
                LArm.setPower(0.6);
                RArm.setPower(-0.6);
            }
            else{
                LArm.setPower(-0.05);
                RArm.setPower(0.05);
            }
//Slide control
            if (gamepad1.right_bumper){//down
                LSlide.setPower(0.7);
                RSlide.setPower(0.7);
            }
            else if (gamepad1.left_bumper){//up
                if ((LArmPos < 1300) && (RArmPos < 1300) && ((LSlidePos > 1500))){
                    LSlide.setPower(0);
                    RSlide.setPower(0);
                }
                else{
                    LSlide.setPower(-0.6);
                    RSlide.setPower(-0.6);
                }
            }
            else{
                LSlide.setPower(-0.04);
                RSlide.setPower(-0.04);
            }

        }
    }
}