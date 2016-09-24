package org.salespointframework.order;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Optional;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.salespointframework.catalog.Product;
import org.salespointframework.core.Currencies;
import org.salespointframework.quantity.Quantity;

/**
 * Unit tests for {@link Cart}.
 * 
 * @author Paul Henke
 * @author Oliver Gierke
 */
public class CartUnitTests {

	static final Quantity QUANTITY = Quantity.of(10);
	static final Product PRODUCT = new Product("name", Money.of(1, Currencies.EURO));

	Cart cart;

	@Before
	public void setUp() {
		cart = new Cart();
	}

	/**
	 * @see #44
	 */
	@Test
	public void addsCartItemCorrectly() {

		CartItem reference = cart.addOrUpdateItem(PRODUCT, QUANTITY);

		assertThat(cart, hasItem(reference));
		assertThat(cart, is(iterableWithSize(1)));
	}

	/**
	 * @see #44
	 */
	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullQuantityOnAdding() {
		cart.addOrUpdateItem(PRODUCT, null);
	}

	/**
	 * @see #44
	 */
	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullProductOnAdding() {
		cart.addOrUpdateItem(null, QUANTITY);
	}

	/**
	 * @see #44
	 */
	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullOnRemovingAnItem() {
		cart.removeItem(null);
	}

	/**
	 * @see #44
	 */
	@Test
	public void removesItemsCorrectly() {

		CartItem reference = cart.addOrUpdateItem(PRODUCT, QUANTITY);

		assertThat(cart.removeItem(reference.getId()).isPresent(), is(true));
		assertThat(cart, is(iterableWithSize(0)));
	}

	/**
	 * @see #44
	 */
	@Test
	public void providesAccessToCartItem() {

		CartItem reference = cart.addOrUpdateItem(PRODUCT, QUANTITY);
		Optional<CartItem> item = cart.getItem(reference.getId());

		assertThat(item.isPresent(), is(true));
		assertThat(item.get(), is(reference));
	}

	/**
	 * @see #44
	 */
	@Test
	public void returnsEmptyOptionalForNonExistingIdentifier() {

		cart.addOrUpdateItem(PRODUCT, QUANTITY);

		assertThat(cart.getItem("foobar"), is(Optional.empty()));
	}

	/**
	 * @see #44
	 */
	@Test(expected = IllegalArgumentException.class)
	public void rejectsNullIdentifier() {
		cart.getItem(null);
	}

	/**
	 * @see #44
	 */
	@Test
	public void clearsCartCorrectly() {

		cart.addOrUpdateItem(PRODUCT, QUANTITY);
		cart.clear();

		assertThat(cart, is(iterableWithSize(0)));
		assertThat(cart.isEmpty(), is(true));
	}

	/**
	 * @see #44
	 */
	@Test
	public void isEmpty() {

		assertThat(cart.isEmpty(), is(true));

		cart.addOrUpdateItem(PRODUCT, QUANTITY);
		assertThat(cart.isEmpty(), is(false));
	}

	/**
	 * @see #44
	 */
	@Test(expected = IllegalArgumentException.class)
	public void toOrderFail() {
		cart.addItemsTo(null);
	}

	/**
	 * @see #44
	 */
	@Test
	public void updatesCartItemIfOneForProductAlreadyExists() {

		CartItem item = cart.addOrUpdateItem(PRODUCT, QUANTITY);

		assertThat(item.getProduct(), is(PRODUCT));
		assertThat(item.getQuantity(), is(QUANTITY));
		assertThat(cart, is(iterableWithSize(1)));

		CartItem updated = cart.addOrUpdateItem(PRODUCT, QUANTITY);

		assertThat(updated, is(not(item)));
		assertThat(cart, is(iterableWithSize(1)));
		assertThat(updated.getProduct(), is(PRODUCT));
		assertThat(updated.getQuantity(), is(QUANTITY.add(QUANTITY)));
	}
}
