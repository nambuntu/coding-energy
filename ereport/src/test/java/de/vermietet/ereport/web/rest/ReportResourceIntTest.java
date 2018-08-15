package de.vermietet.ereport.web.rest;

import de.vermietet.ereport.EreportApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the ReportResource REST controller.
 *
 * @see ReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EreportApp.class)
public class ReportResourceIntTest {

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ReportResource reportResource = new ReportResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(reportResource)
            .build();
    }

    /**
    * Test getReport
    */
    @Test
    public void testGetReport() throws Exception {
        restMockMvc.perform(get("/api/report/get-report"))
            .andExpect(status().isOk());
    }

}
