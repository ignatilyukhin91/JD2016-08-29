package by.it.rudzko.Matlab.ReportBuilder;


import by.it.rudzko.Matlab.Interfaces.IMessages;
import by.it.rudzko.Matlab.Parser;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;

public class ShortReportBuilder extends ReportBuilder{

    private DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
    private PrintWriter n=null;

    @Override
    void printStart(File f) {
        String timeStart = df.format(new Date(System.currentTimeMillis()));
        report.setStart(timeStart);
        try{
            n=new PrintWriter(new FileWriter(f, true));
            n.println(IMessages.SHORT_START +timeStart);
            n.flush();
        }catch (FileNotFoundException e){
            System.out.println(IMessages.FILE_NOT_FOUND);
        } catch (IOException e) {
            System.out.println();
            e.printStackTrace();
        }

        finally {
            if (n != null)
                n.close();
        }
    }

    @Override
    void printOperations(File f) {
        report.setOperations(Parser.getOperations().size()+ IMessages.COMPLETED);
        try{
            n=new PrintWriter(new FileWriter(f, true));
            n.println(Parser.getOperations().size()+ IMessages.COMPLETED);
            n.flush();
        }catch (FileNotFoundException e){
            System.out.println(IMessages.FILE_NOT_FOUND);
        } catch (IOException e) {
            System.out.println();
            e.printStackTrace();
        }

        finally {
            if (n != null)
                n.close();
        }
    }

    @Override
    void printEnd(File f) {
        String timeEnd = df.format(new Date(System.currentTimeMillis()));
        report.setEnd(timeEnd);
        try{
            n=new PrintWriter(new FileWriter(f, true));
            n.println(IMessages.END +timeEnd+"\n\n");
            n.flush();
        }catch (FileNotFoundException e){
            System.out.println(IMessages.FILE_NOT_FOUND);
        } catch (IOException e) {
            System.out.println();
            e.printStackTrace();
        }

        finally {
            if (n != null)
                n.close();
        }
    }
}
