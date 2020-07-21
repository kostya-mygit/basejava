INSERT INTO resume (uuid, full_name)
VALUES ('a751ceb9-c3bf-44e9-a073-a0c130eee359', 'Anton A'),
       ('ba18773e-5b32-41c1-bfa7-9eb225495998', 'Alex A'),
       ('c04c0f94-6126-460a-85a2-840adc38866d', 'Alex A');

INSERT INTO contact (resume_uuid, type, value)
VALUES ('a751ceb9-c3bf-44e9-a073-a0c130eee359', 'PHONE', '11111'),
       ('a751ceb9-c3bf-44e9-a073-a0c130eee359', 'EMAIL', 'email1@mail.ru');
