-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 07, 2024 at 08:18 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sportty`
--

-- --------------------------------------------------------

--
-- Table structure for table `abonnement`
--

CREATE TABLE `abonnement` (
  `id` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `prix` float NOT NULL,
  `description` varchar(255) NOT NULL,
  `id_user` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `abonnement`
--

INSERT INTO `abonnement` (`id`, `type`, `prix`, `description`, `id_user`) VALUES
(32, 'Basic', 61, 'Access to gym facilities during regular hours', NULL),
(33, 'Premium ', 120, 'Includes access to all gym facilities, including classes such as yoga, pilates, and spinning. Also includes one personal training session per month.', NULL),
(34, 'Student Membership', 40, 'Discounted membership for students with valid student ID. Includes access to gym facilities during off-peak hours.', NULL),
(35, 'Adaptive Fitness ', 60, ' Includes personalized fitness programs designed for individuals with ADHD and handicaps, access to all gym facilities including the pool, and exclusive classes led by experienced trainers in adaptive fitness.', NULL),
(36, 'Short-term', 156, 'Short-term membership for visitors or those looking for a temporary fitness solution. Includes access to gym facilities for one week.', NULL),
(37, 'Pool', 50, 'Pool class', NULL),
(38, 'Normal', 151, 'ffref', NULL),
(39, 'special', 80, 'ddddd', NULL),
(40, 'GIL', 588, 'ffr', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `abonnement_utilisateur`
--

CREATE TABLE `abonnement_utilisateur` (
  `id_abonnement` int(11) NOT NULL,
  `id_utilisateur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `abonnement_utilisateur`
--

INSERT INTO `abonnement_utilisateur` (`id_abonnement`, `id_utilisateur`) VALUES
(32, 63),
(32, 69),
(32, 99),
(32, 123),
(33, 63),
(33, 123),
(34, 53),
(34, 63),
(35, 63),
(35, 123),
(35, 136),
(36, 63),
(37, 63),
(37, 123),
(38, 63),
(39, 63);

-- --------------------------------------------------------

--
-- Table structure for table `cabine`
--

