package com.stiw3054;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args){
        try {
            String url="https://github.com/STIW3054-A201/Main-Data/issues/1";
            String url2 = "https://github.com/STIW3054-A201/Main-Data/wiki/List_of_Student";
            String url3 = "https://github.com/STIW3054-A201/Main-Data/issues/4";
            Test test = new Test();
            String HtmlContent=test.getContentByJsoup(url);
            String HtmlContent2  =test.getContentByJsoup(url2);
            String divContent=test.getDivContentByJsoup(HtmlContent);
            String divContent2 = test.getTableByJsoup(HtmlContent2);
            test.TableList(divContent2);
            test.getLinksByJsoup(divContent);
            test.getNamesByJsoup(divContent);
            test.getMatricNumberByJsoup(divContent);            
            test.getDifferent(test.TableList(divContent2),test.getMatricNumberByJsoup(divContent));
            System.out.println("\n\n");
            System.out.println(" Students that submit the GitHub account.");
            System.out.println("| No. | Matric | Name                                  | GitHub Link                            |");
            System.out.println("|-----|--------|---------------------------------------|----------------------------------------|");
            for(int i = 0;i<test.getMatricNumberByJsoup(divContent).size();i++){
                int last=test.getNamesByJsoup(divContent).get(i).indexOf("<br>");
                System.out.printf("|%-5s|%-8s|%-39s|%-40s|\n",i+1,test.getMatricNumberByJsoup(divContent).get(i),test.getNamesByJsoup(divContent).get(i).substring(4, last),test.getLinksByJsoup(divContent).get(i));
            }
            System.out.println("|-----|--------|---------------------------------------|----------------------------------------|");
            System.out.println("\n");
            test.getNameIssue4();
            test.SaveExcel(divContent);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    }