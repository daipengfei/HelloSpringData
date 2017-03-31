package com.april.fourth.rule;

import com.april.fourth.dto.RuleContext;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.easyrules.quartz.RulesEngineScheduler;
import org.easyrules.quartz.RulesEngineSchedulerException;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * Created by daipengfei
 * on 2017/3/30.
 */

public class MySecondRule extends AbstractRule{


    @Override
    public boolean evaluate() {
        return getRuleContext().isPassed() && getRuleContext().getNum() > 20
                && getRuleContext().getNum() <= 30;
    }

    @Override
    public void execute() throws Exception {
        getRuleContext().setFiltered(true);
    }

}
