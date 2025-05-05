package org.com.dianping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.com.dianping") // 确保扫描到 Controller
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}