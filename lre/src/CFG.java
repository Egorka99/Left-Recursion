import java.util.List;

public class CFG {
    private String leftNonTerminal;
    private List<String> rules;

    public CFG(String leftNonTerminal, List<String> rules) {
        this.leftNonTerminal = leftNonTerminal;
        this.rules = rules;
    }

    public CFG() {
    }

    public String getLeftNonTerminal() {
        return leftNonTerminal;
    }

    public List<String> getRules() {
        return rules;
    }

    public void setLeftNonTerminal(String leftNonTerminal) {
        this.leftNonTerminal = leftNonTerminal;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    public void addRule(String rule) {
        rules.add(rule);
    }
}
