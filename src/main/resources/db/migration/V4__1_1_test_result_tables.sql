ALTER TABLE options ADD COLUMN is_correct BOOLEAN DEFAULT FALSE;

CREATE TABLE test_results (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              person_id UUID NOT NULL REFERENCES persons(id),
                              test_id UUID NOT NULL REFERENCES tests(id),
                              score INTEGER NOT NULL,
                              max_possible_score INTEGER NOT NULL,
                              completed_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_test_results_person_id ON test_results(person_id);
CREATE INDEX idx_test_results_test_id ON test_results(test_id);