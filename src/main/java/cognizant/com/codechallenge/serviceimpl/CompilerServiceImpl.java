package cognizant.com.codechallenge.serviceimpl;

import cognizant.com.codechallenge.dto.*;
import cognizant.com.codechallenge.mapper.Mapper;
import cognizant.com.codechallenge.model.Languages;
import cognizant.com.codechallenge.model.TaskResult;
import cognizant.com.codechallenge.model.Tasks;
import cognizant.com.codechallenge.model.VwTaskResult;
import cognizant.com.codechallenge.repo.LanguagesRepo;
import cognizant.com.codechallenge.repo.TaskResultRepo;
import cognizant.com.codechallenge.repo.TasksRepo;
import cognizant.com.codechallenge.repo.VwTaskResultRepo;
import cognizant.com.codechallenge.service.CompilerService;
import cognizant.com.codechallenge.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cognizant.com.codechallenge.utils.ApiMessages.*;
import static cognizant.com.codechallenge.utils.ApiResponseCode.*;


@Service
@Slf4j
public class CompilerServiceImpl implements CompilerService {
    @Value("${COMPILER.BASE_URL}")
    private String COMPILER_BASE_URL;
    @Value("${COMPILER.CLIENT_ID}")
    private String CLIENT_ID;
    @Value("${COMPILER.CLIENT_SECRET}")
    private String CLIENT_SECRET;

    @Autowired
    private TaskResultRepo taskResultRepo;
    @Autowired
    private TasksRepo tasksRepo;
    @Autowired
    private LanguagesRepo languagesRepo;
    @Autowired
    private VwTaskResultRepo vwTaskResultRepo;

    private CompilerResponse postToSever(Object load, String endpoint) {
        String BASE_SERVICE = COMPILER_BASE_URL + endpoint;
        MultivaluedMap<String, Object> map = new MultivaluedHashMap<>();
        map.add("Content-Type", "application/json");
        Client client = getClient();
        WebTarget base = getClient().target(BASE_SERVICE);
        Response response = base.request(MediaType.APPLICATION_JSON)
                .headers(map)
                .post(Entity.json(load));
        String result = response.readEntity(String.class);
        System.out.println(result);
        CompilerResponse out = Utils.getGson().fromJson(result, Utils.getCompilerConversionTyper());
        out.setOutput(out.getOutput().trim());
        client.close();
        return out;
    }

    private Client getClient() {
        Client client = ClientBuilder.newClient();
        return client;
    }

    private Optional<Tasks> findTask(Long id) {
        return tasksRepo.findById(id);
    }

    /**
     * @param payload Validate that task exists
     *                Reject multiple attempts for a given task
     *                Process the payload and send to online compiler
     * @return SubmissionOutcome when task processed successfully
     */
    @Override
    public ResponseEntity executeScript(CompileUiPayload payload) {
        try {
            Optional<Languages> languagesOptional = languagesRepo.findByName(payload.getLanguage());
            if (languagesOptional.isEmpty())
                return ResponseEntity.ok(new ApiResultSet<>(FAILED, NOT_FOUND, LANGUAGE_NOT_SUPPORTED));
            Optional<Tasks> tasksOptional = findTask(payload.getTaskId());
            if (tasksOptional.isEmpty())
                return ResponseEntity.ok(new ApiResultSet<>(FAILED, NOT_FOUND, TASK_NOT_FOUND));
            Optional<TaskResult> taskResultOptional = taskResultRepo.findByUsernameAndTaskId(payload.getUsername(), payload.getTaskId());
            if (taskResultOptional.isPresent())
                return ResponseEntity.ok(new ApiResultSet<>(FAILED, TASK_DONE, TEST_ALREADY_TAKEN));
            Tasks tasks = tasksOptional.get();
            TaskResult userResult = new TaskResult();
            userResult.setStartTime(new Date());
            userResult.setTasks(tasks);
            userResult.setExpectedOutput(tasks.getOutput_param());

            userResult = taskResultRepo.save(userResult);
            Languages languages = languagesOptional.get();
            CompilerRequest load = Mapper.convertObject(payload, CompilerRequest.class);
            load.setClientSecret(CLIENT_SECRET);
            load.setClientId(CLIENT_ID);
            load.setLanguage(languages.getPassedName());
            load.setVersionIndex(languages.getVersionIndex());
            load.setStdin(tasks.getInput_param());
            CompilerResponse compilerResponse = postToSever(load, "execute");
            return saveTaskResult(userResult, payload, compilerResponse);
        } catch (Exception e) {
            log.info("An error processing the request");
            e.printStackTrace();
            return ResponseEntity.ok(new ApiResultSet<>(FAILED, INTERNAL_SERVER_ERROR, ERROR));
        }
    }

