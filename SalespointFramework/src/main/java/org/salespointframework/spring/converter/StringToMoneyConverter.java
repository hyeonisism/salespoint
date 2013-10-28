package org.salespointframework.spring.converter;

import java.math.BigDecimal;

import org.salespointframework.core.money.Money;
import org.springframework.core.convert.converter.Converter;

/**
 * 
 * @author Paul Henke
 *
 */
@Deprecated
public class StringToMoneyConverter implements Converter<String, Money> {

	@Override
	public Money convert(String text) {
		BigDecimal bd = new BigDecimal(text);
		Money money = new Money(bd);
		return money;
	}
}