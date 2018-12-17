import java.io.*;

public class Main {

    private static int N;
    private static long Q;
    private static int[] map;
    private static long[] sums;

    private static byte[] iBuf;
    private static int iBufPos;

    private final static int strLongSize = 15;

    private static byte[] buf = new byte[strLongSize];

    private static byte[] oBuf;
    private static int oBufLen;

    public static void main(String[] args) throws IOException {

        final int bufferSize = 6 * 1024 * 1024; // dirty hack
        iBuf = new byte[bufferSize];
        final int iBufLen = System.in.read(iBuf);
        if (iBufLen == bufferSize) {
            throw new IOException();
        }
        iBufPos = 0;

        N = readStdinInt(); // 1 .. 550

        final boolean isBench = N >= 0;
        if (!isBench) {
            N = readStdinInt();
        }

        readMap();

        Q = readStdinInt(); // 0 .. 100100
        if (Q <= 0) {
            return;
        }

        if (isBench) {
            benchRun();
        } else {
            debugRun();
        }
    }

    private static void readMap() {
        final int total = N * N;
        map = new int[total]; // -10^8 .. 10^8
        sums = new long[total];

        for (int mapIdx = 0; mapIdx < total; ++mapIdx) {
            final int val = readStdinInt();
            map[mapIdx] = val;
            sums[mapIdx] = val;
        }

        for (int i = N, mapIdx = N; i < total; ++i, ++mapIdx) {
            sums[mapIdx] += sums[mapIdx - N];
        }
    }

    private static void debugRun() {
        for (; Q > 0; --Q) {
            int K = readStdinInt();

            if (K == 1) {
                long expectSum = readStdinLong();
                long sum = doSum();
                if (sum != expectSum) {
                    System.out.printf("1 %d got %d\n", expectSum, sum);
                    return;
                }
            } else {
                doUpdate();
            }
        }
    }

    private static void benchRun() {
        final int oBufCap = 64 * 1024;
        oBuf = new byte[oBufCap + strLongSize];
        oBufLen = 0;

        for (; Q > 0; --Q) {
            final byte K = iBuf[iBufPos];
            iBufPos += 2; // K + space

            if (K == '1') {
                long2Bs(doSum());
                oBuf[oBufLen++] = '\n';

                if (oBufLen >= oBufCap) {
                    System.out.write(oBuf, 0, oBufLen);
                    oBufLen = 0;
                }
            } else {
                doUpdate();
            }
        }

        if (oBufLen >= 0) {
            System.out.write(oBuf, 0, oBufLen);
        }
    }

    private static long doSum() {
        int X1 = readStdinInt();
        int Y1 = readStdinInt();
        int X2 = readStdinInt();
        int Y2 = readStdinInt();

        long sum = 0;

        final int dist = X2 - X1 + 1;

        int mapIdx2 = X1 + Y2 * N;
        for (int i = dist; i > 0; --i, ++mapIdx2) {
            sum += sums[mapIdx2];
        }

        if (--Y1 >= 0) {
            int mapIdx1 = X1 + Y1 * N;
            for (int i = dist; i > 0; --i, ++mapIdx1) {
                sum -= sums[mapIdx1];
            }
        }

        return sum;
    }

    private static void doUpdate() {
        final int X = readStdinInt();
        final int Y = readStdinInt();
        final int P = readStdinInt();

        int mapIdx = X + Y * N;
        final long delta = P - map[mapIdx];
        map[mapIdx] = P;

        final int mapIdxAfterLast = X + N * N;
        for (; mapIdx < mapIdxAfterLast; mapIdx += N) {
            sums[mapIdx] += delta;
        }
    }

    private static int readStdinInt() {
        int val = 0;
        boolean neg = false;

        while (true) { // iBufPos < iBufLen
            final byte ch = iBuf[iBufPos++];

            if ('0' <= ch && ch <= '9') {
                val = 10 * val + (ch - '0');
            } else if (ch == '-') {
                neg = true;
            } else {
                break;
            }
        }

        return neg ? -val : val;
    }

    private static long readStdinLong() {
        long val = 0;
        boolean neg = false;

        while (true) {
            byte ch = iBuf[iBufPos++];

            if ('0' <= ch && ch <= '9') {
                val = 10 * val + (ch - '0');
            } else if (ch == '-') {
                neg = true;
            } else {
                break;
            }
        }

        return neg ? -val : val;
    }

    private static void long2Bs(long v) {
        if (v == 0) {
            oBuf[oBufLen++] = '0';
            return;
        } else if (v < 0) {
            oBuf[oBufLen++] = '-';
            v = -v;
        }

        int p;
        for (p = 0; v > 0; ++p) {
            buf[p] = (byte) ((v % 10) + '0');
            v = v / 10;
        }

        while (--p >= 0) {
            oBuf[oBufLen++] = buf[p];
        }
    }
}
