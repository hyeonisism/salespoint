/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.salespointframework.quantity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * An entity that embeds {@link Quantity} and repeats its attribute names, {@code amount} and {@code metric}.
 * 
 * @author Martin Morgenstern
 * @author Oliver Drotbohm
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class RepeatedNameEntity {

	private @Id @GeneratedValue long id;
	private @Getter @Setter int amount;
	private @Getter @Setter String metric;
	private @Getter @Setter Quantity quantity;
}
