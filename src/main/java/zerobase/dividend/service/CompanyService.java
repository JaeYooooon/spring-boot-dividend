package zerobase.dividend.service;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import zerobase.dividend.exception.impl.NoCompanyException;
import zerobase.dividend.model.*;
import zerobase.dividend.persist.*;
import zerobase.dividend.persist.entity.*;
import zerobase.dividend.scraper.Scraper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {

    private final Trie trie;
    private final Scraper yahooFinanceScraper;

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public Company save(String ticker){
        boolean exists = this.companyRepository.existsByTicker(ticker);
        if(exists){
            throw  new RuntimeException("already exists ticker -> " + ticker);
        }
        return this.storeCompanyAndDividend(ticker);}

    public Page<CompanyEntity> getAllCompany(Pageable pageable){
        return this.companyRepository.findAll(pageable);
    }

    private Company storeCompanyAndDividend(String ticker){
        Company company = this.yahooFinanceScraper.scrapCompanyByTicker(ticker);
        if(ObjectUtils.isEmpty(company)){
            throw new RuntimeException("failed to scrap ticker -> " + ticker);
        }

        ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(company);
        CompanyEntity companyEntity = this.companyRepository.save(new CompanyEntity(company));

        List<DividendEntity> dividendEntityList = scrapedResult.getDividends().stream()
                                                                .map(e -> new DividendEntity(companyEntity.getId(), e))
                                                                .collect(Collectors.toList());

        this.dividendRepository.saveAll(dividendEntityList);

        return company;
    }

    public List<String> getCompanyNamesByKeyword(String keyword){
        Pageable limit = PageRequest.of(0, 10);
        Page<CompanyEntity> companyEntities = this.companyRepository
                                                  .findByNameStartingWithIgnoreCase(keyword, limit);

        return companyEntities.stream()
                .map(e -> e.getName()).collect(Collectors.toList());
    }

    public void addAutocompleteKeyword(String keyword){
        this.trie.put(keyword, null);
    }

    public List<String> autocomplete(String keyword){
        return (List<String>) this.trie.prefixMap(keyword).keySet().stream().collect(Collectors.toList());
    }

    public void deleteAutocompleteKeyword(String keyword){
        this.trie.remove(keyword);
    }

    public String deleteCompany(String ticker){

        var company = this.companyRepository.findByTicker(ticker)
                .orElseThrow(() -> new NoCompanyException());

        this.dividendRepository.deleteAllByCompanyId(company.getId());
        this.companyRepository.delete(company);

        this.deleteAutocompleteKeyword(company.getName());

        return company.getName();
    }
}
