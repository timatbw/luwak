package uk.co.flax.luwak;

import org.apache.lucene.search.Query;

/**
 * Copyright (c) 2014 Lemur Consulting Ltd.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Interface defining how to create lucene Query objects from their String representations
 */
public interface MonitorQueryParser {

    /**
     * Create a new lucene Query
     * @param queryString the query's string representation
     * @return a lucene Query
     * @throws MonitorQueryParserException
     */
    Query parse(String queryString) throws MonitorQueryParserException;

}
