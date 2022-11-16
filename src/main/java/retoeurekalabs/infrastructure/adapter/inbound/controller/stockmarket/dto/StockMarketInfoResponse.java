package retoeurekalabs.infrastructure.adapter.inbound.controller.stockmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockMarketInfoResponse {
    private float openPrice;
    private float higherPrice;
    private float lowerPrice;
    private float variationPercentageTwoLastClosingPrices;
}
