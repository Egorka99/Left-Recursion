import java.util.List;

public class CFG {
    private String leftNonTerminal;
    private List<String> rules;

    public CFG(String leftNonTerminal, List<String> rules) {
        this.leftNonTerminal = leftNonTerminal;
        this.rules = rules;
    }


    public String getLeftNonTerminal() {
        return leftNonTerminal;
    }

    public List<String> getRules() {
        return rules;
    }

    public void addRule(String rule) {
        rules.add(rule);
    }
}
