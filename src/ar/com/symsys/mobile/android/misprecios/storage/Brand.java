package ar.com.symsys.mobile.android.misprecios.storage;
/**
 * Created by hsuyama on 10/03/2015.
 */
public class Brand {
	private Integer     BrandId;
    private String      Brand;

    public Integer getBrandId() {
		return BrandId;
	}
	public void setBrandId(Integer brandId) {
		BrandId = brandId;
	}
	public String getBrandName() {
		return Brand;
	}
	public void setBrandName(String name) {
		Brand = name;
	}
}

