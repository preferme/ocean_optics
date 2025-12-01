package houlei.flames.rs232.pack;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import houlei.flames.rs232.HexUtil;
import houlei.flames.rs232.rule.SerialPortResource;


@FixMethodOrder(MethodSorters.JVM)
public class QueryVariableTest {

    @ClassRule
    public static SerialPortResource serialPortResource = new SerialPortResource();

    public static QueryVariable.BinaryCodec binaryCodec = new QueryVariable.BinaryCodec();


    //TODO 添加测试用例, B,A,I,K,T,J,y
    @Test
    public void testQueryPixelBoxcarWidth() {
        QueryVariable.Request request = new QueryVariable.Request('B');
        byte[] requestBinary = binaryCodec.encode(request);
        Assert.assertArrayEquals(new byte[]{'?','B'}, requestBinary);
        System.out.println("[QueryVariableTest][testQueryPixelBoxcarWidth] write: " + request);
        System.out.println("[QueryVariableTest][testQueryPixelBoxcarWidth] write: 0x " + HexUtil.toString(requestBinary, 0, requestBinary.length));
        int length = serialPortResource.getSerialPort().writeBytes(requestBinary, requestBinary.length);
        Assert.assertEquals(requestBinary.length, length);

        byte[] buffer = new byte[256];
        int bytesToRead = serialPortResource.readBytes(buffer, 0, buffer.length);
        System.out.println("[QueryVariableTest][testQueryPixelBoxcarWidth] read: 0x " + HexUtil.toString(buffer, 0, bytesToRead));
        Assert.assertEquals(3, bytesToRead);
        QueryVariable.Response response = (QueryVariable.Response) binaryCodec.decode(buffer);
        System.out.println("[QueryVariableTest][testQueryPixelBoxcarWidth] read: " +response);
        Assert.assertEquals(ResponsePack.Status.ACK, response.getStatus());
        Assert.assertEquals(0, response.getValue());

    }

    @Test
    public void testQueryAddScans() {
        QueryVariable.Request request = new QueryVariable.Request('A');
        byte[] requestBinary = binaryCodec.encode(request);
        Assert.assertArrayEquals(new byte[]{'?','A'}, requestBinary);
        System.out.println("[QueryVariableTest][testQueryAddScans] write: " + request);
        System.out.println("[QueryVariableTest][testQueryAddScans] write: 0x " + HexUtil.toString(requestBinary, 0, requestBinary.length));
        int length = serialPortResource.getSerialPort().writeBytes(requestBinary, requestBinary.length);
        Assert.assertEquals(requestBinary.length, length);

        byte[] buffer = new byte[256];
        int bytesToRead = serialPortResource.readBytes(buffer, 0, buffer.length);
        System.out.println("[QueryVariableTest][testQueryAddScans] read: 0x " + HexUtil.toString(buffer, 0, bytesToRead));
        Assert.assertEquals(3, bytesToRead);
        QueryVariable.Response response = (QueryVariable.Response) binaryCodec.decode(buffer);
        System.out.println("[QueryVariableTest][testQueryAddScans] read: " +response);
        Assert.assertEquals(ResponsePack.Status.ACK, response.getStatus());
        Assert.assertEquals(1, response.getValue());
    }

}