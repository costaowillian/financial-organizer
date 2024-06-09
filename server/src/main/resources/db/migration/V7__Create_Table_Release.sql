CREATE TABLE `lancamento` (
  `ano` int DEFAULT NULL,
  `data_cadastro` date NOT NULL,
  `mes` int DEFAULT NULL,
  `valor` decimal(38,2) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_usuario` bigint DEFAULT NULL,
  `descricao` varchar(100) NOT NULL,
  `status` enum('CANCELADO','EFETIVADO','PENDENTE') NOT NULL,
  `tipo` enum('DESPESAS','RECEITAS') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt2a5b4jc8powehfmsyeufarkr` (`id_usuario`),
  CONSTRAINT `FKt2a5b4jc8powehfmsyeufarkr` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
)