/*
 * Copyright 2016 DiffPlug
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.gradle.spotless;

import java.util.Objects;

final class SpotlessTaskConstants {
	static final String EXTENSION = "spotless";
	static final String CHECK = "Check";
	static final String APPLY = "Apply";

	static final String TASK_GROUP = "Verification";
	static final String CHECK_DESCRIPTION = "Checks that sourcecode satisfies formatting steps.";
	static final String APPLY_DESCRIPTION = "Applies code formatting steps to sourcecode in-place.";

	static String capitalize(String input) {
		Objects.requireNonNull(input, "input");
		return Character.toUpperCase(input.charAt(0)) + input.substring(1);
	}

	// prevent instantiation
	private SpotlessTaskConstants() {}
}