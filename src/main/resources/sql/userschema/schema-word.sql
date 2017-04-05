DROP TABLE IF EXISTS word;
CREATE TABLE IF NOT EXISTS `word` (
    `id`            INT             NOT NULL AUTO_INCREMENT             COMMENT 'id слова',
    `word`          VARCHAR(255)    NOT NULL                            COMMENT 'переводимое слово',
    `transcription` VARCHAR(255)    DEFAULT ""                          COMMENT 'транскрипция',
    `translation`   VARCHAR(1000)   NOT NULL                            COMMENT 'перевод',
    `created`       TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT 'когда добавлено',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `word_UNIQUE` (`word` ASC))
ENGINE = InnoDB
COMMENT = 'Таблица слов';

