CREATE TABLE IF NOT EXISTS "user" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "mid" text,
  "access_token" TEXT,
  "refresh_token" TEXT,
  "sess_data" TEXT,
  "bili_jct" TEXT,
  "dede_user_id" text,
  "ck_md5" TEXT,
  "sid" text,
  "status" integer,
  "cookie" TEXT
);
