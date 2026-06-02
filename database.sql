-- ===== MOMENTS TABLE (feed posts) =====
CREATE TABLE IF NOT EXISTS moments (
  id BIGSERIAL PRIMARY KEY,
  user_id UUID REFERENCES profiles(auth_user_id),
  content TEXT NOT NULL,
  image_urls TEXT[] DEFAULT '{}',
  likes_count INT DEFAULT 0,
  comments_count INT DEFAULT 0,
  created_at TIMESTAMPTZ DEFAULT NOW()
);

-- ===== FOLLOWERS TABLE (stats ke liye) =====
CREATE TABLE IF NOT EXISTS followers (
  id BIGSERIAL PRIMARY KEY,
  following_id UUID REFERENCES profiles(auth_user_id),
  follower_id UUID REFERENCES profiles(auth_user_id),
  created_at TIMESTAMPTZ DEFAULT NOW(),
  UNIQUE(following_id, follower_id)
);

-- ===== INDEXES =====
CREATE INDEX IF NOT EXISTS idx_moments_user ON moments(user_id);
CREATE INDEX IF NOT EXISTS idx_moments_created ON moments(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_followers_following ON followers(following_id);
CREATE INDEX IF NOT EXISTS idx_followers_follower ON followers(follower_id);

-- ===== RLS =====
ALTER TABLE moments ENABLE ROW LEVEL SECURITY;
ALTER TABLE followers ENABLE ROW LEVEL SECURITY;

DROP POLICY IF EXISTS "Public moments" ON moments;
DROP POLICY IF EXISTS "Users can insert own moments" ON moments;
DROP POLICY IF EXISTS "Public followers" ON followers;
DROP POLICY IF EXISTS "Users can follow" ON followers;

CREATE POLICY "Public moments" ON moments FOR SELECT USING (true);
CREATE POLICY "Users can insert own moments" ON moments FOR INSERT WITH CHECK (auth.uid() = user_id);
CREATE POLICY "Public followers" ON followers FOR SELECT USING (true);
CREATE POLICY "Users can follow" ON followers FOR INSERT WITH CHECK (auth.uid() = follower_id);
