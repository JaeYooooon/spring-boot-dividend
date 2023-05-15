package zerobase.dividend.web;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
