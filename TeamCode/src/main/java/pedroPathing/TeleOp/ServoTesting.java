package pedroPathing.TeleOp;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



@TeleOp(name="ServoSync")
//@Disabled

public class ServoTesting extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive = null;
    private DcMotor elevatorL, elevatorR, HangL, HangR = null;
    private Servo  intake_wrist, intake_claw, outtake_armR,outtake_armL , outtake_wrist,outtake_rotation,
            outtake_claw, intake_extension,  intake_rotation = null;
    private Limelight3A limelight = null;






    @Override
    public void runOpMode() {


        //.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
/////////////////////////////////////////////////////////////////////////////////////////////////////////
        //HwMap all items
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");

        elevatorL = hardwareMap.get(DcMotor.class, "elevator_L");
        elevatorR = hardwareMap.get(DcMotor.class, "elevator_R");

        elevatorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevatorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        elevatorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        elevatorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        HangL = hardwareMap.get(DcMotor.class, "HangL");
        HangR = hardwareMap.get(DcMotor.class, "HangR");


        intake_rotation = hardwareMap.get(Servo.class, "intake_rotation");
        intake_wrist = hardwareMap.get(Servo.class, "intake_wrist");
        intake_claw = hardwareMap.get(Servo.class, "intake_claw");
        intake_extension = hardwareMap.get(Servo.class, "intake_extension");


        outtake_armL = hardwareMap.get(Servo.class, "outtake_armL");
        outtake_armR = hardwareMap.get(Servo.class, "outtake_armR");
        outtake_wrist = hardwareMap.get(Servo.class, "outtake_wrist");
        outtake_rotation = hardwareMap.get(Servo.class, "outtake_rotation");
        outtake_claw = hardwareMap.get(Servo.class, "outtake_claw");


        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        elevatorL.setDirection(DcMotor.Direction.REVERSE);
        elevatorR.setDirection(DcMotor.Direction.REVERSE);

//        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//        limelight.pipelineSwitch(0);

        telemetry.update();

        waitForStart();


        runtime.reset();



        while (opModeIsActive()) {
            double max;


            double axial = -gamepad1.left_stick_y;
            double lateral = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;
            // double slidePower = -gamepad2.left_stick_y;

            double leftFrontPower = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower = axial - lateral + yaw;
            double rightBackPower = axial + lateral - yaw;

            if (gamepad2.y) {  // transition
               outtake_armR.setPosition(0);
               outtake_armL.setPosition(1);
            }
            if (gamepad2.a) {  // transition
                outtake_armR.setPosition(1);
                outtake_armL.setPosition(0);
            }



            telemetry.update();






            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));



            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
                rightBackPower /= max;

            }
            leftFrontDrive.setPower(leftFrontPower);
            rightFrontDrive.setPower(rightFrontPower);
            leftBackDrive.setPower(leftBackPower);
            rightBackDrive.setPower(rightBackPower);


        }


    }}