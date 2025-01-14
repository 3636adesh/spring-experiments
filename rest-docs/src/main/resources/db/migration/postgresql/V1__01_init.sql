
create sequence if not exists users_seq start with 1 increment by 50;

CREATE TYPE  gender_enum AS ENUM ('MALE', 'FEMALE');

create table if not exists users (
                                     id bigint DEFAULT nextval('users_seq') not null,
                                     first_name text not null,
                                     last_name text,
                                     age serial not null,
                                     gender gender_enum,
                                     phone_number text,
                                     email text not null UNIQUE,
                                     primary key (id)
);


INSERT INTO users (id, first_name, last_name, age, gender, phone_number, email)
VALUES
    (DEFAULT, 'John', 'Doe', 30, 'MALE', '1234567890', 'john.doe@example.com'),
    (DEFAULT, 'Jane', 'Smith', 25, 'FEMALE', '0987654321', 'jane.smith@example.com'),
    (DEFAULT, 'Alice', 'Johnson', 28, 'FEMALE', '5678901234', 'alice.johnson@example.com')
    ON CONFLICT (email)
DO UPDATE SET
    first_name = EXCLUDED.first_name,
           last_name = EXCLUDED.last_name,
           age = EXCLUDED.age,
           gender = EXCLUDED.gender,
           phone_number = EXCLUDED.phone_number;
