import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileIO {

    private String inputFilePath;
    private String outputFilePath;

    public FileIO(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }


    public CFG[] read() throws IOException {
        List<CFG> cfgList = new ArrayList<>();

        String[] rules = readInputString().split("\r\n");
        for (String rule : rules) {
            cfgList.add(new CFG(String.valueOf(rule.charAt(0)), findRightRules(rule)));
        }

        return cfgList.toArray(new CFG[0]);
    }

    public void write(CFG[] grammar) throws IOException {
        FileWriter fileWriter = new FileWriter(outputFilePath);
        StringBuilder output = new StringBuilder();
        for (CFG cfg : grammar) {
            output.append(cfg.getLeftNonTerminal()).append("-> ");
            cfg.getRules().forEach(r -> output.append(r).append("|"));
            output.append("\r\n");
        }
        fileWriter.write(output.toString());
        fileWriter.flush();
        fileWriter.close();
    }


    private String readInputString() throws IOException {
        FileReader fileReader = new FileReader(inputFilePath);
        StringBuilder input = new StringBuilder();

        int c;
        while ((c = fileReader.read()) != -1) {
            input.append((char) c);
        }
        fileReader.close();

        return input.toString();
    }


    private static List<String> findRightRules(String rule) {
        return new ArrayList<>(Arrays.asList(rule.substring(5).split(" \\| ")));
    }
}
