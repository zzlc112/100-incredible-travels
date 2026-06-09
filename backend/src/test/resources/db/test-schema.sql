CREATE TABLE IF NOT EXISTS travel (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    subtitle VARCHAR(500) NOT NULL,
    cover_image VARCHAR(1000) NOT NULL,
    detail_images TEXT,
    experience_types TEXT NOT NULL,
    visual_style VARCHAR(50) NOT NULL,
    rarity_level INTEGER NOT NULL CHECK(rarity_level >= 1 AND rarity_level <= 4),
    destination VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    highlights TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS admin_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL
);
