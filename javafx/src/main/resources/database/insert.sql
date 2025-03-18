-- insert users
INSERT INTO public.u_user (username, email, password)
VALUES
('john_doe', 'john@example.com', 'helloworld1'),
('jane_smith', 'jane@example.com', 'helloworld2'),
('mike_wilson', 'mike@example.com', 'hellorworld3'),
('sarah_johnson', 'sarah@example.com', 'hellorowlrd4'),
('alex_brown', 'alex@example.com', 'hellorowlrd5');

-- insert tasks
INSERT INTO public.task (author_id, title, description, reward, effort, location, created_at)
VALUES
(1, 'Help with moving furniture', 'Need help moving a couch and bookshelf from living room to bedroom', 25, 3, 'Downtown Area', '2025-03-15 10:30:00'),
(2, 'Dog walking this weekend', 'Looking for someone to walk my dog Saturday and Sunday mornings', 30, 2, 'Westside Park', '2025-03-16 08:45:00'),
(3, 'Grocery shopping assistance', 'Need help with grocery shopping as I recover from surgery', 20, 2, 'Northside Market', '2025-03-14 14:15:00'),
(1, 'Computer setup help', 'Need assistance setting up my new computer and transferring files', 40, 4, 'Southside Apartments', '2025-03-17 09:00:00'),
(4, 'Lawn mowing service', 'Looking for someone to mow my lawn this weekend', 35, 3, 'Eastside Neighborhood', '2025-03-16 16:20:00');

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