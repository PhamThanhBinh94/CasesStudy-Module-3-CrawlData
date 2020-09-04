import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlTV {
    public static void main(String[] args) throws IOException {
        URL url;
        Scanner scanner;
        ProductManager productManager = new ProductManager();
        try {
            url = new URL ("https://mediamart.vn/tivi/");
            scanner = new Scanner(new InputStreamReader(url.openStream()));
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            scanner.close();
            content = content.replaceAll("\\n+", "");
            content = content.replaceAll("\\s+"," ");
//            System.out.println(content);
            Pattern p = Pattern.compile("class=\"pl18-item-image\"> <a href=\"(.*?)\" title=\"(.*?)\" target=\"_blank\"> <img class=\"lazy\" title=\"(.*?)data-original=\"(.*?)\" src=\"/Lib/Plugins/LazyLoad(.*?)pl18-item-name\"> <a href=\"/(.*?)/(.*?)/(.*?).htm\" title=\"(.*?)\"> (.*?) </a> </p> <p><span class=\"product-id hidden\" style=\"display:none!important;\">(.*?)</span></p> <div class=\"pl18-item-pbuy\"> (.*?)<span>đ");
            Matcher m = p.matcher(content);
            while (m.find()) {
//                for (int i=1; i < 13; i++) {
//                    if (i==2 || i==3 || i==5 || i==8 || i==9) continue;
//                    System.out.println(m.group(i));
//                }
//                System.out.println("______________");
                String link = "https://mediamart.vn" + m.group(1);
                String image = m.group(4);
                String type = m.group(6);
                String brand = m.group(7);
                String name = m.group(10);
                String id = "TV" + m.group(11);
                int price = Integer.parseInt(m.group(12).replaceAll("\\.",""));
                System.out.println(link + "\n" + image + "\n" + type + "\n" + brand + "\n" + name + "\n" + id + "\n" + price + "\n_______");
                productManager.insertProduct(id,type,name,brand,price,image,1,link);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
