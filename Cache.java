public class Cache {
    private final byte[][] data;
    private final int[] tags;
    private final boolean[] valid;

    private int hits = 0;
    private int misses = 0;

    public Cache() {
        data = new byte[8][2]; // 8 block, 2 byte/block
        tags = new int[8];
        valid = new boolean[8];
    }

    public byte read(int address, Memory memory) {
        int blockIndex = (address / 2) % 8;
        int tag = address / 16;

        if (!valid[blockIndex] || tags[blockIndex] != tag) {
            // MISS
            misses++;
            int blockAddress = address & ~1;
            data[blockIndex][0] = memory.readByte(blockAddress);
            data[blockIndex][1] = memory.readByte(blockAddress + 1);
            tags[blockIndex] = tag;
            valid[blockIndex] = true;
        } else {
            // HIT
            hits++;
        }

        return data[blockIndex][address % 2];
    }

    public void write(int address, byte value, Memory memory) {
        int blockIndex = (address / 2) % 8;
        int tag = address / 16;

        // Write-through
        memory.writeByte(address, value);

        // Cache update

        if (!valid[blockIndex] || tags[blockIndex] != tag) {
            // MISS
            misses++;
            int blockAddress = address & ~1;
            data[blockIndex][0] = memory.readByte(blockAddress);
            data[blockIndex][1] = memory.readByte(blockAddress + 1);
            tags[blockIndex] = tag;
            valid[blockIndex] = true;
        }

        // Write into cache
        data[blockIndex][address % 2] = value;
    }

    public int getTotalAccesses() {
        return hits + misses;
    }

    public int getHits() {
        return hits;
    }
}
