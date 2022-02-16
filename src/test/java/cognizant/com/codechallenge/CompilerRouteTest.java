package cognizant.com.codechallenge;

import cognizant.com.codechallenge.repo.LanguagesRepo;
import cognizant.com.codechallenge.repo.TaskResultRepo;
import cognizant.com.codechallenge.repo.TasksRepo;
import cognizant.com.codechallenge.repo.VwTaskResultRepo;
import cognizant.com.codechallenge.route.AppRoute;
import cognizant.com.codechallenge.service.CompilerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AppRoute.class)
public class CompilerRouteTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private CompilerService compilerService;
    @MockBean
    private TaskResultRepo taskResultRepo;
    @MockBean
    private TasksRepo tasksRepo;
    @MockBean
    private LanguagesRepo languagesRepo;
    @MockBean
    VwTaskResultRepo vwTaskResultRepo;


    @Test
    public void executeScriptTest() throws JsonProcessingException, Exception {
        when(languagesRepo.findByName(any())).thenReturn(TestData.getLanguages());
        when(taskResultRepo.save(any())).thenReturn(TestData.getTaskResult());
        MvcResult result = mockMvc.perform(post("/script/execute").contentType(TestData.getContentType())
                .content(mapper.writeValueAsString(TestData.getCompileUiPayload()))
        ).andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    public void listPaginatedTaskTest() throws Exception {
        //when(tasksRepo.findAll(any())).thenReturn(getPages());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/task/list" + "?page=0&size=20")
                        .contentType(TestData.getContentType())
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print()).andReturn();
    }

    @Test
    public void listLanguagesTest() throws Exception {
        when(languagesRepo.list()).thenReturn(TestData.getLanguageslist());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/script/languages" + "?page=0&size=20")
                        .contentType(TestData.getContentType())
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print()).andReturn();
    }

    @Test
    public void listTopThreeSuccessTest() throws Exception {
        when(vwTaskResultRepo.listTopThree()).thenReturn(TestData.getVwList());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/task/report")
                        .contentType(TestData.getContentType())
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print()).andReturn();
    }
}