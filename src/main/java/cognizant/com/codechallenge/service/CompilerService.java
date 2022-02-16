package cognizant.com.codechallenge.service;

import cognizant.com.codechallenge.dto.CompileUiPayload;
import org.springframework.http.ResponseEntity;

public interface CompilerService {
    ResponseEntity executeScript(CompileUiPayload payload);
    ResponseEntity listPaginatedTask(int page, int size);
    ResponseEntity listLanguage();
    ResponseEntity listTopThreeSuccess();

}
