# CPU Emulator - Akdeniz University CSE206 Project

This project is a simple CPU Emulator developed in Java as part of the **Computer Organization (CSE206)** course at **Akdeniz University**, under the guidance of **Instructor Taner Danışman**. The project simulates a basic CPU capable of executing a predefined instruction set using memory and cache systems.

## Project Structure

- `CpuEmulator.java`: Core CPU logic including instruction decode/execute.
- `Cache.java`: Simulates a simple direct-mapped cache structure.
- `Memory.java`: Simulates 64KB of main memory with byte and word access.
- `EmulatorDriver.java`: Loads the program, initializes memory and PC, starts execution.
- `program.txt`: Contains the binary machine instructions to be loaded and executed.

## Instruction Set Summary

| Opcode | Operation | Description                            |
|--------|-----------|----------------------------------------|
| 0x0    | START     | Marks the beginning of execution       |
| 0x1    | LOAD      | Load an immediate value into AC        |
| 0x2    | LOADM     | Load a word from memory into AC        |
| 0x3    | STORE     | Store the AC value into memory         |
| 0x4    | CMPM      | Compare AC with memory value           |
| 0x5    | CJMP      | Conditional jump (FLAG > 0)            |
| 0x6    | JMP       | Unconditional jump                     |
| 0x7    | ADD       | Add immediate value to AC              |
| 0x8    | ADDM      | Add memory value to AC                 |
| 0xD    | DISP      | Display the AC value                   |
| 0xE    | HALT      | Stop execution                         |

All instructions are 16-bit: 4 bits opcode, 12 bits operand.

## Technical Highlights

- **Memory Addressing:** Fully 16-bit, wrap-around ensured via `address & 0xFFFF`.
- **Endianness:** Little-endian byte order is respected in `readWord()` and `writeWord()` methods.
- **Cache:** All memory accesses go through a direct-mapped cache that tracks hits/misses.
- **Correct Output:** Final AC value is 210, and cache hit rate is around 68.96%.

## Note on `program.txt`

Original memory addresses in `program.txt` caused overwrites due to cache collisions. The addresses of `limit`, `counter`, and `sum` were updated from `0x0C8`, `0x0C9`, and `0x0CA` to `0x0100`, `0x0102`, and `0x0104` respectively to fix this issue.

## Requirements

- Java version used: **Java 17**
- Tested on: **IntelliJ IDEA / Command line**

## Author

**Cem Erdemir**  
Computer Engineering Department  
Akdeniz University, Turkey
