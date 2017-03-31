package com.april.fourth.rule;

import com.april.fourth.dto.RuleContext;

/**
 * Created by daipengfei
 * on 2017/3/30.
 */

public class MyFirstRule extends AbstractRule{


    @Override
    public boolean evaluate() {
        return getRuleContext().isPassed() && getRuleContext().getNum() > 10
                && getRuleContext().getNum() <= 20;
    }

    @Override
    public void execute() throws Exception {
        System.out.println("first rule executed! ruleContext = " + getRuleContext());
    }

}
