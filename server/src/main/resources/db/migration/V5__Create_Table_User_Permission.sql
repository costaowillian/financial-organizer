CREATE TABLE `user_permission` (
  `id_permission` bigint NOT NULL,
  `id_user` bigint NOT NULL,
  KEY `FKo47t1we6do84ku6rb9jcey2s9` (`id_permission`),
  KEY `FKg5n8alhl8cse8caa03lsjy063` (`id_user`),
  CONSTRAINT `FKg5n8alhl8cse8caa03lsjy063` FOREIGN KEY (`id_user`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FKo47t1we6do84ku6rb9jcey2s9` FOREIGN KEY (`id_permission`) REFERENCES `permission` (`id`)
)