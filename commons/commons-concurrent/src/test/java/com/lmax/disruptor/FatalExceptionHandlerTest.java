/*
 * Copyright 2011 LMAX Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lmax.disruptor;

import org.junit.Assert;
import org.junit.Test;

import com.lmax.disruptor.support.TestEvent;

public final class FatalExceptionHandlerTest {
	@Test
	public void shouldHandleFatalException() {
		
		final Exception causeException = new Exception();
		final TestEvent event = new TestEvent();

		ExceptionHandler<Object> exceptionHandler = new FatalExceptionHandler();

		try {
			exceptionHandler.handleEventException(causeException, 0L, event);
		} catch (RuntimeException ex) {
			Assert.assertEquals(causeException, ex.getCause());
		}
	}
}
