package houlei.flames.rs232.pack;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Arrays;

import houlei.flames.rs232.HexUtil;
import houlei.flames.rs232.rule.SerialPortResource;

public class GetSpectralAcquisitionTest {

    @ClassRule
    public static SerialPortResource serialPortResource = new SerialPortResource();

    GetSpectralAcquisition.BinaryCodec binaryCodec = new GetSpectralAcquisition.BinaryCodec();

    @Test
    public void test() {
        GetSpectralAcquisition.Request request = new GetSpectralAcquisition.Request();
        byte[] requestData = binaryCodec.encode(request);
        System.out.println("[GetSpectralAcquisitionTest][test] write: 0x " + HexUtil.toString(requestData, 0, requestData.length));
        Assert.assertArrayEquals(new byte[]{'S'}, requestData);
//        int bytesToWrite = serialPortResource.getSerialPort().writeBytes(requestData, requestData.length);
        int bytesToWrite = serialPortResource.getSerialPort().writeBytes(requestData, requestData.length);
        Assert.assertEquals(requestData.length, bytesToWrite);
        System.out.println("[GetSpectralAcquisitionTest][test] write: " + request);

        byte[] buffer = new byte[8192+256];
        // 2k*2 + 17    2k*4 + 17
//        int bytesToRead = serialPortResource.getSerialPort().readBytes(buffer, buffer.length);
        int bytesToRead = serialPortResource.readBytes(buffer, 0, buffer.length);
        System.out.println("[GetSpectralAcquisitionTest][test] bytesToRead: " + bytesToRead);
        System.out.println("[GetSpectralAcquisitionTest][test] read : 0x " + HexUtil.toString(buffer, 0, bytesToRead));
//        System.out.println(HexUtil.prettyHexDump(buffer, 0, bytesToRead));

        GetSpectralAcquisition.Response response = (GetSpectralAcquisition.Response) binaryCodec.decode(buffer);

        System.out.println("[GetSpectralAcquisitionTest][test] read : " + response);
        System.out.println("[GetSpectralAcquisitionTest][test] read : " + Arrays.toString(response.getSpectralData()));
    }


}