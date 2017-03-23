package com.april.fourth.service;

import com.april.fourth.annotation.MockClient;
import com.april.fourth.dto.PersonDTO;
import org.springframework.stereotype.Component;

/**
 * Created by daipengfei
 * on 2017/3/23.
 */
@Component
public class HelloPersonImpl implements HelloPerson {

    @Override
    @MockClient(key = "key")
    public PersonDTO helloPerson() {
        return null;
    }

}
