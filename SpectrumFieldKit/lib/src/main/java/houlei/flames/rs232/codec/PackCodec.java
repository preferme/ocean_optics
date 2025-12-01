package houlei.flames.rs232.codec;

import houlei.flames.rs232.pack.RequestPack;
import houlei.flames.rs232.pack.ResponsePack;

public interface PackCodec {

    byte[] encode(RequestPack pack);

    ResponsePack decode(byte[] buffer);

}
