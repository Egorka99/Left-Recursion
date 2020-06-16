import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LeftRecursionElimination {

    private CFG[] grammar;

    public LeftRecursionElimination(CFG[] grammar) {
        this.grammar = grammar;
    }

    private static CFG[] directLREliminate(CFG rule) {

        if (rule.getRules().stream().noneMatch(r -> r.startsWith(rule.getLeftNonTerminal()))) {
            return new CFG[]{rule};
        }

        List<String> alphas = rule.getRules().stream().filter(r -> r.startsWith(rule.getLeftNonTerminal())).collect(Collectors.toList());
        List<String> betas = rule.getRules().stream().filter(r -> !r.startsWith(rule.getLeftNonTerminal())).collect(Collectors.toList());

        CFG startNonTerminalRule = new CFG(rule.getLeftNonTerminal(), new ArrayList<>());

        betas.forEach(b -> startNonTerminalRule.addRule(b + rule.getLeftNonTerminal() + "'"));
        betas.forEach(startNonTerminalRule::addRule);

        CFG newNonTerminalRule = new CFG(rule.getLeftNonTerminal() + "'", new ArrayList<>());

        alphas.forEach(a -> newNonTerminalRule.addRule(a.substring(1) + rule.getLeftNonTerminal() + "'"));
        alphas.forEach(a -> newNonTerminalRule.addRule(a.substring(1)));
        return new CFG[]{startNonTerminalRule, newNonTerminalRule};

    }

    public CFG[] leftRecursionElimination() {
        List<CFG> grammarWithoutDirectLR = new ArrayList<>();
        List<CFG> grammarWithoutLR = new ArrayList<>();
        for (CFG cfg : grammar) {
            grammarWithoutDirectLR.addAll(Arrays.asList(directLREliminate(cfg)));
        }

        for (int i = 0; i < grammarWithoutDirectLR.size(); i++) {
            for (int j = 0; j < i; j++) {
                int length = grammarWithoutDirectLR.get(i).getRules().size();
                for (int k = 0; k < length; k++) {
                    String rule = grammarWithoutDirectLR.get(i).getRules().get(k);
                    if (rule.contains("'")) continue;
                    if (rule.startsWith(grammarWithoutDirectLR.get(j).getLeftNonTerminal())) {
                        grammarWithoutDirectLR.get(i).getRules().remove(rule);
                        for (int m = 0; m < grammarWithoutDirectLR.get(j).getRules().size(); m++) {
                            grammarWithoutDirectLR.get(i).addRule(grammarWithoutDirectLR.get(j).getRules().get(m) + rule.substring(1));
                        }
                    }
                }
            }

            for (CFG cfg : grammarWithoutDirectLR) {
                grammarWithoutLR.remove(cfg);
                grammarWithoutLR.addAll(Arrays.asList(directLREliminate(cfg)));
            }
        }

        return grammarWithoutLR.toArray(new CFG[0]);

    }


    public static void main(String[] args) {
        CFG rule1 = new CFG("S", new ArrayList<>());
        rule1.addRule("Sa");
        rule1.addRule("Ab");
        CFG rule2 = new CFG("A", new ArrayList<>());
        rule2.addRule("BA");
        rule2.addRule("a");
        CFG rule3 = new CFG("B", new ArrayList<>());
        rule3.addRule("aA");
        rule3.addRule("Sb");

        LeftRecursionElimination lre = new LeftRecursionElimination(new CFG[]{rule1, rule2, rule3});



        for (CFG cfg : lre.leftRecursionElimination()) {
            System.out.print(cfg.getLeftNonTerminal() + "-> ");
            cfg.getRules().forEach(r -> System.out.print(r + "|"));
            System.out.println();
        }


    }

}
