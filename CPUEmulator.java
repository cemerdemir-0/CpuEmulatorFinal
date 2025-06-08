import java.util.List;

public class CPUEmulator {
    private int PC;
    private int AC = 0;
    private int FLAG = 0;
    private int baseAddress;

    private final Memory memory;
    private final Cache cache;

    public CPUEmulator(int baseAddress, int initialPC, List<String> instructions) {
        this.baseAddress = baseAddress;
        this.PC = initialPC;
        this.memory = new Memory();
        this.cache = new Cache();
        loadProgram(instructions);
    }

    private void loadProgram(List<String> instructions) {
        for (int i = 0; i < instructions.size(); i++) {
            int instruction = Integer.parseInt(instructions.get(i), 2);
            memory.writeWord(baseAddress + i * 2, instruction);

        }

        //System.out.printf("Program loaded from address: 0x%04X to 0x%04X\n",
                //baseAddress, baseAddress + instructions.size() * 2 - 1);

    }

    public void execute() {
        int stepCounter = 0;

        while (true) {
            int instr = readMemWord(PC);  // <-- READS FROM CACHE
            int opcode = (instr >> 12) & 0xF;
            int operand = instr & 0x0FFF;

            if (stepCounter < 1000) {
                //System.out.printf("PC: %04X | OPCODE: %X | OPERAND: %03X | AC: %d | FLAG: %d\n",
                        //PC, opcode, operand, AC, FLAG);
            }
            stepCounter++;

            switch (opcode) {
                case 0x0: // START
                    PC += 2;
                    break;
                case 0x1: // LOAD
                    AC = operand;
                    PC += 2;
                    break;
                case 0x2: // LOADM
                    AC = readMemWord(baseAddress + operand);
                    PC += 2;
                    break;
                case 0x3: // STORE
                    writeMemWord(baseAddress + operand, AC);
                    PC += 2;
                    break;
                case 0x4: // CMPM
                    int val = readMemWord(baseAddress + operand);
                    //System.out.println("CMPM DEBUG — AC: " + AC + " vs MEM[" + (baseAddress + operand) + "]: " + val);
                    FLAG = Integer.compare(AC, val);
                    PC += 2;
                    break;
                case 0x5: // CJMP
                    if (FLAG > 0) {
                        PC = baseAddress + operand * 2;
                    } else {
                        PC += 2;
                    }
                    break;
                case 0x6: // JMP
                    PC = baseAddress + operand * 2;
                    break;
                case 0x7: // ADD
                    AC += operand;
                    PC += 2;
                    break;
                case 0x8: // ADDM
                    AC += readMemWord(baseAddress + operand);
                    PC += 2;
                    break;
                case 0x9: // SUB
                    AC -= operand;
                    PC += 2;
                    break;
                case 0xA: // SUBM
                    AC -= readMemWord(baseAddress + operand);
                    PC += 2;
                    break;
                case 0xB: // MUL
                    AC *= operand;
                    PC += 2;
                    break;
                case 0xC: // MULM
                    AC *= readMemWord(baseAddress + operand);
                    PC += 2;
                    break;
                case 0xD: // DISP
                    System.out.println("Value in AC: " + AC);
                    PC += 2;
                    break;
                case 0xE: // HALT
                    printStats();
                    return;
                default:
                    System.err.println("Unknown opcode: " + opcode);
                    return;
            }
        }
    }

    private int readMemWord(int address) {
        int low = cache.read(address, memory) & 0xFF;
        int high = cache.read(address + 1, memory) & 0xFF;
        return (high << 8) | low;
    }

    private void writeMemWord(int address, int value) {
        byte low = (byte)(value & 0xFF);
        byte high = (byte)((value >> 8) & 0xFF);
        cache.write(address, low, memory);
        cache.write(address + 1, high, memory);
    }




    private void printStats() {
        int total = cache.getTotalAccesses();
        int hits = cache.getHits();
        double ratio = total == 0 ? 0.0 : (100.0 * hits / total);
        System.out.printf("Cache hit ratio: %.2f%%\n", ratio);
    }
}
