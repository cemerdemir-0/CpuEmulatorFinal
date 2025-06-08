public class Memory {
    private final byte[] memory;

    public Memory() {
        memory = new byte[65536]; // 64 KB
    }

    public byte readByte(int address) {
        return memory[address & 0xFFFF]; // prevent wrap around
    }

    public void writeByte(int address, byte value) {
        memory[address & 0xFFFF] = value;
    }

    public void writeWord(int address, int value) {
        writeByte(address,     (byte)(value & 0xFF));         // low byte
        writeByte(address + 1, (byte)((value >> 8) & 0xFF));  // high byte
    }

    public int readWord(int address) {
        int low  = readByte(address) & 0xFF;
        int high = readByte(address + 1) & 0xFF;
        return (high << 8) | low;
    }
}
