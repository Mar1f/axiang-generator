package com.axiang.cli.example;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import java.util.concurrent.Callable;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/17
 **/
public class Login implements Callable<Integer> {
    @Option(names ={"-u","--user"}, description = "User name")
    String user;
    @Option(names = {"-p", "--p assword"},description ="Passphrase", arity = "0..1", interactive = true)
    String password;
    @Option(names = {"-cp", "--checkPassword"},description ="Passphrase", arity = "0..1", interactive = true)
    String checkPassword;

    public Integer call() throws Exception {
        System.out.println("password =" + password);
        System.out.println("checkPassword =" + checkPassword);
        return 0;
    }
    public static void main (String[] args){
        new CommandLine(new Login()).execute("-u", "user123", "-p", "-cp");
    }
}
