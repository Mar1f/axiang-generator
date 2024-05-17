package com.axiang.cli.pattern;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/17
 **/
public class RemoteControl {
    private Command command;

    public void setCommand(Command command){
        this.command = command;
    }
    public void pressButton(){
        command.execute();
    }

}
