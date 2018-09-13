package co.ke.proaktiv.io.pojos.helpers;

import java.util.stream.Stream;

public enum ServiceProvider {
	RW_OTHERS, RW_AIRTEL, 
	KE_OTHERS, KE_AIRTEL, 
    TZ_OTHERS, TZ_AIRTEL, 
    UG_OTHERS, UG_AIRTEL,
    OTHER;
    
    public static Stream<ServiceProvider> stream() {
        return Stream.of(ServiceProvider.values()); 
    }
}
