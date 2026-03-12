CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(120) NOT NULL,
    username VARCHAR(80) NOT NULL UNIQUE,
    avatar_url TEXT,
    bio VARCHAR(600),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE communities (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(80) NOT NULL,
    slug VARCHAR(80) NOT NULL UNIQUE,
    description VARCHAR(280),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE community_memberships (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    community_id UUID NOT NULL REFERENCES communities(id),
    user_id UUID NOT NULL REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    UNIQUE (community_id, user_id)
);

CREATE TABLE posts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    community_id UUID NOT NULL REFERENCES communities(id),
    user_id UUID NOT NULL REFERENCES users(id),
    title VARCHAR(200) NOT NULL,
    body VARCHAR(8000) NOT NULL,
    score INT NOT NULL DEFAULT 0,
    comment_count INT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_at TIMESTAMPTZ
);

CREATE TABLE comments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    post_id UUID NOT NULL REFERENCES posts(id),
    user_id UUID NOT NULL REFERENCES users(id),
    parent_id UUID REFERENCES comments(id),
    depth INT NOT NULL DEFAULT 0,
    body VARCHAR(2000) NOT NULL,
    score INT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_at TIMESTAMPTZ,
    CONSTRAINT comments_depth_check CHECK (depth >= 0 AND depth <= 6)
);

CREATE TABLE votes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id),
    votable_type VARCHAR(16) NOT NULL,
    votable_id UUID NOT NULL,
    value INT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT votes_value_check CHECK (value IN (-1, 1)),
    UNIQUE (user_id, votable_type, votable_id)
);

CREATE TABLE activities (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id),
    type VARCHAR(16) NOT NULL,
    payload TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_posts_community_created ON posts (community_id, created_at DESC);
CREATE INDEX idx_posts_score_created ON posts (score DESC, created_at DESC);
CREATE INDEX idx_comments_post_parent_depth ON comments (post_id, parent_id, depth, created_at);
CREATE INDEX idx_votes_votable ON votes (votable_type, votable_id);
CREATE INDEX idx_activities_user_created ON activities (user_id, created_at DESC);
