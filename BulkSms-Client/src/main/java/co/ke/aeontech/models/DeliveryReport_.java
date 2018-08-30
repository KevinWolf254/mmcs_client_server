package co.ke.aeontech.models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import co.ke.aeontech.pojos.helpers.Country;

@StaticMetamodel(DeliveryReport.class)
public abstract class DeliveryReport_ {
	public static volatile SingularAttribute<DeliveryReport, Long> id;
	public static volatile SingularAttribute<DeliveryReport, String> messageId;
	public static volatile SingularAttribute<DeliveryReport, Integer> received;
	public static volatile SingularAttribute<DeliveryReport, Integer> rejected;
	public static volatile SingularAttribute<DeliveryReport, String> phoneNosRejected;
	public static volatile SingularAttribute<DeliveryReport, Country> currency;
	public static volatile SingularAttribute<DeliveryReport, BigDecimal> cost;
	public static volatile SingularAttribute<DeliveryReport, Date> date;
	public static volatile SingularAttribute<DeliveryReport, Organisation> organisation;
}
