package com.challenge;

import java.nio.CharBuffer;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with Andy Song.
 * User: andysong
 * Date: 13-10-31
 * Time: AM10:30
 * To change this template use File | Settings | File Templates.
 */
public class IdUtils {

    private final static IdUtils instance = new IdUtils();

    private AtomicLong seed;

    private long publishTime;

    private long baseId;

    public static final int INVALID_INSTANCE = -1000000;

    private IdUtils() {
        seed = new AtomicLong(0);
        publishTime = System.currentTimeMillis();
        baseId = generate(1l);
    }

    public static IdUtils getInstance() {
        return instance;
    }

    public int distance(long id) {

        long distance = (id - baseId);

        if (distance >= Integer.MAX_VALUE)
            return INVALID_INSTANCE;

        return (int)(distance);
    }

    public void reset() {
        seed = new AtomicLong(0);
        publishTime = System.currentTimeMillis();
        baseId = generate(1l);
    }

    /*
        This method is generate the sequence id base on
        type 00000001  8bit
        time elapsed from the begin time  000000000000000000000000000000  40bit
        the remnant-- recycled {0-1024}    0000000000000000 16bit

        type + time + recycled = 64bit (long type)
     */
    public long generate(long type) {

        long timeDuration = System.currentTimeMillis() - publishTime;

        long id = type << 58;

        //System.out.println(Long.toBinaryString(id));
        id ^= timeDuration << 18;
        //System.out.println(Long.toBinaryString(timeDuration << 18));
        //System.out.println(Long.toBinaryString(id));
        long seq = seed.incrementAndGet();
        if (seq > 1024) {
            seq = 0;
            seed.set(0);
        }
        id ^= seq;
        //System.out.println(Long.toBinaryString(id));
        return id;

    }

    /*public static void main(String args[]) {
        long id1 = IdUtils.getInstance().generate(1l);
        long id2 = IdUtils.getInstance().generate(1l);
        long id3 = IdUtils.getInstance().generate(1l);
        long id4 = IdUtils.getInstance().generate(1l);
        long id5 = IdUtils.getInstance().generate(1l);
        long id6 = IdUtils.getInstance().generate(1l);
        long id7 = IdUtils.getInstance().generate(1l);
        long id8 = IdUtils.getInstance().generate(1l);
        long id9 = IdUtils.getInstance().generate(1l);
        long id10 = IdUtils.getInstance().generate(1l);

        System.out.println(id1);
        System.out.println(id10);
        System.out.println(id10-id1);
    }
*/
    public long getBaseId() {
        return baseId;
    }

    public static void main(String[] args) throws Exception {
        CharBuffer buffer = CharBuffer.allocate(64);
        buffer.put("Let's write some Java code! ");

        System.out.println("Position: " + buffer.position());
        System.out.println("Limit   : " + buffer.limit());

        //
        // Read 10 chars from the buffer.
        //
        buffer.flip();
        for (int i = 0; i < 10; i++) {
            System.out.print(buffer.get());
        }
        System.out.println("");

        System.out.println("Position: " + buffer.position());
        System.out.println("Limit   : " + buffer.limit());

        //
        // clear the buffer using compact() method.
        //
        buffer.rewind();
        System.out.println("Position: " + buffer.position());
        System.out.println("Limit   : " + buffer.limit());

        //
        // Write and read some more data.
        //
        buffer.put("Add some more data.");

        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
    }
}
