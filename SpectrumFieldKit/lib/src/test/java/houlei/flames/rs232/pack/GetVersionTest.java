package houlei.flames.rs232.pack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import houlei.flames.rs232.HexUtil;
import houlei.flames.rs232.rule.SerialPortResource;

@FixMethodOrder(MethodSorters.JVM)
public class GetVersionTest {

    @ClassRule
    public static SerialPortResource serialPortResource = new SerialPortResource();

    GetVersion.BinaryCodec codec = new GetVersion.BinaryCodec();

    @Test
    public void write() {
        GetVersion.Request request = new GetVersion.Request();
        byte[] data = codec.encode(request);
        Assert.assertArrayEquals(new byte[]{'v'}, data);
        System.out.println("[GetVersionTest][write] 0x " + HexUtil.toString(data, 0, data.length));
        int length = serialPortResource.getSerialPort().writeBytes(data, data.length);
        Assert.assertEquals(data.length, length);
        System.out.println("[GetVersionTest][write] " + request);
    }

    @Test
    public void read() {
        byte[] buffer = new byte[256];
//        int bytesToRead = serialPortResource.getSerialPort().readBytes(buffer, buffer.length);
        int bytesToRead = serialPortResource.readBytes(buffer, 0, buffer.length);
        System.out.println("[GetVersionTest][read ] 0x " + HexUtil.toString(buffer, 0, bytesToRead));
//        Assert.assertEquals(3, bytesToRead);
        GetVersion.Response response = (GetVersion.Response) codec.decode(buffer);
        Assert.assertEquals(ResponsePack.Status.ACK, response.getStatus());
        Assert.assertEquals(4005, response.getVersion());

        System.out.println("[GetVersionTest][read ] " +response);
    }

}