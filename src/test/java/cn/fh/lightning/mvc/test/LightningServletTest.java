package cn.fh.lightning.mvc.test;

/**
 * Created by whf on 15-2-26.
 */
//@RunWith(MockitoJUnitRunner.class)
public class LightningServletTest {
    /*@Mock
    private ServletContextEvent mockEvent;
    @Mock
    private ServletContext mockContext;
    @Mock
    private ServletConfig mockConfig;

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;

    @Before
    public void initMockObj() {
        Mockito.when(mockConfig.getServletContext())
                .thenReturn(mockContext);
        Mockito.when(mockEvent.getServletContext())
                .thenReturn(mockContext);
        Mockito.when(mockContext.getInitParameter(LightningServlet.CONFIGURE_FILE_ATTRIBUTE))
                .thenReturn(null);

        Mockito.when(mockContext.getResourceAsStream(LightningServlet.DEFAULT_CONFIGURE_FILE_LOCATION))
                .thenReturn(getClass().getResourceAsStream(LightningServlet.DEFAULT_CONFIGURE_FILE_LOCATION));
        Mockito.when(mockContext.getResourceAsStream(LightningServlet.DEFAULT_WEB_CONFIGURE_FILE_LOCATION))
                .thenReturn(getClass().getResourceAsStream(LightningServlet.DEFAULT_WEB_CONFIGURE_FILE_LOCATION));
    }

    @Test
    public void testContextInitialization() throws Exception {
        LightningServlet servlet = new LightningServlet();

        // test load url mapping
        servlet.init(mockConfig);
        // test container initialization
        servlet.contextInitialized(mockEvent);
    }

    *//**
     * Feed servlet with the URL "/application/home".
     * If something wrong happens, this method will throw
     * {@link java.lang.NullPointerException}.
     *//*
    @Test
    public void testProcessValidRequest() throws ServletException, IOException {
        Mockito.when(req.getMethod())
                .thenReturn("GET");
        Mockito.when(req.getRequestURI())
                .thenReturn("/application/home");
        Mockito.when(req.getServletContext())
                .thenReturn(mockContext);

        // mock IoC container
        InjectableBeanContainer container = Mockito.mock(InjectableBeanContainer.class);
        Mockito.when(container.getBeanWithDependencies("homeController"))
                .thenReturn(new HomeController());


        Mockito.when(mockContext.getAttribute(LightningServlet.BEAN_CONTAINER_ATTRIBUTE))
                .thenReturn(container);
        Mockito.when(mockContext.getRequestDispatcher("/WEB-INF/views/home.jsp"))
                .thenReturn(Mockito.mock(RequestDispatcher.class));


        LightningServlet servlet = new LightningServlet();

        // init servlet
        servlet.init(mockConfig);
        servlet.contextInitialized(mockEvent);

        // process request
        servlet.service(req, resp);
    }*/
}
