package com.stiw3054;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class Test {

    public String getContentByJsoup(String url) {
        String content = "";
        try {
            Document doc = Jsoup.connect(url)
                    .data("jquery", "java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(50000)
                    .get();
            content = doc.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public String getDivContentByJsoup(String content) {
        String divContent = "";
        Document doc = Jsoup.parse(content);
        Elements divs = doc.getElementsByClass("js-timeline-item js-timeline-progressive-focus-container");
        divContent = divs.toString();
        return divContent;
    }

    public List<String> getLinksByJsoup(String divContent) {
        String abs = "https://github.com/STIW3054-A201/Main-Data/issues/1";
        Document doc = Jsoup.parse(divContent, abs);
        List<String> linkList = new ArrayList<String>();
        Elements linkStrs = doc.getElementsByClass("d-block comment-body markdown-body  js-comment-body");

        for (Element linkStr : linkStrs) {
            String url = linkStr.getElementsByTag("a").attr("abs:href");

            linkList.add(url);
        }
        return linkList;
    }

    public List<String> getNamesByJsoup(String divContent) {
        String abs = "https://github.com/STIW3054-A201/Main-Data/issues/1";
        String name = "";
        List<String> nameList = new ArrayList<String>();
        Document doc = Jsoup.parse(divContent, abs);
        Elements matricNums = doc.getElementsByClass("d-block comment-body markdown-body  js-comment-body");

        for (Element matricNum : matricNums) {
            Elements url = matricNum.getElementsByTag("p");
            String transfer = url.select("p").toString();
            Pattern pattern = Pattern.compile("Name: (.*?)<br>|Name :(.*?)<br>|:\\\\s(U.*)<br>|Name:(.*?)<br>|name :( .*?)<br> |Name (.*?)<br>");// 匹配的模式 //"<p>(.*?)<br>" //:(.*)<br> //"Name: (.*?)<br>|Name :(.*?)<br>|:\\s(U.*)<br>|Name:(.*?)<br>|name :( .*?)<br> |Name (.*?)<br>"
            Matcher m = pattern.matcher(transfer);
            while (m.find()) {
                name = m.group(0);
            }

            nameList.add(name);
        }
        return nameList;
    }

    public List<String> getMatricNumberByJsoup(String divContent) throws FileNotFoundException, IOException {
        String abs = "https://github.com/STIW3054-A201/Main-Data/issues/1";
        String matric = "";
        List<String> matricList = new ArrayList<String>();
        Document doc = Jsoup.parse(divContent, abs);
        Elements matricNums = doc.getElementsByClass("d-block comment-body markdown-body  js-comment-body");

        for (Element matricNum : matricNums) {
            Elements url = matricNum.getElementsByTag("p");
            String transfer = url.select("p").toString();
            Pattern pattern = Pattern.compile("(\\b2.*?)<br>");
            Matcher m = pattern.matcher(transfer);
            while (m.find()) {
                matric = m.group(1);
            }
            matricList.add(matric);
        }
        return matricList;
    }

    public String getTableByJsoup(String content) {
        String divContent = "";
        Document doc = Jsoup.parse(content);
        Elements divs = doc.getElementsByClass("markdown-body");
        divContent = divs.toString();
        return divContent;
    }

    public List<String> TableList(String divContent) throws IOException {
        String matric = "";
        List<String> matricList = new ArrayList<String>();

        String url = "https://github.com/STIW3054-A201/Main-Data/wiki/List_of_Student";
        Document doc = Jsoup.parse(divContent, url);

        Element element = doc.select("table").first();
        Elements els = element.select("tr");
        for (Element el : els) {
            Elements ele = el.select("td");
            String context = ele.select("td").toString();
            Pattern pattern = Pattern.compile("\\d{6}");
            Matcher m = pattern.matcher(context);
            while (m.find()) {
                matric = m.group(0);
            }
            matricList.add(matric);

        }
        return matricList;
    }

    public void getDifferent(List<String> list1, List<String> list2) {
        for (String str1 : list1) {
            if (!list2.contains(str1)) {

                System.out.println("Student "+str1+ " not submit the GitHub account.");
            }
        }
        for (String str2 : list2) {
            if (list1.contains(str2)) {

                System.out.println("Student "+str2+ " submit the GitHub account.");
            }
        }
    }

    public void getNameIssue4() {
        System.out.println(" Students not comment in issue 4.");
        System.out.println("| No. | Matric | Name                                  |");
        System.out.println("|-----|--------|---------------------------------------|");
        String url1 = "https://github.com/STIW3054-A201/Main-Data/issues/1";
        String url2 = "https://github.com/STIW3054-A201/Main-Data/issues/4";
        String name1 = "";
        String name2 = "";
        String name3 = "";
        String matric = "";
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        List<String> matricList = new ArrayList<String>();
        List<String> nameList = new ArrayList<String>();
        Document doc = Jsoup.parse(url1);
        Document docc = Jsoup.parse(url2);
        Elements doc1 = doc.getElementsByClass("timeline-comment-header-text f5 text-normal");
        Elements docc2 = docc.getElementsByClass("timeline-comment-header-text f5 text-normal");

        for (Element nameIssue1 : doc1) {
            Elements url = nameIssue1.getElementsByTag("a");
            String data1 = url.attr("href");
            list1.add(data1);
        }
        for (Element nameIssue2 : docc2) {
            Elements url = nameIssue2.getElementsByTag("a");
            String data2 = url.attr("href");
            list2.add(data2);
        }

        for (String str2 : list2) {
            if (!list1.contains(str2)) {
                for (int i = 0; i < list1.size(); i++) {
                    Elements tempo = doc.getElementsByClass("js-timeline-item js-timeline-progressive-focus-container");

                    Elements url = doc.getElementsByTag("a");
                    String data1 = url.attr("href");
                    if (data1 == list1.get(i)) {
                        for (Element matricNum : tempo) {
                            Elements urll = matricNum.getElementsByTag("p");
                            String transfer = urll.select("p").toString();
                            Pattern pattern = Pattern.compile("(\\b2.*?)<br>");
                            Matcher m = pattern.matcher(transfer);
                            while (m.find()) {
                                matric = m.group(1);
                            }
                        }
                        for (Element sname : tempo) {
                            Elements urlll = sname.getElementsByTag("p");
                            String transfer = urlll.select("p").toString();
                            Pattern pattern = Pattern.compile("Name: (.*?)<br>|Name :(.*?)<br>|:\\\\s(U.*)<br>|Name:(.*?)<br>|name :( .*?)<br> |Name (.*?)<br>");// 匹配的模式 //"<p>(.*?)<br>" //:(.*)<br> //"Name: (.*?)<br>|Name :(.*?)<br>|:\\s(U.*)<br>|Name:(.*?)<br>|name :( .*?)<br> |Name (.*?)<br>"
                            Matcher m = pattern.matcher(transfer);
                            while (m.find()) {
                                name3 = m.group(0);
                            }
                        }
                        System.out.printf("|%-5s|%-8s|%-39s|%-40s|\n", i + 1, matric, name3);
                    }
                }
            }
        }
        System.out.println("|-----|--------|---------------------------------------|");
    }

    public void SaveExcel(String divContent) throws IOException {
        List<String> matricList = new ArrayList<String>();
        List<String> nameList = new ArrayList<String>();
        List<String> linkList = new ArrayList<String>();
        String abs = "https://github.com/STIW3054-A201/Main-Data/issues/1";
        Document doc = Jsoup.parse(divContent, abs);
        Elements matricNums = doc.getElementsByClass("d-block comment-body markdown-body  js-comment-body");

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("行业统计");

        HSSFRow row = sheet.createRow((int) 0);

        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("ID");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("Matric");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("Name");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("Link");
        cell.setCellStyle(style);
        matricList.addAll(getMatricNumberByJsoup(divContent));
        nameList.addAll(getNamesByJsoup(divContent));
        linkList.addAll(getLinksByJsoup(divContent));
        for (int i = 0; i < matricNums.size(); i++) {
            row = sheet.createRow((int) i + 1);
            String id = Integer.toString(i + 1);
            row.createCell((short) 0).setCellValue(new HSSFRichTextString(id));
            row.createCell((short) 1).setCellValue(new HSSFRichTextString(matricList.get(i)));
            row.createCell((short) 2).setCellValue(new HSSFRichTextString(nameList.get(i)));
            row.createCell((short) 3).setCellValue(new HSSFRichTextString(linkList.get(i)));
        }
        FileOutputStream fout = new FileOutputStream("D:/A201.xls");
        wb.write(fout);
        fout.close();
        System.out.println("Report succesfully created.");
    }
}
