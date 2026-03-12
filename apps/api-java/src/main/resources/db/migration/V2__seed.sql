INSERT INTO users (id, name, username, avatar_url, bio)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Guilherme Soares', 'guilherme', 'https://i.pravatar.cc/128?img=8', 'Building OpenThread in Java and Go.'),
    ('22222222-2222-2222-2222-222222222222', 'Ana Dev', 'ana', 'https://i.pravatar.cc/128?img=47', 'Backend engineer and forum contributor.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO communities (id, name, slug, description)
VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Backend', 'backend', 'Architecture, APIs, and databases.'),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Cloud', 'cloud', 'Infrastructure, observability, and scaling.')
ON CONFLICT (id) DO NOTHING;

INSERT INTO community_memberships (id, community_id, user_id)
VALUES
    ('f0000000-0000-0000-0000-000000000001', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111'),
    ('f0000000-0000-0000-0000-000000000002', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '22222222-2222-2222-2222-222222222222')
ON CONFLICT (community_id, user_id) DO NOTHING;

INSERT INTO posts (id, community_id, user_id, title, body, score, comment_count)
VALUES
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111',
     'Building OpenThread with Java + Go',
     'OpenThread is running with Spring Boot and a Go worker in this first MVP iteration.',
     1,
     1)
ON CONFLICT (id) DO NOTHING;

INSERT INTO comments (id, post_id, user_id, parent_id, depth, body, score)
VALUES
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '22222222-2222-2222-2222-222222222222', NULL, 0,
     'Great direction. Keep the API contract stable while migrating.',
     0)
ON CONFLICT (id) DO NOTHING;

INSERT INTO votes (id, user_id, votable_type, votable_id, value)
VALUES
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '22222222-2222-2222-2222-222222222222', 'POST', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 1)
ON CONFLICT (user_id, votable_type, votable_id) DO NOTHING;

INSERT INTO activities (id, user_id, type, payload)
VALUES
    ('99999999-9999-9999-9999-999999999991', '11111111-1111-1111-1111-111111111111', 'POST', '{"post_id":"cccccccc-cccc-cccc-cccc-cccccccccccc","community_id":"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"}'),
    ('99999999-9999-9999-9999-999999999992', '22222222-2222-2222-2222-222222222222', 'COMMENT', '{"comment_id":"dddddddd-dddd-dddd-dddd-dddddddddddd","post_id":"cccccccc-cccc-cccc-cccc-cccccccccccc"}'),
    ('99999999-9999-9999-9999-999999999993', '22222222-2222-2222-2222-222222222222', 'VOTE', '{"votable_type":"post","votable_id":"cccccccc-cccc-cccc-cccc-cccccccccccc","value":1}')
ON CONFLICT (id) DO NOTHING;
