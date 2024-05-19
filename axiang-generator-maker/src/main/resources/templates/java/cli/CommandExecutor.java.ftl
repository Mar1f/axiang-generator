package ${basePackage}.cli;

import ${basePackage}.cli.command.ConfigCommand;
import ${basePackage}.cli.command.GenerateCommand;
import ${basePackage}.cli.command.ListCommand;
import picocli.CommandLine.Command;
import picocli.CommandLine;
/**
 * @description；
 * @author:mar1
 * @data:2024/05/17
 **/


@Command(name = "${name}", mixinStandardHelpOptions =true)
public class CommandExecutor implements Runnable{
    private final CommandLine commandLine;
    {
        commandLine = new CommandLine(this)
                .addSubcommand(new GenerateCommand())
                .addSubcommand(new ConfigCommand())
                .addSubcommand(new ListCommand());
    }
    @Override
    public void run(){
        //不输入命令，给出提示
        System.out.println("请输入具体命令，或者输入--help 查看命令提示");
    }

    /**
     *
     * @param args
     * @return
     */
    public Integer doExecute(String[] args){
        return commandLine.execute(args);
    }
}