CREATE TABLE `cabine` (
  `id` int(11) NOT NULL,
  `nom_cabine` varchar(255) NOT NULL,
  `capacite` int(11) NOT NULL,
  `has_vr` tinyint(1) NOT NULL,
  `id_salle` int(11) DEFAULT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cabine`
--

INSERT INTO `cabine` (`id`, `nom_cabine`, `capacite`, `has_vr`, `id_salle`, `image`) VALUES
(21, 'spa', 121, 0, 23, 'Capture d\'écran 2024-02-23 152317.png'),
(23, 'sona', 45, 1, 8, ''),
(24, 'privee', 1, 1, 8, ''),
(25, 'jhegfdjhgedjh', 2121, 1, 23, ''),
(26, 'zzzzz', 2121, 1, 23, ''),
(27, 'zafaazf', 50, 1, 8, '');

-- --------------------------------------------------------

--
-- Table structure for table `cours`
--

CREATE TABLE `cours` (
  `id_cours` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `coach` varchar(255) NOT NULL,
  `jours` varchar(255) NOT NULL,
  `duree` int(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `prix` int(50) NOT NULL,
  `id_programme` int(255) DEFAULT NULL,
  `image` varchar(255) NOT NULL,
  `lienVideo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cours`
--

INSERT INTO `cours` (`id_cours`, `nom`, `coach`, `jours`, `duree`, `type`, `prix`, `id_programme`, `image`, `lienVideo`) VALUES
(11, 'cardio', 'mohamed', 'Tuesday', 45, 'Individuel', 70, 1, '', 'https://youtube.com/'),
(12, 'pilate', 'yassmine', 'Thursday', 30, 'individuel', 80, 3, 'allumer serveur.png', 'https://youtube.com/'),
(13, 'swimming', 'yassine', 'Monday', 30, 'In Groups', 70, 13, '', 'https://youtube.com/'),
(14, 'yoga', 'manel', 'Thursday', 60, 'In Groups', 80, 4, '', 'https://youtube.com/'),
(15, 'yoga', 'yassmine', 'Tuesday', 30, 'individuel', 70, 4, '', 'https://youtube.com/');

-- --------------------------------------------------------

--
-- Table structure for table `evenements`
--

CREATE TABLE `evenements` (
  `id_event` int(255) NOT NULL,
  `nom_event` varchar(255) NOT NULL,
  `descri_event` varchar(255) NOT NULL,
  `categorie_event` varchar(255) NOT NULL,
  `date_event` date NOT NULL,
  `lieu_event` varchar(255) NOT NULL,
  `nbr_par` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `evenements`
--

INSERT INTO `evenements` (`id_event`, `nom_event`, `descri_event`, `categorie_event`, `date_event`, `lieu_event`, `nbr_par`) VALUES
(17, 'Bodybuilding Seminar', 'Discover the secrets of effective bodybuilding with our experts.', 'Evénements spéciaux', '2024-02-29', 'Weight Room', 30),
(20, 'Yoga Class', 'Join us for a relaxing yoga session.', 'Sessions de yoga', '2024-03-08', 'Yoga Studio', 29),
(22, 'Fitness Challenge', 'Join our fitness challenge and push your limits!', 'Challenges et compétitions', '2024-02-22', 'Fitness room', 100);

-- --------------------------------------------------------

--
-- Table structure for table `materiel`
--

CREATE TABLE `materiel` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `categorie` varchar(255) NOT NULL,
  `qte` int(11) NOT NULL,
  `id_stock` int(11) NOT NULL,
  `image` varchar(255) NOT NULL,
  `video` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `materiel`
--

INSERT INTO `materiel` (`id`, `nom`, `categorie`, `qte`, `id_stock`, `image`, `video`) VALUES
(21, 'Treadmill', 'Cardio', 25, 28, 'tapie_roulanre.jpg', 'https://youtu.be/2oJ5UxT0X4o'),
(22, 'Yoga Ball', 'Yoga ', 5, 29, 'yogamat.jpg', ' https://youtu.be/NHArEeNEYCE'),
(25, 'Steps', 'Aérobic', 10, 30, 'steps.jpg', 'https://youtu.be/6yF-VaStT_E'),
(27, 'Roman chair', 'Musculation', 5, 28, '426287642_1164905504477961_1281380686367408238_n.jpg', 'https://www.youtube.com/watch?v=FsOUP7POQUc');

-- --------------------------------------------------------

--
-- Table structure for table `panier`
--

CREATE TABLE `panier` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `panier`
--

INSERT INTO `panier` (`id`, `id_user`) VALUES
(1, 0),
(135, 134);

-- --------------------------------------------------------

--
-- Table structure for table `panier_produit`
--

CREATE TABLE `panier_produit` (
  `panier_id` int(11) NOT NULL,
  `produit_id` int(11) NOT NULL,
  `quantite` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `panier_produit`
--

INSERT INTO `panier_produit` (`panier_id`, `produit_id`, `quantite`) VALUES
(0, 1, 3),
(0, 3, 1),
(0, 12, 1),
(0, 15, 1),
(135, 1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

CREATE TABLE `produit` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prix` float NOT NULL,
  `qte` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `categorie` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `produit`
--

INSERT INTO `produit` (`id`, `nom`, `prix`, `qte`, `description`, `categorie`, `image`) VALUES
(1, 'test', 200, 92, 'this is a cool supplement test', 'Supplement', ''),
(3, 'test 2 ', 800, 2, 'acceccories for test', 'Accessories', 'wheyAr.png'),
(9, 'nomProduct', 50, 97, 'Descriotpazfa zadaz azfaz azda', 'Accessories', ''),
(10, 'nomProduct2', 50, 0, 'zadazada zadazada zadaz azfaz ', 'Accessories', ''),
(11, 'nomProduct3', 50, 0, 'zadazada zadazada zadaz azfaz ', 'Accessories', ''),
(12, 'nomProduct9', 50, 6, 'zadazada zadazada zadaz azfaz ', 'Accessories', ''),
(13, 'nomProduct10', 50, 6, 'zadazada zadazada zadaz azfaz zadazada zadaz azfaz zadazada zadaz azfaz zadazada zadaz azfaz zadazada zadaz azfaz zadazada zadaz azfaz zadazada zadaz azfaz zadazada zadaz azfaz ', 'Accessories', ''),
(15, 'test', 58, 966, 'qsrqrq', 'Supplement', 'Screenshot 2023-12-15 172705.png');

-- --------------------------------------------------------

--
-- Table structure for table `programme`
--

CREATE TABLE `programme` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `duree` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `programme`
--

INSERT INTO `programme` (`id`, `nom`, `description`, `duree`) VALUES
(1, 'cardio training', 'cardiovascular training\nburns calories and constitutes\n your main management tool\n weight', 30),
(3, 'pilate', 'Improve amplitude\njoint and flexibility in\nworking on the different\n muscle tissue', 45),
(4, 'Flexibility and Balance', 'yoga to improve stability', 30),
(13, 'aquagym', 'It is a complete\n sport performed in a \ngentle manner which \ncombines muscle strengthening,\n endurance and cardiovascular \nwork exercises ', 40),
(14, 'zumba', 'Zumba is a complete physical\n training program, combining \nall the elements of fitness\n: cardio and muscle preparation, \nbalance and flexibility.', 30);

-- --------------------------------------------------------

--
-- Table structure for table `reclamation`
--

CREATE TABLE `reclamation` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `date` date DEFAULT NULL,
  `nbr_etoile` varchar(255) NOT NULL,
  `reponse` varchar(255) DEFAULT NULL,
  `statut` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reclamation`
--

INSERT INTO `reclamation` (`id`, `nom`, `id_user`, `description`, `date`, `nbr_etoile`, `reponse`, `statut`) VALUES
(23, 'Improvement Suggestions', 123, 'Add more lighting in the weightlifting area', '2024-02-29', 'Rate us 4/5', 'Thank you for your suggestion. we will take it into consideration', 'Finished'),
(24, 'Equipment Problem', 123, 'Equipment Problem', '2024-02-29', 'Rate us 1/5', 'Thank you for bringing this to our attention. We apologize for the inconvenience. Our maintenance team has been notified and will address the issue promptly.', 'In Progress'),
(25, 'Payment Issue', 123, 'Payment not processed', '2024-02-29', 'Rate us 1/5', 'tyutuyyu', 'Finished'),
(27, 'Discomfort in Facilities', 123, 'jklmlmlm+++++++++++++++++', '2024-03-03', 'Rate us 1/5', 'g(gt(gtgtygtt', 'In Progress'),
(28, 'Discomfort in Facilities', 123, 'ythyhyuhy', '2024-03-06', 'Rate us 3/5', NULL, 'en attente'),
(29, 'Discomfort in Facilities', 0, 'fggggg', '2024-03-07', 'Rate us 3/5', NULL, 'en attente');

-- --------------------------------------------------------

--
-- Table structure for table `sale_de_sport`
--

CREATE TABLE `sale_de_sport` (
  `id_salle` int(11) NOT NULL,
  `nom_salle` varchar(255) NOT NULL,
  `descr` varchar(255) NOT NULL,
  `lieu_salle` varchar(255) NOT NULL,
  `num_salle` int(255) NOT NULL,
  `lienVideo` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sale_de_sport`
--

INSERT INTO `sale_de_sport` (`id_salle`, `nom_salle`, `descr`, `lieu_salle`, `num_salle`, `lienVideo`, `image`, `location`) VALUES
(8, 'MayerGym', 'Salle bien equipée avec piscine', 'tabarka', 12345678, 'https://www.youtube.com/watch?v=1MugeX2WMhc&ab_channel=FitnessParkOfficiel', 'gym.jpg', '36.86663711811751, 10.18395387200595'),
(23, 'CaliforniaGym', 'Salle tres belle avec clim ', 'Sousse, Tunis', 12345678, 'https://www.youtube.com/watch?v=J1gSzu0IcBg&ab_channel=PERNELR%C3%A9alisateurPERNELR%C3%A9alisation', 'images (1).jpg', '36.87877753677053, 10.231521824095804'),
(26, 'WetherGym', 'salle de sport bien equipée', 'lafayette_tunisie', 45623198, 'https://www.youtube.com/watch?v=FI-ILCJ3vXs&ab_channel=FitnessParkOfficiel', 'name.jpg', '36.87397142584721, 10.268429019410775'),
(28, 'BeastGym', 'salle de sport speciale pour les etudiants ', 'ariana soghra ', 45469854, 'https://www.youtube.com/watch?v=rJRl4tonSpc&ab_channel=SALLEDESPORTPROGYMPERPIGNAN', 'téléchargement.jpg', '36.88893803271266, 10.165603856649533'),
(29, 'GalaxyGym', 'salle avec des coachs privee', 'passage_tunisie', 45456789, 'https://www.youtube.com/watch?v=Osd4DLpMNp4&ab_channel=Migsflicks', 'gymname.jpg', '36.85446924488728, 10.117023687932617'),
(32, 'testtt', 'azfa', 'gps', 12345678, 'https://www.youtube.com/watch?v=nQjngHwsjPs&ab_channel=3CIO', 'images (1).jpg', '36.85048579306997, 10.228603580745318');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `quantite` int(11) NOT NULL,
  `lieu` varchar(255) NOT NULL,
  `cordonnet` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`id`, `nom`, `quantite`, `lieu`, `cordonnet`) VALUES
