-- insert users
INSERT INTO public.u_user (username, email, password)
VALUES
('johann_doe', 'johann@example.com', 'halloWelt1'),
('jane_schmidt', 'jane@example.com', 'halloWelt2'),
('michael_wilson', 'michael@example.com', 'halloWelt3'),
('sarah_johannsen', 'sarah@example.com', 'halloWelt4'),
('alex_braun', 'alex@example.com', 'halloWelt5');

-- insert tasks
INSERT INTO public.task (author_id, title, description, reward, effort, location, created_at)
VALUES
(1, 'Hilfe beim Möbeltransport', 'Brauche Hilfe, um ein Sofa und ein Bücherregal vom Wohnzimmer ins Schlafzimmer zu bringen', 25, 3, '10115 Berlin', '2025-03-15 10:30:00'),
(2, 'Hundespaziergang am Wochenende', 'Suche jemanden, der meinen Hund am Samstag und Sonntag morgens ausführt', 30, 2, '80331 München', '2025-03-16 08:45:00'),
(3, 'Einkaufshilfe', 'Brauche Hilfe beim Einkaufen, da ich mich von einer Operation erhole', 20, 2, '50667 Köln', '2025-03-14 14:15:00'),
(1, 'Hilfe beim Computer-Setup', 'Benötige Unterstützung beim Einrichten meines neuen Computers und der Datenübertragung', 40, 4, '70173 Stuttgart', '2025-03-17 09:00:00'),
(4, 'Rasenmähen', 'Suche jemanden, der dieses Wochenende meinen Rasen mäht', 35, 3, '01067 Dresden', '2025-03-16 16:20:00');

-- insert applications
INSERT INTO public.application (user_id, task_id, created_at)
VALUES
(3, 1, '2025-03-15 11:45:00'),
(4, 1, '2025-03-15 12:30:00'),
(5, 2, '2025-03-16 09:10:00'),
(1, 3, '2025-03-14 15:00:00'),
(1, 4, '2025-03-17 10:15:00'),
(2, 5, '2025-03-16 17:00:00'),
(5, 3, '2025-03-14 16:30:00'),
(1, 5, '2025-03-16 18:45:00');
