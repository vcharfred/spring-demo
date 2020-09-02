package top.vchar.util;

import io.netty.buffer.ByteBufAllocator;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.Nullable;

import java.net.InetSocketAddress;

/**
 * <p> 网络工具类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/1
 */
public class NetworkUtil {

    /**
     * 未知的主机
     */
    public static final String UNKNOWN = "unknown";

    /**
     * IP 最大长度
     */
    private static final int IP_MAX_LENGTH = 15;

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @return 返回IP地址
     */
    public static String getIpAddress(ServerHttpRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        HttpHeaders httpHeaders = request.getHeaders();
        String ip = httpHeaders.getFirst("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = httpHeaders.getFirst("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = httpHeaders.getFirst("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = httpHeaders.getFirst("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = httpHeaders.getFirst("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                InetSocketAddress remoteAddress = request.getRemoteAddress();
                if(remoteAddress!=null && remoteAddress.getAddress()!=null){
                    ip = remoteAddress.getAddress().getHostAddress();
                }
            }
        } else if (ip.length() > IP_MAX_LENGTH) {
            String[] ips = ip.split(",");
            if (ips.length > 0) {
                for (String strIp : ips) {
                    if (!(UNKNOWN.equalsIgnoreCase(strIp))) {
                        ip = strIp;
                        break;
                    }
                }
            }
        }
        return ip;
    }

    /**
     * 判断是否是上传文件
     * @param mediaType 请求媒体类型
     * @return true-是文件上传
     */
    public static boolean isUploadFile(@Nullable MediaType mediaType) {
        if (mediaType!=null) {
            return mediaType.equalsTypeAndSubtype(MediaType.MULTIPART_FORM_DATA)
                    || mediaType.equalsTypeAndSubtype(MediaType.IMAGE_GIF)
                    || mediaType.equalsTypeAndSubtype(MediaType.IMAGE_JPEG)
                    || mediaType.equalsTypeAndSubtype(MediaType.IMAGE_PNG)
                    || mediaType.equalsTypeAndSubtype(MediaType.MULTIPART_MIXED);
        }
        return false;
    }

    public static DataBuffer toDataBuffer(byte[] bytes) {
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }
}
