import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Script {

    public static void main(String[] args) {
        List<String> urlList = new ArrayList<>();
        urlList.add("http://localhost:8080/message?param1=ok&param2=ok");
        urlList.add("http://localhost:8080/message?param1=multiple_choices&param2=null");
        urlList.add("http://localhost:8080/message?param1=multiple_choices&param2=ok");
        urlList.add("http://localhost:8080/message?param1=bad_request&param2=bad_request");
        urlList.add("http://localhost:8080/message?param1=null&param2=null");
        RestTemplate restTemplate = new RestTemplate();
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            try {
                String forObject = restTemplate.getForObject(urlList.get(random.nextInt(urlList.size())), String.class);
                System.out.println(forObject);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }


    }
}
