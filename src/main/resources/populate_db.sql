INSERT INTO resume (uuid, full_name)
VALUES ('a751ceb9-c3bf-44e9-a073-a0c130eee359', 'Anton A'),
       ('ba18773e-5b32-41c1-bfa7-9eb225495998', 'Alex A'),
       ('c04c0f94-6126-460a-85a2-840adc38866d', 'Alex A');

INSERT INTO contact (resume_uuid, type, value)
VALUES ('a751ceb9-c3bf-44e9-a073-a0c130eee359', 'PHONE', '11111'),
       ('a751ceb9-c3bf-44e9-a073-a0c130eee359', 'EMAIL', 'email1@mail.ru'),
       ('ba18773e-5b32-41c1-bfa7-9eb225495998', 'PHONE', '22222'),
       ('ba18773e-5b32-41c1-bfa7-9eb225495998', 'EMAIL', 'email2@gmail.com'),
       ('ba18773e-5b32-41c1-bfa7-9eb225495998', 'SKYPE', 'skype2');

INSERT INTO section (resume_uuid, type, content)
VALUES ('a751ceb9-c3bf-44e9-a073-a0c130eee359', 'PERSONAL', 'Personal'),
       ('a751ceb9-c3bf-44e9-a073-a0c130eee359', 'OBJECTIVE', 'Objective'),
       ('ba18773e-5b32-41c1-bfa7-9eb225495998', 'PERSONAL', 'Personal'),
       ('ba18773e-5b32-41c1-bfa7-9eb225495998', 'OBJECTIVE', 'Objective');

