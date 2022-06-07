package net.javaguides.springboot;

import net.javaguides.springboot.controller.EmployeeController;
import net.javaguides.springboot.controller.MyUnit;
import net.javaguides.springboot.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class Testingcontrollers {

    @MockBean
    private EmployeeService employeeService;


    @Autowired
    private MockMvc mockMvc;



    @Test
    public void testConcatenate() {
        MyUnit myUnit = new MyUnit();

        String result = myUnit.concatenate("one", "two");

        assertEquals("onetwo", result);

    }

    @Test
    public void doTest() throws Exception {
        mockMvc.perform(get("/takeMoney"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(formLogin().user("admin").password("admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }






}
