package zerobase.dividend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import zerobase.dividend.exception.impl.NoCompanyException;
import zerobase.dividend.model.*;
import zerobase.dividend.persist.*;
import zerobase.dividend.persist.entity.*;

import java.util.*;
import java.util.stream.Collectors;

import static zerobase.dividend.model.constants.CacheKey.KEY_FINANCE;

@Slf4j
@Service
@AllArgsConstructor
public class FinanceService {

    private final DividendRepository dividendRepository;
    private final CompanyRepository companyRepository;
    @Cacheable(key = "#companyName", value = KEY_FINANCE)
    public ScrapedResult getDividendByCompanyName(String companyName){

        log.info("search company -> " + companyName);

        CompanyEntity company = this.companyRepository.findByName(companyName)
                                        .orElseThrow(() -> new NoCompanyException());

        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());

//        List<Dividend> dividends = new ArrayList<>();
//        for(var entity : dividendEntities){
//            dividends.add(Dividend.builder()
//                    .date(entity.getDate())
//                    .dividend(entity.getDividend())
//                    .build());
//        }
        List<Dividend> dividends = dividendEntities.stream().map(e ->
                                 new Dividend(e.getDate(), e.getDividend())).collect(Collectors.toList());

        return new ScrapedResult(new Company(company.getTicker(), company.getName()), dividends);
    }
}
