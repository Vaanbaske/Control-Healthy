-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 15/06/2025 às 00:27
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `saude_bem_estar`
--

-- --------------------------------------------------------

--
-- Estrutura stand-in para view `media_pressao_pacientes`
-- (Veja abaixo para a visão atual)
--
CREATE TABLE `media_pressao_pacientes` (
`id_paciente` int(11)
,`nome` varchar(100)
,`media_sistolica` decimal(14,4)
,`media_diastolica` decimal(14,4)
,`total_registros` bigint(21)
);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pacientes`
--

CREATE TABLE `pacientes` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `idade` int(11) NOT NULL,
  `endereco` varchar(100) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `id_medico` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `pacientes`
--

INSERT INTO `pacientes` (`id`, `nome`, `idade`, `endereco`, `telefone`, `id_medico`) VALUES
(1, 'Teste Testado', 45, '', '', 10),
(2, 'Teste 1', 45, '', '', 4),
(3, 'Teste', 45, '', '', 4);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pressao`
--

CREATE TABLE `pressao` (
  `id` int(11) NOT NULL,
  `id_paciente` int(11) DEFAULT NULL,
  `sistolica` int(11) DEFAULT NULL,
  `diastolica` int(11) DEFAULT NULL,
  `usa_medicacao` tinyint(1) DEFAULT NULL,
  `data_medicao` datetime DEFAULT NULL,
  `data` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `pressao`
--

INSERT INTO `pressao` (`id`, `id_paciente`, `sistolica`, `diastolica`, `usa_medicacao`, `data_medicao`, `data`) VALUES
(11, 1, 120, 80, NULL, NULL, '2025-04-01'),
(12, 1, 122, 82, NULL, NULL, '2025-04-02'),
(13, 1, 118, 79, NULL, NULL, '2025-04-03'),
(14, 2, 120, 80, NULL, NULL, '2025-04-01'),
(15, 2, 122, 82, NULL, NULL, '2025-04-02'),
(16, 3, 130, 85, NULL, NULL, '2025-04-01'),
(17, 3, 128, 84, NULL, NULL, '2025-04-02'),
(19, 7, 150, 120, NULL, NULL, '2025-04-01'),
(20, 7, 150, 1230, NULL, NULL, '2025-04-02'),
(21, 7, 121212, 212121212, NULL, NULL, '2025-04-03'),
(22, 7, 120, 150, NULL, NULL, '2025-04-04'),
(23, 7, 300, 300, NULL, NULL, '2025-04-05'),
(24, 7, 152, 130, NULL, NULL, '2025-04-06'),
(25, 7, 150, 130, NULL, NULL, '2025-04-07'),
(26, 7, 100, 100, NULL, NULL, '2025-04-08'),
(27, 7, 231, 615, NULL, NULL, '2025-04-23'),
(28, 7, 555, 888, NULL, NULL, '2025-05-17'),
(29, 14, 124, 85, NULL, NULL, '2025-06-03');

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `tipo` varchar(20) DEFAULT NULL,
  `endereco` varchar(150) DEFAULT NULL,
  `telefone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `usuarios`
--

INSERT INTO `usuarios` (`id`, `nome`, `email`, `senha`, `tipo`, `endereco`, `telefone`) VALUES
(1, 'Victor', 'victor@teste.com', '123456', 'paciente', NULL, NULL),
(2, 'Victor Sousa', 'guaxinim@hotmail.com', '123456', 'medico', NULL, NULL),
(3, 'João da Silva', 'joao@email.com', '123456', 'paciente', NULL, NULL),
(4, 'Teste medico', 'teste@teste.com', '1234', 'medico', NULL, NULL),
(5, 'Victor', 'eu@eu.com', '123', 'paciente', NULL, NULL),
(6, 'paciente', 'paciente@teste.com', '123', 'paciente', NULL, NULL),
(7, 'Victor Sousa', 'paciente@paciente.com', '123', 'paciente', 'Av. Guaxinim', '(11)98188-1894'),
(8, 'Administrador', 'adm@adm.com', 'adm', 'admin', NULL, NULL),
(9, 'José Bonifacio', 'jose@jose.com', '1234', 'paciente', 'Rua do fulano', '(11)99999-9999'),
(10, 'Clarividencio Ferreira', 'fer@fer.com', '1234', 'medico', NULL, NULL),
(11, 'Rapaz', 'rap@rap.com', '1234', 'paciente', NULL, NULL),
(12, 'alex', 'alex@gmail.com', '123456', 'paciente', NULL, NULL),
(13, 'teste5', 'teste@testado.com', '123', 'paciente', NULL, NULL),
(14, 'testinho2', 'teste@gmail.com', '123456', 'paciente', NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura para view `media_pressao_pacientes`
--
DROP TABLE IF EXISTS `media_pressao_pacientes`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `media_pressao_pacientes`  AS SELECT `pa`.`id` AS `id_paciente`, `pa`.`nome` AS `nome`, avg(`pr`.`sistolica`) AS `media_sistolica`, avg(`pr`.`diastolica`) AS `media_diastolica`, count(`pr`.`id`) AS `total_registros` FROM (`pressao` `pr` join `pacientes` `pa` on(`pa`.`id` = `pr`.`id_paciente`)) GROUP BY `pa`.`id`, `pa`.`nome` ;

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `pacientes`
--
ALTER TABLE `pacientes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_paciente_medico` (`id_medico`);

--
-- Índices de tabela `pressao`
--
ALTER TABLE `pressao`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pressao_ibfk_1` (`id_paciente`);

--
-- Índices de tabela `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `pacientes`
--
ALTER TABLE `pacientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `pressao`
--
ALTER TABLE `pressao`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de tabela `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `pacientes`
--
ALTER TABLE `pacientes`
  ADD CONSTRAINT `fk_paciente_medico` FOREIGN KEY (`id_medico`) REFERENCES `usuarios` (`id`);

--
-- Restrições para tabelas `pressao`
--
ALTER TABLE `pressao`
  ADD CONSTRAINT `pressao_ibfk_1` FOREIGN KEY (`id_paciente`) REFERENCES `usuarios` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
