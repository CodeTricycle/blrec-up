CREATE TABLE IF NOT EXISTS "recorde" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "create_time" DATE,
  "update_time" DATE,
  "room_id" INTEGER,
  "live_start_time" DATE,
  "live_stop_time" DATE,
  "title" TEXT,
  "cover" TEXT
);
