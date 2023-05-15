package zerobase.dividend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.dividend.model.Company;
import zerobase.dividend.model.Dividend;
import zerobase.dividend.model.ScrapedResult;
import zerobase.dividend.persist.CompanyRepository;
import zerobase.dividend.persist.DividendRepository;
import zerobase.dividend.persist.entity.CompanyEntity;
import zerobase.dividend.persist.entity.DividendEntity;

import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FinanceService {
    private final DividendRepository dividendRepository;
    private final CompanyRepository companyRepository;
    public ScrapedResult getDividendByCompanyName(String companyName){
        CompanyEntity company = this.companyRepository.findByName(companyName)
                                        .orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다"));

        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());

//        List<Dividend> dividends = new ArrayList<>();
//        for(var entity : dividendEntities){
//            dividends.add(Dividend.builder()
//                    .date(entity.getDate())
//                    .dividend(entity.getDividend())
//                    .build());
//        }
        List<Dividend> dividends = dividendEntities.stream().map(e -> Dividend.builder()
                                                            .date(e.getDate())
                                                            .dividend(e.getDividend())
                                                            .build()).collect(Collectors.toList());
        return new ScrapedResult(Company.builder()
                                        .ticker(company.getTicker())
                                        .name(company.getName())
                                        .build(), dividends);
    }
}
