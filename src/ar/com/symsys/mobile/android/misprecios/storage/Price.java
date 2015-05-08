package ar.com.symsys.mobile.android.misprecios.storage;

import java.util.Date;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class Price {
    private String      ProductId;
    private int         MarketId;
    private Date	    TimeStamp;
    private float       BulkPrice;
    private int         BulkQuantity;

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public int getMarketId() {
        return MarketId;
    }

    public void setMarketId(int marketId) {
        MarketId = marketId;
    }

    public Date getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Date date) {
        TimeStamp = date;
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

