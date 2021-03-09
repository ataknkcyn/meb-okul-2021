package com;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class OKUL {

    private String ID;
    private String ILADI;
    private String ILCEADI;
    private String OKULADI;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getILADI() {
        return ILADI;
    }

    public void setILADI(String ILADI) {
        this.ILADI = ILADI;
    }

    public String getILCEADI() {
        return ILCEADI;
    }

    public void setILCEADI(String ILCEADI) {
        this.ILCEADI = ILCEADI;
    }

    public String getOKULADI() {
        return OKULADI;
    }

    public void setOKULADI(String OKULADI) {
        this.OKULADI = OKULADI;
    }

}

public class getOkul {

    /*
    il adi
    ilce adi
    okul adi 
    sout komutlari ekran ciktisi icindir silinebilir.
     */
    
    public void getIL() {
        ArrayList<String> iller = new ArrayList<String>();
        int id = 1;
        List<String> list = new ArrayList<String>();
        for (int i = 1; i <= 81; i++) {
            int eskiId = id;

            try {
                Document doc = Jsoup.connect("http://www.meb.gov.tr/baglantilar/okullar/index.php?ILKODU=" + i).get();
                String title = doc.title();
                Elements elems = doc.getElementById("icerik-listesi").getElementsByTag("tbody").get(0).getElementsByTag("tr");
                for (Element elem : elems) {
                    try {
                        OKUL x = new OKUL();
                        if (elem.getElementsByTag("td").get(0).text().split("-").length > 0) {
                            String okul_data[] = elem.getElementsByTag("td").get(0).text().split("-");
                            String il = okul_data[0].trim();
                            String ilce = okul_data[1].trim();
                            String okul = okul_data[2].trim();
                            x.setID(id + "");
                            x.setILADI(il);
                            x.setILCEADI(ilce);
                            x.setOKULADI(okul);
                        } else {
                            // hatali durumlar olusabiliyor onlari belirtmek icin
                            System.out.println("HATALI :: " + elem.getElementsByTag("td"));
                        }
                        id++;
                        Gson builder = new GsonBuilder().create();
                        list.add(builder.toJson(x).toString());
                    } catch (Exception e) {
                        // nadirde olsa bos data yakaladigi durumlarda buraya dusuyor
                        System.out.println(elem.getElementsByTag("td"));
                        System.out.println("---------------------");
                    }

                }
            } catch (Exception e) {
                // il kodunda hata ile karsilasilmasi durumunda onlem almak icin
                e.printStackTrace();
            }
            System.out.println("IL kodu = " + i);
            System.out.println(id - eskiId);
        }
        try {
            FileWriter fileWriter = new FileWriter("okul.json");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(list);
            printWriter.close();
        } catch (Exception e) {
        }

    }

    public static void main(String[] args) {
        getOkul x = new getOkul();
        x.getIL();
    }
}
