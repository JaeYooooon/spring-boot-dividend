package zerobase.dividend.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance")
public class FinanceController {
    @GetMapping("/dividend/{compnayName}")
    public ResponseEntity<?> searchFinance(@PathVariable String compnayName){
        return null;
    }
}
