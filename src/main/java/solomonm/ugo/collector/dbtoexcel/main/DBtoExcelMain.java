package solomonm.ugo.collector.dbtoexcel.main;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class DBtoExcelMain implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("-------------------------------->");
    }
}
