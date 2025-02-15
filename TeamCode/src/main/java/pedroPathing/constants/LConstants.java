package pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = 0.003;
        ThreeWheelConstants.strafeTicksToInches = 0.003;
        ThreeWheelConstants.turnTicksToInches = 0.0031;
        ThreeWheelConstants.leftY = 4.5;
        ThreeWheelConstants.rightY = -4.5;
        ThreeWheelConstants.strafeX = 0;
        ThreeWheelConstants.leftEncoder_HardwareMapName = "left_front_drive";
        ThreeWheelConstants.rightEncoder_HardwareMapName = "right_front_drive";     //REMOVE IMU IF ERRORS!!!!!
        ThreeWheelConstants.strafeEncoder_HardwareMapName = "right_back_drive";
        ThreeWheelConstants.leftEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.rightEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.FORWARD;
//        ThreeWheelIMUConstants.IMU_HardwareMapName = "imu";
//        ThreeWheelIMUConstants.IMU_Orientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT, RevHubOrientationOnRobot.UsbFacingDirection.UP);
    }

}




