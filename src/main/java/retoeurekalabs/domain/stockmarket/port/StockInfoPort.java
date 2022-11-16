package retoeurekalabs.domain.stockmarket.port;

import retoeurekalabs.domain.stockmarket.model.StockSymbolInfoRequest;
import retoeurekalabs.domain.stockmarket.model.StockValuesByTimeSeries;

public interface StockInfoPort {

    StockValuesByTimeSeries getStockValues(StockSymbolInfoRequest stockSymbolInfoRequest);
}
