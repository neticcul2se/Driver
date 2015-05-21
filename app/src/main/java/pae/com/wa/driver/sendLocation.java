package pae.com.wa.driver;

/**
 * Created by Pae on 5/21/15.
 */
public class sendLocation extends HttpGet{

    public void send(int idvan,int idroad,int idown,Double lati, Double longti){
        String resultall = null;
        String url = getUrl(idvan,idroad ,idown,lati,longti);

        resultall = getHttpGet(url);

    }
    public static String getUrl(int idvan,int idroad,int idown,Double latin,Double longit)
    {// connect to map web service
        StringBuffer urlString = new StringBuffer();
        urlString.append("http://128.199.230.75/addmark.php?idvan=");
        urlString.append(idvan);
        urlString.append("&idroad=");
        urlString.append(idroad);
        urlString.append("&idown=");
        urlString.append(idown);
        urlString.append("&lati=");
        urlString.append(latin);
        urlString.append("&longti=");
        urlString.append(longit);

        return urlString.toString();
    }



}
