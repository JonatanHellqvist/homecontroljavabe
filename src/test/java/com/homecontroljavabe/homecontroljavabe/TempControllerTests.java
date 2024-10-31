package com.homecontroljavabe.homecontroljavabe;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.homecontroljavabe.homecontroljavabe.Temp.Temp;
import com.homecontroljavabe.homecontroljavabe.Temp.TempService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TempControllerTests {

    @Autowired
    private TempService tempService; 

    @Test
    public void testGetLatestDht11SensorData() {
        
        String id1 = UUID.randomUUID().toString();
        String id2 = UUID.randomUUID().toString();

        Temp temp1 = new Temp(id1, 20.0, 60.0, 300.0, LocalDateTime.now().minusSeconds(10)); 
        tempService.addDht11SensorData(temp1);

        Temp temp2 = new Temp(id2, 25.0, 55.0, 350.0, LocalDateTime.now()); 
        tempService.addDht11SensorData(temp2);

        Temp result = tempService.getLatestDht11SensorData();

        assertNotNull(result);
        assertEquals(25.0, result.getCelsius());
        assertEquals(55.0, result.getHumidity());
        assertEquals(350.0, result.getPhotoTransistorValue());
        assertNotNull(result.getTimeStamp());
    }
}