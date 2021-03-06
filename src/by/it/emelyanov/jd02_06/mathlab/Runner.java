package by.it.emelyanov.jd02_06.mathlab;

import by.it.emelyanov.jd02_06.mathlab.parser.Parser;
import by.it.emelyanov.jd02_06.mathlab.vars.Variable;

import java.util.Scanner;


public class Runner {
    private static void one(Variable v) {
        if (v != null)System.out.println(v);
    }

    public static void main(String[] args) {

        Scanner scanner=new Scanner(System.in);
        String line;
        Parser parser=new Parser();
        while ((line=scanner.nextLine())!=null){
            if (line.toLowerCase().equals("exit"))
                System.exit(0);
            Variable var= parser.calc(line);
            System.out.println(var);
        }
    }

}
