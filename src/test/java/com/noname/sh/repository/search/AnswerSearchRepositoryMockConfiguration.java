package com.noname.sh.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of AnswerSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AnswerSearchRepositoryMockConfiguration {

    @MockBean
    private AnswerSearchRepository mockAnswerSearchRepository;

}
