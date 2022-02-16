package cognizant.com.codechallenge.route;

import cognizant.com.codechallenge.dto.CompileUiPayload;
import cognizant.com.codechallenge.service.CompilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cognizant.com.codechallenge.utils.AppConstants.*;

@RestController
@RequestMapping("/api")
public class AppRoute {
    @Autowired
    private CompilerService compilerService;

    @PostMapping("/script/execute")
    public ResponseEntity executeScript(@Valid @RequestBody CompileUiPayload payload) {
        return compilerService.executeScript(payload);
    }

    @GetMapping("/script/languages")
    public ResponseEntity listLanguage() {
        return compilerService.listLanguage();
    }

    @GetMapping("/task/list")
    public ResponseEntity listPaginatedTask(@RequestParam(value = PAGE_PARAM, defaultValue = PAGE) int page,
                                            @RequestParam(value = SIZE_PARAM, defaultValue = SIZE) int size) {
        return compilerService.listPaginatedTask(page, size);
    }

    @GetMapping("/task/report")
    public ResponseEntity listTopThreeSuccess() {
        return compilerService.listTopThreeSuccess();
    }


}