(28, 'stock Cardio', 80, 'Gym', '36.795006303330574, 10.164352364609487'),
(29, 'Stock Yoga', 94, 'Gym', '36.77734019312343, 10.22778124272271'),
(30, 'stock Aérobic', 70, 'Gym', '36.7990876445632, 10.106475666402023');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `image_user` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nom`, `prenom`, `email`, `password`, `role`, `image_user`) VALUES
(44, 'mohamed1', 'mohamed', 'mohamed', '8fa1dddd53606ceb933c5c6a12e714ed41e11d37a2b7bc48e91d15b54171d033', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(45, 'Moncef', 'Gharbi', 'moncef@gharbi.com', '9ae9c2e36667de43db0653687de97c6befa7f0d2d50cdd91f4f6f302c7e817ca', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(49, 'nour', 'nour', 'nour', 'faf54c7b56a68006ed70ec1ce5157a9c3af79a958954270f686a12a0b3160e59', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(50, 'Admin', 'Admin', 'Admin@sporty.tn', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'ADMIN', 'Screenshot 2023-12-19 233302.png'),
(53, 'Dina', 'Gharbi1', 'Dina.gharbi11@esprit.tn', '9ae9c2e36667de43db0653687de97c6befa7f0d2d50cdd91f4f6f302c7e817ca', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(56, 'aa', 'aa', 'aa@opl.com', '9ae9c2e36667de43db0653687de97c6befa7f0d2d50cdd91f4f6f302c7e817ca', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(58, 'ddd', 'dddd', 'ddd@kk.com', '1a5aca6be3cf3e8322d61d1e4b05f446b7f8ab9cd93615c1e8be86414da0fad3', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(61, 'yassine', 'yassine', 'yassine@gmail.com', '9ae9c2e36667de43db0653687de97c6befa7f0d2d50cdd91f4f6f302c7e817ca', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(63, 'dina', 'gharbii', 'dina@sporty.tn', 'bcd95b39dbdc52326681630cf9fb9702ecf64c18270832f7a8d9fa07ee8a870f', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(64, 'dina', 'dina', 'dina@gmail.com', '9ae9c2e36667de43db0653687de97c6befa7f0d2d50cdd91f4f6f302c7e817ca', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(66, 'sou', 'sou', 'sou@gmail.com', 'e1ddebc8fe4599cdf4a89425d765ecbd440f47bae95ec9d873f6f7828ba24b04', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(67, 'nawel', 'nawel', 'nawel1@esprit.tn', '9ae9c2e36667de43db0653687de97c6befa7f0d2d50cdd91f4f6f302c7e817ca', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(68, 'dinna', 'dinna', 'dina1@gmail.com', '80a4109778cf5389682009f04031d6624b8298f150397196e4ffd4c1cab4c58d', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(69, 'Dina', 'gharbi', 'dina11@gmail.com', '9ae9c2e36667de43db0653687de97c6befa7f0d2d50cdd91f4f6f302c7e817ca', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(99, 'e', 'e', 'e@ez.com', 'bcd95b39dbdc52326681630cf9fb9702ecf64c18270832f7a8d9fa07ee8a870f', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(103, 'zaiter', 'zaiter', 'zaiter.m4@gmail.com', 'b03a8221555caacd3765aa8f667ee1de5bb274bf036f422e9a2221cff7207ff2', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(104, 'ooo', 'bhh', 'nawel.kaabi@esprit.tn', '9ae9c2e36667de43db0653687de97c6befa7f0d2d50cdd91f4f6f302c7e817ca', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(105, 'd', 'd', 'adem.aloui@esprit.tn', '9ae9c2e36667de43db0653687de97c6befa7f0d2d50cdd91f4f6f302c7e817ca', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(106, 'adem', 'aloui', 'ademaloui744@gmail.com', 'b6f9ccd94c59eaad5a2e6f16f54f7b1d57cb3fce28d47ac0e7bdc25323da1945', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(118, 'd', 'd', 'dinagharbi893@gmail.com', '9ae9c2e36667de43db0653687de97c6befa7f0d2d50cdd91f4f6f302c7e817ca', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(123, 'maher', 'mlaher', 'selmimaher42@gmail.com', 'b03a8221555caacd3765aa8f667ee1de5bb274bf036f422e9a2221cff7207ff2', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(126, 'oussema', 'ayadi', 'oussema@esprit.tn', 'fa152c23468964d502c997560f27bf664c4728f7d4639fb6f020c3bff9b82468', 'COACH', 'Screenshot 2023-12-19 233302.png'),
(127, 'din', 'aa', 'dina@esprit.tn', '9ae9c2e36667de43db0653687de97c6befa7f0d2d50cdd91f4f6f302c7e817ca', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(132, 'dina', 'gharbi', 'dina.gharbi@esprit.tn', '9ae9c2e36667de43db0653687de97c6befa7f0d2d50cdd91f4f6f302c7e817ca', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(133, 'test', 'yassine', 'yassine@esprit.tn', '85191860931153568faa1c4743ec3589a05168ad0e3f0ce9d2e1f7c842d6d48f', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(134, 'testt', 'testtt', 'testtest@gmail.com', '5f1a92931f1472ef6303ee6b6f320dd4c7b3bcf2cc3c3e8395372ff14538baa2', 'ADHERANT', 'Screenshot 2023-12-19 233302.png'),
(136, 'kaabi', 'nawel', 'kaabinawel9@gmail.com', 'b03a8221555caacd3765aa8f667ee1de5bb274bf036f422e9a2221cff7207ff2', 'ADHERANT', '426287642_1164905504477961_1281380686367408238_n.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `abonnement`
--
ALTER TABLE `abonnement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `abonnement_utilisateur`
--
ALTER TABLE `abonnement_utilisateur`
  ADD PRIMARY KEY (`id_abonnement`,`id_utilisateur`),
  ADD KEY `id_utilisateur` (`id_utilisateur`);

