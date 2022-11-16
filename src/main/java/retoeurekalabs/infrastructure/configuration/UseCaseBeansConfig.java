package retoeurekalabs.infrastructure.configuration;

import retoeurekalabs.domain.stockmarket.GetStockInfoUseCase;
import retoeurekalabs.domain.stockmarket.port.StockInfoPort;
import retoeurekalabs.domain.stockmarket.usecase.GetStockInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeansConfig {

    @Bean
    public GetStockInfoUseCase countryCodesGetterUseCase(StockInfoPort stockInfoPort) {
        return new GetStockInfo(stockInfoPort);
    }

}
