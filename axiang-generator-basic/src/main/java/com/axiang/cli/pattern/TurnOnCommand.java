package com.axiang.cli.pattern;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/17
 **/
public class TurnOnCommand implements Command{
    private Device device;

    public TurnOnCommand(Device device){
        this.device = device;
    }
    public void execute(){
        device.turnOn();
    }
}
