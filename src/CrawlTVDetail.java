import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlTVDetail {
    public static void main(String[] args) {
        ProductManager productManager = new ProductManager();
        try {
            URL url = new URL ("https://mediamart.vn/tivi/asanzo/smart-tivi-asanzo-43-inch-43as560-full-hd.htm#pd-special");
            Scanner scanner = new Scanner(new InputStreamReader(url.openStream()));
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            scanner.close();
            content = content.replaceAll("\\n+", "");
            content = content.replaceAll("\\s+"," ");
//            System.out.println(content);
            Pattern p = Pattern.compile("pd-attrvalue-item pd-atv-col-full\">Tổng quan</div> (.*?) </div> <div class=\"pd-attrvalue-item pd-atv-col-full\">Kết nối</div> (.*?) </div> <div class=\"pd-attrvalue-item pd-atv-col-full\">Smart Tivi/ Internet Tivi</div> (.*?) </div> <div class=\"pd-attrvalue-item pd-atv-col-full\">Công nghệ hình ảnh, âm thanh</div> (.*?) </div> </div> <div class=\"cl");
            Matcher m = p.matcher(content);
            while (m.find()) {
                System.out.println(editDetail(m.group(1)));
                System.out.println(editDetail(m.group(2)));
                System.out.println(editDetail(m.group(3)));
                System.out.println(editDetail(m.group(4)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String editDetail(String detail) {
        String result = detail.replaceAll("<div(.*?)>","").replaceAll("<i>","");
        result = result.replaceAll(" </div> ","");
        result = result.replaceAll("</i>","");
        result = result.replaceAll("</div>","__");
        return result;
    }

    public static ArrayList<String> getTVDetail(String new_url) {
        ArrayList<String> details = new ArrayList<>();
        try {
            URL url = new URL (new_url);
            Scanner sc = new Scanner(new InputStreamReader(url.openStream()));
            sc.useDelimiter("\\Z");
            String content = sc.next();
            sc.close();
            content = content.replaceAll("\\n+", "");
            content = content.replaceAll("\\s+"," ");
            Pattern p = Pattern.compile("pd-attrvalue-item pd-atv-col-full\">Tổng quan</div> (.*?) </div> <div class=\"pd-attrvalue-item pd-atv-col-full\">Kết nối</div> (.*?) </div> <div class=\"pd-attrvalue-item pd-atv-col-full\">Smart Tivi/ Internet Tivi</div> (.*?) </div> <div class=\"pd-attrvalue-item pd-atv-col-full\">Công nghệ hình ảnh, âm thanh</div> (.*?) </div> </div> <div class=\"cl");
            Matcher m = p.matcher(content);
            while (m.find()) {
                details.add(editDetail(m.group(1)));
                details.add(editDetail(m.group(2)));
                details.add(editDetail(m.group(3)));
                details.add(editDetail(m.group(4)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return details;
    }
}
