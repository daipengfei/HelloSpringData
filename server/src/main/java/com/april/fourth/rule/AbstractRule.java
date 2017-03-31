package com.april.fourth.rule;

import com.april.fourth.dto.RuleContext;
import org.easyrules.core.BasicRule;

/**
 * Created by daipengfei
 * on 2017/3/31.
 */
public abstract class AbstractRule extends BasicRule {
    private ThreadLocal<RuleContext> contextThreadLocal = new ThreadLocal<>();

    public void setRuleContext(RuleContext ruleContext) {
        this.contextThreadLocal.set(ruleContext);
    }

    public RuleContext getRuleContext() {
        return contextThreadLocal.get();
    }

    @Override
    public String getName() {
        return getClass().getName();
    }
}
