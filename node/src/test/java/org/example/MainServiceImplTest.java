package org.example;

import static org.junit.Assert.assertTrue;

import org.example.dao.RawDataDao;
import org.example.entity.RawData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.Set;


/**
 * Unit test for simple App.
 */
@SpringBootTest
public class MainServiceImplTest
{
    @Autowired
    private RawDataDao rawDataDao;
    @Test
    public void testSaveRawData(){
        Update update = new Update();
        Message message = new Message();
        message.setText("TEST");
        update.setMessage(message);

        RawData rawData = RawData.builder()
                .event(update)
                .build();

        Set<RawData> testData = new HashSet<>();

        testData.add(rawData);
        rawDataDao.save(rawData);

        Assert.isTrue(testData.contains(rawData), "Entity not found us the set");
    }
}
