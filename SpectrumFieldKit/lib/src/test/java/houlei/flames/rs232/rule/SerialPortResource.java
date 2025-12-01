package houlei.flames.rs232.rule;

import com.fazecast.jSerialComm.SerialPort;

import org.junit.rules.ExternalResource;

public class SerialPortResource extends ExternalResource {

    private static final String SERIAL_PORT_NAME = "COM6";

    private SerialPort serialPort;

    public SerialPort getSerialPort() {
        return serialPort;
    }

    @Override
    protected void before() throws Throwable {
        if (serialPort == null) {
            serialPort = SerialPort.getCommPort(SERIAL_PORT_NAME);
            if (!serialPort.openPort()) {
                throw new IllegalStateException("[ConfigRule][apply][evaluate] Serial Port (" + SERIAL_PORT_NAME + ") is not opened.");
            }
//            serialPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            serialPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, 100, 0);
        }
    }

    @Override
    protected void after() {
        if (serialPort.isOpen()) {
            serialPort.closePort();
        }
    }

    public int readBytes(byte[] buffer, int offset, int length) {
        int bytesToRead = 0;
        while (bytesToRead < length) {
            int len = serialPort.readBytes(buffer, length-bytesToRead, offset+bytesToRead);
            if(len <= 0){
                return bytesToRead;
            } else {
                bytesToRead += len;
            }
        }
        return bytesToRead;
    }

}
