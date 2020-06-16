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


    public static void main(String[] args) {
        CFG rule = new CFG("B", Arrays.asList("Sa", "Ab"));
        CFG rule2 = new CFG("S", Collections.singletonList("Ab"));

        for (CFG cfg : directLREliminate(rule)) {
            System.out.print(cfg.getLeftNonTerminal() + "-> ");
            cfg.getRules().forEach(r -> System.out.print(r + "|"));
            System.out.println();
        }

//        for (CFG cfg : directLREliminate(rule2)) {
//            System.out.print(cfg.getLeftNonTerminal() + "-> ");
//            cfg.getRules().forEach(r -> System.out.print(r + "|"));
//            System.out.println();
//        }


    }

}
