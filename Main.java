import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java Main program.txt config.txt");
            return;
        }

        List<String> programLines = Files.readAllLines(Paths.get(args[0]));
        List<String> configLines = Files.readAllLines(Paths.get(args[1]));

        int baseAddr = Integer.decode(configLines.get(0).trim());
        int initialPC = Integer.decode(configLines.get(1).trim());


        /*
        // DEBUG SATIRI
        System.out.println("Initial PC: 0x" + Integer.toHexString(initialPC));
        System.out.println("Base Address: 0x" + Integer.toHexString(baseAddr));
        //


         */

        CPUEmulator emulator = new CPUEmulator(0x2000, 0x2000, programLines);
        emulator.execute();
    }
}
