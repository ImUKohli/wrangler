/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.wrangler.parser;

import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.RecipeParser;
import io.cdap.wrangler.registry.CompositeDirectiveRegistry;
import io.cdap.wrangler.registry.DirectiveInfo;
import io.cdap.wrangler.registry.SystemDirectiveRegistry;
import io.cdap.wrangler.registry.UserDirectiveRegistry;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Unit tests for {@link GrammarBasedParser}.
 */
public class GrammarBasedParserTests {

    @Test
    public void testAggregateStatsParsing() throws Exception {
        // Define the recipe with the aggregate-stats directive
        String[] recipe = new String[] {
                "aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec"
        };

        // Set up the directive registry
        CompositeDirectiveRegistry registry = new CompositeDirectiveRegistry(
                new SystemDirectiveRegistry(),
                new UserDirectiveRegistry()
        );
        registry.reload(); // Ensure the registry is loaded with directives

        // Create a GrammarBasedParser instance
        RecipeParser parser = new GrammarBasedParser("test-namespace", recipe, registry);

        // Parse the recipe - this should not throw an exception
        List<Directive> directives = parser.parse();

        // Verify that the parsing was successful (directives list should not be empty)
        Assert.assertFalse("The parsed directives list should not be empty", directives.isEmpty());
    }
}