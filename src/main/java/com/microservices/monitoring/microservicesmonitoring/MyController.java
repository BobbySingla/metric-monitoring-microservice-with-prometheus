package com.microservices.monitoring.microservicesmonitoring;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

@RestController
@Service
public class MyController extends HttpServlet {

    private final Summary requestDuration200;
    private final Summary requestDuration300;
    private final Summary requestDuration400;
    private final Summary requestDuration500;
    private final Histogram histogramFor200;
    private final Histogram histogramFor300;
    private final Histogram histogramFor400;
    private final Histogram histogramFor500;


    public MyController(CollectorRegistry collectorRegistry) {

        requestDuration200 = Summary.build()
                .name("request_duration_summary_with_status_code_200")
                .help("Time for HTTP request.")
                .quantile(0.9, 0.1)
                .register(collectorRegistry);
        requestDuration300 = Summary.build()
                .name("request_duration_summary_with_status_code_300")
                .help("Time for HTTP request.")
                .quantile(0.9, 0.1)
                .register(collectorRegistry);
        requestDuration400 = Summary.build()
                .name("request_duration_summary_with_status_code_400")
                .help("Time for HTTP request.")
                .quantile(0.9, 0.1)
                .register(collectorRegistry);
        requestDuration500 = Summary.build()
                .name("request_duration_summary_with_status_code_500")
                .help("Time for HTTP request.")
                .quantile(0.9, 0.1)
                .register(collectorRegistry);
        histogramFor200 = Histogram.build()
                .name("request_duration_with_status_code_200")
                .help("Time for HTTP request.")
                .register(collectorRegistry);
        histogramFor300 = Histogram.build()
                .name("request_duration_with_status_code_300")
                .help("Time for HTTP request.")
                .register(collectorRegistry);
        histogramFor400 = Histogram.build()
                .name("request_duration_with_status_code_400")
                .help("Time for HTTP request.")
                .register(collectorRegistry);
        histogramFor500 = Histogram.build()
                .name("request_duration_with_status_code_500")
                .help("Time for HTTP request.")
                .register(collectorRegistry);
    }

    @GetMapping(value = "/message")
    @ResponseBody
    public String hi(@RequestParam(value = "param1") String param1, @RequestParam(value = "param2") String param2, HttpServletResponse response) {
        Summary.Timer timerFor200 = requestDuration200.startTimer();
        Summary.Timer timerFor300 = requestDuration300.startTimer();
        Summary.Timer timerFor400 = requestDuration400.startTimer();
        Summary.Timer timerFor500 = requestDuration500.startTimer();
        Histogram.Timer histogramTimerFor200 = histogramFor200.startTimer();
        Histogram.Timer histogramTimerFor300 = histogramFor300.startTimer();
        Histogram.Timer histogramTimerFor400 = histogramFor400.startTimer();
        Histogram.Timer histogramTimerFor500 = histogramFor500.startTimer();

        if (param1.equals("ok") && param2.equals("ok")) {
            response.setStatus(HttpServletResponse.SC_OK);
            timerFor200.observeDuration();
            histogramTimerFor200.observeDuration();
        } else if (param1.equals("multiple_choices")) {
            if (param2.equals("ok") && param2.length() == 2 && param2.charAt(0) == 'o') {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response.setStatus(HttpServletResponse.SC_MULTIPLE_CHOICES);
                timerFor300.observeDuration();
                histogramTimerFor300.observeDuration();
            } else {
                response.setStatus(HttpServletResponse.SC_MULTIPLE_CHOICES);
                timerFor300.observeDuration();
                histogramTimerFor300.observeDuration();
            }
        } else if (param1.equals("bad_request") && param2.equals("bad_request")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            timerFor400.observeDuration();
            histogramTimerFor400.observeDuration();
        } else {
            param1 = "Error 500: ";
            param2 = "Empty Request";
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            timerFor500.observeDuration();
            histogramTimerFor500.observeDuration();
        }
        String getResponse = param1 + " " + param2;

        return getResponse;

    }
}
