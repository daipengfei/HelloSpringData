package com.april.fourth.rule;

import com.april.fourth.dto.Context;
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
@Rule(name = "myFirstRule")
@Component
public class MyFirstRule{
    private Context context;

    @Condition
    public boolean when(){
        return context.isPassed();
    }

    @Action
    public void then(){
        System.out.println("hello easy rule!");
    }

    public static void main(String[] args)  {
        double[] doubles = {1, 3};
        System.out.println(doubles.length);
    }
}
