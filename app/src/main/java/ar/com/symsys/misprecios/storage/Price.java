package ar.com.symsys.misprecios.storage;

import android.text.format.Time;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class Price {
    private long        ProductId;
    private int         MarketId;
    private Time        TimeStamp;
    private float       BulkPrice;
    private int         BulkQuantity;

    public long getProductId() {
        return ProductId;
    }

    public void setProductId(long productId) {
        ProductId = productId;
    }

    public int getMarketId() {
        return MarketId;
    }

    public void setMarketId(int marketId) {
        MarketId = marketId;
    }

    public Time getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Time timeStamp) {
        TimeStamp = timeStamp;
    }

    public float getBulkPrice() {
        return BulkPrice;
    }

    public void setBulkPrice(float bulkPrice) {
        BulkPrice = bulkPrice;
    }

    public int getBulkQuantity() {
        return BulkQuantity;
    }

    public void setBulkQuantity(int bulkQuantity) {
        BulkQuantity = bulkQuantity;
    }
}

