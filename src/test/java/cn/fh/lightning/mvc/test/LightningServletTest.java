package cn.fh.lightning.mvc.test;

import cn.fh.lightning.mvc.servlet.LightningServlet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * Created by whf on 15-2-26.
 */
@RunWith(MockitoJUnitRunner.class)
public class LightningServletTest {
    @Mock
    private ServletContextEvent mockEvent;
    @Mock
    private ServletContext mockContext;

    @Before
    public void initMockObj() {
        Mockito.when(mockEvent.getServletContext())
                .thenReturn(mockContext);
        Mockito.when(mockContext.getInitParameter(LightningServlet.CONFIGURE_FILE_ATTRIBUTE))
                .thenReturn(null);
        Mockito.when(mockContext.getResourceAsStream(LightningServlet.DEFAULT_CONFIGURE_FILE_LOCATION))
                .thenReturn(getClass().getResourceAsStream(LightningServlet.DEFAULT_CONFIGURE_FILE_LOCATION));
    }

    @Test
    public void testContextInitialization() {
        new LightningServlet().contextInitialized(mockEvent);
    }
}
