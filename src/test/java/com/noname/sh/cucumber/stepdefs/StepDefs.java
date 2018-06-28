package com.noname.sh.cucumber.stepdefs;

import com.noname.sh.ShApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ShApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
