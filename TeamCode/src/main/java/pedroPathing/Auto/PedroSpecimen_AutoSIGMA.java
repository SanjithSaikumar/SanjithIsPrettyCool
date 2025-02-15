package pedroPathing.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;


@Autonomous(name = "Specimen ahhhhAuto", group = "Auto")
public class PedroSpecimen_AutoSIGMA extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;


    private int pathState;

    /* Create and Define Poses + Paths
     * Poses are built with three constructors: x, y, and heading (in Radians).
     * Pedro uses 0 - 144 for x and y, with 0, 0 being on the bottom left.
     * (For Into the Deep, this would be Blue Observation Zone (0,0) to Red Observation Zone (144,144).)
     * Even though Pedro uses a different coordinate system than RR, you can convert any roadrunner pose by adding +72 both the x and y.
     * This visualizer is very easy to use to find and create paths/pathchains/poses: <https://pedro-path-generator.vercel.app/>
     * Lets assume our robot is 18 by 18 inches
     * Lets assume the Robot is facing the human player and we want to score in the bucket */


    private final Pose startPose = new Pose(10, 60, Math.toRadians(0));


    private final Pose Preload = new Pose(37, 74, Math.toRadians(0));

    private final Pose PushPt1 = new Pose(29, 44, Math.toRadians(0));//weird path before pushing

    private final Pose PushPt2 = new Pose(62, 44, Math.toRadians(0));

    private final Pose PushPt3 = new Pose(62, 30, Math.toRadians(0));

    private final Pose PushPt4 = new Pose(30, 30, Math.toRadians(0));

    private final Pose PushPt4TEST = new Pose(62, 30, Math.toRadians(0));
    private final Pose PushPt5 = new Pose(62, 23, Math.toRadians(0));


    private final Pose PushPt6 = new Pose(30, 23, Math.toRadians(0));

    private final Pose PushPt6TEST = new Pose(62, 23, Math.toRadians(0));

    private final Pose PushPt7 = new Pose(62, 19, Math.toRadians(0));


    private final Pose PushPt8 = new Pose(30, 19, Math.toRadians(0));

    private final Pose PushPt9 = new Pose(31, 19, Math.toRadians(0));

    private final Pose PushPt10 = new Pose(15, 19, Math.toRadians(0));


    private final Pose Score1pos = new Pose(37, 72, Math.toRadians(0));

    private final Pose Score2pos = new Pose(37, 70, Math.toRadians(0));

    private final Pose Score3pos = new Pose(37, 68, Math.toRadians(0));

    private final Pose Score4pos = new Pose(37, 66, Math.toRadians(0));

    private final Pose PickUppos1 = new Pose(9, 30, Math.toRadians(0));

    private final Pose PickUppos2 = new Pose(9, 30, Math.toRadians(0));

    private final Pose PickUppos3 = new Pose(9, 30, Math.toRadians(0));


    private final Pose parkPose = new Pose(9, 30, Math.toRadians(90));


    /* These are our Paths and PathChains that we will define in buildPaths() */
    private Path scorePreload;
    private PathChain Push, Score1, PickUp1, Score2, PickUp2, Score3, PickUp3, Score4, park ;

    /** Build the paths for the auto (adds, for example, constant/linear headings while doing paths)
     * It is necessary to do this so that all the paths are built before the auto starts. **/
    public void buildPaths() {
        //MAKING TRAJECTORIES
        /* This is our scorePreload path. We are using a BezierLine, which is a straight line. */
        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(Preload)));
        scorePreload.setConstantHeadingInterpolation(startPose.getHeading());


        //Pushes all three samples
        Push = follower.pathBuilder()

                .addPath(new BezierLine(new Point(Preload), new Point(PushPt1)))
                .setConstantHeadingInterpolation(0) // From preload diagonal back right

                .addPath(new BezierLine(new Point(PushPt1), new Point(PushPt2)))
                .setConstantHeadingInterpolation(0) //forward

                .addPath(new BezierLine(new Point(PushPt2), new Point(PushPt3)))
                .setConstantHeadingInterpolation(0) //right

                .addPath(new BezierLine(new Point(PushPt3), new Point(PushPt4)))
                .setConstantHeadingInterpolation(0)  //push 1

                .addPath(new BezierLine(new Point(PushPt4), new Point(PushPt4TEST)))
                .setConstantHeadingInterpolation(0)  //push 1

                .addPath(new BezierLine(new Point(PushPt4TEST), new Point(PushPt5)))
                .setConstantHeadingInterpolation(0)  //push 1


                .addPath(new BezierLine(new Point(PushPt5), new Point(PushPt6)))
                .setConstantHeadingInterpolation(0)  //push 2

                .addPath(new BezierLine(new Point(PushPt6), new Point(PushPt6TEST)))
                .setConstantHeadingInterpolation(0)  //push 2

                .addPath(new BezierLine(new Point(PushPt6TEST), new Point(PushPt7)))
                .setConstantHeadingInterpolation(0)  //push 2


                .addPath(new BezierLine(new Point(PushPt7), new Point(PushPt8)))
                .setConstantHeadingInterpolation(0) // push last

                .addPath(new BezierLine(new Point(PushPt8), new Point(PushPt9)))
                .setConstantHeadingInterpolation(0) // move away from sample

                .addPath(new BezierLine(new Point(PushPt9), new Point(PushPt10)))
                .setConstantHeadingInterpolation(0) //pickup up from human position

                .build();

        Score1 = follower.pathBuilder()

                .addPath(new BezierLine(new Point(PushPt10), new Point(Score1pos)))
                .setConstantHeadingInterpolation(0)  //score first specimen

                .build();

        PickUp1 = follower.pathBuilder()

                .addPath(new BezierLine(new Point(Score1pos), new Point(PickUppos1)))
                .setConstantHeadingInterpolation(0)   //pickup 2nd Specimen

                .build();

        Score2 = follower.pathBuilder()

                .addPath(new BezierLine(new Point(PickUppos1), new Point(Score2pos)))
                .setConstantHeadingInterpolation(0)  //score 2nd specimen

                .build();

        PickUp2 = follower.pathBuilder()

                .addPath(new BezierLine(new Point(Score2pos), new Point(PickUppos2)))
                .setConstantHeadingInterpolation(0) //pickup up 3rd specimen

                .build();
        Score3 = follower.pathBuilder()

                .addPath(new BezierLine(new Point(PickUppos2), new Point(Score3pos)))
                .setConstantHeadingInterpolation(0)  //score 3rd specimen

                .build();

        PickUp3 = follower.pathBuilder()

                .addPath(new BezierLine(new Point(Score3pos), new Point(PickUppos3)))
                .setConstantHeadingInterpolation(0) //pickup 4th

                .build();

        Score4 = follower.pathBuilder()

                .addPath(new BezierLine(new Point(PickUppos3), new Point(Score4pos)))
                .setConstantHeadingInterpolation(0) //score 4th

                .build();

        park = follower.pathBuilder()

                .addPath(new BezierLine(new Point(Score4pos), new Point(parkPose)))
                .setConstantHeadingInterpolation(0) //park

                .build();

    }

    /** This switch is called continuously and runs the pathing, at certain points, it triggers the action state.
     * Everytime the switch changes case, it will reset the timer. (This is because of the setPathState() method)


     * The followPath() function sets the follower to run the specific path, but does NOT wait for it to finish before moving on. */
    //CALLING TRAJECTORIES
    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(scorePreload);
                setPathState(1);
                break;
            case 1:

                /* You could check for
                - Follower State: "if(!follower.isBusy() {}"
                - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
                - Robot Position: "if(follower.getPose().getX() > 36) {}"
                */

                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Preload */
                    // motor & servo commands go here i think
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(Push,true);
                    setPathState(2);
                }
                break;
            case 2:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    // motor & servo commands go here i think
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(Score1,true);
                    setPathState(3);
                }
                break;

            case 3:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    // motor & servo commands go here i think
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(PickUp1,true);
                    setPathState(4);
                }
                break;

            case 4:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    // motor & servo commands go here i think
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(Score2,true);
                    setPathState(5);
                }
                break;

            case 5:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    // motor & servo commands go here i think
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(PickUp2,true);
                    setPathState(6);
                }
                break;

            case 6:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    // motor & servo commands go here i think
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(Score3,true);
                    setPathState(7);
                }
                break;

            case 7:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    // motor & servo commands go here i think
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(PickUp3,true);
                    setPathState(8);
                }
                break;

            case 8:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    // motor & servo commands go here i think
                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower.followPath(park,true);
                    setPathState(9);
                }
                break;

            case 9:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Level 1 Ascent */
                    // motor & servo commands go here i think
                    /* Set the state to a Case we won't use or define, so it just stops running an new paths */
                    setPathState(-1);
                }
                break;
        }
    }

    /** These change the states of the paths and actions
     * It will also reset the timers of the individual switches **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
    @Override
    public void loop() {

        // These loop the movements of the robot
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();
    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {
    }
}

