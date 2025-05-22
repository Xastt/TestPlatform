CREATE TABLE tests (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       title VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP
);

CREATE TABLE test_questions (
                                test_id UUID NOT NULL,
                                question_id UUID NOT NULL,
                                PRIMARY KEY (test_id, question_id)
);

ALTER TABLE test_questions
    ADD CONSTRAINT fk_testquestions_test
        FOREIGN KEY (test_id) REFERENCES tests(id) ON DELETE CASCADE;

ALTER TABLE test_questions
    ADD CONSTRAINT fk_testquestions_question
        FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE;

CREATE INDEX idx_testquestions_test_id ON test_questions(test_id);
CREATE INDEX idx_testquestions_question_id ON test_questions(question_id);