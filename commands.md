prometheus commands
docker run -p 9090:9090 -v C:\Users\bobby.singla\Desktop\microservices-tradigrade-project\springboot-microservices-monitoring-using-prometheus-and-grafana\prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus

grafana commands
docker run -d -p 3000:3000 grafana/grafana

Step 1: command to convert Java Application to Executable(Fat) Jar File
mvn clean install

Step 2: command to convert Java Application Executable(Fat) Jar File to Docker image using Dockerfile
docker build -t spring-boot-docker .

Step 3: command to up docker container from Docker image 
docker run -p 8080:8080 spring-boot-docker


docker load --input microservices.tar



Step 4:

SAMPLE commands for loading metrics data from SpringBoot Application to prometheus

Counter----- 

rate(request_count_Hi[5m])   --- to print graph for no. of times request hits


Guage-------
avg_over_time(queue_size[5m])        ---   to print queue-size


Histogram
rate(request_duration_sum[5m]) / rate(request_duration_count[5m])     ---to calculate average 
                                                                         request Duration
                                                                         
Summary
rate(request_duration_summary_sum[5m]) / rate(request_duration_summary_count[5m])                                                                         
                                             ---to calculate average request Duration     