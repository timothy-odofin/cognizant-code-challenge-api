package cognizant.com.codechallenge;
import cognizant.com.codechallenge.dto.CompileUiPayload;
import cognizant.com.codechallenge.model.Languages;
import cognizant.com.codechallenge.model.TaskResult;
import cognizant.com.codechallenge.model.Tasks;
import cognizant.com.codechallenge.model.VwTaskResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TestData {


    public static Optional<Languages> getLanguages(){
        Languages lang = new Languages();
        lang.setDateCreated(new Date());
        lang.setName("Odofin Timothy");
        lang.setPassedName("passed");
        lang.setVersionIndex(1);
        lang.setId(new Long(85));
        return Optional.of(lang);
    }

    public static List<VwTaskResult> getVwList(){
        VwTaskResult vw = new VwTaskResult();
        vw.setUsername("testing");
        vw.setSuccessCount(Long.getLong("2"));
        vw.setUsername("Andy");
        return List.of(vw);
    }

    public static List<Languages> getLanguageslist(){
        Languages lang = new Languages();
        lang.setDateCreated(new Date());
        lang.setName("Odofin Timothy");
        lang.setPassedName("passed");
        lang.setVersionIndex(1);
        lang.setId(new Long(85));
        return List.of(lang);
    }
    public static Page<Tasks> getPages(){
        Tasks tasks = new Tasks();
        tasks.setDateCreated(new Date());
        tasks.setDescription("testing");
        tasks.setName("Odofin");
        return Page.empty();
    }
    public static TaskResult getTaskResult(){
        TaskResult task = new TaskResult();
        task.setCpuTime(new Float(2.5));
        task.setEndTime(new Date());
        task.setExpectedOutput("output");
        return task;
    }
    public static String getContentType() {
        return "application/json";
    }
    public static CompileUiPayload getCompileUiPayload(){
        CompileUiPayload payload = new CompileUiPayload();
        payload.setLanguage("English");
        payload.setScript("java");
        payload.setTaskId(new Long(45));
        payload.setUsername("odofin");
        return payload;
    }
}

