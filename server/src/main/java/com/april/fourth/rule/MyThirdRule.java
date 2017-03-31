package com.april.fourth.rule;

import com.april.fourth.dto.RuleContext;
import org.easyrules.api.Rule;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.easyrules.quartz.RulesEngineSchedulerException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * Created by daipengfei
 * on 2017/3/30.
 */

public class MyThirdRule extends AbstractRule {

    @Override
    public boolean evaluate() {
        return getRuleContext().isPassed() && getRuleContext().getNum() > 30;
    }

    @Override
    public void execute() throws Exception {
        getRuleContext().setFiltered(true);
    }

    static RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
            .withSilentMode(true).build();

    static {
        rulesEngine.registerRule(new MyFirstRule());
        rulesEngine.registerRule(new MySecondRule());
        rulesEngine.registerRule(new MyThirdRule());
    }


    public static void main(String[] args) throws RulesEngineSchedulerException {

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        List<RuleContext> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            RuleContext ruleContext = new RuleContext();
            ruleContext.setPassed(true);
            ruleContext.setNum(i);
            list.add(ruleContext);
        }
        List<RuleContext> filtered = new ArrayList<>();
        for (RuleContext ruleContext : list) {
            Future<Boolean> future = executorService.submit(new RuleTask(ruleContext));
            try {
                if(!future.get()){
                    filtered.add(ruleContext);
                }
            } catch (Exception e) {
               ;
            }
        }
        System.out.println(filtered);
    }

    static class RuleTask implements Callable<Boolean> {
        private final RuleContext ruleContext;

        public RuleTask(RuleContext ruleContext) {
            this.ruleContext = ruleContext;
        }

        @Override
        public Boolean call() {
            for (Rule rule : rulesEngine.getRules()) {
                AbstractRule abstractRule = (AbstractRule) rule;
                abstractRule.setRuleContext(ruleContext);
            }
            rulesEngine.fireRules();
            return ruleContext.isFiltered();
        }
    }

}
