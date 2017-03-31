package com.april.fourth.rule;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.springframework.stereotype.Component;


/**
 * Created by daipengfei
 * on 2017/3/30.
 */
@Rule(name = "myFirstRule")
@Component
public class MyFirstRule{

    @Condition
    public boolean when(){
        return true;
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
