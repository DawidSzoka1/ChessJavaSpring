use chess_db;

DELETE FROM figure_moves;
DELETE FROM figure;
DELETE FROM game;
DELETE FROM users_roles;
DELETE FROM role;
DELETE FROM user;


INSERT INTO role values
                     (1, 'ROLE_BASE'),
                     (2, 'ROLE_ADMIN');

INSERT INTO user (enable, id, ranking, password, username)
VALUES (true, 1, 25, '$2a$12$0h6ID5/jGD34HdDXIygwsuWhy0qUz/Bg7A1DCnnbHh4lJKWdcDPcK', 'jan_kowalski'),
       (false, 2, 30, '$2a$12$0h6ID5/jGD34HdDXIygwsuWhy0qUz/Bg7A1DCnnbHh4lJKWdcDPcK', 'anna_nowak'),
       (true, 3, 45, '$2a$12$0h6ID5/jGD34HdDXIygwsuWhy0qUz/Bg7A1DCnnbHh4lJKWdcDPcK', 'michal_adamczyk'),
       (false, 4, 22, '$2a$12$0h6ID5/jGD34HdDXIygwsuWhy0qUz/Bg7A1DCnnbHh4lJKWdcDPcK', 'kasia_kwiatkowska'),
       (true, 5, 35, '$2a$12$0h6ID5/jGD34HdDXIygwsuWhy0qUz/Bg7A1DCnnbHh4lJKWdcDPcK', 'tomasz_wisniewski'),
       (true, 6, 50, '$2a$12$0h6ID5/jGD34HdDXIygwsuWhy0qUz/Bg7A1DCnnbHh4lJKWdcDPcK', 'agnieszka_lewandowska'),
       (false, 7, 28, '$2a$12$0h6ID5/jGD34HdDXIygwsuWhy0qUz/Bg7A1DCnnbHh4lJKWdcDPcK', 'bartek_zielinski'),
       (true, 8, 40, '$2a$12$0h6ID5/jGD34HdDXIygwsuWhy0qUz/Bg7A1DCnnbHh4lJKWdcDPcK', 'joanna_dabrowska'),
       (true, 9, 33, '$2a$12$0h6ID5/jGD34HdDXIygwsuWhy0qUz/Bg7A1DCnnbHh4lJKWdcDPcK', 'mateusz_kaczmarek'),
       (false, 10, 27, '$2a$12$0h6ID5/jGD34HdDXIygwsuWhy0qUz/Bg7A1DCnnbHh4lJKWdcDPcK', 'aleksandra_pawlak');
INSERT INTO users_roles VALUES
                            (2, 1),
                            (1, 2),
                            (1, 3),
                            (1, 4),
                            (1, 5),
                            (1, 6),
                            (1, 7),
                            (1, 8),
                            (1, 9),
                            (1, 10);

INSERT INTO game (black, white, start, end, check_status, game_id, next_move, result) VALUES
                                                                                          (1, 2, TIMESTAMP '2024-03-01 15:30:00', TIMESTAMP '2024-03-01 16:15:00', 'N', 'GME12345AB', 'W', 'BLACK_WINS'),
                                                                                          (3, 4, TIMESTAMP '2024-03-02 18:00:00', TIMESTAMP '2024-03-02 18:45:00', 'N', 'GME67890CD', 'W', 'DRAW'),
                                                                                          (5, 6, TIMESTAMP '2024-03-03 20:10:00', TIMESTAMP '2024-03-03 21:00:00', 'N', 'GME54321EF', 'W', 'WHITE_WINS'),
                                                                                          (7, 8, TIMESTAMP '2024-03-05 11:15:00', TIMESTAMP '2024-03-05 12:05:00', 'N', 'GME98765GH', 'W', 'DRAW'),
                                                                                          (9, 10, TIMESTAMP '2024-03-06 14:40:00', TIMESTAMP '2024-03-06 15:20:00', 'N', 'GME19283IJ', 'W', 'DRAW'),
                                                                                          (2, 5, TIMESTAMP '2024-03-07 16:50:00', TIMESTAMP '2024-03-07 17:35:00', 'N', 'GME56789KL', 'W', 'DRAW'),
                                                                                          (4, 7, TIMESTAMP '2024-03-08 19:20:00', TIMESTAMP '2024-03-08 20:10:00', 'N', 'GME34567MN', 'W', 'WHITE_WINS'),
                                                                                          (6, 9, TIMESTAMP '2024-03-10 12:45:00', TIMESTAMP '2024-03-10 13:30:00', 'N', 'GME87654OP', 'W', 'BLACK_WINS'),
                                                                                          (8, 1, TIMESTAMP '2024-03-11 21:30:00', TIMESTAMP '2024-03-11 22:15:00', 'N', 'GME23456QR', 'W', 'DRAW'),
                                                                                          (10, 3, TIMESTAMP '2024-03-12 10:05:00', TIMESTAMP '2024-03-12 10:50:00', 'N', 'GME78901ST', 'W', 'WHITE_WINS');