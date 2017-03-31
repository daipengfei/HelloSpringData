package com.april.fourth.rule;

import com.april.fourth.dto.RuleContext;
import org.easyrules.api.Rule;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.easyrules.quartz.RulesEngineSchedulerException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
        System.out.println("third rule executed! ruleContext = " + getRuleContext());
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
        for (int i = 0; i < 50; i++) {
            executorService.execute(new RuleTask(i));
        }

    }

    static class RuleTask implements Runnable {
        private final int taskId;

        public RuleTask(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            RuleContext ruleContext = new RuleContext();
            ruleContext.setPassed(true);
            ruleContext.setNum(taskId);
            for (Rule rule : rulesEngine.getRules()) {
                AbstractRule abstractRule = (AbstractRule) rule;
                abstractRule.setRuleContext(ruleContext);
            }
            rulesEngine.fireRules();
        }
    }

}
