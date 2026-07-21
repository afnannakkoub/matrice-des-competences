-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 21 juil. 2026 à 10:47
-- Version du serveur : 9.1.0
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `matrice_competences`
--

-- --------------------------------------------------------

--
-- Structure de la table `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `categories`
--

INSERT INTO `categories` (`id`, `nom`, `description`) VALUES
(1, 'Cloud', 'Cloud Computing'),
(2, 'DevOps', 'Docker Kubernetes CI/CD'),
(3, 'Programming', 'Programming languages'),
(4, 'Programmation', 'Toutes les compétences de développement');

-- --------------------------------------------------------

--
-- Structure de la table `competences`
--

DROP TABLE IF EXISTS `competences`;
CREATE TABLE IF NOT EXISTS `competences` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom` varchar(150) NOT NULL,
  `description` text,
  `categorie_id` bigint NOT NULL,
  `archive` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_categorie` (`categorie_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `competences`
--

INSERT INTO `competences` (`id`, `nom`, `description`, `categorie_id`, `archive`) VALUES
(1, 'Java', 'Java Programming Language', 3, 0),
(2, 'Java', 'Développement Java', 1, 0);

-- --------------------------------------------------------

--
-- Structure de la table `evaluations`
--

DROP TABLE IF EXISTS `evaluations`;
CREATE TABLE IF NOT EXISTS `evaluations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `utilisateur_id` bigint NOT NULL,
  `competence_id` bigint NOT NULL,
  `niveau` int NOT NULL,
  `commentaire` text,
  `date_evaluation` date DEFAULT (curdate()),
  `evalue_par` bigint DEFAULT NULL,
  `auto_evaluation` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_user` (`utilisateur_id`),
  KEY `fk_competence` (`competence_id`),
  KEY `fk_evaluateur` (`evalue_par`)
) ;

-- --------------------------------------------------------

--
-- Structure de la table `propositions_competence`
--

DROP TABLE IF EXISTS `propositions_competence`;
CREATE TABLE IF NOT EXISTS `propositions_competence` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom` varchar(150) NOT NULL,
  `description` text,
  `utilisateur_id` bigint NOT NULL,
  `statut` enum('EN_ATTENTE','APPROUVEE','REFUSEE') DEFAULT 'EN_ATTENTE',
  `date_proposition` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_user_prop` (`utilisateur_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `rapports`
--

DROP TABLE IF EXISTS `rapports`;
CREATE TABLE IF NOT EXISTS `rapports` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `titre` varchar(200) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `date_generation` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `genere_par` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_manager_report` (`genere_par`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nom` (`nom`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `roles`
--

INSERT INTO `roles` (`id`, `nom`) VALUES
(2, 'ADMIN'),
(3, 'EMPLOYE'),
(4, 'MANAGER');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateurs`
--

DROP TABLE IF EXISTS `utilisateurs`;
CREATE TABLE IF NOT EXISTS `utilisateurs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `role_id` bigint NOT NULL,
  `manager_id` bigint DEFAULT NULL,
  `actif` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `poste` varchar(100) NOT NULL,
  `departement` varchar(70) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_role` (`role_id`),
  KEY `fk_manager` (`manager_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateurs`
--

INSERT INTO `utilisateurs` (`id`, `nom`, `prenom`, `email`, `mot_de_passe`, `role_id`, `manager_id`, `actif`, `created_at`, `poste`, `departement`) VALUES
(3, 'Ali', 'Ahmed', 'ali@test.com', '123456', 2, NULL, 1, NULL, 'Développeur', 'Informatique');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur_competence`
--

DROP TABLE IF EXISTS `utilisateur_competence`;
CREATE TABLE IF NOT EXISTS `utilisateur_competence` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `utilisateur_id` bigint NOT NULL,
  `competence_id` bigint NOT NULL,
  `niveau_propose` tinyint NOT NULL,
  `niveau_valide` tinyint DEFAULT NULL,
  `statut` enum('EN_ATTENTE','VALIDE') DEFAULT 'EN_ATTENTE',
  `date_creation` datetime DEFAULT CURRENT_TIMESTAMP,
  `date_validation` datetime DEFAULT NULL,
  `manager_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `utilisateur_id` (`utilisateur_id`,`competence_id`),
  KEY `competence_id` (`competence_id`),
  KEY `manager_id` (`manager_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateur_competence`
--

INSERT INTO `utilisateur_competence` (`id`, `utilisateur_id`, `competence_id`, `niveau_propose`, `niveau_valide`, `statut`, `date_creation`, `date_validation`, `manager_id`) VALUES
(1, 3, 1, 4, 3, 'VALIDE', '2026-07-21 11:05:45', '2026-07-21 11:10:46', 3);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