    @Override
    public ResponseEntity listPaginatedTask(int page, int size) {
        try {
            Page<Tasks> userList = tasksRepo.findAll(PageRequest.of(page, size));
            return ResponseEntity.ok(new ApiResultSet<>(SUCCESS, OKAY,
                    Mapper.convertList(userList.getContent(), TaskResponse.class)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResultSet<>(FAILED, INTERNAL_SERVER_ERROR, ERROR));
        }
    }

    @Override
    public ResponseEntity listLanguage() {
        try {
            List<Languages> languagesList = languagesRepo.list();
            return ResponseEntity.ok(new ApiResultSet<>(SUCCESS, OKAY,
                    Mapper.convertList(languagesList, Language.class)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResultSet<>(FAILED, INTERNAL_SERVER_ERROR, ERROR));
        }
    }

    @Override
    public ResponseEntity listTopThreeSuccess() {
        try {
            List<VwTaskResult> resultSet = vwTaskResultRepo.listTopThree();
            if (resultSet.isEmpty())
                return ResponseEntity.ok(new ApiResultSet<>(FAILED, NOT_FOUND,
                        RECORD_NOT_FOUND));
            List<TaskReportResponse> reports = new ArrayList<>();
            resultSet.forEach(rs -> {
                TaskReportResponse report = new TaskReportResponse();
                report.setSuccessCount(rs.getSuccessCount());
                report.setUsername(rs.getUsername());
                String tasks = taskResultRepo.listTaskNameByUsername(rs.getUsername(), SUCCESS).stream()
                        .filter(name -> name != null && !name.isEmpty()).collect(Collectors.joining(","));
                report.setTasks(tasks);
                reports.add(report);
            });
            return ResponseEntity.ok(new ApiResultSet<>(SUCCESS, OKAY,
                    reports));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResultSet<>(FAILED, INTERNAL_SERVER_ERROR, COMPLIATION_FAILED));
        }
    }

    /**
     * @param result
     * @param uiPayload
     * @param compilerResponse
     * @return Error response if submitted script could not  be compiled
     * Process the result and save the response into db
     * @return SubmissionOutcome if the result successfully processed
     * @throws JsonProcessingException
     */
    private ResponseEntity saveTaskResult(TaskResult result, CompileUiPayload uiPayload, CompilerResponse compilerResponse) throws JsonProcessingException {
        if ((compilerResponse !=null && compilerResponse.getOutput().toLowerCase().contains("error"))||compilerResponse.getError() != null || compilerResponse.getStatusCode() != 200) {
            taskResultRepo.delete(result);
            return ResponseEntity.ok(new ApiResultSet<>(FAILED, BAD_REQUEST, compilerResponse.getError() == null ? compilerResponse.getOutput() : compilerResponse.getError()));
        }
        result.setOutput(compilerResponse.getOutput());
        result.setCpuTime(compilerResponse.getCpuTime());
        result.setEndTime(new Date());
        result.setMemoryUsed(compilerResponse.getMemory());
        result.setUsername(uiPayload.getUsername());
        result.setSubmittedPayload(Utils.getMapper().writeValueAsString(uiPayload));
        result.setStatusCode(compilerResponse.getStatusCode());
        result.setRemark(result.getExpectedOutput().equals(result.getOutput()) ? PASSED : FAILED);
        taskResultRepo.save(result);
        return ResponseEntity.ok(new ApiResultSet<>(SUCCESS, OKAY,
                new SubmissionOutcome(result.getExpectedOutput(), result.getOutput(), result.getRemark())));

    }
}
