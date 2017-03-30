package com.april.fourth.rule;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.BasicRule;
import org.easyrules.core.RulesEngineBuilder;
import org.easyrules.quartz.RulesEngineScheduler;
import org.easyrules.quartz.RulesEngineSchedulerException;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * Created by daipengfei
 * on 2017/3/30.
 */

@Component
public class MyThirdRule extends BasicRule{

    @Override
    public boolean evaluate() {
       return true;
    }

    @Override
    public void execute() throws Exception {
        System.out.println("third rule!!!");
    }

    public static void main(String[] args) throws RulesEngineSchedulerException {
        RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
                .withSilentMode(true)
                .build();
        MyThirdRule myFirstRule = new MyThirdRule();
        rulesEngine.registerRule(myFirstRule);
        RulesEngineScheduler scheduler = RulesEngineScheduler.getInstance();
        scheduler.scheduleAtWithInterval(rulesEngine,new Date(),1);
        scheduler.start();
    }
}
