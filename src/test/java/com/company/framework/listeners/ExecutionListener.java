package com.company.framework.listeners;

import org.testng.IExecutionListener;

/*
* Executes before and after the entire test suite.
* ExecutionListener listens to the start and finish of the test execution.
*/
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
