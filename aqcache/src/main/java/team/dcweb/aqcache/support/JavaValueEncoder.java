package team.dcweb.aqcache.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class JavaValueEncoder extends AbstractValueEncoder {

    public static final JavaValueEncoder INSTANCE = new JavaValueEncoder(true);

    protected static int IDENTITY_NUMBER = 0x4A953A80;

    private static final int INIT_BUF_SIZE = 256;

    public JavaValueEncoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    private static ThreadLocal<WeakReference<ByteArrayOutputStream>> threadLocal =
            ThreadLocal.withInitial(() -> new WeakReference<>(new ByteArrayOutputStream(INIT_BUF_SIZE)));

    @Override
    public byte[] apply(Object value) {
        try {
            WeakReference<ByteArrayOutputStream> ref = threadLocal.get();
            ByteArrayOutputStream bos = ref.get();
            if (bos == null) {
                bos = new ByteArrayOutputStream(INIT_BUF_SIZE);
                threadLocal.set(new WeakReference<>(bos));
            }

            try {
                if (useIdentityNumber) {
                    bos.write((IDENTITY_NUMBER >> 24) & 0xFF);
                    bos.write((IDENTITY_NUMBER >> 16) & 0xFF);
                    bos.write((IDENTITY_NUMBER >> 8) & 0xFF);
                    bos.write(IDENTITY_NUMBER & 0xFF);
                }


                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(value);
                oos.flush();
                return bos.toByteArray();
            } finally {
                bos.reset();
            }
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder("Java Encode error. ");
            sb.append("msg=").append(e.getMessage());
            throw new CacheEncodeException(sb.toString(), e);
        }
    }
}