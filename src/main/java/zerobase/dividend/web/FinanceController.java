package zerobase.dividend.web;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.dividend.service.FinanceService;

@RestController
@RequestMapping("/finance")
@AllArgsConstructor
public class FinanceController {
    private final FinanceService financeService;
    @GetMapping("/dividend/{compnayName}")
    public ResponseEntity<?> searchFinance(@PathVariable String compnayName){
        var result = this.financeService.getDividendByCompanyName(compnayName);
        return ResponseEntity.ok(result);
    }
}
