package top.vchar.client;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * <p> 自定义 GoodsInfo类的 序列化器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/11/19
 */
public class CustomSerializer implements Serializer<GoodsInfo> {
    @Override
    public byte[] serialize(String topic, GoodsInfo data) {
        if(data == null){
            return null;
        }
        byte[] nameBytes, addressBytes;

        try {
            if(data.getName()!=null){
                nameBytes = data.getName().getBytes(StandardCharsets.UTF_8);
            }else {
                nameBytes = new byte[0];
            }
            if(data.getAddress()!=null){
                addressBytes = data.getAddress().getBytes(StandardCharsets.UTF_8);
            }else {
                addressBytes = new byte[0];
            }
            ByteBuffer buffer = ByteBuffer.allocate(4+4+nameBytes.length+addressBytes.length);
            buffer.putInt(nameBytes.length);
            buffer.put(nameBytes);
            buffer.putInt(addressBytes.length);
            buffer.put(addressBytes);
            return buffer.array();
        }catch (Exception e){
            throw new SerializationException("serialization GoodsInfo fail, "+e.getMessage());
        }
    }
}
