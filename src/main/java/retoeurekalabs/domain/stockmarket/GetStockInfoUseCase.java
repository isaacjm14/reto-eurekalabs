package retoeurekalabs.domain.stockmarket;

import retoeurekalabs.domain.stockmarket.model.StockMarketInfo;
import retoeurekalabs.domain.stockmarket.model.StockSymbolInfoRequest;

public interface GetStockInfoUseCase {
    StockMarketInfo handle(StockSymbolInfoRequest stockSymbolAndTimeSeries);
}
