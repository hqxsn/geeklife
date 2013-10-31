package com.challenge;

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

    private IdUtils() {
        seed = new AtomicLong(0);
        publishTime = System.currentTimeMillis();
        baseId = generate(1l);
    }

    public static IdUtils getInstance() {
        return instance;
    }

    //TODO: add check for max positive integer check
    public int distance(long id) {
        return (int)(id - baseId);
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

    public static void main(String args[]) {
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

    public long getBaseId() {
        return baseId;
    }
}
