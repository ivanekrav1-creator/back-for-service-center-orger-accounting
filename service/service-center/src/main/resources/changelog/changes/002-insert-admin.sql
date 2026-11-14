INSERT INTO users(
    id,
    username,
    password,
    role
) VALUES (
    gen_random_uuid(),
    'admin',
    '$2a$10$utiO0FuHRM430YFV2LWY4OWmzDqNTpLigAQG5alQkzeGC59HMV5uu',
    'ADMIN'
)