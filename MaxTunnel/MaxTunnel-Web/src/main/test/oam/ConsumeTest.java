package oam;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bandweaver.tunnel.controller.quartz.TaskEntrance;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= "classpath:context/applicationContext.xml")
public class ConsumeTest {

	@Autowired
	private TaskEntrance taskEntrance;
	
	
	@Test
	public void test1() {
    	taskEntrance.saveToConsumeData();
	}
	
}
