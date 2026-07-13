CREATE TABLE image_submission (
                                  id UUID PRIMARY KEY,
                                  file_name VARCHAR(255) NOT NULL,
                                  email VARCHAR(100) NOT NULL,
                                  submitted_at TIMESTAMP DEFAULT now()
);