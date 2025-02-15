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



@TeleOp(name="testPreset")
//@Disabled

public class testPreset extends LinearOpMode {

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

        elevatorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        elevatorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        elevatorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevatorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        elevatorL.setDirection(DcMotor.Direction.REVERSE);
        elevatorR.setDirection(DcMotor.Direction.REVERSE);




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
          //  double slidePower = -gamepad2.right_stick_y;

            double leftFrontPower = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower = axial - lateral + yaw;
            double rightBackPower = axial + lateral - yaw;






            if (gamepad2.right_bumper){
                intake_extension.setPosition(0.4);
                intake_wrist.setPosition(0.8);

            }
            if (gamepad2.left_bumper){
                intake_extension.setPosition(0);
                intake_wrist.setPosition(0.4);
                outtake_armR.setPosition(1);
                outtake_claw.setPosition(0);

            }


//                boolean clawToggle = false;
//                boolean lastButtonState = false;
//
//                if (gamepad2.b && !lastButtonState) {  // Detects button press (not hold)
//                    clawToggle = !clawToggle;  // Toggle state
//                }
//
//                lastButtonState = gamepad2.b; // Update button state
//
//                if (clawToggle) {
//                    intake_claw.setPosition(0.4);  // Open
//                    outtake_claw.setPosition(1);
//                } else {
//                    intake_claw.setPosition(1);  // Close
//                    outtake_claw.setPosition(0);
//                }






            //    intake_claw.getPosition();
//                if (gamepad2.b &&   intake_claw.getPosition() == 0.4) {  //claw open close test
//                    intake_claw.setPosition(1);
//                    outtake_claw.setPosition(0);
//                }                                     //sigma sigma boi
//
//                if  (gamepad2.b &&   intake_claw.getPosition() == 1) {  //claw open close test
//                    intake_claw.setPosition(0.4);
//                    outtake_claw.setPosition(1);


            //claw open close
            if (gamepad2.b) {
                intake_claw.setPosition(0.4);
                outtake_claw.setPosition(0);
            }else{
                intake_claw.setPosition(1);
                outtake_claw.setPosition(0.4);
            }

            if (gamepad2.x) {
                intake_wrist.setPosition(0.2);

            }

            if (gamepad1.dpad_up) {  //hang
                HangL.setPower(-0.7);
                HangR.setPower(-0.7);
            } else if (gamepad1.dpad_down){
                HangL.setPower(0.7);
                HangR.setPower(0.7);
            }else {
                HangL.setPower(0);
                HangR.setPower(0);
            }

//                if (gamepad2.dpad_up) {  //bucket height
//                    elevatorR.setTargetPosition(-100);
//                    elevatorL.setTargetPosition(-100);
//                    elevatorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    elevatorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    elevatorR.setPower(0.8);
//                    elevatorL.setPower(0.8);
//                    outtake_armR.setPosition(0);
//                }

            if (gamepad2.y) {  // specimen hang height
                elevatorR.setTargetPosition(500);
                elevatorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevatorR.setPower(0.8);
                elevatorL.setTargetPosition(-500    );
                elevatorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                elevatorL.setPower(-0.8);
                outtake_armR.setPosition(0.7);
            }

            if (gamepad2.dpad_down) {  // specimen pickup
                outtake_armR.setPosition(0);
            }

            if (gamepad2.dpad_up) {  // sample
                outtake_claw.setPosition(0);
                sleep(500);
                outtake_armR.setPosition(1);
                outtake_claw.setPosition(0.4);
                intake_claw.setPosition(0.4);
                sleep(3000);
                outtake_armR.setPosition(0.3);
            }



            if (gamepad2.dpad_left) {
                intake_rotation.setPosition(0.2);
                outtake_rotation.setPosition(0);
            }

            if (gamepad2.dpad_right){
                intake_rotation.setPosition(0.9);
                outtake_rotation.setPosition(0);
            }

//                if (gamepad2.a) {
//                    elevatorR.setTargetPosition(0);
//                    elevatorL.setTargetPosition(0);
//                    elevatorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    elevatorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    elevatorR.setPower(0.8);
//                    elevatorL.setPower(-0.8);
//                    outtake_armR.setPosition(0);
//                }


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
