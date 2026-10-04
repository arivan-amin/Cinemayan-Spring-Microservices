CREATE
DATABASE IF NOT EXISTS ledger_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE
USER IF NOT EXISTS 'ledger_user'@'%' IDENTIFIED BY 'ledger@@pass'; GRANT SELECT, INSERT,
UPDATE,
DELETE
ON ledger_db.* TO 'ledger_user'@'%';

-- Liquibase migration user
CREATE
USER IF NOT EXISTS 'ledger_migration'@'%' IDENTIFIED BY 'ledger@@cmy';
GRANT ALL PRIVILEGES ON ledger_db.* TO
'ledger_migration'@'%';

FLUSH
PRIVILEGES;
