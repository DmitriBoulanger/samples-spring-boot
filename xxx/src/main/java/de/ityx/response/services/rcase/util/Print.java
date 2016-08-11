/*
 * Copyright 2012-2015 the original author or authors.
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

package de.ityx.response.services.rcase.util;

import de.ityx.response.services.rcase.Case;
import de.ityx.response.services.rcase.domain.CaseRepository;

public class Print {

    public static StringBuilder printAllLoadedCases(CaseRepository caseRepository) {
        final StringBuilder sb = new StringBuilder("Cases found with findAll():");
        sb.append(LINE);
        for (Case rcase : caseRepository.findAll()) {
            sb.append("\n\t - " + rcase.toString());
        }
        sb.append(LINE);
        return sb;
    }

    private static final String LINE = "\n\t - -----------------------------------------------------------------------------------";

}
