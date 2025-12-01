package houlei.flames.rs232.pack;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import houlei.flames.rs232.HexUtil;
import houlei.flames.rs232.rule.SerialPortResource;

public class QueryCalibrationConstantsTest {


    @ClassRule
    public static SerialPortResource serialPortResource = new SerialPortResource();

    public static QueryCalibrationConstants.BinaryCodec binaryCodec = new QueryCalibrationConstants.BinaryCodec();


    @Test
    public void testSerialNumber() {
        // 0- Serial Number     1- 0阶波长校准系数     2- 1阶波长校准系数     3- 2阶波长校准系数
        // 4- 3阶波长校准系数      5- Stray light constant     6- 0阶非线性修正系数    7- 1阶非线性修正系数
        // 8- 2阶非线性修正系数     9- 3阶非线性修正系数    10- 4阶非线性修正系数   11- 5阶非线性修正系数
        // 12- 6阶非线性修正系数    13- 7阶非线性修正系数   14- 非线性标定的多项式阶
        // 15- Optical bench configuration: gg fff sss
        //      gg – Grating #, fff – filter wavelength, sss – slit size
        // 16- Flame configuration: AWL V
        //      A – Array coating Mfg, W – Array wavelength (VIS, UV, OFLV), L – L2 lens installed, V – CPLD Version
        // 17,18,19-Reserved
        final SetCalibrationConstants.CalibrationConstants constants = SetCalibrationConstants.CalibrationConstants.SerialNumber;
        QueryCalibrationConstants.Request request = new QueryCalibrationConstants.Request(constants.getValue());
        byte[] requestBinary = binaryCodec.encode(request);
        System.out.println("[testSerialNumber][testQueryPixelBoxcarWidth] write: 0x " + HexUtil.toString(requestBinary, 0, requestBinary.length));
        Assert.assertArrayEquals(new byte[]{'?','x', 0, 0}, requestBinary);
        System.out.println("[testSerialNumber][testQueryPixelBoxcarWidth] write: " + request);
        int length = serialPortResource.getSerialPort().writeBytes(requestBinary, requestBinary.length);
        Assert.assertEquals(requestBinary.length, length);

        byte[] buffer = new byte[256];
        int bytesToRead = serialPortResource.readBytes(buffer, 0, buffer.length);
        System.out.println("[testSerialNumber][testQueryPixelBoxcarWidth] read: 0x " + HexUtil.toString(buffer, 0, bytesToRead));
        Assert.assertEquals(12, bytesToRead);
        QueryCalibrationConstants.Response response = (QueryCalibrationConstants.Response) binaryCodec.decode(buffer);
        System.out.println("[testSerialNumber][testQueryPixelBoxcarWidth] read: " +response);
        Assert.assertEquals(ResponsePack.Status.ACK, response.getStatus());
        Assert.assertEquals("FLMS00524", response.getValue());

    }

}