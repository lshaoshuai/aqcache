package team.dcweb.aqcache.support;

import java.util.HashMap;
import java.util.Map;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class DecoderMap {

    private static Map<Integer, AbstractValueDecoder> decoderMap = new HashMap<>();

    public DecoderMap() {
    }

    public static AbstractValueDecoder getDecoder(int identityNumber) {
        return decoderMap.get(identityNumber);
    }

    public static synchronized void register(int identityNumber, AbstractValueDecoder decoder) {
        Map<Integer, AbstractValueDecoder> newMap = new HashMap<>();
        newMap.putAll(decoderMap);
        newMap.put(identityNumber, decoder);
        decoderMap = newMap;
    }

    private static boolean inited = false;

    static void registerBuildInDecoder() {
        if (!inited) {
            register(JavaValueEncoder.IDENTITY_NUMBER, defaultJavaValueDecoder());
            register(KryoValueEncoder.IDENTITY_NUMBER, KryoValueDecoder.INSTANCE);
            inited = true;
        }
    }

    public static JavaValueDecoder defaultJavaValueDecoder() {
        try {
            Class.forName("org.springframework.core.ConfigurableObjectInputStream");
            return SpringJavaValueDecoder.INSTANCE;
        } catch (ClassNotFoundException e) {
            return JavaValueDecoder.INSTANCE;
        }
    }

}