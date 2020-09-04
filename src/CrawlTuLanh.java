import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlTuLanh {
    public static void main(String[] args) throws IOException {
        URL url;
        Scanner scanner;
        ProductManager productManager = new ProductManager();
        try {
            url = new URL ("https://mediamart.vn/tu-lanh/");
            scanner = new Scanner(new InputStreamReader(url.openStream()));
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            scanner.close();
            content = content.replaceAll("\\n+", "");
            content = content.replaceAll("\\s+"," ");
            Pattern p = Pattern.compile("data-original=\"(.*?)\" src=\"/Lib/Plugins/LazyLoad(.*?)pl18-item-name\"> <a href=\"/(.*?)/(.*?)/(.*?).htm\" title=\"(.*?)\"> (.*?) </a> </p> <p><span class=\"product-id hidden\" style=\"display:none!important;\">(.*?)</span></p> <div class=\"pl18-item-pbuy\"> (.*?)<span>đ");
            Matcher m = p.matcher(content);
            while (m.find()) {
                String image = m.group(1);
                String type = m.group(3);
                String brand = m.group(4);
                String name = m.group(7);
                String id = "TL" + m.group(8);
                int price = Integer.parseInt(m.group(9).replaceAll("\\.",""));
//                productManager.insertProduct(id,type,name,brand,price,image);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
