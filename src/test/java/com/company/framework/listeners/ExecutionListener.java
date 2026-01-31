package com.company.framework.listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.IExecutionListener;

/*
* Executes before and after the entire test suite.
* ExecutionListener listens to the start and finish of the test execution.
*/
@Slf4j
public class ExecutionListener implements IExecutionListener {

    @Override
    public void onExecutionStart() {
        System.out.println("Test execution started.");
    }

    @Override
    public void onExecutionFinish() {
        System.out.println("Test execution finished.");
    }
}
