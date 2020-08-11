import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class CoronaRequest {
    String baseurl = "https://disease.sh/v2/countries/";
    Object inline;

    public void get(String country) throws IOException, MalformedURLException {
        URL url = new URL(baseurl + country.toLowerCase());
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        System.out.println("[corona] melakukan request terhadap disease.sh");
        conn.connect();
        if (conn.getResponseCode() == 404) {
            throw new RuntimeException("Country " + country + " Tidak ada!");
        } else {
            Scanner sc = new Scanner(url.openStream());
            while(sc.hasNext()) {
                inline += sc.nextLine();
            }
            sc.close();
        }
    }
}
