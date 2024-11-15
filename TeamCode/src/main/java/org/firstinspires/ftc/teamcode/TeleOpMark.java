package org.firstinspires.ftc.teamcode.drive.opmode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@TeleOp
public class TeleOpMark20 extends LinearOpMode {


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
        
        LArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        RArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        LSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        RSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        
        Wrist.setPosition(1);
        LArm.setPower(0.04);
        RArm.setPower(-0.04);

        waitForStart();
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

            double RFtgtPower = 0;
            double LFtgtPower = 0;
            double RBtgtPower = 0;
            double LBtgtPower = 0;
            double py = -gamepad1.left_stick_y;
            double px = gamepad1.left_stick_x;
            double pa = gamepad1.right_stick_x;

            LFMotor.setPower((py + px + pa)/0.5);
            LBMotor.setPower((-py + px - pa)/0.5);
            RFMotor.setPower((py - px - pa)/0.5);
            RBMotor.setPower((py + px - pa)/0.5);

//Wrist toggle
            if (currentYState && !lastYState) {
                WristIsOpen = !WristIsOpen;
            }

            lastYState = currentYState;

            if (WristIsOpen) {
                Wrist.setPosition(0.4);
            }
            else {
                Wrist.setPosition(0.85);
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
                LArm.setPower(0.04);
                RArm.setPower(-0.04);
            }
//Slide control
            if (gamepad1.right_bumper){//down
                LSlide.setPower(0.7);
                RSlide.setPower(0.7);
            }
            else if (gamepad1.left_bumper){//up
                if ((LArmPos < 1300) && (RArmPos < 1300) && ((LSlidePos > 1500) && (RSlidePos < -1500))){
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
