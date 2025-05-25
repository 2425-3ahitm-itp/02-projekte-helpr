-- insert users
INSERT INTO helpr.u_user (username, password)
VALUES
('jakki', '$2a$12$Jk6ZXhV9jcIrCBxnKRs.3OwUNFzdjzaHXgqSP9EQdgPqxlam8i3bq'),
('johann_doe', '$2a$12$d4ChXyFGmow5ghkste8Y.u.sAEZiJmUs2V6DIckOVv784oOVovB1W'),
('jane_schmidt', '$2a$12$sqEkC799gnP3lUwJeVYJaePw4k7zM/AgqirazBxQqz1CgEFmtw9BG'),
('michael_wilson', '$2a$12$TBmyRIfKthWR2JMWhubXSOHa1.d3QqtevpNA0nidVs8Y1wCHl.AmK'),
('sarah_johannsen', '$2a$12$TBmyRIfKthWR2JMWhubXSOHa1.d3QqtevpNA0nidVs8Y1wCHl.AmK'),
('alex_braun', '$2a$12$fxePAjuMzhqQkPbbAciAPOeqp1jr3X8fNGeJuV9l1QXtV0aMxS3Ne');

-- insert tasks
INSERT INTO helpr.task (author_id, title, description, reward, effort, location, created_at)
VALUES
(1, 'Hilfe beim Möbeltransport', 'Brauche Hilfe, um ein Sofa und ein Bücherregal vom Wohnzimmer ins Schlafzimmer zu bringen', 25, 3, '1010 Wien', '2025-03-15 10:30:00'),
(2, 'Hundespaziergang am Wochenende', 'Suche jemanden, der meinen Hund am Samstag und Sonntag morgens ausführt', 30, 2, '5020 Salzburg', '2025-03-16 08:45:00'),
(3, 'Einkaufshilfe', 'Brauche Hilfe beim Einkaufen, da ich mich von einer Operation erhole', 20, 2, '4020 Linz', '2025-03-14 14:15:00'),
(1, 'Hilfe beim Computer-Setup', 'Benötige Unterstützung beim Einrichten meines neuen Computers und der Datenübertragung', 40, 4, '6020 Innsbruck', '2025-03-17 09:00:00'),
(4, 'Rasenmähen', 'Suche jemanden, der dieses Wochenende meinen Rasen mäht', 35, 3, '8010 Graz', '2025-03-16 16:20:00'),
(2, 'Aufbau eines Gartenzauns', 'Suche Unterstützung beim Aufbau eines Holzzauns', 150, 5, '3100 St. Pölten', '2025-04-10 11:00:00'),
(3, 'Nachhilfe in Mathematik', 'Biete 1 Stunde Nachhilfe, Gymnasium Niveau', 50, 1, '4020 Linz', '2025-03-20 17:30:00'),
(1, 'Fensterputzen dringend gesucht', 'Große Fensterfront muss noch vor Ostern gereinigt werden', 75, 2, '5020 Salzburg', '2025-03-13 12:00:00'),
(4, 'Möbelmontage Ikea', 'Zusammenbau mehrerer Ikea-Möbelstücke benötigt', 90, 4, '8010 Graz', '2025-03-18 10:00:00'),
(2, 'Fahrradreparatur', 'Brauche Hilfe beim Flicken meines Fahrrads', 25, 1, '6020 Innsbruck', '2025-03-19 14:00:00'),
(3, 'Wohnungsreinigung vor Umzug', 'Komplette Reinigung einer kleinen Wohnung', 120, 5, '4040 Linz', '2025-03-22 09:00:00'),
(1, 'Steuererklärung Unterstützung', 'Suche jemanden mit Erfahrung in österreichischem Steuerrecht', 200, 3, '1010 Wien', '2025-03-25 15:00:00'),
(4, 'Paketabholung bei Post', 'Brauche jemanden der Paket abholt', 10, 1, '5020 Salzburg', '2025-03-12 10:30:00'),
(2, 'Wand streichen', 'Wände in einem Zimmer weiß streichen', 80, 3, '8010 Graz', '2025-03-21 11:00:00');

-- insert applications
INSERT INTO helpr.application (user_id, task_id, created_at)
VALUES
(3, 1, '2025-03-15 11:45:00'),
(4, 1, '2025-03-15 12:30:00'),
(5, 2, '2025-03-16 09:10:00'),
(1, 3, '2025-03-14 15:00:00'),
(1, 4, '2025-03-17 10:15:00'),
(2, 5, '2025-03-16 17:00:00'),
(5, 3, '2025-03-14 16:30:00'),
(1, 5, '2025-03-16 18:45:00');
