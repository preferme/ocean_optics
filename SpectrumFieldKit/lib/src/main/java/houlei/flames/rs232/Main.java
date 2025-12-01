package houlei.flames.rs232;

import com.fazecast.jSerialComm.SerialPort;



public class Main {

    public static void main(String[] args) throws InterruptedException {
        final String PORT_NAME = "COM6";

        SerialPort port = SerialPort.getCommPort(PORT_NAME);

        System.out.println("[Main] " + port.getDescriptivePortName());

        if (port.openPort()) {
//            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);
            port.writeBytes(new byte[]{'v', '\r', '\n'}, 1);
            byte[] buffer = new byte[256];
            while (true) {
                //noinspection BusyWait
                Thread.sleep(50);
                int length = port.readBytes(buffer, buffer.length);
                System.out.println("[Main] Read " + length);
                if (length > 0) {
                    System.out.println(HexUtil.toString(buffer, 0, length));
                }
                if (length == 0) {
                    break;
                }
            }

            port.writeBytes(new byte[]{'v', '\r'}, 2);
//            byte[] buffer = new byte[256];
            while (true) {
                //noinspection BusyWait
                Thread.sleep(50);
                int length = port.readBytes(buffer, buffer.length);
                System.out.println("[Main] Read " + length);
                if (length > 0) {
                    System.out.println(HexUtil.toString(buffer, 0, length));
                }
                if (length == 0) {
                    break;
                }
            }

            System.out.println(0x0FA5);
        }
    }

}