--
-- Indexes for table `cabine`
--
ALTER TABLE `cabine`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_salle_de_sport` (`id_salle`);

--
-- Indexes for table `cours`
--
ALTER TABLE `cours`
  ADD PRIMARY KEY (`id_cours`),
  ADD KEY `fk_id_programme` (`id_programme`);

--
-- Indexes for table `evenements`
--
ALTER TABLE `evenements`
  ADD PRIMARY KEY (`id_event`);

--
-- Indexes for table `materiel`
--
ALTER TABLE `materiel`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_stock` (`id_stock`);

--
-- Indexes for table `panier`
--
ALTER TABLE `panier`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `panier_produit`
--
ALTER TABLE `panier_produit`
  ADD PRIMARY KEY (`panier_id`,`produit_id`),
  ADD KEY `panier_id` (`panier_id`,`produit_id`),
  ADD KEY `produit_id_fk` (`produit_id`);

--
-- Indexes for table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `programme`
--
ALTER TABLE `programme`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reclamation`
--
ALTER TABLE `reclamation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `sale_de_sport`
--
ALTER TABLE `sale_de_sport`
  ADD PRIMARY KEY (`id_salle`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `abonnement`
--
ALTER TABLE `abonnement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `cabine`
--
ALTER TABLE `cabine`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `cours`
--
ALTER TABLE `cours`
  MODIFY `id_cours` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `evenements`
--
ALTER TABLE `evenements`
  MODIFY `id_event` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `materiel`
--
ALTER TABLE `materiel`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `panier`
--
ALTER TABLE `panier`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=136;

--
-- AUTO_INCREMENT for table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `programme`
--
ALTER TABLE `programme`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `reclamation`
--
ALTER TABLE `reclamation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `sale_de_sport`
--
ALTER TABLE `sale_de_sport`
  MODIFY `id_salle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=137;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `abonnement`
--
ALTER TABLE `abonnement`
  ADD CONSTRAINT `id_user_fk` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `abonnement_utilisateur`
--
ALTER TABLE `abonnement_utilisateur`
  ADD CONSTRAINT `abonnement_utilisateur_ibfk_1` FOREIGN KEY (`id_abonnement`) REFERENCES `abonnement` (`id`),
  ADD CONSTRAINT `abonnement_utilisateur_ibfk_2` FOREIGN KEY (`id_utilisateur`) REFERENCES `user` (`id`);

--
-- Constraints for table `cabine`
--
ALTER TABLE `cabine`
  ADD CONSTRAINT `id_salle_de_sport_fk` FOREIGN KEY (`id_salle`) REFERENCES `sale_de_sport` (`id_salle`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `cours`
--
ALTER TABLE `cours`
  ADD CONSTRAINT `fk_id_programme` FOREIGN KEY (`id_programme`) REFERENCES `programme` (`id`);

--
-- Constraints for table `materiel`
--
ALTER TABLE `materiel`
  ADD CONSTRAINT `id_stock_fk` FOREIGN KEY (`id_stock`) REFERENCES `stock` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
