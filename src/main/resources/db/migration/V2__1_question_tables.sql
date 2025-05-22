CREATE TABLE question (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          content VARCHAR(1000) NOT NULL
);

CREATE TABLE options (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         question_id UUID NOT NULL,
                         option_content VARCHAR(1000) NOT NULL,
                         CONSTRAINT fk_options_question FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
);

CREATE INDEX idx_options_question_id ON options(question_id);